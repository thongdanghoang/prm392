package com.API;

import Data.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("prm392/auth/users/sign-up")
    Call<Void> signUp(@Body User user);

    @POST("prm392/auth/auth/sign-in")
    Call<ResponseBody> signIn(@Body User user);
}
