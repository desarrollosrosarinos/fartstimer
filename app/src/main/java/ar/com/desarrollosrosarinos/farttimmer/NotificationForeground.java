package ar.com.desarrollosrosarinos.farttimmer;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationForeground {

    public void startNotificationForeground(Service servicio,int NotificationID,String contentTitle,String contentText){
        /*Notification note=new Notification(R.drawable.log_trans_mini,contentTitle,System.currentTimeMillis());

        Intent notificationIntent = new Intent(servicio.getApplicationContext(), Start.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent contentIntent = PendingIntent.getActivity(servicio.getApplicationContext(), 0, notificationIntent, 0);
        note.setLatestEventInfo(servicio, contentTitle, contentText, contentIntent);
        note.icon=R.drawable.log_trans_mini;*/
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground(servicio,NotificationID,contentTitle,contentText);
        }else {
            Intent notificationIntent = new Intent(servicio.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(servicio.getApplicationContext(), 0, notificationIntent, 0);

            Notification.Builder builder = new Notification.Builder(servicio.getApplicationContext());
            builder.setSmallIcon(R.drawable.ass).setContentTitle(contentTitle).setContentText(contentText).setContentIntent(contentIntent);

            Notification notification = builder.build();

            servicio.startForeground(NotificationID, notification);
        }
    }

    private void startMyOwnForeground(Service servicio,int NotificationID,String contentTitle,String contentText)
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = ""+NotificationID;
            String channelName = contentText;
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) servicio.getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(servicio, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ass)
                    .setContentTitle(contentTitle)
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            servicio.startForeground(2, notification);
        }
    }

    public void clearNotifications(Service servicio,int NotificationID){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "" + NotificationID;
            NotificationManager mNotificationManager =
                    (NotificationManager) servicio.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
        }
    }
}
