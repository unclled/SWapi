package com.project.data.mapper

import com.project.domain.model.Character
import com.project.data.local.entity.CharacterEntity
import com.project.data.remote.dto.CharacterDto
import com.project.domain.model.CharacterFullDetails

fun CharacterDto.toEntity(): CharacterEntity {
    val extractedId = url.split("/").last { it.isNotEmpty() }

    return CharacterEntity(
        id = extractedId,
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,

        homeworldUrl = homeworld,
        filmsUrls = films,
        speciesUrls = species,
        vehiclesUrls = vehicles,
        starshipsUrls = starships,

        homeworldName = null,
        filmNames = emptyList(),
        speciesNames = emptyList(),

        created = created,
        edited = edited,
        url = url
    )
}

fun CharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,

        homeworld = homeworldUrl,
        films = filmsUrls,
        species = speciesUrls,
        vehicles = vehiclesUrls,
        starships = starshipsUrls,

        homeworldName = homeworldName ?: "Unknown",
        filmNames = filmNames,
        speciesNames = speciesNames,

        url = url
    )
}

fun CharacterEntity.toFullDetails(): CharacterFullDetails {
    return CharacterFullDetails(
        character = this.toDomain(),

        homeworldName = this.homeworldName ?: "Unknown",
        films = this.filmNames,
        species = this.speciesNames.ifEmpty { listOf("N/A") }
    )
}