package com.baswarajmamidgi.vnrstaff;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by baswarajmamidgi on 31/01/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String image = remoteMessage.getNotification().getIcon();
        String title = remoteMessage.getNotification().getTitle();
        String content = remoteMessage.getNotification().getBody();
        String sound = remoteMessage.getNotification().getSound();
        int id = 0;
        Object obj = remoteMessage.getData().get("id");
        if (obj != null) {
            id = Integer.valueOf(obj.toString());
        }

        this.sendNotification(image, id, title, content, sound);
    }


    private void sendNotification(String image, int id, String title, String content, String sound) {

        Log.i("log",title +" "+content);

        Intent intent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
        intent.putExtra("notification", content);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.vnrlogo)
                    .setContentTitle(URLDecoder.decode(title, "UTF-8"))
                    .setContentText(URLDecoder.decode(content, "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i("log",e.getLocalizedMessage());
        }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(id, notificationBuilder.build());
        } else {
            Log.d("log", "failednotificationBuilder");
        }

    }
}
