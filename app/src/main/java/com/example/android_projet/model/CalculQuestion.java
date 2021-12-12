package com.example.android_projet.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CalculQuestion  implements Question{

    private Calcul mCalcul;
    private ArrayList<String> manswers;
    private int answerId;

    public CalculQuestion(){
        mCalcul = new Calcul();
        manswers = new ArrayList<>();
    }

    public CalculQuestion getCalculQuestion(int difficulty){
        manswers.clear();
        mCalcul.generateCalcul(difficulty);
        Calcul wrongAnswer = new Calcul();

        int answer = mCalcul.getResult();
        answerId = (int)(Math.random() * 4);
        int i = 0;
        while (i < 4){
            wrongAnswer.generateCalcul(difficulty);
            if (answer == wrongAnswer.getResult()){
                continue;
            }
            if (answerId == i){
                manswers.add(Integer.toString(answer));
            }
            else{
                manswers.add(Integer.toString(wrongAnswer.getResult()));
            }
            i++;

        }

        return this;
    }

    public String get(int i){
        return manswers.get(i);
    }

    public boolean checkAnswer(int i){
        return answerId == i;
    }

    public String getCalculString(){
        return mCalcul.toString();
    }
}
