package com.example.android_projet.model;

public interface QuestionBank {
    public Question getNextQuestion();
    public Question getCurrentQuestion();
    public QuestionBank generateQuestion();
}
