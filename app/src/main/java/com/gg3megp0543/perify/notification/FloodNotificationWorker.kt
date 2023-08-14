package com.gg3megp0543.perify.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.core.utils.CHANNEL_DESCRIPTION
import com.gg3megp0543.perify.core.utils.CHANNEL_ID
import com.gg3megp0543.perify.core.utils.CHANNEL_NAME
import com.gg3megp0543.perify.core.utils.KEY_DEPTH
import com.gg3megp0543.perify.core.utils.KEY_TITLE
import com.gg3megp0543.perify.core.utils.NOTIFICATION_ID


class FloodNotificationWorker(context: Context, params: WorkerParameters) :

    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val title = inputData.getString(KEY_TITLE)
        val depth = inputData.getString(KEY_DEPTH)
        if (!title.isNullOrEmpty() && !depth.isNullOrEmpty()) {
            showNotification(title, depth)
        }
        return Result.success()
    }

    private fun showNotification(title: String?, depth: String?) {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(depth)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_notifications)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = CHANNEL_NAME
            val channelDescription = CHANNEL_DESCRIPTION

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}