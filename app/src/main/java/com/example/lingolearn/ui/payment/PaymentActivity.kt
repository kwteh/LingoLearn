package com.example.lingolearn.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.lingolearn.R
import com.example.lingolearn.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getStringExtra("product")
        if (product != null) {
            if (product == "pen") {
                binding.paymentProductTxt.text = "Smart Dictionary Pen"
                binding.paymentProductImage.setImageResource(R.drawable.smart_pen)
                binding.paymentPriceTxt.text = "RM 199.00"
            }
            else if (product == "book") {
                binding.paymentProductTxt.text = "The Big Quiz Book"
                binding.paymentProductImage.setImageResource(R.drawable.the_big_quiz_book)
                binding.paymentPriceTxt.text = "RM 99.00"
            }
        } else {
            Toast.makeText(this, "Invalid Product", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.paymentProceedBtn.setOnClickListener {
            val paymentMethod = binding.paymentRadgroup.checkedRadioButtonId

            if (paymentMethod != -1) {
                val selected = findViewById<RadioButton>(paymentMethod).text.toString()

                if (selected == getString(R.string.payment_e_wallet)) {
                    val intent = Intent(this, PaymentEWalletActivity::class.java)
                    intent.putExtra("price", binding.paymentPriceTxt.text.toString())
                    startActivity(intent)
                    finish()
                }
                if (selected == getString(R.string.payment_online_banking)) {
                    val intent = Intent(this, PaymentOnlineBankingActivity::class.java)
                    intent.putExtra("price", binding.paymentPriceTxt.text.toString())
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }

        binding.paymentBackBtn.setOnClickListener {
            finish()
        }
    }
}