package com.mohamedkevinlukepierce.budgetbuddy;

/**
 * Created by kbphan on 11/19/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportsFragment extends Fragment {
    private static final String TAG = "Overview Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reports_fragment,container,false);


        return view;
    }
}
