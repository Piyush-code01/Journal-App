package com.myandroid.journalapp

import java.sql.Timestamp

data class Journal (
     val title:String="",
     val thoughts:String="",
     val imageUrl:Int,
     val userId:String="",
     val TimeAdded: Timestamp?=null,
     val userName: String=""

)