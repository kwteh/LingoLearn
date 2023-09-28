package com.example.lingolearn.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lingolearn.databinding.ActivityRegisterBinding
import com.example.lingolearn.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "RegistrationActivity"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")

        binding.registerBtn.setOnClickListener {
            val username = binding.registerUsernameEditText
            val firstName = binding.registerFirstNameEditTxt
            val lastName = binding.registerLastNameEditTxt
            val phone = binding.registerPhoneEditTxt
            val address = binding.registerAddressEditTxt
            val email = binding.registerEmailEditTxt
            val password = binding.registerPasswordEditTxt
            val confirmPassword = binding.registerConfirmPasswordEditTxt

            val emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val phoneValidation = "^(\\+?6?01)[02-46-9][0-9]{7}\$|^(\\+?6?01)[1][-][0-9]{8}\$"
            val passwordValidation = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"

            if (username.text.isEmpty()) {
                username.error = "Username is required"
            } else if (firstName.text.isEmpty()) {
                firstName.error = "First name is required"
            } else if (lastName.text.isEmpty()) {
                lastName.error = "Last name is required"
            } else if (!phone.text.toString().matches(phoneValidation.toRegex())) {
                phone.error = "Invalid phone number"
            } else if (address.text.isEmpty()) {
                address.error = "Address is required"
            } else if (!email.text.toString().matches(emailValidation.toRegex())) {
                email.error = "Invalid email address"
            } else if (!password.text.toString().matches(passwordValidation.toRegex())) {
                password.error = "Password must contain at least 8 characters, including uppercase, lowercase, and digits"
            } else if (password.text.toString() != confirmPassword.text.toString()) {
                confirmPassword.error = "Passwords do not match"
            } else {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener {
                    val currentUser = auth.currentUser
                    val newUser = User(
                        fName = firstName.text.toString(),
                        lName = lastName.text.toString(),
                        phoneNo = phone.text.toString(),
                        homeAddress = address.text.toString(),
                        email = email.text.toString(),
                        uid = currentUser!!.uid,
                        userName = username.text.toString(),
                        role = "User")
                    database.child(newUser.uid.toString()).setValue(newUser).addOnSuccessListener {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                        Log.e("RegisterError", it.message.toString())
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("RegisterError", it.message.toString())
                }
            }
        }

        binding.registerLoginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}