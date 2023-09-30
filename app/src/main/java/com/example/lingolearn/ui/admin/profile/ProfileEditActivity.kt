package com.example.lingolearn.ui.admin.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lingolearn.databinding.ActivityProfileEditBinding
import com.example.lingolearn.ui.register.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("User")

        val uid = intent.getStringExtra("uid") ?: ""

        database.child(uid).get().addOnSuccessListener {
            binding.profileEditEmail.setText(it.child("email").value.toString())
            binding.profileEditRole.setText(it.child("role").value.toString())
            binding.profileEditUsername.setText(it.child("userName").value.toString())
            binding.profileEditFname.setText(it.child("fname").value.toString())
            binding.profileEditLname.setText(it.child("lname").value.toString())
            binding.profileEditPhone.setText(it.child("phoneNo").value.toString())
            binding.profileEditAddress.setText(it.child("homeAddress").value.toString())
        }.addOnFailureListener {
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            Log.e("ProfileEditError", it.message.toString())
        }

        binding.profileEditSubmitBtn.setOnClickListener {
            database.child(uid).get().addOnSuccessListener {
                val fName = binding.profileEditFname.text.toString()
                val lName = binding.profileEditLname.text.toString()
                val phoneNo = binding.profileEditPhone.text.toString()
                val homeAddress = binding.profileEditAddress.text.toString()
                val email = it.child("email").value.toString()
                val uid = it.child("uid").value.toString()
                val userName = binding.profileEditUsername.text.toString()
                val role = binding.profileEditRole.text.toString()

                val editedUser = User(fName, lName, phoneNo, homeAddress, email, uid, userName, role)

                database.child(uid).setValue(editedUser).addOnSuccessListener {
                    Toast.makeText(this, "Profile edited successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("ProfileEditError", it.message.toString())
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("ProfileEditError", it.message.toString())
            }
        }

        binding.profileEditCancelBtn.setOnClickListener {
            finish()
        }
    }
}