package com.example.lingolearn.ui.admin.vocabulary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lingolearn.databinding.FragmentVocabularyManagementBinding
import com.example.lingolearn.ui.adapter.VocabularyManagementAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VocabularyManagementFragment : Fragment() {

    private lateinit var binding: FragmentVocabularyManagementBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: VocabularyManagementAdapter
    private val vocabularyList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyManagementBinding.inflate(inflater, container, false)

        binding.vocabularyManagementAddBtn.setOnClickListener {
            addVocabulary(binding.vocabularyAddTxt.text.toString())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("Vocabulary")

        adapter = VocabularyManagementAdapter(vocabularyList,
            onDeleteClick = {vocabulary -> showDeleteConfirmationDialog(vocabulary)})

        binding.vocabularyManagementRecyclerview.adapter = adapter
        binding.vocabularyManagementRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        loadVocabularyFromDatabase()
    }

    private fun loadVocabularyFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                vocabularyList.clear()

                for (vocabularySnapshot in snapshot.children) {
                    vocabularyList.add(vocabularySnapshot.getValue(String::class.java)!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                Log.e("VocabularyManagementError", error.message)
            }
        })
    }

    private fun addVocabulary(vocabulary: String) {
        if (!vocabulary.isEmpty()) {
            vocabularyList.add(vocabulary)
            database.setValue(vocabularyList).addOnSuccessListener {
                Toast.makeText(requireContext(), "Vocabulary added successfully", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("QuizAddError", it.message.toString())
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a vocabulary", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog(vocabulary: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete vocabulary")
        builder.setMessage("Are you sure you want to delete this vocabulary?")

        builder.setPositiveButton("Delete") { dialog, which ->

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (vocabularySnapshot in snapshot.children) {
                        val vocabularyTarget = vocabularySnapshot.getValue(String::class.java)

                        if (vocabularyTarget == vocabulary) {
                            vocabularySnapshot.ref.removeValue()
                            Toast.makeText(requireContext(), "Vocabulary deleted successfully", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to delete vocabulary: ${error.message}", Toast.LENGTH_SHORT).show()
                    Log.e("VocabularyManagementError", error.message)
                }
            })
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}