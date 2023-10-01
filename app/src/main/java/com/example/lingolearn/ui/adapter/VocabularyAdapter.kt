package com.example.lingolearn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lingolearn.R

class VocabularyAdapter(
    private val vocabularyList: List<String>,
    private val onPronounceClick: (String) -> Unit
) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vocabularyTxt: TextView = itemView.findViewById(R.id.item_vocabulary_txt)

        private val pronounceBtn: Button = itemView.findViewById(R.id.item_vocabulary_pronounce)

        fun bind(vocabulary: String) {
            vocabularyTxt.text = vocabulary

            pronounceBtn.setOnClickListener {
                onPronounceClick(vocabulary)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vocabulary = vocabularyList[position]
        holder.bind(vocabulary)
    }

    override fun getItemCount(): Int {
        return vocabularyList.size
    }
}