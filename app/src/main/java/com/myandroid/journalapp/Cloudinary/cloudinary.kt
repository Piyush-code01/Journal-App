package com.myandroid.journalapp.Cloudinary

import android.app.Application
import com.cloudinary.android.MediaManager

class cloudinary: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = HashMap<String,String>()
        config["cloud_name"] = "dewdzbj4l"
        config["api_key"]="672161468152573"

        MediaManager.init(this,config)

    }
}