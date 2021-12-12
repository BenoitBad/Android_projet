package com.example.android_projet.model;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class FindQuestion implements Question{
    public String question;
    public Drawable image;
    public Point answerCoords;
    public int answerRadius;

    public FindQuestion(String question, Drawable image, Point answerCoords, int answerRadius){
        this.question = question;
        this.image = image;
        this.answerCoords = answerCoords;
        this.answerRadius = answerRadius;
    }

    public String getQuestion() {
        return question;
    }

    public Drawable getImage() {
        return image;
    }

    public Point getAnswerCoords() {
        return answerCoords;
    }

    public int getAnswerRadius(){
        return answerRadius;
    }
}
