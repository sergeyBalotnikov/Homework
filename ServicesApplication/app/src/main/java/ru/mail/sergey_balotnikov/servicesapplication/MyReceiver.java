package ru.mail.sergey_balotnikov.servicesapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_KEY = "MyServiceAction";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, MyService.class);

        Toast.makeText(context, ""+intent.getAction(), Toast.LENGTH_LONG).show();
        serviceIntent.putExtra(ACTION_KEY, intent.getAction());
        context.startService(serviceIntent);
        context.stopService(serviceIntent);
    }
}
