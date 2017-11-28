package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


import com.mohamedkevinlukepierce.budgetbuddy.OverviewFragment.OnListFragmentInteractionListener;
import com.mohamedkevinlukepierce.budgetbuddy.BudgetContent.BudgetItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BudgetItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<BudgetItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    private static Context applicationContext = MainActivity.getContextOfApplication();
    private static  SharedPreferences generalSharedPreferences;
    private static SharedPreferences.Editor generalEditor;

    public MyItemRecyclerViewAdapter(List<BudgetItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        int valueOfItem = Integer.parseInt(mValues.get(position).value);
        String valueStr;
        if (valueOfItem < 0) {
            valueOfItem = Math.abs(valueOfItem);
            valueStr = "- $" + valueOfItem;
        }
        else {
            valueStr = "$" + valueOfItem;
        }
        holder.mContentView.setText(valueStr);

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {

                    int position = holder.getAdapterPosition();
                    BudgetItem holding = mValues.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mValues.size());

                    float mtotalBudget;
                    float mtotalExpense;

                    generalSharedPreferences = applicationContext.getSharedPreferences("General Preference", applicationContext.MODE_PRIVATE);
                    generalEditor = generalSharedPreferences.edit();

                    if(holding.getType().equalsIgnoreCase("savings")){
                        mtotalBudget = BudgetContent.getTotalBudget() - Integer.parseInt(holding.value);
                        BudgetContent.setTotalBudget(mtotalBudget);
                        generalEditor.putFloat("totalBudget", mtotalBudget);
                    }
                    else{
                        mtotalBudget = BudgetContent.getTotalExpense() + Integer.parseInt(holding.value);
                        BudgetContent.setTotalExpense(mtotalBudget);
                        mtotalExpense = BudgetContent.getTotalBudget() - Integer.parseInt(holding.value);
                        BudgetContent.setTotalBudget(mtotalExpense);
                        generalEditor.putFloat("totalBudget", mtotalBudget);
                        generalEditor.putFloat("totalExpense", mtotalExpense);
                    }

                    generalEditor.apply();

                    Toast.makeText(v.getContext(),"Deleted!", Toast.LENGTH_LONG).show();

                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public BudgetItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.nameItem);
            mContentView = (TextView) view.findViewById(R.id.value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
