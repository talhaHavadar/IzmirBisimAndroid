package com.scorptech.izmirbisim;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scorptech.izmirbisim.eventBus.RxBus;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by talha on 02.02.2016.
 */
public class App extends Application {

    private static RxBus eventBus;
    private static Retrofit mRetrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = new RxBus();

    }

    public static RxBus getEventBus() {
        if (eventBus == null) {
            eventBus = new RxBus();
        }
        return eventBus;
    }

    public static Retrofit getRetrofit() {

        if (mRetrofit == null) {
            Gson gson = new GsonBuilder().create();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl("API_URL_COMES_HERE")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }

        return mRetrofit;
    }

}
