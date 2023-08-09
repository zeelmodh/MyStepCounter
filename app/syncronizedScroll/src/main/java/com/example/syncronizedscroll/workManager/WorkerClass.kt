package com.example.syncronizedscroll.workManager

import android.app.*
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.syncronizedscroll.R


open class WorkerClass(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.e("WorkManager", inputData.toString())
        if (inputData.getString("Notification_title") != null) {
            val title = inputData.getString("Notification_title")
            val walkedSteps = inputData.getString("walked_steps")
            val totalSteps = inputData.getString("total_steps")
            val totalDistance = inputData.getString("total_distance")

            showNotification(title.toString(), walkedSteps.toString(), totalSteps.toString(), totalDistance.toString())
            Log.d("on", "work manager")
        }
        return Result.success()
    }

    private fun showNotification(title: String, walkedSteps: String, totalSteps: String, totalDistance: String){

        val notificationLayout = RemoteViews(applicationContext.packageName, R.layout.notification_lay)
        notificationLayout.setTextViewText(R.id.title, title)
        notificationLayout.setTextViewText(R.id.walkedSteps, "Your Steps : $walkedSteps / $totalSteps")
        notificationLayout.setTextViewText(R.id.totalDistance, "Total Distance : $totalDistance")

        val pendingIntent: PendingIntent = Intent(applicationContext, Class.forName("com.example.syncronizedscroll.activity.MainActivity"))
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
        notificationManager.notify(1, builder.build())
        Log.e("WorkManager", "notified from worker")
    }
}