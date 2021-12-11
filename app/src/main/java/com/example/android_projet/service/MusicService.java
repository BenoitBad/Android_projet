package com.example.android_projet.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.example.android_projet.Const;

public class MusicService extends Service {
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra(Const.MUSIC_SERVICE_ACTION);
        if(player != null){
            switch (action){
                case Const.PAUSE_MUSIC:
                    player.pause();
                    break;
                case Const.RESUME_MUSIC:
                    player.start();
                    break;
            }
        } else { // Si premier lancement lance la musique
            System.out.println("Premier lancement du service");
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            player.setLooping(true);
            player.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
    }

}
