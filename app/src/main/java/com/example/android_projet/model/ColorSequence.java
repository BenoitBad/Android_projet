package com.example.android_projet.model;

import java.util.Random;
import java.util.ArrayList;

public class ColorSequence implements Question  {
    private ArrayList<Integer> mColorArrayList;
    private int currentIndex;

    public ColorSequence(){
        mColorArrayList = new ArrayList<Integer>();
        currentIndex = 0;
    }

    public ArrayList<Integer> getCurrentSequence(){
        return mColorArrayList;
    }

    public boolean checkSequenceColor(int id){
        return mColorArrayList.get(currentIndex++) == id;
    }

    public ColorSequence shuffle(int difficulty) {
        Random rand = new Random();
        for (int i = 0; i < difficulty ; i++) {
            int randomNum = rand.nextInt((3 - 0) + 1);
            mColorArrayList.add(randomNum);
        }
        return this;
    }


}
