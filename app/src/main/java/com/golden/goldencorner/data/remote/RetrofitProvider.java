package com.golden.goldencorner.data.remote;

import android.net.TrafficStats;

import com.golden.goldencorner.BuildConfig;
import com.golden.goldencorner.data.local.SharedPreferencesManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitProvider {

    public static final String TAG = RetrofitProvider.class.getSimpleName();

    private static final int readTimeout = 2;
    private static final int writeTimeout = 2;
    private static final int connectTimeout = 2;

    private static Retrofit retrofit = null;

    @Singleton
    public static RemoteApi getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteApi.BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            TrafficStats.setThreadStatsTag(0x1000);

        }
        return retrofit.create(RemoteApi.class);
    }

    private static OkHttpClient client;

    private static OkHttpClient getOkHttpClient() {
        if (client == null) {
            if (BuildConfig.DEBUG) {
                // set your desired log level
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(connectTimeout, TimeUnit.MINUTES)
                        .writeTimeout(writeTimeout, TimeUnit.MINUTES)
                        .readTimeout(readTimeout, TimeUnit.MINUTES)
                        .addInterceptor(getInterceptor())
                        .addInterceptor(logging)
                        .build();
            } else {
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(connectTimeout, TimeUnit.MINUTES)
                        .writeTimeout(writeTimeout, TimeUnit.MINUTES)
                        .readTimeout(readTimeout, TimeUnit.MINUTES)
                        .addInterceptor(getInterceptor())
                        .build();
                TrafficStats.setThreadStatsTag(0x1000);

            }
        }
        return client;
    }

    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder request = original.newBuilder().addHeader("Accept-Language", SharedPreferencesManager.getCurrentLang());
                //request.method(original.method(), original.body());
                return chain.proceed(request.build());
            }
        };
    }
//    public static void getAddress(String token, AddressCallback callback) {
//        Request request = new Request.Builder()
//                .url(String.format("https://goldencorner.com.sa/golden/api/web/v1/product/address?access-token=%s", token))
////                .url(String.format("https://goldencorner.com.sa/golden/api/web/v1/product/address?access-token=i2Gu6hkb4IUneAtsYibqNupKQpEA4_Hb"))
//                .get().build();
//        getOkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//                callback.onFailure(e);
//            }
//            @Override
//            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
//                String resp = response.body().string();
//                try {
//                    AddressResponse  addressResponse = new Gson().fromJson(resp, AddressResponse.class);
//                    callback.onResponse(addressResponse);
//                } catch (Exception e) {
//                    callback.onFailure(new Exception("Address Exception"));
//                }
//            }
//        });
//    }
//    public static void getFavorites(String token, long page, FavoritesCallback callback) {
//        Request request = new Request.Builder()
//                .url(String.format("https://goldencorner.com.sa/golden/api/web/v1/product/favorite?access-token=%s&page=%s", token, page))
////                .url(String.format("https://goldencorner.com.sa/golden/api/web/v1/product/favorite?access-token=i2Gu6hkb4IUneAtsYibqNupKQpEA4_Hb&page=1"))
//                .get()
//                .build();
//        getOkHttpClient().newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                callback.onFailure(e);
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String resp = response.body().string();
//                try {
//                    FavResponse addressResponse = new Gson().fromJson(resp, FavResponse.class);
//                    callback.onResponse(addressResponse);
//                } catch (Exception e) {
//                    callback.onFailure(new Exception("Address Exception"));
//                }
//            }
//        });
//    }
//    public interface FavoritesCallback {
//        void onFailure(Exception e);
//        void onResponse(FavResponse  response);
//    }
}
