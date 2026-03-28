package com.project.domain.usecase

import com.project.domain.model.Character
import com.project.domain.model.CharacterFullDetails
import com.project.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: String): Flow<CharacterFullDetails> {
        return repository.getCharacterFullDetails(id)
    }
}