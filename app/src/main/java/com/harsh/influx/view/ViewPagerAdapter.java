package com.harsh.influx.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.harsh.influx.data.FoodCategory;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<FoodCategory> foodItems = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<FoodCategory> foodItems) {
        super(fm);
        this.foodItems.clear();
        this.foodItems.addAll(foodItems);
    }


    @Override
    public Fragment getItem(int position) {
        return FoodItemFragment.newInstance(foodItems.get(position).Fnblist());
    }

    @Override
    public int getCount() {
        return foodItems.size();
    }
}
