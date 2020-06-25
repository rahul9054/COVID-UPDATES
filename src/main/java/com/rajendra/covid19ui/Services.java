package com.rajendra.covid19ui;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Services {
    @GET("data.json")
    Call<Example> covidUpdate();



    @GET("state_district_wise.json")
    Call<StateExample>  covidUpdateStatewise();

}
