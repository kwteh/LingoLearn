package com.example.lingolearn.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.lingolearn.R

class QuizLanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_language)

        val btnNext: Button = findViewById(R.id.btnNext)

        btnNext.setOnClickListener{
            val radGrpLanguage: RadioGroup = findViewById(R.id.radgroupLanguage)
            val checkedLanguage = radGrpLanguage.checkedRadioButtonId
            val intent = Intent(this, QuizActivity::class.java)

            if (checkedLanguage != -1) {
                val selected = findViewById<RadioButton>(checkedLanguage).text.toString()

                if (selected == getString(R.string.purchase_rad_english)) {
                    intent.putExtra("language", "English")
                }
                if (selected == getString(R.string.purchase_rad_chinese)) {
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