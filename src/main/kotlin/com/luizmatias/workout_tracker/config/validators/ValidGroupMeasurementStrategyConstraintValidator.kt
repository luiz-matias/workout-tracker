package com.luizmatias.workout_tracker.config.validators

import com.luizmatias.workout_tracker.features.group.model.GroupMeasurementStrategy
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidGroupMeasurementStrategyConstraintValidator : ConstraintValidator<ValidGroupMeasurementStrategy, String> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean = GroupMeasurementStrategy.entries.any { it.name == value }
}
