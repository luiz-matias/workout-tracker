package com.luizmatias.workout_tracker.config.api.exception.common_exceptions

open class NotFoundException(
    override val message: String = "Resource not found",
) : Throwable(message)

class UserNotFoundException(
    override val message: String = "User not found",
) : NotFoundException(message)
