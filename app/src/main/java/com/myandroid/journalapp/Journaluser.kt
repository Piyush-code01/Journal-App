package com.myandroid.journalapp

import android.app.Application
import com.cloudinary.android.MediaManager

class Journaluser: Application() {

    val username:String? = null
    val userId:String?=null

companion object{
    var instance: Journaluser?= null
        get(){
            if(field==null){
                field= Journaluser()
            }

        return field
        }

}
    override fun onCreate() {
        super.onCreate()

        val config = HashMap<String,String>()
        config["cloud_name"] = "dewdzbj4l"
        config["api_key"]="672161468152573"

        MediaManager.init(this,config)

    }

}