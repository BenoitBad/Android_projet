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
        if (currentIndex >= mColorArrayList.size()){
            return false;
        }
        return mColorArrayList.get(currentIndex++) == id;
    }
    public boolean isFinished(){
        return currentIndex == mColorArrayList.size();
    }

    public ColorSequence shuffle(int difficulty) {
        mColorArrayList.clear();
        currentIndex = 0;
        Random rand = new Random();
        for (int i = 0; i < difficulty ; i++) {
            int randomNum = rand.nextInt((3 - 0) + 1);
            mColorArrayList.add(randomNum);
        }
        return this;
    }

    public int getColorId(int i){
        return mColorArrayList.get(i);
    }

    public int size(){
        return mColorArrayList.size();
    }


}
