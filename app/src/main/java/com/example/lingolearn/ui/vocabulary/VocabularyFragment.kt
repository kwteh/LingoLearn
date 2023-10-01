package com.example.lingolearn.ui.vocabulary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lingolearn.databinding.FragmentVocabularyBinding
import com.example.lingolearn.ui.adapter.VocabularyAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class VocabularyFragment : Fragment(), OnInitListener {

    private lateinit var binding: FragmentVocabularyBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: VocabularyAdapter
    private lateinit var textToSpeech: TextToSpeech
    private val vocabularyList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVocabularyBinding.inflate(inflater, container, false)

        textToSpeech = TextToSpeech(requireContext(), this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("Vocabulary")

        adapter = VocabularyAdapter(vocabularyList,
            onPronounceClick = { vocabulary -> toSpeech(vocabulary) }
        )

        binding.vocabularyRecycler.adapter = adapter
        binding.vocabularyRecycler.layoutManager = LinearLayoutManager(requireContext())

        loadVocabularyFromDatabase()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "Missing language data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    private fun toSpeech(string: String) {
        textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null, null)
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
                Log.e("VocabularyError", error.message)
            }
        })
    }
}