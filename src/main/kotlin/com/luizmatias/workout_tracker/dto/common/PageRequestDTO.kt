package com.luizmatias.workout_tracker.dto.common

import jakarta.validation.constraints.Min
import java.beans.ConstructorProperties
import org.hibernate.validator.constraints.Range


data class PageRequestDTO @ConstructorProperties("page", "count", "sort", "order") constructor(
    @field:Min(1)
    val page: Int = 1,
    @field:Range(min = 1, max = 50)
    val count: Int = 10,
    val sort: String? = null,
    val order: String? = null,
)
