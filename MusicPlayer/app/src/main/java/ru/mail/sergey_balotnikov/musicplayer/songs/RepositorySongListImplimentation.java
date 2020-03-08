package ru.mail.sergey_balotnikov.musicplayer.songs;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;

public class RepositorySongListImplimentation implements RepositorySongList {

    private List<SongEntity> songEntityList;

    public RepositorySongListImplimentation() {
        songEntityList=new ArrayList<>();
    }

    @Override
    public CompletableFuture<List<SongEntity>> getSongList() {
        return CompletableFuture.supplyAsync(this::getSongEntityList, Executors.newSingleThreadExecutor());
    }

    @Override
    public SongEntity getSong(int position) {
        return songEntityList.get(position);
    }

    private List<SongEntity> getSongEntityList(){
        setSongEntityList(Environment.getExternalStorageDirectory().getAbsolutePath());
        return songEntityList;
    }
    private void setSongEntityList(String path){
        File currentFile = new File(path);
        if(!currentFile.isDirectory()){
            if(currentFile.getName().endsWith(".mp3")){
                songEntityList.add(new SongEntity(currentFile.getName(), currentFile.getAbsolutePath()));
                Log.d(Consts.LOG_TAG, currentFile.getName());
                return;
            }
        }else {
            File[]files=currentFile.listFiles();
            if(files!=null){
                for(File file:files){
                    setSongEntityList(file.getAbsolutePath());
                }
            }
        }
    }
}
