package com.example.lingolearn.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.MainActivity
import com.example.lingolearn.databinding.ActivityLoginBinding
import com.example.lingolearn.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "LoginActivity"

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailEditTxt
            val password = binding.loginPasswordEditTxt

            val emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (email.text.isEmpty()) {
                email.error = "Email is required"
            } else if (!email.text.toString().matches(emailValidation.toRegex())) {
                email.error = "Invalid email address"
            } else if (password.text.isEmpty()) {
                password.error = "Password is required"
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener {
                    Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Incorrect email address or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.loginRegisterBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}