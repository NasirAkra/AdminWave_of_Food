package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.AddItemAdapter
import com.example.adminwaveoffood.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BackButton.setOnClickListener {
            finish()
        }
        val menuFoodName= listOf("Burger","Sandwich","Momo","item","Sandwich","Momo")
        val menuFoodPrice= listOf("$5","$6","$7","$8","$9","$10")
        val menuFoodImage= listOf(R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu3,)
        val adapter=AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuFoodPrice), ArrayList(menuFoodImage))
        binding.MenuRecycleView.layoutManager=LinearLayoutManager(this)
        binding.MenuRecycleView.adapter=adapter




    }
}