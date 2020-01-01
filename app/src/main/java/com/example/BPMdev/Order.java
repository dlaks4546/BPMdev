package com.example.BPMdev;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order extends AppCompatActivity {
    public static Retrofit retrofit;
    RecyclerView Orderreclerview;
    List<Order_Item> items;
    Order_Item[] item;
    TextView UserNameCheck;
    OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

/*        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        getSupportActionBar().setTitle("페이레터-"+LoginData.getId());
        Orderreclerview=(RecyclerView)findViewById(R.id.orderview);

        getOrderData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_refresh){
            Toast.makeText(getApplicationContext(), "Refresh App", Toast.LENGTH_LONG).show();
        }

        if(id == R.id.action_home){
            Toast.makeText(getApplicationContext(), "메인으로", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getOrderData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        items = new ArrayList<>();

        Orderreclerview.setHasFixedSize(true);
        Orderreclerview.setLayoutManager(layoutManager);

        try {
            //String jsontext = ldata.message;

            String jsontext = "{\"orderlist\":[{\"productname\":\"Mouse\", \"price\":\"23000\",\"date\":\"2019-11-12\",\"deliveryno\":\"123456789\" },"+"{\"productname\":\"KeyBoard\", \"price\":\"12000\",\"date\":\"2019-11-12\",\"deliveryno\":\"123456789\"},"+"{\"productname\":\"HDD\", \"price\":\"156000\",\"date\":\"2019-11-12\",\"deliveryno\":\"123456789\"}]}";
            JSONObject object = null;
            object = new JSONObject(jsontext);
            JSONArray OrderInfoArray = (JSONArray) object.getJSONArray("orderlist");
            item = new Order_Item[OrderInfoArray.length()];
            UserNameCheck = (TextView) findViewById(R.id.OrderText);
            Log.i("....", "SUCCESS.........1");
            for (int i = 0; i < OrderInfoArray.length(); i++) {
                JSONObject accountObject = (JSONObject) OrderInfoArray.get(i);
                item[i] = new Order_Item("상품명 : " + accountObject.getString("productname") + "입니다.",
                        "가격 : " + accountObject.getString("price") + "원",
                        "구매 날짜 : " + accountObject.getString("date") + "입니다.",
                        "배송 번호 : " + accountObject.getString("deliveryno"));
                items.add(item[i]);
                UserNameCheck.setText(LoginData.user_id + " 님의 구매 목록입니다.");
            }
            orderAdapter = new OrderAdapter(getApplicationContext(), items, R.layout.activity_order);
            Orderreclerview.setAdapter(orderAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*OrderService loginApi = retrofit.create(OrderService.class);
        final Call<OrderData> nicknameChangeCall = loginApi.ORDER_DATA_CALL(LoginData.id);
        nicknameChangeCall.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, Response<OrderData> response) {
                OrderData ldata = response.body();
                if (response.isSuccessful()) {
                    Log.i("....", "SUCCESS.........1");

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    items = new ArrayList<>();

                    Orderreclerview.setHasFixedSize(true);
                    Orderreclerview.setLayoutManager(layoutManager);


                    try {
                        //String jsontext = ldata.message;

                        String jsontext = "{\"cartlist\":[{\"productname\":\"Mouse\", \"price\":\"23000\"},"+"{\"productname\":\"KeyBoard\", \"price\":\"12000\"},"+"{\"productname\":\"HDD\", \"price\":\"156000\"}]}";
                        JSONObject object = null;
                        object = new JSONObject(jsontext);
                        JSONArray accountInfoArray = (JSONArray) object.getJSONArray("accounts");
                        item = new Order_Item[accountInfoArray.length()];
                        UserNameCheck = (TextView) findViewById(R.id.OrderText);
                        for (int i = 0; i < accountInfoArray.length(); i++) {
                            JSONObject accountObject = (JSONObject) accountInfoArray.get(i);
                            item[i] = new Order_Item("상품명 : " + accountObject.getString("productname") + "입니다.",
                                    "가격 : " + accountObject.getString("price") + "원",
                                    "구매 날짜 : " + accountObject.getString("date") + "입니다.");
                            items.add(item[i]);
                            UserNameCheck.setText(LoginData.id + " 님의 구매 목록입니다.");
                        }
                        orderAdapter = new OrderAdapter(getApplicationContext(), items, R.layout.activity_order);
                        Orderreclerview.setAdapter(orderAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("....", "FAILURE..........." + ldata);
                }
            }

            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {
                Log.i("test....", "FAILURE...........");
                Log.d("Error", t.getMessage());
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
