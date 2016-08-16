package com.imooc.tomsom.testdemo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import Bean.HeWeather;
import Bean.HeatherData;
import Bean.ResultCode;
import Bean.Results;
import Bean.WeatherBean;
import Bean.javaBean;

/**
 * Created by admin on 2016/8/11.
 */
public class Weather_Activity extends Activity {

    Context context = this;
    String cityName;
    String id;
    Intent intent;
    private TextView textView;
    Gson gson;
    String cityurl ;
//    String cityurl1 = "http://v.juhe.cn/weather/index?cityname=";
//    String cityurl2 = "&dtype=&format=&key=283a0a5758e7077da40b4fea4860a6e6";
    String cityurl1 = "https://api.heweather.com/x3/weather?";
    String key = "&key=" + "aea87a09511742a7ae6887d3c61a61a0";
    String cityid = "cityid=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        textView = (TextView)findViewById(R.id.weather_text);
        intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        id = intent.getStringExtra("id");
        textView.setText(cityName);
        cityurl =cityurl1 + cityid + id + key;
        Log.d("TAG",cityurl);
        /**
         * 网络获取数据
         */
        gson = new Gson();
        RequestQueue mqueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(cityurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("TAG",s);
//               HeatherData heatherData = gson.fromJson(s, new TypeToken<HeatherData>() {}.getType());
                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.deleteCharAt(11);
                sb.deleteCharAt(15);
                sb.delete(22, 26);
                String result = sb.toString();
                Log.d("MySelf",result);
                WeatherBean weatherBean = gson.fromJson(result,WeatherBean.class);
                List<WeatherBean.HeWeatherdataserviceBean> HeWeatherdataservice = weatherBean.getHeWeatherdataservice();
                Log.d("MySelf",HeWeatherdataservice.get(0).getAqi().getCity().getAqi().toString());
//                HeatherData heatherData = gson.fromJson(result, HeatherData.class);
//                List<HeatherData.HeWeatherText> heWeatherText = heatherData.getHeWeatherText();
//                Log.d("MySelf",heWeatherText.get(0).toString());
//                List<HeatherData.HeWeather> heWeatherList = heatherData.getHeWeather();
//                String a = heWeatherList.get(0).getAqi().getCity().getQlty();
//                Log.d("MySelf",a);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);

            }
        });
        mqueue.add(stringRequest);

    }
}
