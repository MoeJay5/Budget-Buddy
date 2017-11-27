package com.mohamedkevinlukepierce.budgetbuddy;

/**
 * Created by kbphan on 11/19/17.
 * Edited by Luke Duhe on 11/26/17.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;



public class ReportsFragment extends Fragment {

    private static final String TAG = "Reports Fragment";

    private int[] yData = {75, 25};
    private String[] xData = {"Budget Remaining", "Spent this month"};
    PieChart pieChart;

    private Snackbar infoBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.reports_fragment, container, false);

        Log.d(TAG, "onCreate: starting to create chart");
        pieChart = (PieChart) view.findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDescription(null);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onNothingSelected() {

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String percentage = e.toString();
                percentage = percentage.substring(pos1 + 3, percentage.length()-2);

                for (int i = 0; i < yData.length; i++) {
                    if (yData[i] == Integer.parseInt(percentage)) {
                        pos1 = i;
                        break;
                    }
                }
                String fieldName = xData[pos1];

                infoBar = Snackbar.make(view, fieldName + "\n" +  percentage + "%", Snackbar.LENGTH_LONG);
                TextView snackBarTextView = (TextView) infoBar.getView().findViewById( android.support.design.R.id.snackbar_text );
                snackBarTextView.setTextSize( 24 );
                snackBarTextView.setTypeface(snackBarTextView.getTypeface(), Typeface.BOLD);
                infoBar.show();
            }

        });
        return view;
    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, null);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(32);
        pieDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieDataSet.setDrawIcons(true);
        pieDataSet.setValueTextColor(Color.WHITE);

        Legend legend = pieChart.getLegend();
        legend.setFormSize(0);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.argb(255, 102, 187, 106));
        colors.add(Color.argb(255, 239, 83, 80));


        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setRotationEnabled(false);
    }
}


