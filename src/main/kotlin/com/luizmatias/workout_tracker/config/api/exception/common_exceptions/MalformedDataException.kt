package com.luizmatias.workout_tracker.config.api.exception.common_exceptions

class MalformedDataException(override val message: String = "Malformed data") : Throwable(message) {
}