package com.agremo.loan_app

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.agremo.loan_app.activity.MainActivity
import com.agremo.loan_app.activity.splashscreen
import com.agremo.loan_app.data.notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var channelID = "Loan_Updates"
    private var channelName = "Loan_Express"

    @SuppressLint("UnspecifiedImmutableFlag")

    override fun onMessageReceived(message: RemoteMessage) {

        if(message.notification != null)
        {
            generatenotification(message.notification!!.title!! , message.notification!!.body!!)
        }
    }
    fun generatenotification(title: String, descreption : String)
    {

        val intent = Intent(this , splashscreen()::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra("title" , title)
        intent.putExtra("description" , descreption)

        val pendingintent = PendingIntent.getActivity(this, 0 , intent , PendingIntent.FLAG_ONE_SHOT)


        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.applogo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingintent)

        builder = builder.setContent(getRemoteview(title, descreption))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationchannel = NotificationChannel( channelID , channelName , NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationchannel)
        }

        notificationManager.notify(0 , builder.build())

    }

    private fun getRemoteview(title: String, descreption: String): RemoteViews? {

        val remoteViews = RemoteViews("com.agremo.loan_app" , R.layout.notification)

        remoteViews.setTextViewText(R.id.notifytitle , title)
        remoteViews.setTextViewText(R.id.notifydescription , descreption)
        remoteViews.setImageViewResource(R.id.notifyApplogo , R.drawable.applogo)

        return remoteViews
    }
}