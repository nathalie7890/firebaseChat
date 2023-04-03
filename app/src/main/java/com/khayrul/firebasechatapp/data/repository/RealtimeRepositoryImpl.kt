package com.khayrul.firebasechatapp.data.repository

import android.util.Log
import com.khayrul.firebasechatapp.data.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.khayrul.firebasechatapp.domain.repository.RealtimeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RealtimeRepositoryImpl: RealtimeRepository {
    private val ref = Firebase.database.getReference("chats")

    private fun getCombinedUid(uid1: String, uid2: String): String {
        if(uid1 < uid2)  {
            return uid1+uid2
        }
        return uid2+uid1
    }

    override suspend fun addMessage(msg: Message) {
        ref.child("messages").push().setValue(msg).await()
    }

    override suspend fun addMessage(uid1: String, uid2: String, msg: Message) {
        ref.child(getCombinedUid(uid1, uid2)+"/messages")
            .push()
            .setValue(msg)
            .await()
    }

    override fun getAllMessages(uid1: String, uid2: String) = callbackFlow<List<Message>> {
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

    override fun getAllMessages() = callbackFlow<List<Message>> {
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