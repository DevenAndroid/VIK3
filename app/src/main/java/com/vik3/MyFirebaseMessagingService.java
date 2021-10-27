package com.vik3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Deven on 02/06/2021.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
    }

    public void showNotification(String message, String title) {

        /*Intent snoozeIntent = new Intent(this, MainActivity.class);
//        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);*/

        String channelId = "Your_channel_id";
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                /*.addAction(R.drawable.ic_baseline_arrow_left_24, "Snooze",
                        snoozePendingIntent)*/
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
//            notificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(0, notification);

    }

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
//        getSharedPreferences("FIREBASE_TOKEN", MODE_PRIVATE).edit().putString("firebase", s).apply();
    }


    public static String getToken(Context context) {
        return context.getSharedPreferences("FIREBASE_TOKEN", MODE_PRIVATE).getString("firebase", "empty");
    }
}
