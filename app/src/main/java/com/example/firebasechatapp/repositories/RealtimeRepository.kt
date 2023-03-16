package com.example.firebasechatapp.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RealtimeRepository {
    val ref = Firebase.database.getReference("messages")

    suspend fun addMessage() {
        ref.setValue("My first message").await()
    }

    fun getAllMessages() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                Log.d("debugging", "Message is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("debugging", "Failed to read messages", error.toException())
            }
        })
    }
}