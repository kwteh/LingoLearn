package com.example.lingolearn.ui.admin.quiz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.databinding.ActivityQuizEditBinding
import com.example.lingolearn.ui.quiz.Quiz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class QuizEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizEditBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("EnglishQuestion")

        val quizKey = intent.getStringExtra("quizKey") ?: ""

        database.child(quizKey).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val question = snapshot.child("text").getValue(String::class.java)
                val answers = snapshot.child("answers").getValue(object : GenericTypeIndicator<List<String>>() {})

                binding.quizEditQuestion.setText(question)
                binding.quizEditAns1.setText(answers?.getOrNull(0) ?: "")
                binding.quizEditAns2.setText(answers?.getOrNull(1) ?: "")
                binding.quizEditAns3.setText(answers?.getOrNull(2) ?: "")
                binding.quizEditAns4.setText(answers?.getOrNull(3) ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@QuizEditActivity, error.message, Toast.LENGTH_SHORT).show()
                Log.e("QuizEditError", error.message)
            }
        })

        binding.quizEditSubmitBtn.setOnClickListener {
            val question = binding.quizEditQuestion.text.toString()
            val ans1 = binding.quizEditAns1.text.toString()
            val ans2 = binding.quizEditAns2.text.toString()
            val ans3 = binding.quizEditAns3.text.toString()
            val ans4 = binding.quizEditAns4.text.toString()

            if (question.isEmpty() || ans1.isEmpty() || ans2.isEmpty() || ans3.isEmpty() || ans4.isEmpty()) {
                Toast.makeText(this, "Inputs cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                val editedQuiz = Quiz(text = question, answers = listOf(ans1, ans2, ans3, ans4))

                database.child(quizKey).setValue(editedQuiz).addOnSuccessListener {
                    Toast.makeText(this@QuizEditActivity, "Quiz updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this@QuizEditActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("QuizEditError", it.message.toString())
                }
            }
        }

        binding.quizEditCancelBtn.setOnClickListener {
            finish()
        }
    }
}