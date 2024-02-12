package com.example.RegistrationAndLogin.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.RegistrationAndLogin.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class SignupViewModel : ViewModel(){

    private val TAG = SignupViewModel::class.simpleName

    var signupUIState = mutableStateOf(SignupUIState())
    var allValidationsPassed = mutableStateOf(false)
    var signUpInprogress = mutableStateOf(false)

    fun onEvent(navController: NavController, event : SignupUIEvent,context: Context){

        when(event){
            is SignupUIEvent.FirstNameChanged ->{
                signupUIState.value = signupUIState.value.copy(
                    firstName = event.firstName
                )
                validateDataWithRules()
                printState()
            }
            is SignupUIEvent.LastNameChanged ->{
                signupUIState.value = signupUIState.value.copy(
                    lastName = event.lastName
                )
                validateDataWithRules()
                printState()
            }
            is SignupUIEvent.EmailChanged ->{
                signupUIState.value = signupUIState.value.copy(
                    email = event.email
                )
                validateDataWithRules()
                printState()
            }
            is SignupUIEvent.PasswordChanged ->{
                signupUIState.value = signupUIState.value.copy(
                    password = event.password
                )
                validateDataWithRules()
                printState()
            }
            is SignupUIEvent.PrivacyPolicyCheckBoxClicked ->{
                signupUIState.value = signupUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
                validateDataWithRules()
                printState()
            }
            is SignupUIEvent.RegisterButtonClicked ->{
                validateDataWithRules()
                signUp(navController,context)
            }
        }

    }

    private fun signUp(navController: NavController,context: Context){
        Log.v("TAG", "Inside_printState")
        printState()

        //validateDataWithRules()
        createUserInFirebase(
            navController,
            firstName = signupUIState.value.firstName,
            lastName = signupUIState.value.lastName,
            email= signupUIState.value.email,
            password= signupUIState.value.password,
            context = context
        )

    }

    private fun validateDataWithRules(){
        val fNameResult = Validator.validateFirstName(
            fName = signupUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = signupUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = signupUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = signupUIState.value.password
        )

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = signupUIState.value.privacyPolicyAccepted
        )

        Log.d("TAG","privacyPolicyAcceptance = $privacyPolicyResult")

        signupUIState.value = signupUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status



        )

        if(fNameResult.status && lNameResult.status && emailResult.status && passwordResult.status&&privacyPolicyResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value = false
        }


    }



    private fun printState(){
        Log.v("TAG", "Inside_printState")
        Log.v("TAG",signupUIState.value.toString())
    }


    private fun createUserInFirebase(navController: NavController, firstName: String,lastName: String,email:String, password:String,context: Context){

        signUpInprogress.value = true

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.v("TAG", "Inside_OnCompleteListener")
                Log.v("TAG","isSucccessful = ${it.isSuccessful}")
                signUpInprogress.value = false
                if(it.isSuccessful) {
                    addDataToFirebase(firstName,lastName,email,password,context)
                    navController.navigate("MainScreen")
                }
            }
            .addOnFailureListener {
                Log.v("TAG", "Inside_OnFailureListener")
                Log.v("TAG","Exception = ${it.message}")
                Log.v("TAG","Exception = ${it.localizedMessage}")
                signUpInprogress.value = true
            }
    }

    fun addDataToFirebase(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        context: Context
    ) {

        val uId: MutableLiveData<String> = MutableLiveData()
        FirebaseAuth.getInstance().currentUser?.also { it.uid?.also{uid-> uId.value = uid } }
        // on below line creating an instance of firebase firestore.
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        //creating a collection reference for our Firebase Firestore database.
        val dbUsers: CollectionReference = db.collection("Users")
        //adding our data to our courses object class.
        //val users = Users(firstName, lastName, email,password)
        val user= Users(firstName, lastName, email, password)

        //below method is use to add data to Firebase Firestore.
       val userRef = dbUsers.document(uId.value.toString()).set(user).addOnSuccessListener {
            // after the data addition is successful
            // we are displaying a success toast message.
            Toast.makeText(
                context,
                "Your Course has been added to Firebase Firestore",
                Toast.LENGTH_SHORT
            ).show()

        }.addOnFailureListener { e ->
            // this method is called when the data addition process is failed.
            // displaying a toast message when data addition is failed.
            Toast.makeText(context, "Fail to add course \n$e", Toast.LENGTH_SHORT).show()
        }

    }



}