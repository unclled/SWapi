package com.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,

    val homeworldUrl: String,
    val filmsUrls: List<String>,
    val speciesUrls: List<String>,
    val vehiclesUrls: List<String>,
    val starshipsUrls: List<String>,

    val homeworldName: String? = null,
    val filmNames: List<String> = emptyList(),
    val speciesNames: List<String> = emptyList(),

    val created: String,
    val edited: String,
    val url: String
)