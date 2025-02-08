package com.luizmatias.workout_tracker.dto.common

import java.util.*

data class ErrorResponseDTO(
    val timestamp: Date = Date(),
    val status: Int,
    val error: String,
    val path: String,
    val extras: MutableMap<String, String?>? = null
)