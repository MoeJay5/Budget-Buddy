package com.mohamedkevinlukepierce.budgetbuddy;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class LoginActivity extends AppCompatActivity{
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    public static final String SHARED_PREFS = "PINs";
    private TextView dynamicText;


    private PinLockListener mPinLockListener = new PinLockListener(){
        SharedPreferences pins =getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        @Override
        public void onComplete(String pin){
            if(pin.equals(pins.getString(String.format("pin%d", ProfileActivity.state), "1234"))){
                //change activity to whatever it should be after logging in
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
            dynamicText.setText("Enter PIN");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        dynamicText = (TextView) findViewById(R.id.dynamic_text);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.black));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }
}
