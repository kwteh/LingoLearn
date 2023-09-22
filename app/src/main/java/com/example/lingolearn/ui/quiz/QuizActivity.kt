package com.example.lingolearn.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lingolearn.R

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val txt: TextView = findViewById(R.id.questionText)
        txt.text = intent.getStringExtra("language").toString()
    }
}