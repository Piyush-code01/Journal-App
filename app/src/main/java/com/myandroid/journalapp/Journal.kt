package com.myandroid.journalapp

import com.google.firebase.Timestamp


data class Journal (
    val title:String="",
    val thoughts:String="",
    val imageUrl:String ="",
    val userId:String="",
    val timeAdded: Timestamp?=null,
    val userName: String=""

)