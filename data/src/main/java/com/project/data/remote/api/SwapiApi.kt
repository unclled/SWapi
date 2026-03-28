package com.project.data.remote.api

import com.project.data.remote.dto.CharacterDto
import com.project.data.remote.dto.FilmDto
import com.project.data.remote.dto.PaginatedResponseDto
import com.project.data.remote.dto.PlanetDto
import com.project.data.remote.dto.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface SwapiApi {
    @GET("people/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null
    ): PaginatedResponseDto<CharacterDto>

    @GET("people/{id}/")
    suspend fun getCharacterById(
        @Path("id") id: String
    ): CharacterDto

    @GET
    suspend fun getPlanet(@Url url: String): PlanetDto

    @GET
    suspend fun getFilm(@Url url: String): FilmDto

    @GET
    suspend fun getSpecies(@Url url: String): SpeciesDto
}