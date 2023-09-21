package com.example.lingolearn.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lingolearn.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private var emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var phoneNumberValidation = "^(\\+?6?01)[02-46-9][0-9]{7}\$|^(\\+?6?01)[1][-][0-9]{8}\$"
    private var passwordValidation = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegisterNow : Button = findViewById(R.id.btnRegisterNow)

        btnRegisterNow.setOnClickListener(){

            val fName = findViewById<EditText>(R.id.editTextTextFirstName).text.toString()
            val lName = findViewById<EditText>(R.id.editTextTextLastName).text.toString()
            val phoneNo = findViewById<EditText>(R.id.editTextPhone).text.toString()
            val homeAddress = findViewById<EditText>(R.id.editTextTextHomeAddress).text.toString()
            val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val userName = findViewById<EditText>(R.id.editTextUsername).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextNewPassword).text.toString()
            val passwordConfirmation = findViewById<EditText>(R.id.editTextTextNewPasswordConfirmation).text.toString()

            val newUser = User( fName,lName, phoneNo, homeAddress, email, userName, password)

            emailValidationCheck(email)
            phoneNoValidationCheck(phoneNo)
            passwordValidationCheck(password)

            if(password != passwordConfirmation){
                Toast.makeText(this,"Password Inconsistent !", Toast.LENGTH_SHORT).show()
                onPause()
            }else {

                database = FirebaseDatabase.getInstance().getReference("User")
                database.child(userName).get().addOnSuccessListener {
                    if (it.exists()) {
                        val userName = it.child("userName").value
                        Toast.makeText(this, "Username Already Exists !", Toast.LENGTH_SHORT).show()
                        onPause()
                    } else {
                        database = FirebaseDatabase.getInstance().getReference("User")
                        database.child(userName).setValue(newUser).addOnSuccessListener {

                            Toast.makeText(this, "Successfully Register!", Toast.LENGTH_SHORT)
                                .show()

                        }.addOnFailureListener { exception ->
                            Toast.makeText(this, "Failure 1: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Failure 2: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun passwordValidationCheck(password: String) {
        if(password.matches(passwordValidation.toRegex())){

        }else{
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            onStop()
        }
    }


    private fun phoneNoValidationCheck(phoneNo: String) {
        if(phoneNo.matches(phoneNumberValidation.toRegex())){

        }else{
            Toast.makeText(this, "Invalid Phone Number !", Toast.LENGTH_SHORT).show()
            onPause()
        }
    }

    private fun emailValidationCheck(email: String) {
        if(email.matches(emailValidation.toRegex())){

        }else{
            Toast.makeText(this, "Invalid Email Address !", Toast.LENGTH_SHORT).show()
            onPause()
        }
    }
}