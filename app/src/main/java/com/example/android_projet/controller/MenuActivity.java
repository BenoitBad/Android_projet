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
        if (!mMusicController.getBgMusicOn()){ mSoundButton.setImageResource(R.drawable.mute_sound); }

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
                builder.setTitle(R.string.credits)
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

        // Bouton exit
        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        // R??cup??re le profile
        profile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
        System.out.println("Profile:" + profile);

        mGameList = new ArrayList<String>();
        mGameList.add(getString(R.string.memory));
        mGameList.add(getString(R.string.Find));
        mGameList.add(getString(R.string.calculation));

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


        builder.setTitle(getString(R.string.menu_select_game))
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
                                break;
                            case 2:
                                Intent calculActivityIntent = new Intent(MenuActivity.this, CalculActivity.class);
                                calculActivityIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
                                startActivityForResult(calculActivityIntent, Const.ID_GAME_MATHS);
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
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
        setResult(RESULT_OK, intent);
        finish();
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
        else if (requestCode == Const.ID_GAME_MATHS && resultCode == RESULT_OK){
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