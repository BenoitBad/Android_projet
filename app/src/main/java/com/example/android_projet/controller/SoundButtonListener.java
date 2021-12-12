package com.example.android_projet.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.example.android_projet.R;

public class SoundButtonListener implements View.OnClickListener {

    MusicController mMusicController;

    SoundButtonListener(MusicController musicController){
        this.mMusicController = musicController;
    }

    @Override
    public void onClick(View v) {
        mMusicController.toggleMusic();
        ImageButton b = (ImageButton) v;
        if (mMusicController.getBgMusicOn()){
            b.setImageResource(R.drawable.sound);
        }
        else{
            b.setImageResource(R.drawable.mute_sound);
        }



    }
}
