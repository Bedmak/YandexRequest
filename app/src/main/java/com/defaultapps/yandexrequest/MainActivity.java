package com.defaultapps.yandexrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.defaultapps.yandexrequest.network.NetworkService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    NetworkService ns;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ns = new NetworkService();

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.buttonRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ns.sendRequest(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("onFailure", "Error - ", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("onResponse", "callSuccessful");
                        saveHtml(response.body().string());
                    }
                }, editText.getText().toString());
            }
        });
    }

    public void saveHtml(String str) {
        File file = new File(getApplicationContext().getFilesDir().getPath() + "/request.html");

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e("SaveFileError", e.toString());
        }
    }

}
