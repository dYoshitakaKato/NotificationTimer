package com.example.yoshitakakato.notificationtimer;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

/**
 * Created by yoshitaka.kato on 2017/07/30.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra("notificationId", 0);
        notificationManager.notify(id, createNotification(context, intent));
    }

    private Notification createNotification(Context context, Intent intent) {

        Notification notification = createNotificationBeforeO(context, intent);
        return notification;
    }

    private Notification createNotificationForO(Context context, Intent intent){
        return null;
    }

    private Notification createNotificationBeforeO(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(intent.getCharSequenceExtra(Constant.TITLE_KEY));
        builder.setContentText(intent.getCharSequenceExtra(Constant.TEXT_KEY));
        Intent notifyIntent = new Intent (Intent.ACTION_VIEW, Uri.parse(intent.getCharSequenceExtra(Constant.URL_KEY).toString()));
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        return builder.build();
    }
}
