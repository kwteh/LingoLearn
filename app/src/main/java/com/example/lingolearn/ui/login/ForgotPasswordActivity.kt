package com.example.lingolearn.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.forgotPasswordBtn.setOnClickListener {
            val emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val email = binding.forgotPasswordEmailInput

            if (email.text.isEmpty()) {
                email.error = "Email is required"
            } else if (!email.text.toString().matches(emailValidation.toRegex())) {
                email.error = "Invalid email address"
            } else {
                auth.sendPasswordResetEmail(email.text.toString()).addOnSuccessListener {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("ForgotPasswordError", it.message.toString())
                }
            }
        }

        binding.forgotPasswordBackBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}