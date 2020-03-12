package ru.mail.sergey_balotnikov.musicplayer.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mail.sergey_balotnikov.musicplayer.notification.MusicServiceNotification;
import ru.mail.sergey_balotnikov.musicplayer.songs.SongEntity;
import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;
import ru.mail.sergey_balotnikov.musicplayer.utils.SongListProvider;
import ru.mail.sergey_balotnikov.musicplayer.utils.SongNameParser;

public class MusicService extends IntentService {

    private MediaPlayer player;
    private List<SongEntity> songList;
    private int currentPosition;
    private boolean isPause;

    public boolean isPause() {
        return isPause;
    }

    public MusicService() {
        super("Music");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songList = new ArrayList<>();
        songList = SongListProvider.getSystemSongs();
        player = new MediaPlayer();
        player.setOnCompletionListener(mediaPlayer ->{
            mediaPlayer.reset();
            next();
        });
        currentPosition = 0;
        isPause = false;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = "";
        try {
            action = intent.getStringExtra(Consts.ACTION_KEY);
            SongEntity songEntity = (SongEntity) intent.getSerializableExtra(Consts.SONG);
            for(int i = 0; i<songList.size(); i++){
                if (songList.get(i).getSongPath().equals(songEntity.getSongPath()))
                    currentPosition=i;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(Consts.LOG_TAG, "onHandleIntent service action is null");
        }
        switch (action){
            case Consts.ACTION_PLAY: play();
                break;
            case Consts.ACTION_PAUSE: pause();
                break;
            case Consts.ACTION_NEXT: next();
                break;
            case Consts.ACTION_PREVIOUS: previous();
                break;
            case Consts.ACTION_STOP: stop();
                break;

        }
    }
    public String getCurrentSongTitle(){
        String title = songList.get(currentPosition).getSongName();
        if(title!=null && !title.equals("")){
            title = SongNameParser.getParseSongName(title);
        }
        return title;
    }

    public void play(){

        if(isPause){
            player.start();
            isPause=false;
            MusicServiceNotification.showNotification(getApplicationContext(),
                    songList.get(currentPosition).getSongName(), isPause);
            return;
        }
        player.reset();
        isPause = false;
        try {
            player.setDataSource(getApplicationContext(), Uri.parse(songList.get(currentPosition).getSongPath()));
            MusicServiceNotification.showNotification(getApplicationContext(),
                    songList.get(currentPosition).getSongName(), isPause);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();

    }
    public void pause(){
        player.pause();
        isPause=true;
        MusicServiceNotification.showNotification(getApplicationContext(),
                songList.get(currentPosition).getSongName(), isPause);
    }
    public void previous(){
        isPause=false;
        --currentPosition;
        if(currentPosition<0){
            currentPosition=songList.size()-1;
        }
        play();
    }
    public void next(){
        isPause=false;
        ++currentPosition;
        if(currentPosition>=songList.size()){
            currentPosition=0;
        }
        play();
    }
    public void stop(){
        isPause=false;
        player.stop();
        MusicServiceNotification.showNotification(getApplicationContext(),
                songList.get(currentPosition).getSongName(), isPause);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return super.onUnbind(intent);
    }

    public class MusicServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
