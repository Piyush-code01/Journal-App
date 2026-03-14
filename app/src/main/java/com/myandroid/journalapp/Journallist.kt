package com.myandroid.journalapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.myandroid.journalapp.databinding.ActivityJournallistBinding

class Journallist : AppCompatActivity() {

    private lateinit var binding: ActivityJournallistBinding
    lateinit var firebaseAuth: FirebaseAuth
    var db= FirebaseFirestore.getInstance()
    lateinit var user: FirebaseUser
    lateinit var storagereference: StorageReference
    var collectionsreference: CollectionReference=db.collection("Journals")
    lateinit var noPostTextView: TextView
    lateinit var journalList:MutableList<Journal>
    lateinit var adapter: JournalRecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityJournallistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)


        //firebase auth
        firebaseAuth= Firebase.auth
        user=firebaseAuth.currentUser!!

        //Recycler View
        binding.rvjournal.setHasFixedSize(true)
        binding.rvjournal.layoutManager= LinearLayoutManager(this)

        //post arraylist
        journalList=arrayListOf<Journal>()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_add -> if (user!=null && firebaseAuth!=null){
                val intent= Intent(this, AddJournal::class.java)
                startActivity(intent)
            }
            R.id.action_sign_out -> {
                if(user!=null && firebaseAuth!=null ){
                    firebaseAuth.signOut()
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        journalList.clear()

       collectionsreference.whereEqualTo("userId", Journaluser.instance?.userId).
       get()
           .addOnSuccessListener {

               if (!it.isEmpty){
                   binding.tvnopost.visibility=View.INVISIBLE

                   it.forEach {
                       var journal =it.toObject(Journal::class.java)

                       journalList.add(journal)
                   }
                   adapter= JournalRecyclerAdapter(this,journalList)
                   binding.rvjournal.adapter=adapter
                   adapter.notifyDataSetChanged()
               }

               else{
                   binding.tvnopost.visibility=View.VISIBLE
                   binding.rvjournal.adapter = null

               }

           }.addOnFailureListener {
               Toast.makeText(this,"Oops!,, something went wrong ", Toast.LENGTH_SHORT).show()
           }




    }

}
