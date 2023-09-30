package com.example.lingolearn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lingolearn.R
import com.example.lingolearn.ui.register.User

class ProfileAdapter(
    private val profileList: List<User>,
    private val onEditClick: (User) -> Unit
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameText: TextView = itemView.findViewById(R.id.item_profile_username_txt)
        private val nameText: TextView = itemView.findViewById(R.id.item_profile_name_txt)
        private val emailText: TextView = itemView.findViewById(R.id.item_profile_email_txt)

        private val editBtn: Button = itemView.findViewById(R.id.item_profile_edit_btn)

        fun bind(user: User) {
            usernameText.text = user.userName
            val name = "${user.fName} ${user.lName}"
            nameText.text = name
            emailText.text = user.email

            editBtn.setOnClickListener {
                onEditClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profileList[position]
        holder.bind(profile)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }
}