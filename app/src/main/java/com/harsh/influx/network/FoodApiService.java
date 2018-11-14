package com.harsh.influx.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApiService {

    @GET("v2/5b700cff2e00005c009365cf")
//    @GET("/foods")
    Call<JsonObject> fetchFood();
}
