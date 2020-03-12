package ru.mail.sergey_balotnikov.musicplayer.songs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mail.sergey_balotnikov.musicplayer.R;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongEntityHolder> {

    private List<SongEntity> songEntityList;
    private FragmentSongList.OnChangeSongListener listener;

    public SongListAdapter(FragmentSongList.OnChangeSongListener listener) {
        this.listener = listener;
    }

    public void setSongEntityList(List<SongEntity> songEntityList) {
        this.songEntityList = songEntityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongEntityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song,parent,false);
        return new SongEntityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongEntityHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(view->listener
                .onSongChanged(songEntityList.get(position)));
    }

    @Override
    public int getItemCount() {
        return songEntityList!=null?songEntityList.size():0;
    }

    public class SongEntityHolder extends RecyclerView.ViewHolder {
        private TextView tvSongName;

        public SongEntityHolder(@NonNull View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tvSongName);
        }

        private void bind(int position) {
            tvSongName.setText(songEntityList.get(position).getSongName());

        }
    }
}
