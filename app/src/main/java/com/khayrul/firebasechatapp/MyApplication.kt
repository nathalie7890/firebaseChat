package com.khayrul.firebasechatapp

import android.app.Application
import com.khayrul.firebasechatapp.service.AuthService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication:Application() {
    @Inject
    lateinit var authService: AuthService
    var username: String? = null

    fun fetchUserName() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = authService.getCurrentUser()
            username = res?.name
        }
    }

    override fun onCreate() {
        super.onCreate()
        fetchUserName()
    }
}