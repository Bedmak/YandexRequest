package com.defaultapps.yandexrequest.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkService {

    private static final String URL = "http://yandex.ru/yandsearch?text=";
    private static final String PAGE = "&p=";

    OkHttpClient client = new OkHttpClient();

    public void sendRequest(Callback callback , String req, int page) {
        Request request = new Request.Builder()
                .url(URL+req+PAGE+page)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void cancerRequest() {
        client.dispatcher().cancelAll();
    }

}
