package com.project.domain.model

data class Character(
    val id: String,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: String,
    val homeworldName: String,
    val films: List<String>,
    val filmNames: List<String>,
    val species: List<String>,
    val speciesNames: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val url: String
)