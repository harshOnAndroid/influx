package com.harsh.influx.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private HomeDataRepository repository;
    public LiveData<List<FoodCategory>> foodLiveData;


    public HomeViewModel() {
    }

    public LiveData<List<FoodCategory>> fetchFood(){
//        return foodLiveData;
        repository = HomeDataRepository.getInstance();
        return repository.fetchFood();
    }
}
