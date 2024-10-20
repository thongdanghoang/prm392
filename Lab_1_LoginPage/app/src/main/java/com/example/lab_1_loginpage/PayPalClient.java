package com.example.lab_1_loginpage;

import com.API.PayPalAPI;

import Data.AccessTokenResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.Credentials;

public class PayPalClient {

    private static final String CLIENT_ID = "ASr82b1SL3bd5zzoUvGWfLVzMOuUG6mx4cponr369FZfPLDaI5sczWS8LzTWoRBNZ6GdYQjeT5khfFOa";
    private static final String SECRET = "EG_g6xO_dzF7e-if1UgdnUxmFwyqdKqvVcJ8wma8LBjb-I3Jrq0Gq5kv9_DR_qtxKez3ZgGR4rsn1wsI"; // Thay bằng Secret từ PayPal Dashboard
    private static final String BASE_URL = "https://api.sandbox.paypal.com";

    private PayPalAPI payPalAPI;

    public PayPalClient() {
        // Cấu hình Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        payPalAPI = retrofit.create(PayPalAPI.class);
    }

    public void getAccessToken(Callback<AccessTokenResponse> callback) {
        String credentials = Credentials.basic(CLIENT_ID, SECRET);
        Call<AccessTokenResponse> call = payPalAPI.getAccessToken(credentials, "client_credentials");
        call.enqueue(callback);
    }
}
