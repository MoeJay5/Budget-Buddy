package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity{ //profile selection logic
    public static Button profile1, profile2, profile3;
    public static int state;
    public static final String SHARED_PREFS = "General Preference";

    @Override
    public void onRestart() { //When back button is pressed on Android device the layout is refreshed
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences savedPreference = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if (savedPreference.getBoolean("darkThemeEnabled", false))
            setTheme(R.style.AppTheme_Dark);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        profile1 = (Button) findViewById(R.id.profileButton1);
        profile1.setText(savedPreference.getString("name1", "Profile 1")); //Calling from saved data, if non exist then return "Profile 1"

        profile2 = (Button) findViewById(R.id.profileButton2);
        profile2.setText(savedPreference.getString("name2", "Profile 2")); //Calling from saved data, if non exist then return "Profile 2"

        profile3 = (Button) findViewById(R.id.profileButton3);
        profile3.setText(savedPreference.getString("name3", "Profile 3")); //Calling from saved data, if non exist then return "Profile 3"

        profile1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 1;
                        login(state);
                    }
                }
        );
        profile2.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 2;
                        login(state);
                    }
                }
        );
        profile3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        state = 3;
                        login(state);
                    }
                }
        );
    }

    private void login(int loginState)
    {
        SharedPreferences savedPreference = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Intent intent;
        //If PIN non existent then skip to Main Activity
        if(savedPreference.getString(String.format("pin%d", loginState), "null") == "null" || savedPreference.getString(String.format("pin%d", loginState), "null") == "")
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
        else
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}