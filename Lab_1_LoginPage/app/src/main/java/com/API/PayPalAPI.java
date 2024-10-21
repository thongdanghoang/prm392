package com.API;

import Data.AccessTokenResponse;
import Data.PaymentResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PayPalAPI {

    @FormUrlEncoded
    @POST("/v1/oauth2/token")
    Call<AccessTokenResponse> getAccessToken(
            @Header("Authorization") String authorization,
            @Field("grant_type") String grantType
    );

    @POST("/v1/payments/payment")
    Call<PaymentResponse> createPayment(
            @Header("Authorization") String authHeader,
            @Body RequestBody paymentData
    );
}
