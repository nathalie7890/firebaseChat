package com.example.firebasechatapp

import android.app.Application
import android.util.Log
import com.example.firebasechatapp.service.AuthService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var authService: AuthService
    var username: String? = null

    fun fetchUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = authService.getCurrentUser()
            username = res?.name
        }
    }

    override fun onCreate() {
        super.onCreate()
        fetchUser()
    }
}