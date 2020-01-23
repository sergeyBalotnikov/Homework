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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements OnDateWriterListener{

    public static final String LOG_TAG = "SVB_MAIN";
    public static final String ACTION_MAIN = "MainActivityAction";
    private ServiceConnection connection;
    private Intent serviceIntent;
    private Button send;
    private TextView text;
    private MyReceiver myReceiver;
    private PendingIntent pendingIntent;
    private MyService myService;


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
        connection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "[[service Connected]]");
                MyService.MyBinder myBinder = (MyService.MyBinder)binder;
                myService=myBinder.getService();
                myService.setListener(MainActivity.this);
                /*onDateWriten();*/
            }
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "[[service disconnected]]");
            }
        };
        serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra(MyReceiver.ACTION_KEY, ACTION_MAIN);
        registerReceiver();
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
    }*/

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
        stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public void onDateWriten() {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(MyService.FILE_DATE)));
            while (reader.ready()){
                builder.append(reader.readLine()+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        text.setText(builder.toString());
    }
}
