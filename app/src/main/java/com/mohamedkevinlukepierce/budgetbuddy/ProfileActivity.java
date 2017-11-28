package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity{ //profile selection logic
    public static Button profile;

    @Override
    public void onRestart() { //When back button is pressed on Android device the layout is refreshed
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences generalSharedPreference = getSharedPreferences("General Preference", MODE_PRIVATE);

        if (generalSharedPreference.getBoolean("darkThemeEnabled", false)) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        profile = (Button) findViewById(R.id.profileButton);
        profile.setText(generalSharedPreference.getString("name", "Profile")); //Calling from saved data, if non exist then return "Profile 1"

        profile.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        login();
                    }
                }
        );
    }

    private void login()
    {
        SharedPreferences generalSharedPreference = getSharedPreferences("General Preference", MODE_PRIVATE);
        Intent intent;
        //If PIN non existent then skip to Main Activity
        if(generalSharedPreference.getString("pin", "null") == "null" || generalSharedPreference.getString("pin", "null") == "")
            intent = new Intent(getApplicationContext(), MainActivity.class);
        else
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}