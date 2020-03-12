package ru.mail.sergey_balotnikov.musicplayer.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(Consts.KEY_BROADCAST_ACTION)
                .putExtra(Consts.ACTION_KEY, intent.getAction()));
    }
}
