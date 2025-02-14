package com.luizmatias.workout_tracker.config.api.validators

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ValidGroupMeasurementStrategyConstraintValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
)
@Retention(
    AnnotationRetention.RUNTIME,
)
annotation class ValidGroupMeasurementStrategy(
    val message: String = "Invalid group measurement strategy",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Any>> = [],
)
