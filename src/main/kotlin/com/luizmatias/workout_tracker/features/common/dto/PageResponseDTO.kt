package com.luizmatias.workout_tracker.features.common.dto

data class PageResponseDTO<T>(
    val page: Int,
    val count: Int,
    val totalPages: Int,
    val totalCount: Int,
    val content: List<T>,
)
