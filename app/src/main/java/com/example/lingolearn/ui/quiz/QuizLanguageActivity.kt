package com.example.lingolearn.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityQuizLanguageBinding

class QuizLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener{
            val radGrpLanguage: RadioGroup = binding.radgroupLanguage
            val checkedLanguage = radGrpLanguage.checkedRadioButtonId

            if (checkedLanguage != -1) {
                val selected = findViewById<RadioButton>(checkedLanguage).text.toString()
                val intent = Intent(this, QuizActivity::class.java)

                if (selected == getString(R.string.quiz_rad_english)) {
                    intent.putExtra("language", "English")
                }
                if (selected == getString(R.string.quiz_rad_chinese)) {
                    intent.putExtra("language", "Chinese")
                }
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }

    }
}