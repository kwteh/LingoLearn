package com.example.lingolearn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lingolearn.R

class VocabularyManagementAdapter(
    private val vocabularyList: List<String>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<VocabularyManagementAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vocabularyTxt: TextView = itemView.findViewById(R.id.item_vocabulary_management_txt)

        private val deleteBtn: Button = itemView.findViewById(R.id.item_vocabulary_management_delete)

        fun bind(vocabulary: String) {
            vocabularyTxt.text = vocabulary

            deleteBtn.setOnClickListener {
                onDeleteClick(vocabulary)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyManagementAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary_management, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VocabularyManagementAdapter.ViewHolder, position: Int) {
        val vocabulary = vocabularyList[position]
        holder.bind(vocabulary)
    }

    override fun getItemCount(): Int {
        return vocabularyList.size
    }
}