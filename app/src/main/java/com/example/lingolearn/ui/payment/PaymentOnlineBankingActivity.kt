package com.example.lingolearn.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityPaymentOnlineBankingBinding

class PaymentOnlineBankingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPaymentOnlineBankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentOnlineBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentBankProceedBtn.setOnClickListener {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.paymentBankCancelBtn.setOnClickListener {
            finish()
        }
    }
}