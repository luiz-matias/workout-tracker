package com.luizmatias.workout_tracker.config.api.validators

import com.luizmatias.workout_tracker.model.group.GroupMeasurementStrategy
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidGroupMeasurementStrategyConstraintValidator : ConstraintValidator<ValidGroupMeasurementStrategy, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return GroupMeasurementStrategy.entries.any { it.name == value }
    }
}