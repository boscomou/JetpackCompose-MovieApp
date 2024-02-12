package com.example.RegistrationAndLogin.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.RegistrationAndLogin.ButtonComponent
import com.example.RegistrationAndLogin.ClickableLoginTextComponent
import com.example.RegistrationAndLogin.DividerTextComponent
import com.example.RegistrationAndLogin.ErrorDialog
import com.example.RegistrationAndLogin.HeadingTextComponent
import com.example.RegistrationAndLogin.MyTextFieldComponent
import com.example.RegistrationAndLogin.NormalTextComponent
import com.example.RegistrationAndLogin.PasswordTextFieldComponent
import com.example.RegistrationAndLogin.UnderLinedNormalTextComponent
import com.example.RegistrationAndLogin.login.LoginUIEvent
import com.example.RegistrationAndLogin.login.LoginViewModel
import com.example.movieapptest.R

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    val activity = (LocalContext.current as? Activity)
    BackHandler(enabled = true) {

        activity?.finish()
    }
    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)

        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id =  R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome),30)
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextSelected ={
                        loginViewModel.onEvent(navController, LoginUIEvent.EmailChanged(it))
                    },
                    errorStates = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected ={
                        loginViewModel.onEvent(navController, LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(40.dp))
                UnderLinedNormalTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(navController, LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value

                )
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                ClickableLoginTextComponent(navController= navController,tryingToLogin = false)
            }
        }
        if(loginViewModel.loginInprogress.value) {

            CircularProgressIndicator()
        }
        if(loginViewModel.loginError.value){
            ErrorDialog(
                loginViewModel.errorMessage,
                loginError = {
                    loginViewModel.loginError.value=it
                    loginViewModel.loginInprogress.value = it
                })
            loginViewModel.reLogin(navController)
        }
    }

}