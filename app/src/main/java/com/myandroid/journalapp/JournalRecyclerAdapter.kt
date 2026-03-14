package com.myandroid.journalapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myandroid.journalapp.databinding.JournalitemBinding

class JournalRecyclerAdapter (val context : Context,var journallist:List<Journal>):
    RecyclerView.Adapter<JournalRecyclerAdapter.MyViewholder>() {

        private lateinit var binding: JournalitemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewholder {
       binding= JournalitemBinding.inflate(LayoutInflater.from(parent.context),parent,false
      )

        return MyViewholder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewholder,
        position: Int
    ) {
            val journal= journallist[position]
        holder.bind(journal)
    }

    override fun getItemCount(): Int {
        return journallist.size
    }


     class MyViewholder(val binding: JournalitemBinding) : RecyclerView.ViewHolder(binding.root){
         fun bind(journal: Journal){

             binding.tvjournaltitle.text=journal.title
            binding.tvjournalitemusername.text=journal.userName
             binding.tvjournalcreationtime.text = journal.timeAdded?.toDate()?.toString() ?: ""
             binding.tvjournaldescription.text=journal.thoughts

             // Manual Image Loading (URL to Bitmap)
             val executor = java.util.concurrent.Executors.newSingleThreadExecutor()
             val handler = android.os.Handler(android.os.Looper.getMainLooper())
             executor.execute {
                 try {
                     val inputStream = java.net.URL(journal.imageUrl).openStream()
                     val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                     handler.post {
                         binding.ivjournalimage.setImageBitmap(bitmap)
                     }
                 } catch (e: Exception) {
                     e.printStackTrace()
                 }
             }


         }
   }
}