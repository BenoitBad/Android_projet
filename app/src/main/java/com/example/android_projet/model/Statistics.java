package com.example.android_projet.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android_projet.Const;

public class Statistics implements Parcelable {

    private int mProfileId;

    // Global
    public int nb_connection;

    // Nombre de parties jouées
    public int nb_game_play;
    public int nb_game_play_find;
    public int nb_game_play_memory;
    public int nb_game_play_maths;

    // Score total
    public int nb_score_global;
    public int nb_score_memory;
    public int nb_score_find;
    public int nb_score_maths;

    public Statistics(int profileId) {
        mProfileId = profileId;
        nb_connection = 0;
        nb_game_play = 0;
        nb_game_play_find = 0;
        nb_game_play_memory = 0;
        nb_game_play_maths = 0;
        nb_score_global = 0;
        nb_score_memory = 0;
        nb_score_find = 0;
        nb_score_maths = 0;
    }

    protected Statistics(Parcel in) {
        mProfileId = in.readInt();
        nb_connection = in.readInt();
        nb_game_play = in.readInt();
        nb_game_play_find = in.readInt();
        nb_game_play_memory = in.readInt();
        nb_game_play_maths = in.readInt();
        nb_score_global = in.readInt();
        nb_score_memory = in.readInt();
        nb_score_find = in.readInt();
        nb_score_maths = in.readInt();
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
        dest.writeInt(nb_connection);

        dest.writeInt(nb_game_play);
        dest.writeInt(nb_game_play_find);
        dest.writeInt(nb_game_play_memory);
        dest.writeInt(nb_game_play_maths);

        dest.writeInt(nb_score_global);
        dest.writeInt(nb_score_memory);
        dest.writeInt(nb_score_find);
        dest.writeInt(nb_score_maths);
    }
}
