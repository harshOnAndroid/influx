package com.harsh.influx.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harsh.influx.data.FoodItem;
import com.harsh.influx.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodItemFragment extends Fragment {
    @BindView(R.id.rvFoodList)
    RecyclerView rvFoodList;

    private MutableLiveData<List<FoodItem>> foodItemsLiveData = new MutableLiveData<>();
    private FoodItemsAdapter adapter;

    public static FoodItemFragment newInstance(List<FoodItem> fnblist) {
        FoodItemFragment frag = new FoodItemFragment();
        Bundle args = new Bundle();
        args.putString("foodItemsLiveData", new Gson().toJson(fnblist));
        frag.setArguments(args);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_item, null, false);
        ButterKnife.bind(this, rootView);

        rvFoodList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FoodItemsAdapter();
        rvFoodList.setAdapter(adapter);

        String strList = getArguments().getString("foodItemsLiveData");
        List<FoodItem> foodItems = new Gson().fromJson(strList, new TypeToken<List<FoodItem>>(){}.getType());
        foodItemsLiveData.setValue(foodItems);

        foodItemsLiveData.observe(this, new Observer<List<FoodItem>>() {
            @Override
            public void onChanged(@Nullable List<FoodItem> items) {
                if (items!=null){
                    adapter.updateList(items);
                }
            }
        });

        return rootView;
    }
}
