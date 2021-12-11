package com.example.android_projet.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.android_projet.Const;
import com.example.android_projet.R;

public class FindActivity extends Activity {

    ImageView mImageAnswer;

    MusicController mMusicController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        // Music
        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic();

        //mImageAnswer = findViewById(R.id.activity_find_answer_image);
        //TypedArray questionsTitle = getResources().obtainTypedArray(R.array.questionTitleList);
        //TypedArray questionsImages = getResources().obtainTypedArray(R.array.questionDrawableList);
        //@SuppressLint("ResourceType") Drawable image = questionsImages.getDrawable(0);
        //mImageAnswer.setImageDrawable(image);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMusicController.forceStopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMusicController.startMusic();
    }
}
