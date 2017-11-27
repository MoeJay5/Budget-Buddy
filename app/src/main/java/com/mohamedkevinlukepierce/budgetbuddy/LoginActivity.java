package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class LoginActivity extends AppCompatActivity{
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    public static final String SHARED_PREFS = "PINs";
    private TextView dynamicText;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final SharedPreferences PINS= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lock_screen);

        dynamicText = (TextView) findViewById(R.id.dynamic_text);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(new PinLockListener(){

            @Override
            public void onComplete(String pin){
                if(pin.equals(PINS.getString(String.format("pin%d", ProfileActivity.state), "1234"))){
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }
                else{//wrong PIN entered
                    mPinLockView.resetPinLockView();//clears the currently entered PIN and resets the indicator dots
                    dynamicText.setText("Incorrect PIN");
                }

            }

            @Override
            public void onEmpty(){}//this method has to be overridden, just ignore it

            @Override
            public void onPinChange(int pinLength, String intermediatePin){
                dynamicText.setText("Enter PIN:");
            }
        });

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.black));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }
}
