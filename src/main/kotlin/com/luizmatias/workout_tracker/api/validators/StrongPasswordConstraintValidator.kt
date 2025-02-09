package com.luizmatias.workout_tracker.api.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.passay.*

class StrongPasswordConstraintValidator : ConstraintValidator<StrongPassword, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val validator = PasswordValidator(
            mutableListOf(
                LengthRule(8, 50), //Should have between 8 and 50 characters
                CharacterRule(EnglishCharacterData.UpperCase, 1), //Should have at least 1 uppercase digit
                CharacterRule(EnglishCharacterData.LowerCase, 1), //Should have at least 1 lowercase digit
                CharacterRule(EnglishCharacterData.Digit, 1), //Should have at least 1 digit
                CharacterRule(EnglishCharacterData.Special, 1), //Should have at least 1 special digit (symbol)
                WhitespaceRule(), //Should not have whitespaces
                IllegalSequenceRule(
                    EnglishSequenceData.Alphabetical,
                    3,
                    false
                ), //Should not have numerical sequences (like abcde)
                IllegalSequenceRule(
                    EnglishSequenceData.Numerical,
                    3,
                    false
                ), //Should not have numerical sequences (like 12345)
                IllegalSequenceRule(
                    EnglishSequenceData.USQwerty,
                    3,
                    false
                ) //Should not have keyboard sequences (like qwerty)
            )
        )

        val result = validator.validate(PasswordData(value))

        if (result.isValid) {
            return true
        }

        val messages = validator.getMessages(result)
        val messageTemplate = java.lang.String.join(", ", messages)
        context!!.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }
}