package com.defaultapps.yandexrequest.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkService {

    private static final String URL = "http://yandex.ru/yandsearch?text=";

    OkHttpClient client = new OkHttpClient();

    public void sendRequest(Callback callback , String req) {
        Request request = new Request.Builder()
                .url(URL+req)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void cancerRequest() {
        client.dispatcher().cancelAll();
    }

}
