package com.example.RegistrationAndLogin.signup

data class Users(

    var firstName:String="",
    var lastName:String="",
    var email:String="",
    var password:String="",
    var photoUrl:String="",
    var bookmark: ArrayList<String> =ArrayList<String>()

)
