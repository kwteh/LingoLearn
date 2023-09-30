package com.example.lingolearn.ui.product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lingolearn.R
import com.example.lingolearn.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        val intent = Intent(requireContext(), ProductViewActivity::class.java)
        binding.productPenImage.setOnClickListener {
            intent.putExtra("product", "pen")
            startActivity(intent)
        }

        binding.productBookImage.setOnClickListener {
            intent.putExtra("product", "book")
            startActivity(intent)
        }

        return binding.root
    }
}