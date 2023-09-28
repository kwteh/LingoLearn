package com.example.lingolearn.ui.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lingolearn.R
import com.example.lingolearn.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        val infoData = arrayOf(
            Info(getString(R.string.about_us_title1), getString(R.string.about_us_team), false),
            Info(getString(R.string.about_us_title2), getString(R.string.about_us_company), false),
            Info(getString(R.string.about_us_title3), getString(R.string.about_us_contact), true)
        )

        var page = 1

        binding.aboutUsBtnForward.setOnClickListener {
            page = (page % infoData.size) + 1
            var currentInfo = infoData[page - 1]

            binding.aboutUsTitleTxt.text = currentInfo.title
            binding.aboutUsContentTxt.text = currentInfo.content
            binding.aboutUsCallBtn.visibility = if (currentInfo.showButton) View.VISIBLE else View.INVISIBLE
            binding.aboutUsEmailBtn.visibility = if (currentInfo.showButton) View.VISIBLE else View.INVISIBLE
        }

        binding.aboutUsBtnBack.setOnClickListener {
            page = (page + infoData.size - 2) % infoData.size + 1
            val currentInfo = infoData[page - 1]

            binding.aboutUsTitleTxt.text = currentInfo.title
            binding.aboutUsContentTxt.text = currentInfo.content
            binding.aboutUsCallBtn.visibility = if (currentInfo.showButton) View.VISIBLE else View.INVISIBLE
            binding.aboutUsEmailBtn.visibility = if (currentInfo.showButton) View.VISIBLE else View.INVISIBLE
        }

        return binding.root
    }
}