package com.API;

import java.util.List;

import Data.Table;
import retrofit2.Call;

import retrofit2.http.GET;

public interface ApiService {
    @GET("Seats")
    Call<List<Table>> getSeats();
}
