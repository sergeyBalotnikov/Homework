package ru.mail.sergey_balotnikov.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import ru.mail.sergey_balotnikov.musicplayer.player.FragmentPlayer;
import ru.mail.sergey_balotnikov.musicplayer.service.MusicService;
import ru.mail.sergey_balotnikov.musicplayer.songs.FragmentSongList;
import ru.mail.sergey_balotnikov.musicplayer.songs.SongEntity;
import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;
import ru.mail.sergey_balotnikov.musicplayer.utils.SongNameParser;

public class MainActivity extends AppCompatActivity implements FragmentSongList.OnChangeSongListener {

    private Intent intent;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        createNotificationChannel();
        intent = new Intent(this, MusicService.class).setAction(Consts.SERVICE_KEY);
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            openSongList();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, Consts.REQUEST_PERMISSION);
        }
    }

    private void openSongList(){
        getSupportFragmentManager().beginTransaction().add(R.id.flFragmentContainer,
                FragmentSongList.newInstance(), FragmentSongList.class.getName()).commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Consts.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openSongList();
                }
                break;
            }
        }
    }

    @Override
    public void onSongChanged(SongEntity songEntity) {
        Log.d(Consts.LOG_TAG, ""+songEntity.getSongName());
        intent.putExtra(Consts.ACTION_KEY, Consts.ACTION_PLAY);
        intent.putExtra(Consts.SONG, songEntity);
        startService(intent);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, FragmentPlayer.newInstance(),
                FragmentPlayer.class.getName())
                .addToBackStack(null).commit();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Consts.NOTIFICATION_CHANNEL_ID,
                    Consts.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
