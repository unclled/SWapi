package com.project.domain.model

data class CharacterFullDetails(
    val character: Character,
    val homeworldName: String,
    val films: List<String>,
    val species: List<String>
)