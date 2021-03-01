package com.example.theostore;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface THEOStoreApi {

    @GET("/retrieve/{tray}")
    Call<Tray> retrieveTray(@Path("tray") String tray);

}
