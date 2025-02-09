package com.luizmatias.workout_tracker.api.exception.common_exceptions

class BusinessRuleConflictException(override val message: String = "Business rule conflict") : Throwable(message) {
}