package com.myandroid.journalapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.myandroid.journalapp.databinding.ActivityMainBinding
import com.myandroid.journalapp.databinding.ActivitySignUpactivityBinding


class MainActivity : AppCompatActivity() {


    lateinit var binding1 : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding1= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding1.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding1.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val db = Firebase.firestore
    }





}