package com.example.BPMdev;

import com.example.BPMdev.MoneyData;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PC on 2016-05-20.
 */
public interface MoneyService {
    @POST("/MoneyData.jsp")
    Call<MoneyData> MONEY_DATA_CALL(@Query("command") String command);
}
