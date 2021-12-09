package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.Profile;
import com.example.android_projet.service.MusicService;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {

    Button mMenuButtonPlay;
    Button mButtonStat;
    Button mButtonCredit;
    Button mButtonExit;
    ArrayList<String> mGameList;

    boolean mBgMusicOn;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mMenuButtonPlay = findViewById(R.id.activity_menu_button_play);
        mButtonStat = findViewById(R.id.activity_menu_button_stat);
        mButtonCredit = findViewById(R.id.activity_menu_button_credit);
        mButtonExit = findViewById(R.id.activity_menu_button_exit);

        // Récupère le profile
        profile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
        // ### DEBUG
        System.out.println(profile);

        mGameList = new ArrayList<String>();
        mGameList.add("Color Memory");

        mBgMusicOn = true; // TODO: Valeur à recupérer dans les sharedPreferences

        mMenuButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGameList();
            }
        });
    }
    private void showGameList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] s = Arrays.copyOf(mGameList.toArray(), mGameList.toArray().length, String[].class);


        builder.setTitle("Well done!")
                .setTitle("Your score is " + 2)
                .setItems(s, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gameActivityIntent = new Intent(MenuActivity.this, MemoryActivity.class);
                        switch(which){
                            case 0:
                                //startActivity(gameActivityIntent);
                                break;
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
    protected void onStop() {
        super.onStop();
        if(mBgMusicOn){
            Intent pauseMusicIntent = new Intent(this, MusicService.class);
            pauseMusicIntent.putExtra(Const.MUSIC_SERVICE_ACTION, Const.PAUSE_MUSIC);
            startService(pauseMusicIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mBgMusicOn){
            Intent resumeMusicIntent = new Intent(this, MusicService.class);
            resumeMusicIntent.putExtra(Const.MUSIC_SERVICE_ACTION, Const.RESUME_MUSIC);
            startService(resumeMusicIntent);
        }
    }
}