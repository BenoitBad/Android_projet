package com.example.android_projet.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android_projet.Const;

public class ProfileSelectionButtonListener implements View.OnClickListener {
    private Context mContext;

    ProfileSelectionButtonListener(Context context){
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        Intent profileIntent = new Intent(mContext, ProfileSelectionActivity.class);
        mContext.startActivity(profileIntent);
    }
}
