package com.example.theostore;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface THEOStoreApi {

    @GET("/api/trays")
    Call<List<Tray>> getTrays();

    @GET("/api/trays/{name}")
    Call<Tray> getTray(@Path("name") String tray);

    @GET("/api/images/{name}")
    Call<ResponseBody> getTrayImage(@Path("name") String tray);

    // PATCH is also an option because it just updates the thing we want
    @PUT("/api/trays/{name}")
    Call<Tray> updateTray(@Path("name") String name, @Body Tray tray);

}
