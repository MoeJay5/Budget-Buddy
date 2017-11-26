package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity{ //profile selection logic
    public static Button profile1, profile2, profile3;
    public static int state;
    public static final String SHARED_PREFS = "ProfileNames";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);
        SharedPreferences profiles = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        profile1 = (Button) findViewById(R.id.profileButton1);
        profile1.setText(profiles.getString("profile1", "Profile 1" )); //Calling from saved data, if non exist then return "Profile 1"

        profile2 = (Button) findViewById(R.id.profileButton2);
        profile2.setText(profiles.getString("profile2", "Profile 2" )); //Calling from saved data, if non exist then return "Profile 2"

        profile3 = (Button) findViewById(R.id.profileButton3);
        profile3.setText(profiles.getString("profile3", "Profile 3" )); //Calling from saved data, if non exist then return "Profile 3"

        ImageButton profile1Edit = (ImageButton) findViewById(R.id.editButton1);
        ImageButton profile2Edit = (ImageButton) findViewById(R.id.editButton2);
        ImageButton profile3Edit = (ImageButton) findViewById(R.id.editButton3);

        profile1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        profile2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),  LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        profile3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),  LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

        profile1Edit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 1;
                        Intent intent = new Intent(getApplicationContext(),  ProfileEvent.class);
                        startActivity(intent);
                    }
                }
        );
        profile2Edit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 2;
                        Intent intent = new Intent(getApplicationContext(),  ProfileEvent.class);
                        startActivity(intent);
                    }
                }
        );
        profile3Edit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 3;
                        Intent intent = new Intent(getApplicationContext(),  ProfileEvent.class);
                        startActivity(intent);
                    }
                }
        );
    }
}