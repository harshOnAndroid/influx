package com.harsh.influx.view;

import android.os.Bundle;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.harsh.influx.R;
import com.harsh.influx.data.FoodItem;
import com.harsh.influx.data.SelectedOrderLiveData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    boolean isVisible = false;

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void toggleSlider_openSlider(){


        if (!isVisible){
            onView(withId(R.id.checkoutLayout)).perform(click());
            onView(withId(R.id.sliderParentLayout)).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }

        isVisible = !isVisible;

    }


    @Test
    public void toggleSlider_closeSlider(){

        if (isVisible){
            onView(withId(R.id.checkoutLayout)).perform(click());
            onView(withId(R.id.sliderParentLayout)).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }

        isVisible = !isVisible;

    }

    @Test
    public  void addItem(){
        try {
            homeActivityActivityTestRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    FoodItem item = new FoodItem();
                    item.Cinemaid("1234");
                    item.ImageUrl("");
                    item.Name("Wada Paav");
                    item.ItemPrice("9.00");
                    item.VistaFoodItemId("1234");

                    SelectedOrderLiveData.getInstance().addFoodItem(item);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Double total = homeActivityActivityTestRule.getActivity().getTotal(SelectedOrderLiveData.getInstance().getValue());

        assertEquals(total, new Double(9.00));
    }
}