package com.luizmatias.workout_tracker.features.group.dto

import com.luizmatias.workout_tracker.config.validators.ValidGroupMeasurementStrategy
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class GroupCreationDTO(
    @field:NotBlank
    @field:Size(min = 2, max = 255)
    val name: String,
    @field:Size(min = 2, max = 255)
    val description: String?,
    val bannerUrl: String?,
    @field:ValidGroupMeasurementStrategy
    val measurementStrategy: String,
)
