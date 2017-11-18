package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileEvent extends AppCompatActivity { //Profile Objects

    public static EditText profileUserText;
    private ImageButton addProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        profileUserText = (EditText) findViewById(R.id.profileUserText);
        addProfileBtn = (ImageButton) findViewById(R.id.addProfileSelect);

        if (ProfileActivity.state == 1)
            profileUserText.setText(ProfileActivity.profile1.getText());
        else if (ProfileActivity.state == 2)
            profileUserText.setText(ProfileActivity.profile2.getText());
        else if (ProfileActivity.state == 3)
            profileUserText.setText(ProfileActivity.profile3.getText());

        profileUserText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(profileUserText.length() <= 0 || profileUserText.length() > 20)
                    addProfileBtn.setVisibility(View.INVISIBLE);
                else
                    addProfileBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        addProfileBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),  ProfileActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}