package com.example.firebasechatapp.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import com.example.firebasechatapp.R
import com.example.firebasechatapp.utils.Constants.NOTIFICATION_ID
import com.example.firebasechatapp.utils.Constants.NOTIFICATION_NAME

object NotificationUtils {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun createNotification(context: Context, title: String, msg: String) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setPriority(PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(0, notification)
    }
}