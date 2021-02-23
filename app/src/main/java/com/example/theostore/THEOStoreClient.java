package com.example.theostore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class THEOStoreClient {

    private static final String HOST_ADDRESS = "http://192.168.X.XXX:5000";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final THEOStoreApi theoStoreApi = retrofit.create(THEOStoreApi.class);

    public void bringTray(String name) {
        // name, info, status, capacity, timestamp
        Tray tray = new Tray(name, "out");
        Call<Tray> call = theoStoreApi.updateTray(name, tray);

        call.enqueue(new Callback<Tray>() {
            @Override
            public void onResponse(Call<Tray> call, Response<Tray> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<Tray> call, Throwable t) {

            }
        });
    }

}
