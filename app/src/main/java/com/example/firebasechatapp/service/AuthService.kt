package com.example.firebasechatapp.service

import com.example.firebasechatapp.model.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class AuthService(private val auth: FirebaseAuth, private val ref: CollectionReference) {

    suspend fun register(user: User) {
        val res = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        res.user?.uid?.let {
            ref.document(it).set(user.copy(id = it))
        }
//        ref.document(res.user!!.uid).set(user).await()
//        val res = auth.createUserWithEmailAndPassword(user.email, user.password).await()
//        if (res.user != null) {
//            ref.document(user.email).set(user)
//        }
    }

    suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()
        return res.user?.uid != null
    }

    fun isLoggedIn(): Boolean {
        auth.currentUser ?: return false
        return true
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun getCurrentUser(): User? {
        return auth.uid?.let {
            ref.document(it).get().await().toObject(User::class.java)
        }
    }

    fun getUid(): String? {
        return auth.uid
    }
}