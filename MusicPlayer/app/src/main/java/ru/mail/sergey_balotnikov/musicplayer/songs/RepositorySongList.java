package ru.mail.sergey_balotnikov.musicplayer.songs;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RepositorySongList {
    CompletableFuture<List<SongEntity>> getSongList();
    SongEntity getSong(int position);
}
