package com.example.adminwaveoffood

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.selectImage.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        binding.BackButton.setOnClickListener {
            finish()
        }

    }
    private val pickImage=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri -> if(uri!=null)
    {
        binding.selectedImage.setImageURI(uri)
    }
    }
}