package ru.mail.sergey_balotnikov.musicplayer.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import ru.mail.sergey_balotnikov.musicplayer.R;
import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;

public class MusicServiceNotification {
    public static void showNotification(Context context, String songTitle, boolean isPause){
        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, Consts.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_equalizer)
                .setContentTitle(songTitle)
                .setOnlyAlertOnce(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_previous,"Prev",
                        createPendingIntentForAction(context, Consts.ACTION_PREVIOUS))
                .addAction(isPause?R.drawable.ic_play:R.drawable.ic_pause,isPause?"Play":"Pause",
                        isPause?createPendingIntentForAction(context, Consts.ACTION_PLAY):
                                createPendingIntentForAction(context, Consts.ACTION_PAUSE))
                .addAction(R.drawable.ic_next,"Next",
                        createPendingIntentForAction(context, Consts.ACTION_NEXT))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2,4)
                        .setMediaSession(new MediaSessionCompat(context, "TAG").getSessionToken()))
                .build();
        notificationManagerCompat.notify(Consts.NOTIFICATION_ID, notification);

    }
    private static PendingIntent createPendingIntentForAction(Context context, String action){
        return PendingIntent.getBroadcast(context,0,
                new Intent(context, NotificationReceiver.class)
                        .setAction(action), PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private MusicServiceNotification(){}
}
