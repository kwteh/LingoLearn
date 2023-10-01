package com.example.lingolearn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lingolearn.R
import com.example.lingolearn.ui.quiz.Quiz

class QuizAdapter(
    private val quizList: List<Quiz>,
    private val onDeleteClick: (Quiz) -> Unit,
    private val onEditClick: (Quiz) -> Unit
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quizText: TextView = itemView.findViewById(R.id.item_quiz_question_text)
        private val quizAns1: TextView = itemView.findViewById(R.id.item_quiz_answer_1_txt)
        private val quizAns2: TextView = itemView.findViewById(R.id.item_quiz_answer_2_txt)
        private val quizAns3: TextView = itemView.findViewById(R.id.item_quiz_answer_3_txt)
        private val quizAns4: TextView = itemView.findViewById(R.id.item_quiz_answer_4_txt)

        private val deleteBtn: Button = itemView.findViewById(R.id.item_quiz_delete_btn)
        private val editBtn: Button = itemView.findViewById(R.id.item_quiz_edit_btn)

        fun bind(quiz: Quiz) {
            quizText.text = quiz.text
            quizAns1.text = quiz.answers!![0]
            quizAns2.text = quiz.answers!![1]
            quizAns3.text = quiz.answers!![2]
            quizAns4.text = quiz.answers!![3]

            deleteBtn.setOnClickListener {
                onDeleteClick(quiz)
            }

            editBtn.setOnClickListener {
                onEditClick(quiz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.bind(quiz)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}