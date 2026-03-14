package com.myandroid.journalapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.firebase.Timestamp
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.myandroid.journalapp.databinding.ActivityAddJournalBinding

class AddJournal : AppCompatActivity() {


    lateinit var binding: ActivityAddJournalBinding

    //credentials
    var currentuserid :String=""
    var currentusername: String=""

    //Firebase
    private lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    //FireBase FireStore
    var db: FirebaseFirestore= FirebaseFirestore.getInstance()
     lateinit var imageuri: Uri
     private lateinit var imagePickerLauncher: ActivityResultLauncher<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJournalBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        auth= FirebaseAuth.getInstance()

        //imagepicker
        imagePickerLauncher=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri -> if(uri!=null){
                imageuri=uri
            //Preview of the selected image
            binding.camera.setImageURI(imageuri)
            }
        }


        binding.apply {

            progressbar.visibility=View.INVISIBLE

            //intiialise the camera action
            camera.setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }

            if(Journaluser.instance!= null){

                currentuserid=Journaluser.instance!!.userId.toString()
                currentusername= Journaluser.instance!!.username.toString()

                TitleTextview.text=currentusername
                savebutton.setOnClickListener(){
                    SaveJournal()
                }

            }


        }
    }

   private fun SaveJournal() {
     var title: String=binding.titleedittext.text.toString().trim()

       var thoughts:String=binding.thoughtsedittext.text.toString().trim()

       binding.progressbar.visibility=View.VISIBLE
       if(!TextUtils.isEmpty(title)&& !TextUtils.isEmpty(thoughts)&&imageuri!=null)
       {
           MediaManager.get().upload(imageuri).unsigned("Journalapp")
               .callback(object: UploadCallback{
                   override fun onStart(requestId: String?) {

                   }

                   override fun onProgress(
                       requestId: String?,
                       bytes: Long,
                       totalBytes: Long
                   ) {

                   }

                   override fun onSuccess(
                       requestId: String?,
                       resultData: Map<*, *>?
                   ) {
                       //url for the image

                       val imageUrl= resultData!!["url"] as String?

                       saveToFirestore(title, thoughts, imageUrl!!)
                   }



                   override fun onError(
                       requestId: String?,
                       error: ErrorInfo?
                   ) {
                       binding.progressbar.visibility = View.INVISIBLE
                       Toast.makeText(this@AddJournal, "Upload Failed: ${error?.description}",Toast.LENGTH_SHORT).show()
                   }

                   override fun onReschedule(
                       requestId: String?,
                       error: ErrorInfo?
                   ) {

                   }

               }).dispatch()

       }
       else {
           Toast.makeText(this, "Please select an image and fill all fields", Toast.LENGTH_SHORT).show()
       }



    }

    private fun saveToFirestore(title:String,thoughts:String,imageUrl: String){

        //make a journal object

        val journal=Journal(title=title,
            thoughts=thoughts,
            imageUrl=imageUrl ?:"",
            userId = currentuserid,
            timeAdded = Timestamp.now(),
            userName = currentusername
            )

        db.collection("Journals")
            .add(journal)
            .addOnSuccessListener {
                binding.progressbar.visibility=View.INVISIBLE
                Toast.makeText(this,"Journal saved Successfully", Toast.LENGTH_LONG).show()
                var i = Intent(this, Journallist::class.java)
                startActivity(i)
                finish()
            }

            .addOnFailureListener { e ->
                binding.progressbar.visibility = View.INVISIBLE
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onStart() {
        super.onStart()
        user=auth.currentUser!!
    }

    override fun onStop() {
        super.onStop()
        if(auth!=null){

        }

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode==1&& resultCode==RESULT_OK){
//            if (data!=null){
//                imageuri=data.data!!
//                binding.camera.setImageURI(imageuri)
//            }
//        }
//    }


}