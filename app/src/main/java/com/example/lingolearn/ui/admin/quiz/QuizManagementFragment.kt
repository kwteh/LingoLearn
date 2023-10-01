package com.example.lingolearn.ui.admin.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lingolearn.databinding.FragmentQuizManagementBinding
import com.example.lingolearn.ui.adapter.QuizAdapter
import com.example.lingolearn.ui.quiz.Quiz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class QuizManagementFragment : Fragment() {
    private lateinit var binding: FragmentQuizManagementBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: QuizAdapter
    private val quizList: MutableList<Quiz> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizManagementBinding.inflate(inflater, container, false)

        binding.quizManagementAddBtn.setOnClickListener {
            startActivity(Intent(requireContext(), QuizAddActivity::class.java))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("EnglishQuestion")

        adapter = QuizAdapter(quizList,
            onDeleteClick = { quiz -> showDeleteConfirmationDialog(quiz) },
            onEditClick = {quiz -> editQuizActivity(quiz) }
        )

        binding.quizManagementRecyclerview.adapter = adapter
        binding.quizManagementRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        loadQuizFromFirebase()
    }

    private fun loadQuizFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizList.clear()

                for (questionSnapshot in snapshot.children) {
                    val question = questionSnapshot.child("text").getValue(String::class.java)
                    val answers = questionSnapshot.child("answers").getValue(object : GenericTypeIndicator<List<String>>() {})

                    val quiz = Quiz(question, answers)
                    quizList.add(quiz)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                Log.e("QuizManagementError", error.message)
            }
        })
    }

    private fun showDeleteConfirmationDialog(quiz: Quiz) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete quiz")
        builder.setMessage("Are you sure you want to delete this quiz?")

        builder.setPositiveButton("Delete") { dialog, which ->
            val quizText = quiz.text

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (questionSnapshot in snapshot.children) {
                        val question = questionSnapshot.child("text").getValue(String::class.java)

                        if (question == quizText) {
                            questionSnapshot.ref.removeValue()
                            Toast.makeText(requireContext(), "Quiz deleted successfully", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to delete quiz: ${error.message}", Toast.LENGTH_SHORT).show()
                    Log.e("QuizManagementError", error.message)
                }
            })
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun editQuizActivity(quiz: Quiz) {
        val quizText = quiz.text
        var quizKey: String? = null

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (questionSnapshot in snapshot.children) {
                    val question = questionSnapshot.child("text").getValue(String::class.java)

                    if (question == quizText) {
                        quizKey = questionSnapshot.key
                        break
                    }
                }

                if (quizKey != null) {
                    val intent = Intent(requireContext(), QuizEditActivity::class.java)
                    intent.putExtra("quizKey", quizKey)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Quiz not found for editing", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to find quiz: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("QuizManagementError", error.message)
            }
        })
    }
}