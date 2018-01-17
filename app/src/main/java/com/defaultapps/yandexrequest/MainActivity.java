package com.defaultapps.yandexrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.defaultapps.yandexrequest.network.NetworkService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    NetworkService ns;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ns = new NetworkService(getApplicationContext());

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.buttonRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ns.sendRequest(editText.getText().toString());
            }
        });
    }
}
