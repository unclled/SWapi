package com.project.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.project.data.local.SwapiDatabase
import com.project.data.local.entity.CharacterEntity
import com.project.data.mapper.toEntity
import com.project.data.remote.api.SwapiApi
import java.util.concurrent.ConcurrentHashMap

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val api: SwapiApi,
    private val database: SwapiDatabase,
    private val query: String? = null
) : RemoteMediator<Int, CharacterEntity>() {

    private val dao = database.characterDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    (state.pages.sumOf { it.data.size } / 10) + 1
                }
            }

            val response = api.getCharacters(page = page, search = query)

            val baseEntities = response.results.map { dto ->
                dto.toEntity().copy(
                    homeworldName = "LOADING...",
                    filmNames = emptyList(),
                    speciesNames = emptyList()
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) dao.clearAll()
                dao.insertCharacters(baseEntities)
            }

            val uniquePlanets = response.results.map { it.homeworld }.distinct()
            val uniqueFilms = response.results.flatMap { it.films }.distinct()
            val uniqueSpecies = response.results.flatMap { it.species }.distinct()

            val localPlanetMap = fetchWithCache(uniquePlanets, planetCache) { api.getPlanet(it).name }
            val localFilmMap = fetchWithCache(uniqueFilms, filmCache) { api.getFilm(it).title }
            val localSpeciesMap = fetchWithCache(uniqueSpecies, speciesCache) { api.getSpecies(it).name }

            val enrichedEntities = response.results.map { dto ->
                dto.toEntity().copy(
                    homeworldName = localPlanetMap[dto.homeworld] ?: "Unknown",
                    filmNames = dto.films.mapNotNull { localFilmMap[it] },
                    speciesNames = dto.species.mapNotNull { localSpeciesMap[it] }
                )
            }

            database.withTransaction {
                dao.insertCharacters(enrichedEntities)
            }

            MediatorResult.Success(endOfPaginationReached = response.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchWithCache(
        urls: List<String>,
        cache: ConcurrentHashMap<String, String>,
        fetchBlock: suspend (String) -> String
    ): Map<String, String> {
        val resultMap = mutableMapOf<String, String>()

        for (url in urls) {
            val cachedValue = cache[url]
            if (cachedValue != null) {
                resultMap[url] = cachedValue
            } else {
                val fetchedValue = runCatching { fetchBlock(url) }.getOrNull()

                if (fetchedValue != null) {
                    cache[url] = fetchedValue
                    resultMap[url] = fetchedValue
                } else {
                    resultMap[url] = "Unknown"
                }
            }
        }
        return resultMap
    }

    companion object {
        private val planetCache = ConcurrentHashMap<String, String>()
        private val filmCache = ConcurrentHashMap<String, String>()
        private val speciesCache = ConcurrentHashMap<String, String>()
    }
}