package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityAdminProfileBinding


class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAdminProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BackButton.setOnClickListener {
            finish()
        }

        binding.name.isEnabled=false
        binding.address.isEnabled=false
        binding.phone.isEnabled=false
        binding.email.isEnabled=false
        binding.password.isEnabled=false
        binding.editButtton.setOnClickListener {
            binding.name.isEnabled=true
            binding.address.isEnabled=true
            binding.phone.isEnabled=true
            binding.email.isEnabled=true
            binding.password.isEnabled=true

        }

    }
}