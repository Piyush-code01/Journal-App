package com.myandroid.journalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

import com.myandroid.journalapp.databinding.ActivityMainBinding
import com.myandroid.journalapp.databinding.ActivitySignUpactivityBinding


class
MainActivity : AppCompatActivity() {


    lateinit var binding1 : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        enableEdgeToEdge()
        binding1= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding1.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding1.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding1.button.setOnClickListener {
            val intent = Intent(this, sign_upactivity::class.java)
            startActivity(intent)
        }

        binding1.loginbutton.setOnClickListener (){
            LoginWithEmailPassword(
                binding1.emailtext.text.toString().trim(),
                binding1.editText.text.toString().trim()
            )
        }


    }

    private fun LoginWithEmailPassword(email:String,Password: String){
           auth .signInWithEmailAndPassword(email,Password).addOnCompleteListener(this) {
                task ->
               if(task.isSuccessful){
                   val user=auth
                   gotoJournalList()
               }
               else{
                   Toast.makeText(this,"Authentication Failed!!",Toast.LENGTH_LONG).show()
               }
           }
    }


    override fun onStart() {
        super.onStart()

        val currentuser=auth.currentUser
        if(currentuser!=null){
           gotoJournalList()

        }
    }
    fun gotoJournalList(){
        var intent = Intent(this, Journallist::class.java)
        startActivity(intent)
        finish()
    }




}