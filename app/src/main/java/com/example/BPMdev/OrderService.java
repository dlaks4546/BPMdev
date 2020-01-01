package com.example.BPMdev;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PC on 2016-06-10.
 */
public interface OrderService {
    @POST("/Order.jsp")
    Call<OrderData> ORDER_DATA_CALL(@Query("Name") String Name);
}
