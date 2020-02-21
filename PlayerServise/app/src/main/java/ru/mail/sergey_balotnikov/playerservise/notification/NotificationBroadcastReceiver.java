package ru.mail.sergey_balotnikov.playerservise.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.mail.sergey_balotnikov.playerservise.utils.Consts;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(Consts.BROADCAST_ACTION)
                .putExtra(Consts.KEY_ACTION, intent.getAction()));

    }
}