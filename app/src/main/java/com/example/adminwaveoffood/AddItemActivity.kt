package com.example.adminwaveoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.example.adminwaveoffood.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class AddItemActivity : AppCompatActivity() {
    //All Item Details
    private lateinit var name:String
    private lateinit var price:String
    private lateinit var description:String
    private lateinit var ingredients:String
    private  var image:Uri?=null
    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding:ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Initialize Firebase
        auth= FirebaseAuth.getInstance()
        //Initialize database
        database= FirebaseDatabase.getInstance()
        binding.AdditemButton.setOnClickListener {
            name=binding.foodName.text.toString().trim()
            price=binding.foodPrice.text.toString().trim()
            description=binding.foodDescription.text.toString().trim()
            ingredients=binding.foodIngredient.text.toString().trim()
            if (!(name.isBlank()||price.isBlank()||description.isBlank()||ingredients.isBlank()))
            {
                uploadData()
                Toast.makeText(this, "Add Items Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()

            }

        }
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.BackButton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {
        //get the reference to the menu node in the database
        val menuRef=database.getReference("Menu")
        //Generate a unique key for the new item
        val newItemKey=menuRef.push().key
        if (image!=null)
        {
            val storageRef=FirebaseStorage.getInstance().reference
            val imageREf=storageRef.child("menu_images/ ${newItemKey}.jpg")
            val uploadTask=imageREf.putFile(image!!)
            uploadTask.addOnSuccessListener {
                imageREf.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //Create a new item
                    val newItem =AllMenu(
                        name=name,
                        price=price,
                        description=description,
                        ingredient = ingredients,
                        image= downloadUrl.toString(),

                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data Upload Successfully", Toast.LENGTH_SHORT).show()
                        }


                    }


                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Image Upload Failed ", Toast.LENGTH_SHORT).show()
                    }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "Data Not Uploaded", Toast.LENGTH_SHORT).show()
                }

        }


        else {
            Toast.makeText(this, "Please Selected Image", Toast.LENGTH_SHORT).show()

        }


    }

    private val pickImage=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri -> if(uri!=null)
    {
        binding.selectedImage.setImageURI(uri)
        image=uri
    }
    }
}