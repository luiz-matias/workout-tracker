package com.luizmatias.workout_tracker.config.api.validators

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(
    validatedBy = [com.luizmatias.workout_tracker.config.api.validators.StrongPasswordConstraintValidator::class],
)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
)
@Retention(
    AnnotationRetention.RUNTIME,
)
annotation class StrongPassword(
    val message: String = "Weak password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Any>> = [],
)
