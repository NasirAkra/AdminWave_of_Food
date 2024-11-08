package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.deliveryAdapter
import com.example.adminwaveoffood.databinding.ActivityOutForDeliveryBinding
class OutForDeliveryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOutForDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOutForDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BackButton.setOnClickListener {
            finish()
        }

        val customersName= arrayListOf("Nasir","Habib","Waseem")
        val moneyStatues= arrayListOf("Received","Pending","Not Received")
        val adapter=deliveryAdapter(customersName,moneyStatues )
        binding.deliveryRecycleView.layoutManager=LinearLayoutManager(this)
        binding.deliveryRecycleView.adapter=adapter


    }
}