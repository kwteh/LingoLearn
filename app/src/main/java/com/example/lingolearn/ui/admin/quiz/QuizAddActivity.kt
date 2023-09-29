package com.example.lingolearn.ui.admin.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lingolearn.databinding.ActivityQuizAddBinding
import com.example.lingolearn.ui.quiz.Quiz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizAddBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("EnglishQuestion")

        binding.quizAddSubmitBtn.setOnClickListener {
            val question = binding.quizAddQuestion.text.toString()
            val ans1 = binding.quizAddAns1.text.toString()
            val ans2 = binding.quizAddAns2.text.toString()
            val ans3 = binding.quizAddAns3.text.toString()
            val ans4 = binding.quizAddAns4.text.toString()

            val newQuiz = Quiz(text = question, answers = listOf(ans1, ans2, ans3, ans4))

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usedKeys = snapshot.children.mapNotNull { it.key?.toIntOrNull() }.toSortedSet()
                    val availableGap = (0 until usedKeys.size).firstOrNull { it !in usedKeys }
                    val newKey = availableGap ?: usedKeys.size

                    // Set the new data with the found or next available key
                    database.child(newKey.toString()).setValue(newQuiz)
                        .addOnSuccessListener {
                            // Quiz added successfully
                            Toast.makeText(this@QuizAddActivity, "Quiz added successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            // Error occurred while adding quiz
                            Toast.makeText(this@QuizAddActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("QuizAddError", it.message.toString())
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@QuizAddActivity, error.message, Toast.LENGTH_SHORT).show()
                    Log.e("QuizAddError", error.message)
                }
            })

        }

        binding.quizAddCancelBtn.setOnClickListener {
            finish()
        }
    }
}