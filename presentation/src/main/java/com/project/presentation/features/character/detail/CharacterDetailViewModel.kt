package com.project.presentation.features.character.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.project.domain.model.CharacterFullDetails
import com.project.domain.usecase.GetSingleCharacterUseCase
import com.project.presentation.navigation.CharacterDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    private val characterId = savedStateHandle.toRoute<CharacterDetailRoute>().characterId

    val state: StateFlow<DetailState> = getCharacterUseCase(characterId)
        .map<CharacterFullDetails, DetailState> { character ->
            DetailState.Success(character)
        }
        .catch { emit(DetailState.Error(it.message ?: "DATABASE ERROR")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailState.Loading
        )
}

sealed interface DetailState {
    object Loading : DetailState
    data class Success(val data: CharacterFullDetails) : DetailState
    data class Error(val message: String) : DetailState
}