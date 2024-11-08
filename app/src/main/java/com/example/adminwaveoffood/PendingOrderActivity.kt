package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.PendingOrderAdapter
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout first
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set BackButton click listener
        binding.BackButton.setOnClickListener {
            finish()
        }

        // Sample data
        val pendingCustomersName = arrayListOf("Nasir", "Habib", "Waseem")
        val orderQuantity = arrayListOf("3", "4", "6")
        val orderFoodImages = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)

        // Set up the RecyclerView with a LinearLayoutManager and adapter
        val adapter = PendingOrderAdapter(pendingCustomersName, orderQuantity, this, orderFoodImages )
        binding.PendingorderRecycleView.layoutManager = LinearLayoutManager(this)
        binding.PendingorderRecycleView.adapter = adapter
    }
}
