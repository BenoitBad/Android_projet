package com.example.android_projet.model;

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
    public QuestionBank generateQuestion() {
        return null;
    }
}
