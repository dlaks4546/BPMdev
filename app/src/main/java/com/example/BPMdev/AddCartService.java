package com.example.BPMdev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PC on 2016-05-31.
 */
public interface AddCartService {
    @GET("/AddCart.jsp")
    Call<AddCartData> ADD_CART_DATA_CALL(@Query("BookNum") String BookNum,
                                         @Query("UserName") String UserName);
}
