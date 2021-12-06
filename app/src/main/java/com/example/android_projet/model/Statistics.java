package com.example.android_projet.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android_projet.Const;

public class Statistics implements Parcelable {

    private int mProfileId;

    // ### Global
    public int mNbConnection;

    public int mNbQuestionsAnswered;
    public int mNbRightAnswer;
    public int mNbWrongAnswer;

    // ### Quizz
    public int mNbRightAnswerQuizz;
    public int mNbWrongAnswerQuizz;

    // ### Memory
    public int mNbRightAnswerMemory;
    public int mNbWrongAnswerMemory;

    // ### Find (ou est Charlie)
    public int mNbRightAnswerFind;
    public int mNbWrongAnswerFind;

    public Statistics(int profileId) {
        mProfileId = profileId;
        mNbConnection = 0;
        mNbQuestionsAnswered = 0;
        mNbRightAnswer = 0;
        mNbWrongAnswer = 0;
        mNbRightAnswerQuizz = 0;
        mNbWrongAnswerQuizz = 0;
        mNbRightAnswerMemory = 0;
        mNbWrongAnswerMemory = 0;
        mNbRightAnswerFind = 0;
        mNbWrongAnswerFind = 0;
    }

    protected Statistics(Parcel in) {
        mProfileId = in.readInt();
        mNbConnection = in.readInt();
        mNbQuestionsAnswered = in.readInt();
        mNbRightAnswer = in.readInt();
        mNbWrongAnswer = in.readInt();
        mNbRightAnswerQuizz = in.readInt();
        mNbWrongAnswerQuizz = in.readInt();
        mNbRightAnswerMemory = in.readInt();
        mNbWrongAnswerMemory = in.readInt();
        mNbRightAnswerFind = in.readInt();
        mNbWrongAnswerFind = in.readInt();
    }

    public static final Creator<Statistics> CREATOR = new Creator<Statistics>() {
        @Override
        public Statistics createFromParcel(Parcel in) {
            return new Statistics(in);
        }

        @Override
        public Statistics[] newArray(int size) {
            return new Statistics[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mProfileId);

        dest.writeInt(mNbConnection);

        dest.writeInt(mNbQuestionsAnswered);
        dest.writeInt(mNbRightAnswer);
        dest.writeInt(mNbWrongAnswer);

        dest.writeInt(mNbRightAnswerQuizz);
        dest.writeInt(mNbWrongAnswerQuizz);

        dest.writeInt(mNbRightAnswerMemory);
        dest.writeInt(mNbWrongAnswerMemory);

        dest.writeInt(mNbRightAnswerFind);
        dest.writeInt(mNbWrongAnswerFind);
    }
}
