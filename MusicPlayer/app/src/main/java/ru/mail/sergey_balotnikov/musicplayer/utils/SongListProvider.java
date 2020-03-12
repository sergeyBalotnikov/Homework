package ru.mail.sergey_balotnikov.musicplayer.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.mail.sergey_balotnikov.musicplayer.songs.SongEntity;

public class SongListProvider {

    private static List<SongEntity> songEntities = new ArrayList<>();

    public static List<SongEntity> getSystemSongs(){
        setSongListFromSystem(Environment.getExternalStorageDirectory().getAbsolutePath());
        return songEntities;
    }

    private static void setSongListFromSystem(String path) {
        File currentFile = new File(path);
        if(!currentFile.isDirectory()){
            if(currentFile.getName().endsWith(".mp3")){
                Log.d(Consts.LOG_TAG, currentFile.getName());
                songEntities.add(new SongEntity(currentFile.getName(), currentFile.getAbsolutePath()));
                return;
            }
        }else {
            File[]files=currentFile.listFiles();
            if(files!=null){
                for(File file:files){
                    setSongListFromSystem(file.getAbsolutePath());
                }
            }
        }
    }

    private SongListProvider(){
    }
}
