package com.example.lingolearn.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
            var database : DatabaseReference
            val intent = Intent(this, MainActivity::class.java)
            var username : String

            database = FirebaseDatabase.getInstance().getReference("PassData")
            database.get().addOnSuccessListener {
                username = database.child("username").get().toString()
            }
            startActivity(intent)

            finish()
        }
    }
}