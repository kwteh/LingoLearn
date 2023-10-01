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

        binding.paymentBankCancelBtn.setOnClickListener {
            finish()
        }
    }
}