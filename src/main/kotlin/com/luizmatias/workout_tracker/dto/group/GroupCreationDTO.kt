package com.luizmatias.workout_tracker.dto.group

import com.luizmatias.workout_tracker.config.api.validators.ValidGroupMeasurementStrategy
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class GroupCreationDTO(
    @field:NotBlank
    @field:Size(min = 2, max = 255)
    val name: String,
    @field:Size(min = 2, max = 255)
    val description: String?,
    @field:Size(min = 3, max = 100)
    val bannerUrl: String?,
    @field:ValidGroupMeasurementStrategy
    val measurementStrategy: String,
)
