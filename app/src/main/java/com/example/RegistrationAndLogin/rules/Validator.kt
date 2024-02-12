package com.example.RegistrationAndLogin.rules

object Validator {

    fun validateFirstName(fName:String):ValidationResult{
        return ValidationResult(
            (!fName.isNullOrEmpty()&& fName.length>=4)
        )

    }

    fun validateLastName(lName:String): ValidationResult {
        return ValidationResult(
            (!lName.isNullOrEmpty()&& lName.length>=2)
        )
    }

    fun validateEmail(email:String): ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty()&& email.length>=4)
        )
    }

    fun validatePassword(password:String): ValidationResult{
        return ValidationResult(
            (!password.isNullOrEmpty()&& password.length>=4)
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue:Boolean): ValidationResult{
        return ValidationResult(
            statusValue
        )
    }
}

data class ValidationResult(
    val status :Boolean = false
)