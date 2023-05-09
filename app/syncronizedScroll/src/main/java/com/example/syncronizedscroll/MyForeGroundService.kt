package com.example.syncronizedscroll

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class MyForeGroundService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("Service", intent.toString())
        if (intent != null) {
            val title = intent.getStringExtra("Notification_title")
            val walkedSteps = intent.getStringExtra("walked_steps")
            val totalSteps = intent.getStringExtra("total_steps")
            val totalDistance = intent.getStringExtra("total_distance")
            showNotification(title.toString(), walkedSteps.toString(), totalSteps.toString(), totalDistance.toString())
        }
        return START_STICKY
    }


    private fun showNotification(title: String, walkedSteps: String, totalSteps: String, totalDistance: String){

        val notificationLayout = RemoteViews(packageName, R.layout.notification_lay)
        notificationLayout.setTextViewText(R.id.title, title)
        notificationLayout.setTextViewText(R.id.walkedSteps, "Your Steps : $walkedSteps / $totalSteps")
        notificationLayout.setTextViewText(R.id.totalDistance, "Total Distance : $totalDistance")

        val pendingIntent: PendingIntent = Intent(applicationContext, Class.forName("com.example.syncronizedscroll.MainActivity"))
            .let { notificationIntent ->
                PendingIntent.getActivity(applicationContext, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

        val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel_id"
        val channelName = "channel_name"

        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        val notification: Notification = builder.build()
        startForeground(123, notification)
        Log.e("Service", "notified")
    }
}