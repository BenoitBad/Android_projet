package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.Profile;
import com.example.android_projet.model.Statistics;

public class StatisticsActivity extends AppCompatActivity {

    private ImageButton mBackButton;
    private ImageButton mSoundButton;

    private MusicController mMusicController;

    private Profile profile;

    public TextView nb_game_play;
    public TextView nb_game_play_find;
    public TextView nb_game_play_memory;
    public TextView nb_game_play_maths;
    public TextView nb_score_global;
    public TextView nb_score_memory;
    public TextView nb_score_find;
    public TextView nb_score_maths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mBackButton = findViewById(R.id.activity_statistics_imageButton_back);
        mSoundButton = findViewById(R.id.activity_statistics_imageButton_sound);

        nb_game_play = findViewById(R.id.activity_statistics_nb_game_play);
        nb_game_play_find = findViewById(R.id.activity_statistics_nb_game_play_find);
        nb_game_play_memory = findViewById(R.id.activity_statistics_nb_game_play_memory);
        nb_game_play_maths = findViewById(R.id.activity_statistics_nb_game_play_maths);
        nb_score_global = findViewById(R.id.activity_statistics_nb_score_global);
        nb_score_memory = findViewById(R.id.activity_statistics_nb_score_memory);
        nb_score_find = findViewById(R.id.activity_statistics_nb_score_find);
        nb_score_maths = findViewById(R.id.activity_statistics_nb_score_maths);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic();
        mSoundButton.setOnClickListener(new SoundButtonListener(mMusicController));

        profile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
        System.out.println(profile);
        Statistics stats = profile.getStatistics();

        if(stats != null){
            nb_game_play.append(" " + stats.nb_game_play);
            nb_game_play_find.append(" " + stats.nb_game_play_find);
            nb_game_play_memory.append(" " + stats.nb_game_play_memory);
            nb_game_play_maths.append(" " + stats.nb_game_play_maths);
            nb_score_global.append(" " + stats.nb_game_play_maths);
            nb_score_memory.append(" " + stats.nb_score_memory);
            nb_score_find.append(" " + stats.nb_score_find);
            nb_score_maths.append(" " + stats.nb_score_maths);
        }
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