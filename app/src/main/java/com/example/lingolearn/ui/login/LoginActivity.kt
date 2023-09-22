package com.example.lingolearn.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lingolearn.MainActivity
import com.example.lingolearn.R
import com.example.lingolearn.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var userName = ""
    private var password = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tfUsername: EditText = findViewById(R.id.tfUsername)
        val tfPassword: EditText = findViewById(R.id.tfPassword)
        val btnRegister: Button = findViewById(R.id.btnSignUp)
        val btnSignIn: Button = findViewById(R.id.btnLogin)

        btnSignIn.setOnClickListener() {

            val userName: String = tfUsername.text.toString()
            val password: String = tfPassword.text.toString()

            readData(userName, password)

        }

        btnRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun readData(tfUsername: String, tfPassword: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(tfUsername).get().addOnSuccessListener {
            if (it.exists()) {
                val password = it.child("password").value

                if(tfPassword == password){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", tfUsername)
                    val v: EditText = findViewById(R.id.tfUsername)
                    val currentTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    val formatted = currentTime.format(formatter)

                    val logRec = LoginRecord(tfUsername, formatted)

                    database = FirebaseDatabase.getInstance().getReference("LoginRecord")
                    database.child(tfUsername).setValue(logRec).addOnSuccessListener {
                        Toast.makeText(this, "Welcome Back, ${tfUsername}", Toast.LENGTH_SHORT).show()
                    }
                    updateData(v.text.toString())

                    startActivity(intent)



                }else{
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()

            }

        }.addOnFailureListener {
            Toast.makeText(this, "Username does not exists!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateData(x: String){
        var database : DatabaseReference
        database = FirebaseDatabase.getInstance().getReference("PassData")

        val up = mapOf<String,String>(
            "username" to x,

            )
        database.updateChildren(up)

        val parentLayout: View = findViewById(android.R.id.content)
        val Snackbar = Snackbar.make(parentLayout, "refer success !", Snackbar.LENGTH_SHORT)
        Snackbar.show()
    }
}