package com.defaultapps.yandexrequest.network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkService {

    private static final String URL = "http://yandex.ru/yandsearch?text=";
    private Context context;

    public NetworkService(Context context) { this.context = context; }

    OkHttpClient client = new OkHttpClient();

    public void sendRequest(String req) {
        Request request = new Request.Builder()
                .url(URL+req)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("onResponse", "callSuccessful");
                File file = new File(context.getFilesDir().getPath() + "/request.html");

                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    out.write(response.body().string());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.e("SaveFileError", e.toString());
                }

            }
        });

    }

}
