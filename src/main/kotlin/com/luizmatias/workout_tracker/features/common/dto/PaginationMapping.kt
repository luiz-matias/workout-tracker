package com.luizmatias.workout_tracker.features.common.dto

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

fun <T, R> Page<T>.toPageResponseDTO(mapper: (T) -> R): PageResponseDTO<R> =
    PageResponseDTO(
        page = this.number + 1,
        count = this.count(),
        totalPages = this.totalPages,
        totalCount = this.totalElements.toInt(),
        content = this.content.map(mapper),
    )

fun PageRequestDTO.toPageRequest(): PageRequest =
    if (this.sort != null) {
        PageRequest.of(
            this.page - 1,
            this.count,
            Sort.by(
                if (this.order ==
                    "desc"
                ) {
                    Sort.Direction.DESC
                } else {
                    Sort.Direction.ASC
                },
                this.sort,
            ),
        )
    } else {
        PageRequest.of(this.page - 1, this.count)
    }
