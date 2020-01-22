package ru.mail.sergey_balotnikov.servicesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "SVB_MAIN";
    public static final String ACTION_MAIN = "MainActivityAction";

    private ServiceConnection connection;
    private Intent serviceIntent;
    private Button send;
    private TextView text;
    private MyReceiver myReceiver;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.sendMessageButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent().setAction(ACTION_MAIN));
            }
        });
        text = findViewById(R.id.text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();
        connection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "[[service Connected]]");
                MyService.MyBinder myBinder = (MyService.MyBinder)binder;
                text.setText(myBinder.getDateFile());
            }
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "[[service disconnected]]");
            }
        };
        serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra(MyReceiver.ACTION_KEY, ACTION_MAIN);
        startService(serviceIntent);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
    }
    public void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MAIN);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        unbindService(connection);
        super.onDestroy();
    }
}
