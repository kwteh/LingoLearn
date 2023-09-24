package com.example.lingolearn.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lingolearn.MainActivity
import com.example.lingolearn.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QuizWonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_won)

        val score: Int = intent.getIntExtra("score", -1)
        val scoreTxt : TextView = findViewById(R.id.scoreWinTxt)
        val txt = "Score: $score"

        scoreTxt.text = txt

        val returnBtn: Button = findViewById(R.id.returnBtn)
        returnBtn.setOnClickListener {
            val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("PassData")

            database.child("username").get().addOnSuccessListener { dataSnapshot ->
                val username = dataSnapshot.value.toString()
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("username", username)
                startActivity(intent)
                finish()

            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Failure: $exception", Toast.LENGTH_SHORT).show()
            }
        }
    }
}