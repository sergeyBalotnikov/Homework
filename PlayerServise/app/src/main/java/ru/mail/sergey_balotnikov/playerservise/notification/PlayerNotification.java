package ru.mail.sergey_balotnikov.playerservise.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.mail.sergey_balotnikov.playerservise.R;
import ru.mail.sergey_balotnikov.playerservise.utils.Consts;

public class PlayerNotification {

    public static final int NOTIFICATION_ID = 1224;

    public static void createPlayerNotification(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context, Consts.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_equalizer)
                    .setContentTitle(context.getResources().getString(R.string.notification_title))
                    .build();

            notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        }
    }
    private PendingIntent pendingIntentCreator(Context context, String action){
        return PendingIntent.getBroadcast(context, 0,
                new Intent(context, NotificationBroadcastReceiver.class).setAction(action),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PlayerNotification() {
    }
}
