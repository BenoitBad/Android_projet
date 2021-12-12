package com.example.android_projet.model;

import java.util.List;

public interface QuestionBank {
    public Question getNextQuestion();
    public Question getCurrentQuestion();
    public void generateQuestion(List<Question> list);
}
