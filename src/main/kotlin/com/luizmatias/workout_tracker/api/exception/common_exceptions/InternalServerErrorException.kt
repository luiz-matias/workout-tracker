package com.luizmatias.workout_tracker.api.exception.common_exceptions

class InternalServerErrorException(override val message: String = "An internal server error has occurred.") :
    Throwable(message) {
}