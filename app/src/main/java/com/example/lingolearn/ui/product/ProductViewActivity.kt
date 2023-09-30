package com.example.lingolearn.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityProductViewBinding
import com.example.lingolearn.ui.payment.PaymentActivity

class ProductViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getStringExtra("product")
        if (product != null) {
            if (product == "pen"){
                binding.productViewTitleTxt.text = "Smart Dictionary Pen"
                binding.productViewImage.setImageResource(R.drawable.smart_pen)
                binding.productViewDescTxt.text = "The ultimate quiz book for family and friends - perfect for home entertainment and virtual pub quizzes."
                binding.productViewPriceTxt.text = "RM 199.00"
            }else if (product == "book"){
                binding.productViewTitleTxt.text = "The Big Quiz Book"
                binding.productViewImage.setImageResource(R.drawable.the_big_quiz_book)
                binding.productViewDescTxt.text = "The Smart Dictionary Pen enables users to build and save a personal vocabulary list. This feature allows you to keep track of the words you've looked up, making it an excellent tool for language learners aiming to expand their lexicon."
                binding.productViewPriceTxt.text = "RM 99.00"
            }
        } else {
            Toast.makeText(this, "Invalid Product", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.productViewBuyBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
            finish()
        }

        binding.productViewBackBtn.setOnClickListener {
            finish()
        }
    }
}