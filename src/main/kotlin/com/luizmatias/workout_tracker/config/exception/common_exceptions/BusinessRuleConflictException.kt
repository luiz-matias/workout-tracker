package com.luizmatias.workout_tracker.config.exception.common_exceptions

class BusinessRuleConflictException(
    override val message: String = "Business rule conflict",
) : Throwable(message)
