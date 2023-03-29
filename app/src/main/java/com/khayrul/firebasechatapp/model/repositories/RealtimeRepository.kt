package com.khayrul.firebasechatapp.model.repositories

import android.util.Log
import com.khayrul.firebasechatapp.model.model.Message
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
        if(uid1 < uid2)  {
            return uid1+uid2
        }
        return uid2+uid1
    }

    suspend fun addMessage(msg: Message) {
        ref.child("messages").push().setValue(msg).await()
    }

    suspend fun addMessage(uid1: String, uid2: String, msg: Message) {
        ref.child(getCombinedUid(uid1, uid2)+"/messages")
            .push()
            .setValue(msg)
            .await()
    }

    fun getAllMessages(uid1: String, uid2: String) = callbackFlow<List<Message>> {
        ref.child(getCombinedUid(uid1, uid2)+"/messages")
            .addValueEventListener(object: ValueEventListener {
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