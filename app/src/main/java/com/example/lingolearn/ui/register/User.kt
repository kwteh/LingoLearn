package com.example.lingolearn.ui.register

data class User(
    var fName: String ?= null,
    var lName : String ?= null,
    var phoneNo : String ?= null,
    var homeAddress : String ?= null,
    var email : String ?= null,
    var userName : String ?= null,
    var password : String ?= null
)