package com.example.lingolearn.ui.payment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.databinding.ActivityPaymentOnlineBankingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentOnlineBankingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPaymentOnlineBankingBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentOnlineBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("PaymentHistory")
        auth = FirebaseAuth.getInstance()

        val price = intent.getStringExtra("price")

        binding.paymentBankProceedBtn.setOnClickListener {
            val name = binding.paymentBankNameIn
            val bank = binding.paymentBankBankIn
            val bankAcc = binding.paymentBankBankAccIn
            val pac = binding.paymentBankPacIn

            val bankAccPattern = "^\\d{12}$"
            val pacPattern = "^\\d{6}$"

            if (name.text.toString().isEmpty()) {
                name.error = "Please enter your name"
            } else if (bank.text.toString().isEmpty()) {
                bank.error = "Please enter your bank"
            } else if (!bankAcc.text.toString().matches(bankAccPattern.toRegex())) {
                bankAcc.error = "Invalid bank acc (xxxxxxxxxxxx)"
            } else if (!pac.text.toString().matches(pacPattern.toRegex())) {
                pac.error = "Invalid PAC (xxxxxx)"
            } else {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val paymentHistory = "${dateFormat.format(Date())}: OnlineBanking: $price"
                val paymentHistoryRef = database.child(auth.currentUser!!.uid).push()

                paymentHistoryRef.setValue(paymentHistory).addOnSuccessListener {
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("PaymentOnlineBankingError", it.message.toString())
                }
            }
        }

        binding.paymentBankCancelBtn.setOnClickListener {
            finish()
        }
    }
}