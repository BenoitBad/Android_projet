package com.example.android_projet.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.FindQuestion;
import com.example.android_projet.model.FindQuestionBank;
import com.example.android_projet.model.Profile;
import com.example.android_projet.model.Question;
import com.example.android_projet.model.QuestionBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindActivity extends AppCompatActivity {

    private TextView mQuestion;
    private ImageView mImageAnswer;
    private ImageButton mSoundButton;

    private MusicController mMusicController;

    private Profile profile;

    private QuestionBank mQuestionBank;
    private int nbQuestionPerGame = 5;
    private int nbQuestionAnswered = 0;
    private boolean mEnableTouchEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        // Music
        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);
        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic();

        // Bouton de son
        mSoundButton = findViewById(R.id.activity_find_imageButton_sound);
        mSoundButton.setOnClickListener(new SoundButtonListener(mMusicController));

        mImageAnswer = findViewById(R.id.activity_find_answer_image);
        mQuestion = findViewById(R.id.activity_find_question);

        // Récupère le profile
        profile = getIntent().getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
        profile.setScore(0);
        profile.setLastGame(Const.ID_GAME_FIND);
        profile.getStatistics().nb_game_play++;
        profile.getStatistics().nb_game_play_find++;

        mImageAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mEnableTouchEvent = false;
                    nbQuestionAnswered++;
                    FindQuestion question = (FindQuestion) mQuestionBank.getCurrentQuestion();
                    double xAnswer = question.getAnswerCoords().x;
                    double yAnswer = question.getAnswerCoords().y;
                    int radiusAnswer = question.getAnswerRadius();
                    ImageView imgView = (ImageView) v;
                    float ratioX = 1000f / (float) imgView.getMeasuredWidth();
                    float ratioY = 1000f / (float) imgView.getMeasuredHeight();
                    System.out.println("Click en : x = " + event.getX() * ratioX + " y = " + event.getY() * ratioY);
                    if(Math.sqrt(Math.pow(event.getX()*ratioX - xAnswer,2) + Math.pow(event.getY() * ratioY - yAnswer,2)) < radiusAnswer){
                        profile.incrementScore();
                        if(nbQuestionAnswered == nbQuestionPerGame){
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                            builder.setTitle("You win ! You had " + profile.getScore() + " points ! ")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            backToMenu();
                                        }
                                    })
                                    .create()
                                    .show();
                            return true;
                        }
                        loadQuestion((FindQuestion) mQuestionBank.getNextQuestion());
                        return true;
                    }
                    System.out.println("PERDU");
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                    builder.setTitle("You lose ! You had " + profile.getScore() + " points ! ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    backToMenu();
                                }
                            })
                            .create()
                            .show();
                }
                return false;
            }
        });

        mQuestionBank = generateQuestions();
        loadQuestion((FindQuestion) mQuestionBank.getCurrentQuestion());
    }

    private void backToMenu(){
        Intent intent = new Intent();
        profile.getStatistics().nb_score_find += profile.getScore();
        profile.getStatistics().nb_score_global += profile.getScore();
        intent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profile);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        return mEnableTouchEvent && super.dispatchTouchEvent(ev);
    }

    private QuestionBank generateQuestions(){
        TypedArray questionsTitle = getResources().obtainTypedArray(R.array.questionTitleList);
        TypedArray questionsImages = getResources().obtainTypedArray(R.array.questionDrawableList);
        TypedArray questionsAnswerCoord = getResources().obtainTypedArray(R.array.questionAnswerCoord);
        TypedArray questionsAnswerRadius = getResources().obtainTypedArray(R.array.questionAnswerRadius);

        int nbQuestionStored = questionsTitle.length();
        ArrayList<Question> questionList = new ArrayList<Question>();
        Random random = new Random();
        for(int i=0; i < nbQuestionPerGame; i++){
            int questionSelected = random.nextInt(nbQuestionStored);

            String question = questionsTitle.getString(questionSelected);
            Drawable image = questionsImages.getDrawable(questionSelected);

            String[] stringCoords = questionsAnswerCoord.getString(questionSelected).split(" ");
            int xCoord = Integer.parseInt(stringCoords[0]);
            int yCoord = Integer.parseInt(stringCoords[1]);
            Point answerCoords = new Point(xCoord, yCoord);

            int answerRadius = questionsAnswerRadius.getInt(questionSelected, 20);

            questionList.add(new FindQuestion(question, image, answerCoords, answerRadius));
        }

        return new FindQuestionBank(questionList);
    }

    private void loadQuestion(FindQuestion question){
        mQuestion.setText(question.getQuestion());
        mImageAnswer.setImageDrawable(question.getImage());
        mEnableTouchEvent = true;
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
