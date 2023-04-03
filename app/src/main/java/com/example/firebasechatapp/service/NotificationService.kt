package com.example.firebasechatapp.service

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.firebasechatapp.utils.Constants

class NotificationService : NotificationListenerService() {
    override fun onCreate() {
        super.onCreate()
        Log.d(Constants.DEBUG, "Running")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Log.d(Constants.DEBUG, "Found a notification")
    }
}