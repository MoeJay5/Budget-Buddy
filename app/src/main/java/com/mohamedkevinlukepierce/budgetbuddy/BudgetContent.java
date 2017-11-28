package com.mohamedkevinlukepierce.budgetbuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 11/24/2017.
 */

public class BudgetContent {

    private static Context applicationContext = MainActivity.getContextOfApplication();
    private static  SharedPreferences generalSharedPreferences;
    private static SharedPreferences.Editor generalEditor;

    /**
     * An array of budget items.
     */
    public static final List<BudgetItem> ITEMS = new ArrayList<BudgetItem>();


    public static final List<BudgetItem> HOLD = new ArrayList<BudgetItem>();
    /**
     * A map of budget items, by ID.
     */
    public static final Map<String, BudgetItem> ITEM_MAP = new HashMap<String, BudgetItem>();

    private static float totalBudget;
    private static float totalExpense;

    public static float getTotalBudget() {
        return totalBudget;
    }

    public static float getTotalExpense() {
        return totalExpense;
    }

    public static void addItem(BudgetItem item) {
        generalSharedPreferences = applicationContext.getSharedPreferences("General Preference", applicationContext.MODE_PRIVATE);
        generalEditor = generalSharedPreferences.edit();
        totalBudget = generalSharedPreferences.getFloat(String.format("totalBudget%d", ProfileActivity.state), totalBudget);
        totalExpense = generalSharedPreferences.getFloat(String.format("totalExpense%d", ProfileActivity.state), totalExpense);


        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        int itemValue = Integer.parseInt(item.value);
        totalBudget += itemValue;
        if (itemValue < 0) {
            totalExpense += (-1) * itemValue;
        }

        generalEditor.putFloat(String.format("totalBudget%d", ProfileActivity.state), totalBudget);
        generalEditor.putFloat(String.format("totalExpense%d", ProfileActivity.state), totalExpense);

        generalEditor.apply();
    }

    public static BudgetItem createBudgetItem(String name, String value, String type) {
        return new BudgetItem(String.valueOf(name), name, value, type);
    }

    /**
     * budget item that contains information about an entry.
     */
    public static class BudgetItem {
        public final String id;
        public final String name;
        public final String value;
        public final String type;

        public BudgetItem(String id, String name, String value, String type) {
            this.id = id;
            this.name = name;
            if (type.equals("expense")) {
                this.value = "-" + value;
            }
            else {
                this.value = value;
            }
            this.type = type;
        }

        public String getType() {
            return this.type;
        }


        @Override
        public String toString() {
            return name;
        }
    }
}
