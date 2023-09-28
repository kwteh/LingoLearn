package com.example.lingolearn.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.MainActivity
import com.example.lingolearn.databinding.ActivityQuizWonBinding

class QuizWonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizWonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizWonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score: Int = intent.getIntExtra("score", -1)

        binding.scoreWinTxt.text = "Score: $score"

        binding.returnBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}