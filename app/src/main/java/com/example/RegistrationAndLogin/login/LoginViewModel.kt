package com.example.RegistrationAndLogin.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.RegistrationAndLogin.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){

    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInprogress = mutableStateOf(false)
    var loginError = mutableStateOf(false)
    var errorMessage =""


    fun onEvent(navController: NavController, event: LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged ->{
                loginUIState.value= loginUIState.value.copy(
                    email = event.email

                )
                validateLoginUIDataWithRules()
            }

            is LoginUIEvent.PasswordChanged ->{
                loginUIState.value= loginUIState.value.copy(
                    password = event.password

                )
                validateLoginUIDataWithRules()

            }

            is LoginUIEvent.LoginButtonClicked ->{
                validateLoginUIDataWithRules()
                login(navController)

            }


        }
    }



    private fun validateLoginUIDataWithRules(){

        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )


        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,


            )

        if(emailResult.status && passwordResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value = false
        }


    }

    fun login(navController: NavController){
        loginInprogress.value=true

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.v("TAG", "Inside_login_success")
                Log.v("TAG","isSucccessful = ${it.isSuccessful}")
                loginInprogress.value = false
                if(it.isSuccessful) {
                    navController.navigate("MainScreen")
                }
            }
            .addOnFailureListener {
                Log.v("TAG", "Inside_login_failure")
                Log.v("TAG","Exception = ${it.message}")
                Log.v("TAG","Exception = ${it.localizedMessage}")
                errorMessage= it.localizedMessage
                loginError.value = true

            }


    }

    fun reLogin(navController: NavController){
        navController.navigate("LoginScreen")

    }







}