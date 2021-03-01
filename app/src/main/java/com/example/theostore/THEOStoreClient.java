package com.example.theostore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class THEOStoreClient {

    private static final String HOST_ADDRESS = "http://10.0.2.2:5000";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final THEOStoreApi theoStoreApi = retrofit.create(THEOStoreApi.class);

    public static void bringTray(String name) {
        System.out.println("okay bringtray was called");
        Call<Tray> call = theoStoreApi.retrieveTray(name);

        call.enqueue(new Callback<Tray>() {
            @Override
            public void onResponse(Call<Tray> call, Response<Tray> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                }

                System.out.println("okay we got here");

            }



            @Override
            public void onFailure(Call<Tray> call, Throwable t) {
                System.out.println("We failed here");
                System.out.println(t.toString());
            }
        });
    }

}
