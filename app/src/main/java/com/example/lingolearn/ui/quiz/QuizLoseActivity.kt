package com.example.lingolearn.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.databinding.ActivityQuizLoseBinding

class QuizLoseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizLoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizLoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score: Int = intent.getIntExtra("score", -1)
        binding.scoreLoseTxt.text = "Score: $score"

        binding.tryAgainBtn.setOnClickListener{
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }
    }
}