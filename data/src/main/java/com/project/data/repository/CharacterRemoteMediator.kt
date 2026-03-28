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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

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
                    state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    (state.pages.sumOf { it.data.size } / 10) + 1
                }
            }

            val response = api.getCharacters(page = page, search = query)


            val enrichedEntities = coroutineScope {
                val planetCache = mutableMapOf<String, String>()

                response.results.map { dto ->
                    async {
                        val planetName = planetCache.getOrPut(dto.homeworld) {
                            runCatching { api.getPlanet(dto.homeworld).name }.getOrDefault("Unknown")
                        }

                        val filmTitles = dto.films.map { url ->
                            async { runCatching { api.getFilm(url).title }.getOrNull() }
                        }.awaitAll().filterNotNull()

                        val speciesNames = dto.species.map { url ->
                            async { runCatching { api.getSpecies(url).name }.getOrNull() }
                        }.awaitAll().filterNotNull()

                        dto.toEntity().copy(
                            homeworldName = planetName,
                            filmNames = filmTitles,
                            speciesNames = speciesNames
                        )
                    }
                }.awaitAll()
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }
                dao.insertCharacters(enrichedEntities)
            }

            MediatorResult.Success(endOfPaginationReached = response.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}