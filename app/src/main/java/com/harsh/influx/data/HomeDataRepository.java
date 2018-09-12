package com.harsh.influx.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.harsh.influx.network.FoodApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeDataRepository {

    private static HomeDataRepository instance;
    private MutableLiveData<List<FoodCategory>> foodLiveData ;

    public static HomeDataRepository getInstance() {
        if (instance==null)
            instance = new HomeDataRepository();
        return instance;
    }
    private HomeDataRepository() {
        foodLiveData = new MutableLiveData<>();
    }

    LiveData<List<FoodCategory>> fetchFood(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.mocky.io/").build();

        FoodApiService apiService = retrofit.create(FoodApiService.class);

        apiService.fetchFood().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject responseObj = response.body();

                try {
                    JsonObject status = responseObj.getAsJsonObject("status");

                    if (status.get("Id").getAsString().equalsIgnoreCase("1")){
                        String strFoodList = responseObj.getAsJsonArray("FoodList").toString();

                        List<FoodCategory> list = new Gson().fromJson(strFoodList, new TypeToken<List<FoodCategory>>(){}.getType());
                        foodLiveData.postValue(list);

                    }else
                        foodLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                    foodLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                foodLiveData.setValue(null);
            }
        });

        return foodLiveData;
    }
}
