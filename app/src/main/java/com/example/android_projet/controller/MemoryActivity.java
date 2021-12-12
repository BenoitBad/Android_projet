package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.ColorSequence;
import com.example.android_projet.model.ColorSequenceBank;
import com.example.android_projet.model.Profile;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MemoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button mButtonBlue;
    Button mButtonRed;
    Button mButtonYellow;
    Button mButtonGreen;
    ColorSequenceBank mColorSequenceBank;
    ArrayList<Button> buttonId;
    Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        mColorSequenceBank = new ColorSequenceBank();
        buttonId = new ArrayList<Button>();
        // Récupère le profile
        mProfile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);

        mButtonBlue = findViewById(R.id.memory_blue_btn);
        mButtonRed = findViewById(R.id.memory_red_btn);
        mButtonYellow = findViewById(R.id.memory_yellow_btn);
        mButtonGreen = findViewById(R.id.memory_green_btn);

        buttonId.add(mButtonBlue);
        buttonId.add(mButtonRed);
        buttonId.add(mButtonYellow);
        buttonId.add(mButtonGreen);

        mButtonBlue.setOnClickListener(this);
        mButtonRed.setOnClickListener(this);
        mButtonYellow.setOnClickListener(this);
        mButtonGreen.setOnClickListener(this);
        displaySequence();
        mProfile.setScore(0);
        mProfile.getStatistics().nb_game_play++;
        mProfile.getStatistics().nb_game_play_memory ++;
        mProfile.setLastGame(Const.ID_GAME_MEMORY);
    }


    public void manageStateButton(boolean state){
        for (Button b : buttonId){
            b.setEnabled(state);
        }
    }
    public void displaySequence(){
        manageStateButton(false);
        Handler handler = new Handler();
        new CountDownTimer(1000, 50) {

            @Override
            public void onTick(long arg0) {

            }

            @Override
            public void onFinish() {

                ColorSequence c = (ColorSequence) mColorSequenceBank.getCurrentQuestion();
                for (int i = 0;i < c.size();i++){
                    Button b = buttonId.get(c.getColorId(i));
                    int highLightColor = getHighlightColorFromId(c.getColorId(i));
                    int color = getButtonColor(c.getColorId(i));
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            b.setBackgroundColor(highLightColor);
                        }
                    }, 500 * ((long)i) + 200*i);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            b.setBackgroundColor(color);
                        }
                    }, 500 * ((long)i+1) + 200*i);

                }
                handler.postDelayed(new Runnable() {
                    public void run() {
                        manageStateButton(true);
                    }
                }, 500 * ((long)c.size()) + 200*c.size());

            }
        }.start();



    }

    public int getHighlightColorFromId(int id){
        int color = 0;
        switch (id){
            case 0:
                return ContextCompat.getColor(this,R.color.lightBlue_highlight);
            case 1:
                return ContextCompat.getColor(this,R.color.red_highlight);
            case 2:
                return ContextCompat.getColor(this,R.color.yellow_highlight);
            case 3:
                return ContextCompat.getColor(this,R.color.green_highlight);
            default:
                return 0;
        }
    }

    public int getButtonColor(int id){
        switch (id){
            case 0:
                return ContextCompat.getColor(this,R.color.lightBlue);
            case 1:
                return ContextCompat.getColor(this,R.color.red);
            case 2:
                return ContextCompat.getColor(this,R.color.yellow);
            case 3:
                return ContextCompat.getColor(this,R.color.green);
            default:
                return 0;
        }
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        ColorSequence c = (ColorSequence) mColorSequenceBank.getCurrentQuestion();
        int id = buttonId.indexOf(v);
        boolean check = c.checkSequenceColor(id);
        if (check && c.isFinished()){
            mColorSequenceBank.getNextQuestion();
            displaySequence();
            mProfile.incrementScore();
        }
        else if (!check){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You lose ! You had " + mProfile.getScore() + " points ! ")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            mProfile.getStatistics().nb_score_global += mProfile.getScore();
                            mProfile.getStatistics().nb_score_memory += mProfile.getScore();
                            intent.putExtra(Const.BUNDLE_EXTRA_PROFILE,mProfile);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }

    }
}