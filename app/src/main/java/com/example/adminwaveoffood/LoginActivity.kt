@file:Suppress("DEPRECATION")

package com.example.adminwaveoffood

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwaveoffood.databinding.ActivityLoginBinding
import com.example.adminwaveoffood.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString((R.string.WebclientID))).requestEmail().build()

        //initialize firebase auth
        auth= Firebase.auth

        //initialize firebase database
        database= Firebase.database.reference

        //Initialize google Sign In

        googleSignInClient=GoogleSignIn.getClient(this,googleSignInOptions)
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
        binding.GoogleButton.setOnClickListener {
            val signIntent=googleSignInClient.signInIntent

            launcher.launch(signIntent)
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
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result->
        if (result.resultCode== Activity.RESULT_OK)
        {
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful)
            {
                val account:GoogleSignInAccount=task.result
                val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    authTask->
                     if (authTask.isSuccessful)
                     {
                         //Sign is Successfully with Google
                         Toast.makeText(this, "Sign in is Successfully with Google", Toast.LENGTH_SHORT).show()
                        updateUi()
                     }
                    else
                     {
                         Toast.makeText(this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show()
                     }
                }
            }
            else
            {
                Toast.makeText(this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //Check user already logged  in
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser !=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}