package com.project.domain.repository

import androidx.paging.PagingData
import com.project.domain.model.Character
import com.project.domain.model.CharacterFullDetails
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getPagedCharacters(query: String?): Flow<PagingData<Character>>
    fun getCharacterFullDetails(id: String): Flow<CharacterFullDetails>
}