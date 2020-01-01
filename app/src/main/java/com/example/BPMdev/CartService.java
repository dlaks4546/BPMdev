package com.example.BPMdev;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PC on 2016-05-31.
 */
public interface CartService {
    @POST("/Cart.jsp")
    Call<CartData> Cart_List(@Query("Name") String Name);

    @POST("/removeCart.jsp")
    Call<CartData> REMOVE_CART_DATA_CALL(@Query("BookName") String BookName,
                                            @Query("UserName") String UserName);

}

