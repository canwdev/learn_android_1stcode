package com.example.xmljsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonTestActivity extends AppCompatActivity {

    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test);

        responseText = (TextView) findViewById(R.id.textView_responseText);
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(MainActivity.URL_JSON)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    // parseJsonWithJsonObject(responseString);
                    parseJsonWithGson(responseString);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parseJsonWithJsonObject(String jsonString) {
        try {
            String finString = "";

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");

                String tempString = "";
                tempString = id + " " + name + " " + version + "\n";
                finString += tempString;
            }
            showResponse(finString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJsonWithGson(String jsonString) {
        String finString = "";

        Gson gson = new Gson();
        List<GsonApp> appList = gson.fromJson(jsonString, new TypeToken<List<GsonApp>>() {
        }.getType());
        for (GsonApp app : appList) {
            finString += app.getId() + " " + app.getName() + " " + app.getName() + "\n";
        }
        showResponse(finString);
    }

    private void showResponse(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // UI 操作开线程
                responseText.setText(string);
            }
        });
    }
}
