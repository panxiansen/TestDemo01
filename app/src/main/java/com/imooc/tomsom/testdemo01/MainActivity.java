package com.imooc.tomsom.testdemo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Bean.City_info;

public class MainActivity extends Activity {

    Context context = this;
    String str;
    Gson gson;
    private ListView listView;
    private EditText editText;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> newadapter;//更新之后的适配器
    private List<String> districtList = new ArrayList<String>();
    private List<String> idList = new ArrayList<String>();
    private List<String> newIdList = new ArrayList<String>();
    private List<String> newList = new ArrayList<String>();//EditText更新的List



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText() != null) {
                    String input = editText.getText().toString();
                    for (int i = 0; i < districtList.size(); i++) {
                        String a = districtList.get(i).toString();
                        String b = idList.get(i).toString();
                        if (a.contains(input)) {
                            newList.add(a);
                            newIdList.add(b);
                        }
                    }
                    newadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(newadapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView = (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districtList);

        gson = new Gson();
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest("https://api.heweather.com/x3/citylist?search=allchina&key=aea87a09511742a7ae6887d3c61a61a0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("TAG", response);
                        City_info city_info = gson.fromJson(response, City_info.class);
                        List<City_info.City> cityList = city_info.getCity_info();
//                        Log.d("MySelf",cityList.get(2).getCity().toString());
                        for (int i = 0;i<cityList.size();i++){
                            String dist = cityList.get(i).getCity();
                            String id = cityList.get(i).getId();
//                            Log.d("TAG" , dist);
                            districtList.add(dist);
                            idList.add(id);
                        }

                        listView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Log.d("TAG", districtList.get(position));
                intent.putExtra("id", idList.get(position));
                intent.putExtra("cityName", districtList.get(position));
                intent.setClass(MainActivity.this, Weather_Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
