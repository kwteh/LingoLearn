package com.example.lingolearn.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityPaymentEwalletBinding

class PaymentEWalletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentEwalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentEwalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val price = intent.getStringExtra("price")
        binding.paymentEwalletPriceTxt.text = price

        binding.paymentEwalletProceedBtn.setOnClickListener {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.paymentEwalletCancelBtn.setOnClickListener {
            finish()
        }
    }
}