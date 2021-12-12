package com.example.android_projet.model;

import java.util.List;

public class CalculQuestionBank implements  QuestionBank{
    private CalculQuestion cq;
    private int currentDifficulty;
    private int level;

    public CalculQuestionBank(){
        cq = new CalculQuestion();
        level = 5;
    }
    @Override
    public Question getNextQuestion() {

        return cq.getCalculQuestion((int)((++currentDifficulty)/level)+1);
    }

    @Override
    public Question getCurrentQuestion() {
        return cq;
    }

    @Override
    public void generateQuestion(List<Question> list) {

    }
}
