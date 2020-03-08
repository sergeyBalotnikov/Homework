package ru.mail.sergey_balotnikov.musicplayer.player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.mail.sergey_balotnikov.musicplayer.R;
import ru.mail.sergey_balotnikov.musicplayer.service.MusicService;
import ru.mail.sergey_balotnikov.musicplayer.utils.Consts;

import static android.content.Context.BIND_AUTO_CREATE;

public class FragmentPlayer extends Fragment {
    public static FragmentPlayer newInstance(){
        return new FragmentPlayer();
    }
    private Button playPause, next, previous, stop;
    private TextView title;
    private ServiceConnection serviceConnection;
    private MusicService service;
    private MusicService.MusicServiceBinder binder;
    private boolean isServiceBound;
    private BroadcastReceiver receiver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    String action = intent.getExtras().getString(Consts.ACTION_KEY);
                    switch (action){
                        case Consts.ACTION_PAUSE: playPause();
                        break;
                        case Consts.ACTION_PLAY: playPause();
                        break;
                        case Consts.ACTION_PREVIOUS: previous();
                        break;
                        case Consts.ACTION_NEXT: next();
                        break;
                        case Consts.ACTION_STOP: stop();
                        break;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.tvSongTitle);
        playPause = view.findViewById(R.id.btnPlay);
        playPause.setOnClickListener((v)->playPause());
        next = view.findViewById(R.id.btnNext);
        next.setOnClickListener((v)->next());
        previous = view.findViewById(R.id.btnPrevious);
        previous.setOnClickListener((v)->previous());
        stop = view.findViewById(R.id.btnStop);
        stop.setOnClickListener((v)->stop());
    }

    private void playPause(){
        if(isServiceBound){
            if(service.isPause()){
                service.play();
                playPause.setBackgroundResource(R.drawable.ic_pause);
            } else {
                service.pause();
                playPause.setBackgroundResource(R.drawable.ic_play);
            }
            setTitleOfSong();
        }
    }
    private void previous(){
        if(isServiceBound){
            if(service.isPause()){
                service.previous();
                playPause.setBackgroundResource(R.drawable.ic_pause);
            } else {
                service.previous();
            }
            setTitleOfSong();
        }
    }
    private void next(){
        if(isServiceBound){
            if(service.isPause()){
                service.next();
                playPause.setBackgroundResource(R.drawable.ic_pause);
            } else {
                service.next();
            }
            setTitleOfSong();
        }
    }
    private void stop(){
        if(isServiceBound){
            service.stop();
            playPause.setBackgroundResource(R.drawable.ic_play);
            setTitleOfSong();
        }
    }

    private void initConnection(){
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                isServiceBound=true;
                binder=(MusicService.MusicServiceBinder) iBinder;
                service = binder.getService();
                setTitleOfSong();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isServiceBound=false;
            }
        };
    }
    @Override
    public void onResume() {
        super.onResume();
        initConnection();
        try {
            getActivity().registerReceiver(receiver, new IntentFilter(Consts.KEY_BROADCAST_ACTION));
            getActivity().bindService(new Intent(getActivity(), MusicService.class),
                    serviceConnection, BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        getActivity().unregisterReceiver(receiver);
        getActivity().unbindService(serviceConnection);
        super.onDetach();
    }

    private void setTitleOfSong(){
        title.setText(service.getCurrentSongTitle());
    }
}
