package com.example.lingolearn.ui.admin.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lingolearn.databinding.FragmentProfileManagementBinding
import com.example.lingolearn.ui.adapter.ProfileAdapter
import com.example.lingolearn.ui.register.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileManagementFragment : Fragment() {
    private lateinit var binding: FragmentProfileManagementBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: ProfileAdapter
    private val profileList: MutableList<User> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileManagementBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("User")

        adapter = ProfileAdapter(profileList,
            onEditClick = {user -> editProfileActivity(user) }
        )

        binding.profileManagementRecyclerview.adapter = adapter
        binding.profileManagementRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        loadUserFromDatabase()
    }

    private fun loadUserFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profileList.clear()

                for (userSnapshot in snapshot.children) {
                    val fName = userSnapshot.child("fname").getValue(String::class.java)
                    val lName = userSnapshot.child("lname").getValue(String::class.java)
                    val phoneNo = userSnapshot.child("phoneNo").getValue(String::class.java)
                    val homeAddress = userSnapshot.child("homeAddress").getValue(String::class.java)
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val uid = userSnapshot.child("uid").getValue(String::class.java)
                    val userName = userSnapshot.child("userName").getValue(String::class.java)
                    val role = userSnapshot.child("role").getValue(String::class.java)

                    val user = User(fName, lName, phoneNo, homeAddress, email, uid, userName, role)
                    profileList.add(user)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                Log.e("ProfileManagementError", error.message)
            }
        })
    }

    private fun editProfileActivity(user: User) {
        val intent = Intent(requireContext(), ProfileEditActivity::class.java)
        intent.putExtra("uid", user.uid)
        startActivity(intent)
    }
}