package com.project.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object CharactersListRoute

@Serializable
data class CharacterDetailRoute(
    val characterId: String
)