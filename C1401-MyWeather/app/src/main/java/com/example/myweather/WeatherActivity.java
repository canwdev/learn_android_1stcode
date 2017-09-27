package com.example.myweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myweather.gson.Forecast;
import com.example.myweather.gson.Weather;
import com.example.myweather.util.Conf;
import com.example.myweather.util.HttpUtil;
import com.example.myweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public static final String WEATHER_API_URL = "https://free-api.heweather.com/v5/weather?";
    private static final String KEY = "&key=" + Conf.HEWEATHER_KEY;
    private static final String CITY_SAMPLE = "city=CN101010100";
    public static final String WEATHER_API_URL_SAMPLE = WEATHER_API_URL + CITY_SAMPLE + KEY;
    private String cityWeatherId = "city=CN101240213";
    // 各控件
    private ImageView bgImage;
    private ScrollView weatherScrollView;
    private TextView titleCityText;
    private TextView titleUpdateTimeText;
    private TextView temperatureText;
    private TextView weatherStatusText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // 适配透明状态
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 初始化各控件
        bgImage = (ImageView) findViewById(R.id.imageView_bg);
        weatherScrollView = (ScrollView) findViewById(R.id.ScrollView_weather);
        titleCityText = (TextView) findViewById(R.id.textView_cityName);
        titleUpdateTimeText = (TextView) findViewById(R.id.textView_updateTime);
        temperatureText = (TextView) findViewById(R.id.textView_tDegree);
        weatherStatusText = (TextView) findViewById(R.id.textView_tStatus);
        forecastLayout = (LinearLayout) findViewById(R.id.LinearLayout_forecast);
        aqiText = (TextView) findViewById(R.id.textView_aqi);
        pm25Text = (TextView) findViewById(R.id.textView_pm25);
        comfortText = (TextView) findViewById(R.id.textView_comfort);
        carWashText = (TextView) findViewById(R.id.textView_carWash);
        sportText = (TextView) findViewById(R.id.textView_sport);

        SharedPreferences prefAllSettings = getSharedPreferences(Conf.PREF_FILE_NAME, MODE_PRIVATE);
        String weatherCache = prefAllSettings.getString(Conf.PREF_WEATHER_CACHE, null);
        String tempCityWeatherId = prefAllSettings.getString(Conf.PREF_WEATHER_ID, null);

        if (weatherCache!=null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherCache);
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            if (tempCityWeatherId != null) {
                cityWeatherId = "city="+tempCityWeatherId;
                String url = WEATHER_API_URL+cityWeatherId+KEY;
                requestWeather(url);
            } else {
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                startActivity(intent);
                requestWeather(WEATHER_API_URL_SAMPLE);
            }
        }


        String pictureUrl = prefAllSettings.getString(Conf.PREF_BG_URL, null);
        if (pictureUrl!=null) {
            Glide.with(this).load(pictureUrl).into(bgImage);
        } else {
            loadBgImage();
        }

    }

    private void loadBgImage() {
        final String bingPicApiUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(bingPicApiUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicUrl = response.body().string();
                SharedPreferences.Editor editor = getSharedPreferences(Conf.PREF_FILE_NAME, MODE_PRIVATE).edit();
                editor.putString(Conf.PREF_BG_URL, bingPicUrl);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPicUrl).into(bgImage);
                    }
                });
            }
        });
    }

    private void requestWeather(String weatherUrl) {
        weatherScrollView.setVisibility(View.INVISIBLE);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = getSharedPreferences(Conf.PREF_FILE_NAME, MODE_PRIVATE).edit();
                            editor.putString(Conf.PREF_WEATHER_CACHE, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            weatherScrollView.setVisibility(View.VISIBLE);
                            Toast.makeText(WeatherActivity.this, "Get weather information failed"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        weatherScrollView.setVisibility(View.VISIBLE);
                        Toast.makeText(WeatherActivity.this, "Get weather information failed"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loadBgImage();
    }

    private void showWeatherInfo(Weather weather) {
        titleCityText.setText(weather.basic.cityName);
        titleUpdateTimeText.setText(weather.basic.update.updateTime.split(" ")[1]);
        temperatureText.setText(weather.now.temperature + "℃");
        weatherStatusText.setText(weather.now.more.info);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.frag_weather_forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.textView_fDate);
            TextView infoText = (TextView) view.findViewById(R.id.textView_fStatus);
            TextView maxText = (TextView) view.findViewById(R.id.textView_tMax);
            TextView minText = (TextView) view.findViewById(R.id.textView_tMin);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max + "℃");
            minText.setText(forecast.temperature.min + "℃");
            forecastLayout.addView(view);
        }

        LinearLayout LinearLayoutAqi = (LinearLayout) findViewById(R.id.LinearLayout_aqi);
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
            LinearLayoutAqi.setVisibility(View.VISIBLE);
        } else {
            LinearLayoutAqi.setVisibility(View.GONE);
        }
        comfortText.setText(weather.suggestion.comfort.info);
        carWashText.setText(weather.suggestion.carWash.info);
        sportText.setText(weather.suggestion.sport.info);
        weatherScrollView.setVisibility(View.VISIBLE);
    }
}
