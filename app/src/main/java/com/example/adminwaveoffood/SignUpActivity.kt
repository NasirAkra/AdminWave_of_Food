package com.example.adminwaveoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding
import com.example.adminwaveoffood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username:String
    private lateinit var restunant:String

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize firebase auth
        auth=Firebase.auth

        //initialize firebase database
        database=Firebase.database.reference



        binding.CreateButton.setOnClickListener {

            //get text from edittext
            email=binding.email.text.toString().trim()
            password=binding.password.text.toString().trim()
            username=binding.name.text.toString().trim()
            restunant=binding.ResturantName.text.toString().trim()
            
            if (email.isBlank()||password.isBlank()||username.isBlank()||restunant.isBlank())
            {
                Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show()

            }
            else
            {
                createAccount(email,password)

            }




        }
        binding.AlreadyHaveButton.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val listOfLocation= arrayOf("Pakpattan","Arifwala","Sahiwal","Okara")
        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listOfLocation)
        val autoCompleteTextView=binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
            if (task.isSuccessful)
            {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount",task.exception)
            }
        }

    }
    // Save Data into FireBase Database

    private fun saveUserData() {
        email=binding.email.text.toString().trim()
        password=binding.password.text.toString().trim()
        username=binding.name.text.toString().trim()
        restunant=binding.ResturantName.text.toString().trim()
        val user=UserModel(email,password,username,restunant)
        val userID=FirebaseAuth.getInstance().currentUser!!.uid
        // Save data into firebase Database
        
        database.child("User").child(userID).setValue(user)
    }
}