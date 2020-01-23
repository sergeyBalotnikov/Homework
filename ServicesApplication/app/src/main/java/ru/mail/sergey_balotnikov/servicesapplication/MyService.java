package ru.mail.sergey_balotnikov.servicesapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {

    public static final String TAG = "SVB_TAG";
    public static final String FILE_DATE = "dateFile";
    private ExecutorService executorService;
    private final IBinder mBinder = new MyBinder();
    private OnDateWriterListener listener;

    public void setListener(OnDateWriterListener listener) {
        this.listener = listener;
    }

    public void writeFile(String string, int id){
        executorService.execute(new DateWriter(string, id));
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "service created");
        executorService = Executors.newFixedThreadPool(4);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        writeFile(intent.getStringExtra(MyReceiver.ACTION_KEY), startId);
        if(listener!=null) {
            listener.onDateWriten();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

   /* String getDateFile(){
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MyService.FILE_DATE));
            while (reader.readLine()!=null){
                builder.append(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }*/

    class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    class DateWriter implements Runnable{

        private String receiverAction;
        private int startId;

        public DateWriter(String string, int startId) {
            this.startId = startId;
            receiverAction=string;
            Log.d(TAG, "date writer created. Action - "+receiverAction);
        }

        @Override
        public void run() {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput(FILE_DATE, MODE_APPEND)));
                Locale locale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
                String dateTime = new SimpleDateFormat("dd.MM.yyyy", locale)
                        .format(Calendar.getInstance().getTime())+"]["
                        + new SimpleDateFormat("hh:mm:ss", locale)
                        .format(Calendar.getInstance().getTime())+"]]\n";
                bufferedWriter.write("\n[["+dateTime);
                bufferedWriter.write(receiverAction);
                bufferedWriter.close();
                Log.d(TAG, "["+dateTime +"]"+ receiverAction);
//                logFileString=logFileString.concat(bufferedWriter.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Write file exception: "+e.getMessage());
            }
            stop();
        }
        private void stop(){
            Log.d(TAG, "Date writer " + startId + " end, stopSelf(" + startId + ")");
            stopForeground(startId);
            stopSelf();
        }
    }
}
