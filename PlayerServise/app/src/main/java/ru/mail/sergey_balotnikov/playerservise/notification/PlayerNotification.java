package ru.mail.sergey_balotnikov.playerservise.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Token;
import ru.mail.sergey_balotnikov.playerservise.R;
import ru.mail.sergey_balotnikov.playerservise.utils.Consts;

public class PlayerNotification {

    public static final int NOTIFICATION_ID = 1224;

    public static void createPlayerNotification(Context context, boolean isPlay){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context, Consts.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_equalizer)
                    .setContentTitle(context.getResources().getString(R.string.notification_title))
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.ic_previous, context.getResources()
                                    .getString(R.string.action_previous_title),
                            pendingIntentCreator(context, Consts.ACTION_PREVIOUS, isPlay))
                    .addAction(R.drawable.ic_play, context.getResources()
                                    .getString(R.string.action_play_pause_title),
                            pendingIntentCreator(context, Consts.ACTION_PLAY, isPlay))
                    .addAction(R.drawable.ic_next, context.getResources()
                                    .getString(R.string.action_next_title),
                            pendingIntentCreator(context, Consts.ACTION_NEXT, isPlay))
                    .addAction(R.drawable.ic_stop, context.getResources()
                                    .getString(R.string.action_stop_title),
                            pendingIntentCreator(context, Consts.ACTION_STOP, isPlay))
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(new MediaSessionCompat(context, Consts.MEDIA_SESSION_TAG).getSessionToken())
                            .setShowActionsInCompactView(0,1,2,4))
                    .build();

            notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        }
    }
    private static PendingIntent pendingIntentCreator(Context context, String action, boolean isPlay){
        if(action.equals(Consts.ACTION_PLAY)||action.equals(Consts.ACTION_PAUSE)){
            return PendingIntent.getBroadcast(context, 0,
                    new Intent(context, NotificationBroadcastReceiver.class)
                            .setAction(isPlay?Consts.ACTION_PLAY:Consts.ACTION_PAUSE),
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return PendingIntent.getBroadcast(context, 0,
                new Intent(context, NotificationBroadcastReceiver.class)
                        .setAction(action),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PlayerNotification() {
    }
}
