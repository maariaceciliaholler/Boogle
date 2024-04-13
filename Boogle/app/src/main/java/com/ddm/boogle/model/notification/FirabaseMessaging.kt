package com.ddm.boogle.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ddm.boogle.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data.isNotEmpty().let {

            val title = remoteMessage.data["title"]
            val messageBody = remoteMessage.data["message"]

            showNotification(title, messageBody)
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val channelId = "com.ddm.boogle.notifications"
        val channelName = "Boogle Notifications"
        val notificationId = 1234

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.logoboogle)
            .setAutoCancel(true)

        val notificationManager = getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager?.createNotificationChannel(channel)
        }

        notificationManager?.notify(notificationId, notificationBuilder.build())
    }
}
