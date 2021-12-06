package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_projet.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    Button mMenuButtonPlay;
    Button mButtonStat;
    Button mButtonCredit;
    Button mButtonExit;
    ArrayList<String> mGameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mMenuButtonPlay = findViewById(R.id.activity_menu_button_play);
        mButtonStat = findViewById(R.id.activity_menu_button_stat);
        mButtonCredit = findViewById(R.id.activity_menu_button_credit);
        mButtonExit = findViewById(R.id.activity_menu_button_exit);

        mGameList = new ArrayList<String>();

        mMenuButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGameList();
            }
        });
    }
    private void showGameList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + 2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

}