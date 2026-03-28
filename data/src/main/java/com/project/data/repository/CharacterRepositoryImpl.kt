package com.project.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.project.data.local.SwapiDatabase
import com.project.domain.model.Character
import com.project.data.mapper.toDomain
import com.project.data.mapper.toFullDetails
import com.project.data.remote.api.SwapiApi
import com.project.domain.model.CharacterFullDetails
import com.project.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: SwapiApi,
    private val database: SwapiDatabase
) : CharacterRepository {
    private val dao = database.characterDao

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedCharacters(query: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                api = api,
                database = database,
                query = query
            ),
            pagingSourceFactory = {
                dao.getPagedCharacters(query)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun getCharacterFullDetails(id: String): Flow<CharacterFullDetails> {
        return dao.getCharacterByIdFlow(id)
            .filterNotNull()
            .map { it.toFullDetails() }
    }
}