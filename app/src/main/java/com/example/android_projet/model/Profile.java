package com.example.android_projet.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
    private String mNickName;
    private int mScore;
    private int mLastGame;

    private final int mId;

    // Ce constructeur devrait être utilisé uniquement dans l'écran de séléction du profil et ensuite l'objet sera passé via parcelable
    public Profile(int id, String nickName) {
        mId = id;
        mNickName = nickName;
        mScore = -1;
        mLastGame = -1;
    }

    public Profile(Parcel in) {
        mId = in.readInt();
        mNickName = in.readString();
        mScore = in.readInt();
        mLastGame = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public int getId() { return mId; }

    public String getNickName() {
        return mNickName;
    }

    public int getScore() {
        return mScore;
    }

    public int getLastGame() { return mLastGame; }

    public void setScore(int score) {
        mScore = score;
    }

    public void incrementScore(){ mScore++; }

    public void setNickName(String NickName) {
        this.mNickName = NickName;
    }

    public void setLastGame(int game_id) { this.mLastGame = game_id; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNickName);
        dest.writeInt(mScore);
        dest.writeInt(mLastGame);
    }

    @Override
    public String toString(){
        return "Profile [" + mId + " : " + mNickName + ", " + mLastGame + ", " + mScore + "]";
    }
}
