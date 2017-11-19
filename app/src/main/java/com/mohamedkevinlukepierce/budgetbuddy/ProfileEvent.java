package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileEvent extends AppCompatActivity { //Profile Objects


    public static final String SHARED_PREFS = "ProfileNames";

    public static EditText profileUserText;
    private ImageButton addProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        SharedPreferences sharedProfiles = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE); //Where data for profiles is saved
        SharedPreferences.Editor editor = sharedProfiles.edit(); //Data editor

        profileUserText = (EditText) findViewById(R.id.profileUserText);
        addProfileBtn = (ImageButton) findViewById(R.id.addProfileSelect);

        profileUserText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(profileUserText.length() <= 0 || profileUserText.length() > 20)
                    addProfileBtn.setVisibility(View.INVISIBLE);
                else {
                    addProfileBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        addProfileBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        SharedPreferences sharedProfiles = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedProfiles.edit();
                        if (ProfileActivity.state == 1)
                            editor.putString("profile1", profileUserText.getText().toString()); //Saving user input to data
                        else if (ProfileActivity.state == 2)
                            editor.putString("profile2", profileUserText.getText().toString()); //Saving user input to data
                        else if (ProfileActivity.state == 3)
                            editor.putString("profile3", profileUserText.getText().toString()); //Saving user input to data
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(),  ProfileActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}