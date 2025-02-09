package com.luizmatias.workout_tracker.api.exception.common_exceptions

class UnauthorizedException(override val message: String = "Unauthorized") : Throwable(message) {
}