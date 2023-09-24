package com.example.lingolearn.ui.profile

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lingolearn.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val usernameIn: EditText = findViewById(R.id.usernameEdit)
        val roleIn: EditText = findViewById(R.id.roleEdit)
        val firstnameIn: EditText = findViewById(R.id.firstnameEdit)
        val lastnameIn: EditText = findViewById(R.id.lastnameEdit)
        val emailIn: EditText = findViewById(R.id.emailEdit)
        val phoneIn: EditText = findViewById(R.id.phoneEdit)
        val addressIn: EditText = findViewById(R.id.addressEdit)

        val editBtn: Button = findViewById(R.id.editProfileBtn)
        val saveBtn: Button = findViewById(R.id.saveProfileBtn)

        database = FirebaseDatabase.getInstance().getReference("PassData")
        database.child("username").get().addOnSuccessListener { dataSnapshot ->
            val username = dataSnapshot.value.toString()
            usernameIn.setText(username)

            database = FirebaseDatabase.getInstance().getReference("User")
            database.child(username).get().addOnSuccessListener {
                roleIn.setText(it.child("role").value.toString())
                firstnameIn.setText(it.child("fname").value.toString())
                lastnameIn.setText(it.child("lname").value.toString())
                emailIn.setText(it.child("email").value.toString())
                phoneIn.setText(it.child("phoneNo").value.toString())
                addressIn.setText(it.child("homeAddress").value.toString())

            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Failure: $exception", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Failure: $exception", Toast.LENGTH_SHORT).show()
        }

        editBtn.setOnClickListener {
            firstnameIn.isEnabled = true
            lastnameIn.isEnabled = true
            emailIn.isEnabled = true
            phoneIn.isEnabled = true
            addressIn.isEnabled = true
            saveBtn.visibility = View.VISIBLE
            editBtn.visibility = View.GONE
        }

        saveBtn.setOnClickListener {
            firstnameIn.isEnabled = false
            lastnameIn.isEnabled = false
            emailIn.isEnabled = false
            phoneIn.isEnabled = false
            addressIn.isEnabled = false
            saveBtn.visibility = View.GONE
            editBtn.visibility = View.VISIBLE
        }
    }
}