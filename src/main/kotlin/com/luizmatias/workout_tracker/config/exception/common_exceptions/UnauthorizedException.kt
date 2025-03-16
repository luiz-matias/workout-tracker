package com.luizmatias.workout_tracker.config.exception.common_exceptions

class UnauthorizedException(
    override val message: String = "Unauthorized",
) : Throwable(message)
