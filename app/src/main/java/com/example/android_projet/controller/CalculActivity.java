package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.Calcul;
import com.example.android_projet.model.CalculQuestion;
import com.example.android_projet.model.CalculQuestionBank;
import com.example.android_projet.model.Profile;

public class CalculActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mTextView;
    Button mCalculBtn1;
    Button mCalculBtn2;
    Button mCalculBtn3;
    Button mCalculBtn4;
    ImageButton mSoundButton;
    CalculQuestionBank mCalculQuestionBank;
    MusicController mMusicController;
    Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        mTextView = findViewById(R.id.calcul_answer_textview);
        mCalculBtn1 = findViewById(R.id.calcul_btn_1);
        mCalculBtn2 = findViewById(R.id.calcul_btn_2);
        mCalculBtn3 = findViewById(R.id.calcul_btn_3);
        mCalculBtn4 = findViewById(R.id.calcul_btn_4);
        mProfile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);

        // Music
        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic();

        // Bouton de son
        mSoundButton = findViewById(R.id.activity_calcul_imageButton_sound);
        mSoundButton.setOnClickListener(new SoundButtonListener(mMusicController));

        mCalculQuestionBank = new CalculQuestionBank();
        mCalculQuestionBank.getNextQuestion();
        displayCalcul();
        mProfile.setScore(0);
        mProfile.getStatistics().nb_game_play++;
        mProfile.getStatistics().nb_game_play_maths ++;
        mProfile.setLastGame(Const.ID_GAME_MATHS);

    }

    public void displayCalcul(){
        CalculQuestion calculQuestion = (CalculQuestion)mCalculQuestionBank.getCurrentQuestion();
        mTextView.setText(calculQuestion.getCalculString());
        mCalculBtn1.setText(calculQuestion.get(0));
        mCalculBtn2.setText(calculQuestion.get(1));
        mCalculBtn3.setText(calculQuestion.get(2));
        mCalculBtn4.setText(calculQuestion.get(3));

        mCalculBtn1.setOnClickListener(this);
        mCalculBtn2.setOnClickListener(this);
        mCalculBtn3.setOnClickListener(this);
        mCalculBtn4.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int index = -1;
        CalculQuestion calculQuestion = (CalculQuestion) mCalculQuestionBank.getCurrentQuestion();
        if (v == mCalculBtn1){
            index = 0;
        }
        else if(v == mCalculBtn2){
            index = 1;
        }
        else if(v == mCalculBtn3){
            index = 2;
        }
        else if(v == mCalculBtn4){
            index = 3;
        }
        System.out.print(index);
        if (calculQuestion.checkAnswer(index)){
            mCalculQuestionBank.getNextQuestion();
            mProfile.incrementScore();
            displayCalcul();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format(getString(R.string.game_lose), mProfile.getScore()))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            mProfile.getStatistics().nb_score_global += mProfile.getScore();
                            mProfile.getStatistics().nb_score_maths += mProfile.getScore();
                            intent.putExtra(Const.BUNDLE_EXTRA_PROFILE,mProfile);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
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