package com.example.android_projet.controller;

import androidx.annotation.Nullable;
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
import android.widget.ImageButton;

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

    ImageButton mSoundButton;

    MusicController mMusicController;

    ArrayList<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);
        SharedPreferences musicPreferences = getSharedPreferences(Const.MUSIC_INFO, MODE_PRIVATE);

        profileList = new ArrayList<Profile>();

        mMusicController = MusicController.getInstance(musicPreferences.getBoolean(Const.MUSIC_ON, true), this);
        mMusicController.startMusic(); // Start music only if ON

        // Bouton de son
        mSoundButton = findViewById(R.id.profileSelection_activity_imageButton_sound);
        mSoundButton.setOnClickListener(new SoundButtonListener(this.mMusicController));

        getProfileList();

        // Tag = numéro du bouton
        mProfile1 = findViewById(R.id.profileSelection_activity_button_profile1);
        mProfile1.setTag(1);
        mProfile2 = findViewById(R.id.profileSelection_activity_button_profile2);
        mProfile2.setTag(2);
        mProfile3 = findViewById(R.id.profileSelection_activity_button_profile3);
        mProfile3.setTag(3);
        mProfile4 = findViewById(R.id.profileSelection_activity_button_profile4);
        mProfile4.setTag(4);

        refreshProfiles();

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
            builder.setTitle(R.string.profileSelection_enter_name)
                    .setMessage(R.string.profileSelection_name)
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

    public void refreshProfiles(){
        if(profileList.get(0) != null){
            mProfile1.setText(profileList.get(0).getNickName());
            mProfile1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        if(profileList.get(1) != null){
            mProfile2.setText(profileList.get(1).getNickName());
            mProfile2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        if(profileList.get(2) != null){
            mProfile3.setText(profileList.get(2).getNickName());
            mProfile3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        if(profileList.get(3) != null){
            mProfile4.setText(profileList.get(3).getNickName());
            mProfile4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }

    public void getProfileList(){
        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);
        Gson gson = new Gson();
        profileList.clear();
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 1, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 2, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 3, null), Profile.class));
        profileList.add(gson.fromJson(preferences.getString(Const.PROFILE_JSON_nb + 4, null), Profile.class));
    }

    private void GoToMenu(int idProfileSelected){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        menuIntent.putExtra(Const.BUNDLE_EXTRA_PROFILE, profileList.get(idProfileSelected-1));
        startActivityForResult(menuIntent, Const.ID_MENU);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMusicController.forceStopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMusicController.startMusic();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences preferences = getSharedPreferences(Const.PROFILES_INFO, MODE_PRIVATE);
        Profile profile;
        if (requestCode == Const.ID_MENU && resultCode == RESULT_OK) {
            profile = data.getParcelableExtra(Const.BUNDLE_EXTRA_PROFILE);
            String profileJson = new GsonBuilder().create()
                    .toJson(profile);
            preferences.edit()
                    .putString(Const.PROFILE_JSON_nb + profile.getId(), profileJson)
                    .commit();
        }
        getProfileList();
        refreshProfiles();
        super.onActivityResult(requestCode, resultCode, data);
    }
}