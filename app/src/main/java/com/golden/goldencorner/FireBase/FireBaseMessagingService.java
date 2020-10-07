package com.golden.goldencorner.FireBase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.golden.goldencorner.R;
import com.golden.goldencorner.ui.splash.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class FireBaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static int count = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "Notification Message TITLE: " + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "Notification Message BODY: "  + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message DATA: "  + remoteMessage.getData().toString());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),
                remoteMessage.getData());
    }
    //This method is only generating push notification
    private void sendNotification(String messageTitle, String messageBody, Map<String, String> row) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = null;

        contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(this, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.golden_corner_logo))
                .setSmallIcon(R.drawable.golden_corner_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(contentIntent);

        notificationManager.notify(count, notificationBuilder.build());
        count++;
    }
}