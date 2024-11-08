package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityCreateUserBinding


class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCreateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.BackButton.setOnClickListener {
           finish()
       }
    }
}