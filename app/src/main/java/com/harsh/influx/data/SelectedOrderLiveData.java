package com.harsh.influx.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.util.List;

public class SelectedOrderLiveData extends LiveData<List<FoodItem>> {

    private SelectedOrderRepository repository;

    private static SelectedOrderLiveData instance;

    public static SelectedOrderLiveData getInstance() {
        if (instance == null)
            instance = new SelectedOrderLiveData();
        return instance;
    }

    private SelectedOrderLiveData() {
        super();
        instance = this;
        repository = SelectedOrderRepository.getInstance();
    }

    @Override
    protected void postValue(List<FoodItem> value) {
        super.postValue(value);
    }

    @Override
    protected void setValue(List<FoodItem> value) {
        super.setValue(value);
    }

    @Nullable
    @Override
    public List<FoodItem> getValue() {
        return super.getValue();
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    public void addFoodItem(FoodItem foodItem){
       setValue(repository.addFoodItem(foodItem));
    }

    public void removeFoodItem(FoodItem foodItem){
        setValue(repository.removeFoodItem(foodItem));
    }

    public FoodItem getItem(FoodItem foodItem){
        return repository.getItem(foodItem);
    }

}
