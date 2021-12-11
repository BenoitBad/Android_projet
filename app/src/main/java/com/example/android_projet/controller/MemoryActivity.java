package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_projet.R;
import com.example.android_projet.model.ColorSequenceBank;

public class MemoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button mButtonBlue;
    Button mButtonRed;
    Button mButtonYellow;
    Button mButtonGreen;
    ColorSequenceBank mColorSequenceBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        mColorSequenceBank = new ColorSequenceBank();

        mButtonBlue = findViewById(R.id.memory_blue_btn);
        mButtonRed = findViewById(R.id.memory_red_btn);
        mButtonYellow = findViewById(R.id.memory_yellow_btn);
        mButtonGreen = findViewById(R.id.memory_green_btn);

        mButtonBlue.setOnClickListener(this);
        mButtonRed.setOnClickListener(this);
        mButtonYellow.setOnClickListener(this);
        mButtonGreen.setOnClickListener(this);
        mColorSequenceBank.getCurrentQuestion();
    }

    public void displaySequence(){
        
    }

    public int getButtonColor(View v){
        int id = 0;
        if (v == mButtonBlue) {
            id = 1;
        }
        if (v == mButtonRed) {
            id = 2;
        }
        if (v == mButtonYellow) {
            id = 3;
        }
        if (v == mButtonGreen) {
            id = 4;
        }
        return id;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        int id = getButtonColor(v);

        System.out.println();
    }
}