package com.defaultapps.yandexrequest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.defaultapps.yandexrequest.network.NetworkService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    
    private static final String QUERY = "link link_theme_normal organic__url link_cropped_no i-bem";

    private NetworkService ns;
    private EditText editText;

    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ns = new NetworkService();

        editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.buttonRequest);
        RecyclerView resultRecyclerView = findViewById(R.id.resultRecyclerView);

        resultAdapter = new ResultAdapter();
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(resultAdapter);

        final Handler mainThreadHandler = new Handler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ns.sendRequest(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("onFailure", "Error - ", e);
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Ошибка поиска", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("onResponse", "callSuccessful");
                        Document doc = Jsoup.parse(response.body().string());
                        Elements elements = doc.getElementsByClass(QUERY);
                        if (elements.size() == 0) {
                            mainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        final List<Result> searchResults = new ArrayList<>();
                        for (Element element: elements) {
                            searchResults.add(new Result(element.text(), element.attr("href")));
                        }
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                resultAdapter.setData(searchResults);
                            }
                        });
                    }
                }, editText.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        ns.cancerRequest();
        super.onDestroy();
    }
}
