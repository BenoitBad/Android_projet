package com.example.android_projet.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.Profile;
import com.example.android_projet.service.MusicService;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {

    Button mMenuButtonPlay;
    Button mButtonStat;
    Button mButtonCredit;
    Button mButtonExit;
    ImageButton mSoundButton;
    ImageButton mProfileSelectionButton;
    ArrayList<String> mGameList;

    Profile profile;
    MusicController mMusicController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mMenuButtonPlay = findViewById(R.id.activity_menu_button_play);
        mButtonStat = findViewById(R.id.activity_menu_button_stat);
        mButtonCredit = findViewById(R.id.activity_menu_button_credit);
        mButtonExit = findViewById(R.id.activity_menu_button_exit);


        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic();

        // Bouton de son
        mSoundButton = findViewById(R.id.activity_menu_imageButton_sound);
        mSoundButton.setOnClickListener(new SoundButtonListener(mMusicController));

        // Bouton selection du profil
        mProfileSelectionButton = findViewById(R.id.activity_menu_imageButton_profileSelection);
        mProfileSelectionButton.setOnClickListener(new ProfileSelectionButtonListener(this));

        // Bouton stats
        mButtonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statsIntent = new Intent(MenuActivity.this, StatisticsActivity.class);
                statsIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
                startActivity(statsIntent);
            }
        });

        // Bouton credits
        mButtonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Créé par Benoit SECHEHAYE, Loïc FAISY et Davy MU")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        // Récupère le profile
        profile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
        System.out.println("Profile:" + profile);

        mGameList = new ArrayList<String>();
        mGameList.add("Color Memory");
        mGameList.add("Find");

        mMenuButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGameList();
            }
        });
    }
    private void showGameList(){
        System.out.println("Profile:" + profile);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] s = Arrays.copyOf(mGameList.toArray(), mGameList.toArray().length, String[].class);


        builder.setTitle("Choisis ton mode de jeu !")
                .setItems(s, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent gameActivityIntent = new Intent(MenuActivity.this, MemoryActivity.class);
                                gameActivityIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
                                startActivityForResult(gameActivityIntent,Const.ID_GAME_MEMORY);
                                break;
                            case 1:
                                Intent findActivityIntent = new Intent(MenuActivity.this, FindActivity.class);
                                findActivityIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
                                startActivityForResult(findActivityIntent, Const.ID_GAME_FIND);
                        }

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);
        if (requestCode == Const.ID_GAME_MEMORY && resultCode == RESULT_OK) {
            profile = data.getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
            String profileJson = new GsonBuilder().create()
                    .toJson(profile);
            preferences.edit()
                    .putString(Const.PROFILE_JSON_nb + profile.getId(), profileJson)
                    .commit();
        } else if (requestCode == Const.ID_GAME_FIND && resultCode == RESULT_OK){
            profile = data.getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
            String profileJson = new GsonBuilder().create()
                    .toJson(profile);
            preferences.edit()
                    .putString(Const.PROFILE_JSON_nb + profile.getId(), profileJson)
                    .commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}