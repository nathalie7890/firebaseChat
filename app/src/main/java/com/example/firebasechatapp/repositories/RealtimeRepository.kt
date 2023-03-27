package com.example.firebasechatapp.repositories

import android.util.Log
import com.example.firebasechatapp.data.model.Friend
import com.example.firebasechatapp.data.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RealtimeRepository {
    private val ref = Firebase.database.getReference("chats")

    private fun getCombinedUid(uid1: String, uid2: String): String {
        return if (uid1 < uid2) {
            uid1 + uid2
        } else {
            uid2 + uid1
        }
    }

    suspend fun addMessage(msg: Message) {
        ref.child("messages").push().setValue(msg).await()
    }

    suspend fun addMessage(uid1: String, uid2: String, msg: Message) {
        ref.child(uid1).push().setValue(uid2).await()
        ref.child(uid2).push().setValue(uid1).await()
        ref.child(getCombinedUid(uid1, uid2)).push().setValue(msg).await()
    }


    fun getAllMessages(uid1: String, uid2: String) = callbackFlow<List<Message>> {
        val uid = getCombinedUid(uid1, uid2)
        ref.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                snapshot.children.forEach {
                    val msg = it.getValue<Message>()
                    msg?.let { message ->
                        messages.add(message)
                    }
                }
                trySend(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("debugging", "Failed to read messages", error.toException())
            }
        })
        awaitClose()
    }

    fun getAllMessages() = callbackFlow<List<Message>> {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                snapshot.children.forEach {
                    val msg = it.getValue<Message>()
                    msg?.let { message ->
                        messages.add(message)
                    }
                }
                trySend(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("debugging", "Failed to read messages", error.toException())
            }
        })
        awaitClose {}
    }
}