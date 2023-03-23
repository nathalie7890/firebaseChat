package com.example.firebasechatapp

import android.app.Application
import android.util.Log
import com.example.firebasechatapp.data.service.AuthService
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

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            val res = authService.getCurrentUser()
            username = res?.name
        }
    }
}