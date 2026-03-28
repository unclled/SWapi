package com.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponseDto<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)