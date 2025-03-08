package com.luizmatias.workout_tracker.dto.common

data class PageResponseDTO<T>(
    val page: Int,
    val count: Int,
    val totalPages: Int,
    val totalCount: Int,
    val content: List<T>,
)
