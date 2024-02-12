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
import com.example.RegistrationAndLogin.CheckboxComponent
import com.example.RegistrationAndLogin.ClickableLoginTextComponent
import com.example.RegistrationAndLogin.DividerTextComponent
import com.example.RegistrationAndLogin.HeadingTextComponent
import com.example.RegistrationAndLogin.MyTextFieldComponent
import com.example.RegistrationAndLogin.NormalTextComponent
import com.example.RegistrationAndLogin.PasswordTextFieldComponent
import com.example.RegistrationAndLogin.signup.SignupUIEvent
import com.example.RegistrationAndLogin.signup.SignupViewModel
import com.example.movieapptest.R


@Composable
fun SignupScreen(navController: NavController, signupViewModel: SignupViewModel = viewModel()){
    val activity = (LocalContext.current as? Activity)
    BackHandler(enabled = true) {

        activity?.finish()
    }

    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val context = LocalContext.current.applicationContext
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingTextComponent(value = stringResource(id = R.string.create_account),30)
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        signupViewModel.onEvent(navController, SignupUIEvent.FirstNameChanged(it),context)
                    },
                    errorStates = signupViewModel.signupUIState.value.firstNameError

                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource(id = R.drawable.profile),
                    onTextSelected ={
                        signupViewModel.onEvent(navController, SignupUIEvent.LastNameChanged(it),context)
                    },
                    errorStates = signupViewModel.signupUIState.value.lastNameError

                )
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.message),
                    onTextSelected ={
                        signupViewModel.onEvent(navController, SignupUIEvent.EmailChanged(it),context)
                    },
                    errorStates = signupViewModel.signupUIState.value.emailError

                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.lock),
                    onTextSelected ={
                        signupViewModel.onEvent(navController, SignupUIEvent.PasswordChanged(it),context)
                    },
                    errorStatus = signupViewModel.signupUIState.value.passwordError
                )

                CheckboxComponent(
                    value = stringResource(id = R.string.term_and_conditions),
                    navController,
                    onCheckedChange = {
                        signupViewModel.onEvent(navController,
                            SignupUIEvent.PrivacyPolicyCheckBoxClicked(it),
                            context
                        )
                    }

                )
                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(value  = stringResource(id = R.string.register), onButtonClicked =
                {
                    signupViewModel.onEvent(navController, SignupUIEvent.RegisterButtonClicked,context)
                }, isEnabled = signupViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                ClickableLoginTextComponent(navController)
            }
        }

        if(signupViewModel.signUpInprogress.value) {
            CircularProgressIndicator()
        }
    }
}