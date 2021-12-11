package com.example.android_projet.controller;

import android.content.Context;
import android.view.View;

public class SoundButtonListener implements View.OnClickListener {

    MusicController mMusicController;

    SoundButtonListener(MusicController musicController){
        this.mMusicController = musicController;
    }

    @Override
    public void onClick(View v) {
        mMusicController.toggleMusic();
    }
}
