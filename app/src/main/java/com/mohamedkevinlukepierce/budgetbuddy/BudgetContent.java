package com.mohamedkevinlukepierce.budgetbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 11/24/2017.
 */

public class BudgetContent {

    /**
     * An array of budget items.
     */
    public static final List<BudgetItem> ITEMS = new ArrayList<BudgetItem>();

    /**
     * A map of budget items, by ID.
     */
    public static final Map<String, BudgetItem> ITEM_MAP = new HashMap<String, BudgetItem>();

    public static void addItem(BudgetItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static BudgetItem createBudgetItem(String name, String value) {
        return new BudgetItem(String.valueOf(name), name, value);
    }

    /**
     * budget item that contains information about an entry.
     */
    public static class BudgetItem {
        public final String id;
        public final String name;
        public final String value;

        public BudgetItem(String id, String name, String value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
