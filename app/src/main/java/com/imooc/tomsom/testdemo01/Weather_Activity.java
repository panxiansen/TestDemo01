package com.imooc.tomsom.testdemo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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


import Bean.WeatherBean;


/**
 * Created by admin on 2016/8/11.
 */
public class Weather_Activity extends Activity implements View.OnClickListener{

    /**
     * View中组件
     */
    private ImageButton back_button;
    private TextView suggestion_comf_brf;
    private TextView suggestion_sport_brf;
    private TextView suggestion_cw_brf;
    private TextView suggestion_drsg_brf;
    private TextView suggestion_uv_brf;
    private TextView suggestion_trav_brf;
    private TextView suggestion_flu_brf;
    private TextView basic_update_loc;//本地更新时间
    private TextView now_cond_txt;//本地天气描述
    private TextView now_tmp;//现在温度

    Context context = this;
    String cityName;
    String id;
    Intent intent;
    private TextView cityText;
    private List<WeatherBean.HeWeatherdataserviceBean> HeWeatherdataservice;
    Gson gson;
    String cityurl ;
    String cityurl1 = "https://api.heweather.com/x3/weather?";
    String key = "&key=" + "aea87a09511742a7ae6887d3c61a61a0";
    String cityid = "cityid=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        init();
        intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        cityText.setText(cityName);
        id = intent.getStringExtra("id");
        cityurl =cityurl1 + cityid + id + key;
        Log.d("TAG", cityurl);
        DownLoadText(context);


    }

    public void init(){
        back_button = (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        cityText = (TextView)findViewById(R.id.city_text);
        suggestion_comf_brf = (TextView) findViewById(R.id.suggestion_comf_brf);
        suggestion_cw_brf = (TextView) findViewById(R.id.suggestion_cw_brf);
        suggestion_drsg_brf = (TextView) findViewById(R.id.suggestion_drsg_brf);
        suggestion_flu_brf = (TextView) findViewById(R.id.suggestion_flu_brf);
        suggestion_sport_brf = (TextView) findViewById(R.id.suggestion_sport_brf);
        suggestion_trav_brf = (TextView) findViewById(R.id.suggestion_trav_brf);
        suggestion_uv_brf = (TextView) findViewById(R.id.suggestion_uv_brf);
        basic_update_loc = (TextView) findViewById(R.id.basic_update_loc);
        now_cond_txt = (TextView) findViewById(R.id.now_cond_txt);
        now_tmp = (TextView) findViewById(R.id.now_tmp);
    }

    @Override
    public void onBackPressed() {
        /**
         * 返回键跳转到上一个页面
         */
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void DownLoadText(Context context){
        /**
         * 网络获取数据方法
         */
        gson = new Gson();
        RequestQueue mqueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(cityurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("TAG",s);
                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.deleteCharAt(11);
                sb.deleteCharAt(15);
                sb.delete(22, 26);
                String result = sb.toString();
                Log.d("MySelf",result);
                WeatherBean weatherBean = gson.fromJson(result,WeatherBean.class);
                HeWeatherdataservice = weatherBean.getHeWeatherdataservice();
                Log.d("MySelf",HeWeatherdataservice.get(0).getBasic().getCity().toString());
                suggestion_comf_brf.setText("舒适程度:" + HeWeatherdataservice.get(0).getSuggestion().getComf().getBrf().toString());
                suggestion_drsg_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getDrsg().getBrf().toString());
                suggestion_uv_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getUv().getBrf().toString());
                suggestion_cw_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getCw().getBrf().toString());
                suggestion_trav_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getTrav().getBrf().toString());
                suggestion_flu_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getFlu().getBrf().toString());
                suggestion_sport_brf.setText(HeWeatherdataservice.get(0).getSuggestion().getSport().getBrf().toString());
                basic_update_loc.setText(HeWeatherdataservice.get(0).getBasic().getUpdate().getLoc().toString());
                now_cond_txt.setText(HeWeatherdataservice.get(0).getNow().getCond().getTxt().toString());
                now_tmp.setText(HeWeatherdataservice.get(0).getNow().getTmp().toString() + "℃");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);

            }
        });
        mqueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
