package com.example.RegistrationAndLogin.signup

data class SignupUIState (

    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var privacyPolicyAccepted: Boolean = false,

    var firstNameError: Boolean = false,
    val lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var privacyPolicyError: Boolean = false
)