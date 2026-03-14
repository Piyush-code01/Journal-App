package com.myandroid.journalapp

import com.google.firebase.Timestamp


data class Journal (
    var title:String="",
    var thoughts:String="",
    var imageUrl:String ="",
    var userId:String="",
    var timeAdded: Timestamp?=null,
    var userName: String=""

)