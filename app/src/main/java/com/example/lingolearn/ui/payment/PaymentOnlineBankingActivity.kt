package com.example.lingolearn.ui.payment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingolearn.databinding.ActivityPaymentOnlineBankingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentOnlineBankingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPaymentOnlineBankingBinding
    private lateinit var database: DatabaseReference
    private val paymentList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentOnlineBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("PaymentHistory")

        val price = intent.getStringExtra("price")

        binding.paymentBankProceedBtn.setOnClickListener {
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    paymentList.clear()

                    for (paymentSnapshot in snapshot.children) {
                        paymentList.add(paymentSnapshot.getValue(String::class.java)!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@PaymentOnlineBankingActivity, error.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("PaymentEWalletError", error.message)
                }
            })

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val paymentHistory = "${dateFormat.format(Date())}: OnlineBanking: $price"

            paymentList.add(paymentHistory)

            database.setValue(paymentList)

            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.paymentBankCancelBtn.setOnClickListener {
            finish()
        }
    }
}