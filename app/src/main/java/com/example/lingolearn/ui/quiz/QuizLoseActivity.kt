package com.example.lingolearn.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lingolearn.R

class QuizLoseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_lose)

        val score: Int = intent.getIntExtra("score", -1)
        val scoreTxt : TextView = findViewById(R.id.scoreLoseTxt)
        val txt = "Score: $score"

        scoreTxt.text = txt

        val tryAgainBtn : Button = findViewById(R.id.tryAgainBtn)
        tryAgainBtn.setOnClickListener{
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}