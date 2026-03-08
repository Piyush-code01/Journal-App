package com.myandroid.journalapp

import android.app.Application

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

}