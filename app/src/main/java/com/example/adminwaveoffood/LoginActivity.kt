package com.example.adminwaveoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityLoginBinding
import com.example.adminwaveoffood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

@Suppress("NAME_SHADOWING")
class LoginActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private  var username:String?=null
    private  var restunant:String?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize firebase auth
        auth= Firebase.auth

        //initialize firebase database
        database= Firebase.database.reference

        binding.LoginButton.setOnClickListener {

            //get text from edittext
            email=binding.email.text.toString().trim()
            password=binding.password.text.toString().trim()
            if (password.isBlank()||email.isBlank())
            {
                Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                createAccount(email,password)
            }

        }
        binding.dontHaveButton.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if (task.isSuccessful)
            {
                auth.currentUser
                updateUi()
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                    if (task.isSuccessful)
                    {
                        auth.currentUser
                        Toast.makeText(this, " Create user  and Login Successfully", Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUi()
                    }
                    else
                    {
                        Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account","createAccount",task.exception)
                    }
                }

            }
        }
    }

    private fun saveUserData() {
        email=binding.email.text.toString().trim()
        password=binding.password.text.toString().trim()
        val user= UserModel(restunant,username,email,password)
        val userID=FirebaseAuth.getInstance().currentUser?.uid

         userID?.let {
             database.child("User").child(it).setValue(user)
         }


    }

    private fun updateUi() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}