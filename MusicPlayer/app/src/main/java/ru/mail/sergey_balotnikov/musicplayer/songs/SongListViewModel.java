package ru.mail.sergey_balotnikov.musicplayer.songs;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executors;

public class SongListViewModel extends AndroidViewModel {

    private RepositorySongList repository;
    private MutableLiveData<List<SongEntity>> songList;

    public SongListViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositorySongListImplimentation();
        songList = new MutableLiveData<>();
        fetchSongList();
    }

    public LiveData<List<SongEntity>> getSongList() {
        return songList;
    }

    private void fetchSongList(){
        repository.getSongList().thenAcceptAsync(songs->songList.postValue(songs),
                Executors.newSingleThreadExecutor());
    }

    public SongEntity getSong(int position){
        return repository.getSong(position);
    }

}
