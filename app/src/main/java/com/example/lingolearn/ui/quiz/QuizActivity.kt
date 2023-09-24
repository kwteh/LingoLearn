package com.example.lingolearn.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.lingolearn.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlin.math.min

class QuizActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private val quizList: MutableList<Quiz> = mutableListOf()

    private lateinit var currentQuiz: Quiz
    private lateinit var currentAnswers: MutableList<String>
    private var quizIndex = 0
    private var numQuiz = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val quesImage: ImageView = findViewById(R.id.questionImage)
        val language: String = intent.getStringExtra("language").toString()

        // Set Image for ImageView
        if (language == "Chinese"){
            quesImage.setImageResource(R.drawable.quiz_chinese)
            database = FirebaseDatabase.getInstance().getReference("ChineseQuestion")
        }
        else {
            quesImage.setImageResource(R.drawable.quiz_english)
            database = FirebaseDatabase.getInstance().getReference("EnglishQuestion")
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizList.clear()

                for (questionSnapshot in snapshot.children) {
                    val question = questionSnapshot.child("text").getValue(String::class.java)
                    val answers = questionSnapshot.child("answers").getValue(object : GenericTypeIndicator<List<String>>() {})

                    val quiz = Quiz(question, answers)
                    quizList.add(quiz)
                }
                numQuiz = min((quizList.size + 1) / 2, 5)

                randomizeQuiz()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast(error.message)
            }
        })

        val submitBtn: Button = findViewById(R.id.submitButton)
        submitBtn.setOnClickListener {
            val checkedId = findViewById<RadioGroup>(R.id.questionRadioGroup).checkedRadioButtonId

            if (checkedId != -1) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                if (currentAnswers[answerIndex] == currentQuiz.answers!![0]) {
                    quizIndex++
                    if(quizIndex < numQuiz) {
                        showToast("Correct!")
                        score +=100
                        currentQuiz = quizList[quizIndex]
                        setQuiz()
                    }
                    else {
                        score +=100
                        val intent = Intent(this, QuizWonActivity::class.java)
                        intent.putExtra("score", score)

                        showToast("Congratulations!")
                        startActivity(intent)
                        finish()
                    }
                }
                else {
                    val intent = Intent(this, QuizLoseActivity::class.java)
                    intent.putExtra("score", score)

                    showToast("Wrong!")
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun randomizeQuiz() {
        quizList.shuffle()
        quizIndex = 0
        setQuiz()
    }

    private fun setQuiz() {
        val quesTxt: TextView = findViewById(R.id.questionText)
        val firstAnsRad: RadioButton = findViewById(R.id.firstAnswerRadioButton)
        val secondAnsRad: RadioButton = findViewById(R.id.secondAnswerRadioButton)
        val thirdAnsRad: RadioButton = findViewById(R.id.thirdAnswerRadioButton)
        val fourthAnsRad: RadioButton = findViewById(R.id.fourthAnswerRadioButton)

        currentQuiz = quizList[quizIndex]
        currentAnswers = currentQuiz.answers!!.toMutableList()
        currentAnswers.shuffle()
        quesTxt.text = currentQuiz.text

        quesTxt.text = currentQuiz.text
        firstAnsRad.text = currentAnswers[0]
        secondAnsRad.text = currentAnswers[1]
        thirdAnsRad.text = currentAnswers[2]
        fourthAnsRad.text = currentAnswers[3]
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}