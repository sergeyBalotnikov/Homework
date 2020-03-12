package ru.mail.sergey_balotnikov.musicplayer.songs;

import java.io.Serializable;

public class SongEntity implements Serializable {
    private String songName;
    private String songPath;

    public SongEntity(String songName, String songPath) {
        this.songName = songName;
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongPath() {
        return songPath;
    }
}
