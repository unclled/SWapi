package com.project.domain.usecase

import androidx.paging.PagingData
import com.project.domain.model.Character
import com.project.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(query: String? = null): Flow<PagingData<Character>> {
        return repository.getPagedCharacters(query)
    }
}