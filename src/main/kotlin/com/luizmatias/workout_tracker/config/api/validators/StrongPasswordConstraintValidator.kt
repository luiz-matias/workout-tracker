package com.luizmatias.workout_tracker.config.api.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.EnglishSequenceData
import org.passay.IllegalSequenceRule
import org.passay.LengthRule
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.passay.WhitespaceRule

class StrongPasswordConstraintValidator : ConstraintValidator<StrongPassword, String> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        val validator =
            PasswordValidator(
                mutableListOf(
                    LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH), // Should have between 8 and 50 characters
                    CharacterRule(EnglishCharacterData.UpperCase, 1), // Should have at least 1 uppercase digit
                    CharacterRule(EnglishCharacterData.LowerCase, 1), // Should have at least 1 lowercase digit
                    CharacterRule(EnglishCharacterData.Digit, 1), // Should have at least 1 digit
                    CharacterRule(EnglishCharacterData.Special, 1), // Should have at least 1 special digit (symbol)
                    WhitespaceRule(), // Should not have whitespaces
                    IllegalSequenceRule(
                        EnglishSequenceData.Alphabetical,
                        MAX_SEQUENCE_LENGTH,
                        false,
                    ), // Should not have numerical sequences (like abcde)
                    IllegalSequenceRule(
                        EnglishSequenceData.Numerical,
                        MAX_SEQUENCE_LENGTH,
                        false,
                    ), // Should not have numerical sequences (like 12345)
                    IllegalSequenceRule(
                        EnglishSequenceData.USQwerty,
                        MAX_SEQUENCE_LENGTH,
                        false,
                    ), // Should not have keyboard sequences (like qwerty)
                ),
            )

        val result = validator.validate(PasswordData(value))

        if (result.isValid) {
            return true
        }

        val messages = validator.getMessages(result)
        val messageTemplate = java.lang.String.join(", ", messages)
        context!!
            .buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 50
        private const val MAX_SEQUENCE_LENGTH = 3
    }
}
