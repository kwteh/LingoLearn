package com.example.lingolearn.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getStringExtra("product")
        binding.paymentProductTxt.text = product

        binding.paymentBackBtn.setOnClickListener {
            finish()
        }
    }
}