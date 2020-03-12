package ru.mail.sergey_balotnikov.musicplayer.songs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.mail.sergey_balotnikov.musicplayer.R;

public class FragmentSongList extends Fragment {
    public static FragmentSongList newInstance(){
        return new FragmentSongList();
    }
    private RecyclerView rvSongList;
    private SongListViewModel model;
    private OnChangeSongListener listener;
    private SongListAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnChangeSongListener){
            listener=(OnChangeSongListener)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= ViewModelProviders.of(requireActivity()).get(SongListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSongList=view.findViewById(R.id.rvSongList);
        songListInit();
        model.getSongList().observe(getViewLifecycleOwner(), songEntities ->
                setAdapterSongList(songEntities));
    }

    private void setAdapterSongList(List<SongEntity> songList){
        adapter.setSongEntityList(songList);
    }

    private void songListInit(){
        adapter = new SongListAdapter(listener);
        rvSongList.setAdapter(adapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.rvSongList.setClickable(false);
    }

    public interface OnChangeSongListener{
        void onSongChanged(final SongEntity songEntity);
    }
}
