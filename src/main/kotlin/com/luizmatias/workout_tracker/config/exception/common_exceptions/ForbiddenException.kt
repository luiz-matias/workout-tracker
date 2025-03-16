package com.luizmatias.workout_tracker.config.exception.common_exceptions

class ForbiddenException(
    override val message: String = "Forbidden access",
) : Throwable(message)
