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
     * An array of sample (dummy) items.
     */
    public static final List<BudgetItem> ITEMS = new ArrayList<BudgetItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, BudgetItem> ITEM_MAP = new HashMap<String, BudgetItem>();

    private static final int COUNT = 15;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createBudgetItem(i));
        }
    }

    private static void addItem(BudgetItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static BudgetItem createBudgetItem(int position) {
        return new BudgetItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class BudgetItem {
        public final String id;
        public final String content;
        public final String details;

        public BudgetItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
