package com.example.android_projet.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.android_projet.Const;
import com.example.android_projet.service.MusicService;

public class MusicController {
    private static MusicController instance;

    private boolean mBgMusicOn;
    private Context mContext;

    private MusicController(boolean bgMusicOn, Context context){
        this.mBgMusicOn = bgMusicOn;
        this.mContext = context;
    }

    public static MusicController getInstance(boolean bgMusicOn, Context context){
        if(instance == null) {
            instance = new MusicController(bgMusicOn, context);
        }
        return instance;
    }

    public void savePreferences(){
        SharedPreferences musicPreferences = mContext.getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        musicPreferences.edit().putBoolean(Const.MUSIC_ON, mBgMusicOn).commit();
        System.out.println("pref saved");
    }

    public void toggleMusic(){
        if(mBgMusicOn){
            System.out.println("Toggle stop");
            disableMusic();
        } else {
            System.out.println("Toggle start");
            enableMusic();
        }
    }

    public void enableMusic(){
        mBgMusicOn = true;
        savePreferences();
        startMusic();
    }

    public void disableMusic(){
        mBgMusicOn = false;
        savePreferences();
        stopMusic();
    }

    public void startMusic(){
        if(mBgMusicOn){
            System.out.println("Reprise de la musique");
            Intent resumeMusicIntent = new Intent(mContext, MusicService.class);
            resumeMusicIntent.putExtra(Const.MUSIC_SERVICE_ACTION, Const.RESUME_MUSIC);
            mContext.startService(resumeMusicIntent);
        }
    }

    public void stopMusic(){
        if(!mBgMusicOn){
            System.out.println("Pause de la musique");
            Intent pauseMusicIntent = new Intent(mContext, MusicService.class);
            pauseMusicIntent.putExtra(Const.MUSIC_SERVICE_ACTION, Const.PAUSE_MUSIC);
            mContext.startService(pauseMusicIntent);
        }
    }

    public boolean getBgMusicOn(){
        return mBgMusicOn;
    }

    public void forceStopMusic(){
        System.out.println("Pause de la musique");
        Intent pauseMusicIntent = new Intent(mContext, MusicService.class);
        pauseMusicIntent.putExtra(Const.MUSIC_SERVICE_ACTION, Const.PAUSE_MUSIC);
        mContext.startService(pauseMusicIntent);
        mContext.startService(pauseMusicIntent);
    }
}
