package com.harsh.influx.data;

import java.util.ArrayList;
import java.util.List;

public class SelectedOrderRepository {

    private static final int INCREASE = 1;
    private static final int DECREASE = 0;
    private List<FoodItem> orderItems = new ArrayList<>();

    private static SelectedOrderRepository instance;

    public static SelectedOrderRepository getInstance() {
        if (instance == null)
            instance = new SelectedOrderRepository();
        return instance;
    }

    public List<FoodItem> addFoodItem(FoodItem foodItem) {
        if (isItemPresent(foodItem)) {
            changeQuantity(INCREASE, foodItem);
        } else {
            foodItem.Quantity(1);
            orderItems.add(foodItem);
        }

        return orderItems;
    }

    public List<FoodItem> removeFoodItem(FoodItem foodItem) {
        if (isItemPresent(foodItem)) {
            changeQuantity(DECREASE, foodItem);
        }
        return orderItems;
    }

    private void changeQuantity(int operation, FoodItem newFoodItem) {
        int quantity = -1;
        int pos = -1;

        for (int i = 0; i < orderItems.size(); i++) {
            FoodItem item = orderItems.get(i);

            if (item.VistaFoodItemId().equals(newFoodItem.VistaFoodItemId())) {
                quantity = item.Quantity();

                if (operation == INCREASE) {
                    item.Quantity(quantity + 1);

                    if (item.SubItemCount() > 0)
                        item.Subitems().addAll(newFoodItem.Subitems());

                } else if (operation == DECREASE && quantity >= 1) {

                    if (item.SubItemCount() > 0) {

                        if (item.Subitems().removeAll(newFoodItem.Subitems())){
                            item.Quantity(quantity - 1);
                            if (quantity==1)
                                pos = i;
                        }

                    } else
                        item.Quantity(quantity - 1);



                } else
                    pos = i;
            }

        }

        if (pos >= 0) {
            orderItems.remove(pos);
        }

    }

    private boolean isItemPresent(FoodItem foodItem) {
        return getItem(foodItem) != null;
    }

    public FoodItem getItem(FoodItem foodItem) {
        for (FoodItem item : orderItems) {
            if (item.VistaFoodItemId().equals(foodItem.VistaFoodItemId()))
                return item;
        }

        return null;
    }
}
