package com.example.android_projet.model;

import android.content.res.TypedArray;

import com.example.android_projet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindQuestionBank implements QuestionBank{
    private List<Question> mQuestions;
    private int mCurrentQuestionIndex;

    public FindQuestionBank(List<Question> questionList){
        generateQuestion(questionList);
    }

    @Override
    public Question getNextQuestion() {
        mCurrentQuestionIndex++;
        return getCurrentQuestion();
    }

    @Override
    public Question getCurrentQuestion() {
        return mQuestions.get(mCurrentQuestionIndex);
    }

    @Override
    public void generateQuestion(List<Question> list) {
        Collections.shuffle(list);
        mQuestions = list;
    }
}
