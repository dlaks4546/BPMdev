package com.example.BPMdev;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    @POST("/Login.jsp")
    Call<LoginData> loginRequst(@Query("usernum") String usernum,
                                @Query("password") String password);
}
