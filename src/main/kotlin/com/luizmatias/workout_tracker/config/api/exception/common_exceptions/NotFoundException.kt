package com.luizmatias.workout_tracker.config.api.exception.common_exceptions

class NotFoundException(override val message: String = "Resource not found"): Throwable(message) {
}