package com.example.lingolearn.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lingolearn.databinding.FragmentProfileBinding
import com.example.lingolearn.ui.register.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        val usernameIn: EditText = binding.usernameEdit
        val roleIn: EditText = binding.roleEdit
        val firstnameIn: EditText = binding.firstnameEdit
        val lastnameIn: EditText = binding.lastnameEdit
        val emailIn: EditText = binding.emailEdit
        val phoneIn: EditText = binding.phoneEdit
        val addressIn: EditText = binding.addressEdit

        val editBtn: Button = binding.editProfileBtn
        val saveBtn: Button = binding.saveProfileBtn

        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            usernameIn.setText(it.child("userName").value.toString())
            roleIn.setText(it.child("role").value.toString())
            firstnameIn.setText(it.child("fname").value.toString())
            lastnameIn.setText(it.child("lname").value.toString())
            emailIn.setText(it.child("email").value.toString())
            phoneIn.setText(it.child("phoneNo").value.toString())
            addressIn.setText(it.child("homeAddress").value.toString())
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            Log.e("ProfileError", it.message.toString())
        }

        editBtn.setOnClickListener {
            firstnameIn.isEnabled = true
            lastnameIn.isEnabled = true
            usernameIn.isEnabled = true
            phoneIn.isEnabled = true
            addressIn.isEnabled = true
            saveBtn.visibility = View.VISIBLE
            editBtn.visibility = View.GONE
            Toast.makeText(requireContext(), "Editing Profile", Toast.LENGTH_SHORT).show()
        }

        saveBtn.setOnClickListener {
            firstnameIn.isEnabled = false
            lastnameIn.isEnabled = false
            usernameIn.isEnabled = false
            phoneIn.isEnabled = false
            addressIn.isEnabled = false
            saveBtn.visibility = View.GONE
            editBtn.visibility = View.VISIBLE

            database = FirebaseDatabase.getInstance().getReference("User")
            database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                val updatedUser = User(
                    fName = firstnameIn.text.toString(),
                    lName = lastnameIn.text.toString(),
                    phoneNo = phoneIn.text.toString(),
                    homeAddress = addressIn.text.toString(),
                    email = emailIn.text.toString(),
                    uid = auth.currentUser!!.uid,
                    userName = usernameIn.text.toString(),
                    role = roleIn.text.toString()
                )
                database.child(auth.currentUser!!.uid).setValue(updatedUser).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("ProfileError", it.message.toString())
                }
            }
        }

        return binding.root
    }
}