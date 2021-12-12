package com.example.android_projet.model;

import java.util.List;

public class ColorSequenceBank implements QuestionBank{
    private ColorSequence cs;
    private int currentDifficulty;

    public ColorSequenceBank(){
        cs  = new ColorSequence();
        cs.shuffle(1);
        currentDifficulty = 1;
    }


    @Override
    public Question getNextQuestion() {
        System.out.println("Called");
        return cs.shuffle(++currentDifficulty);
    }

    @Override
    public Question getCurrentQuestion() {
        return cs;
    }

    @Override
    public void generateQuestion(List<Question> list) {}
}
