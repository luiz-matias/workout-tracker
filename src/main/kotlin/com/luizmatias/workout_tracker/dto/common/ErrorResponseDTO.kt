package com.luizmatias.workout_tracker.dto.common

import java.time.Instant

data class ErrorResponseDTO(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val path: String,
    val extras: MutableMap<String, String?>? = null,
)
