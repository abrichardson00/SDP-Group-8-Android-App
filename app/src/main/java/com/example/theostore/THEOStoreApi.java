package com.example.theostore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface THEOStoreApi {

    @GET("api/trays")
    Call<List<Tray>> getTrays();




}
