package com.example.android_projet.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android_projet.Const;
import com.example.android_projet.R;
import com.example.android_projet.model.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ProfileSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    Button mProfile1;
    Button mProfile2;
    Button mProfile3;
    Button mProfile4;

    ArrayList<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);

        profileList = new ArrayList<Profile>();

        Gson gson = new Gson();
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 1, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 2, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 3, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 4, null), Profile.class));

        // Tag = numéro du bouton
        mProfile1 = findViewById(R.id.profileSelection_activity_button_profile1);
        mProfile1.setTag(1);
        if(profileList.get(0) != null){
            mProfile1.setText(profileList.get(0).getNickName());
            mProfile1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        mProfile2 = findViewById(R.id.profileSelection_activity_button_profile2);
        mProfile2.setTag(2);
        if(profileList.get(1) != null){
            mProfile2.setText(profileList.get(1).getNickName());
            mProfile2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        mProfile3 = findViewById(R.id.profileSelection_activity_button_profile3);
        mProfile3.setTag(3);
        if(profileList.get(2) != null){
            mProfile3.setText(profileList.get(2).getNickName());
            mProfile3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        mProfile4 = findViewById(R.id.profileSelection_activity_button_profile4);
        mProfile4.setTag(4);
        if(profileList.get(3) != null){
            mProfile4.setText(profileList.get(3).getNickName());
            mProfile4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        mProfile1.setOnClickListener(this);
        mProfile2.setOnClickListener(this);
        mProfile3.setOnClickListener(this);
        mProfile4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);
        int btnNumber = (int) v.getTag();

        Profile profileSelected = profileList.get(btnNumber-1);
        if(profileSelected != null){
            GoToMenu(btnNumber);
        } else {
            EditText input = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Entrez votre nom")
                    .setMessage("Nom :")
                    .setView(input)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = input.getText().toString();
                            // Crée le nouveau profil avec le nom et l'id séléctionné puis le passe en Json pour le stocker dans les sharedPreferences
                            profileList.set(btnNumber-1, new Profile(btnNumber, name));
                            String profileJson = new GsonBuilder().create()
                                    .toJson(profileList.get(btnNumber-1));
                            preferences.edit()
                                    .putString(Const.PROFILE_JSON_nb + btnNumber, profileJson)
                                    .commit();
                            GoToMenu(btnNumber);
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        }
    }

    private void GoToMenu(int idProfileSelected){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        menuIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profileList.get(idProfileSelected-1));
        startActivity(menuIntent);
    }
}