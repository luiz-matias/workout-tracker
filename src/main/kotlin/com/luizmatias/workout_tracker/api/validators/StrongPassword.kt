package com.luizmatias.workout_tracker.api.validators

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [StrongPasswordConstraintValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class StrongPassword(
    val message: String = "Weak password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Any>> = []
)