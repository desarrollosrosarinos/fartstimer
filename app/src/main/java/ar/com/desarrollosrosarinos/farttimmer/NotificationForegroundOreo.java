package ar.com.desarrollosrosarinos.farttimmer;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationForegroundOreo {

    public void startNotificationForeground(Service servicio,int NotificationID,String contentTitle,String contentText){
    	if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
			startMyOwnForeground(servicio,NotificationID,contentTitle,contentText);
		}else {
			Intent notificationIntent = new Intent(servicio, MainActivity.class);
			notificationIntent.setAction(MainActivity.class.toString());
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			PendingIntent pendingIntent = PendingIntent.getActivity(servicio, 0,
					notificationIntent, 0);

			Bitmap icon = BitmapFactory.decodeResource(servicio.getResources(),
					R.drawable.ass);

			NotificationCompat.Builder notification = new NotificationCompat.Builder(servicio, "fartid")
					.setContentTitle(contentTitle)
					.setTicker(contentTitle)
					.setContentText(contentText)
					.setSmallIcon(R.drawable.ass)
					.setLargeIcon(
							Bitmap.createScaledBitmap(icon, 128, 128, false))
					.setContentIntent(pendingIntent)
					.setOngoing(true);
			servicio.startForeground(NotificationID, notification.build());
		}
    }

    public void clearNotifications(Service servicio,int NotificationID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "" + NotificationID;
            NotificationManager mNotificationManager =
                    (NotificationManager) servicio.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
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
}
