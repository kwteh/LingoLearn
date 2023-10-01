package com.example.lingolearn.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lingolearn.databinding.FragmentPaymentHistoryBinding
import com.example.lingolearn.ui.adapter.PaymentAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PaymentHistoryFragment : Fragment() {

    private lateinit var binding: FragmentPaymentHistoryBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: PaymentAdapter
    private var paymentList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)

        return binding.root

        loadPaymentFromDatabase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("PaymentHistory")

        adapter = PaymentAdapter(paymentList)

        binding.paymentHistoryRecycler.adapter = adapter
        binding.paymentHistoryRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadPaymentFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                paymentList.clear()

                for (paymentSnapshot in snapshot.children) {
                    paymentList.add(paymentSnapshot.getValue(String::class.java)!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                Log.e("PaymentHistoryError", error.message)
            }
        })
    }
}