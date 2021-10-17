package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    fileNameAndDownloadStatus: FileNameAndDownloadStatus,
    applicationContext: Context,
) {
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
        .putExtra("fileNameAndDownloadStatus", fileNameAndDownloadStatus) //in DetailActivity.kt line 14: intent.getParcelableExtra<FileNameAndDownloadStatus>("fileNameAndDownloadStatus")

    val contentPendingIntent = PendingIntent.getActivity(applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(applicationContext,
        applicationContext.getString(R.string.notification_channel_id))
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_check_button),
            contentPendingIntent)

    notify(NOTIFICATION_ID, builder.build())
}
