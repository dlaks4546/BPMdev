package com.example.BPMdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class MyCart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    public static Retrofit retrofit;
    RecyclerView cartreclerview;
    private SwipeRefreshLayout mSwipeRefresh;
    List<Cart_Item> items;
    Cart_Item[] item;
    Button btCartBuy = null;
    String bookname = null;
    int count=0;
    TextView UserNameCheck;
    CartListAdapter cartListAdapter;
    TextView priceview;
    String strLoginID = null;
    TextView CartTitle;
    //final String URL="http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080/img/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setTitle(LoginData.getUser_company_name()+"-"+LoginData.getUser_name()+ "         " + LoginData.getUser_point()+"PT");

/*        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swype_layout);

        mSwipeRefresh.setOnRefreshListener(this);
        btCartBuy=(Button)findViewById(R.id.button_cart_buy);
        cartreclerview=(RecyclerView)findViewById(R.id.cartview);
        CartTitle = (TextView) findViewById(R.id.Carttitle);

        getCartData();

        btCartBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "상품구매하는 로직 태우기", Toast.LENGTH_SHORT).show();
            }
        });
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
            Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCartData() {
            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
            items=new ArrayList<>();
            cartreclerview.setHasFixedSize(true);
            cartreclerview.setLayoutManager(layoutManager);
            try {
                int sum=0;
                String jsontext = "{\"cartlist\":[{\"productname\":\"Mouse\", \"price\":\"23000\"},"+"{\"productname\":\"KeyBoard\", \"price\":\"12000\"},"+"{\"productname\":\"HDD\", \"price\":\"156000\"}]}";
                //String jsontext = '{"cartlist":[{"productname":"미션임파서블","price" : "3000"},{"productname":"장난감","price":"5000"},{"productname" : "범죄도시","price":"10000"},{"productname":"아저씨","price":"100"}]}';
                JSONObject object = null;
                object = new JSONObject(jsontext);
                //JSONObject object = new JSONObject(jsontext);
                JSONArray CartInfoArray = (JSONArray) object.getJSONArray("cartlist");
                item=new Cart_Item[CartInfoArray.length()];
                String a=Integer.toString(CartInfoArray.length());
                UserNameCheck=(TextView)findViewById(R.id.Carttitle);
                priceview=(TextView)findViewById(R.id.price);

                Log.i("....", jsontext);
                Log.i("index",a);
                for(int i=0; i<CartInfoArray.length(); i++)
                {
                    JSONObject cartObject = (JSONObject)CartInfoArray.get(i);
                    //String url[] = accountObject.getString("imagesrc").split(":");
                    item[i]=new Cart_Item(getURLForResource(R.drawable.a), cartObject.getString("productname"), "가격 : "+cartObject.getString("price")+"원");  // drawble.a 를 accountObject.getString("imagsrc")로 이미지 소스 가져와서 이미지 삽입하는 방법.

                    items.add(item[i]);
                    UserNameCheck.setText(strLoginID + " 님의 장바구니 목록입니다.");
                    bookname=cartObject.getString("productname");
                    sum=sum+Integer.parseInt(cartObject.getString("price"));
                }
                priceview.setText(sum+"원 "+"  ->뿌뿌뿌뿌");
                cartListAdapter = new CartListAdapter(getApplicationContext(), items, R.layout.activity_my_cart);

                cartreclerview.setAdapter(cartListAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //cartListAdapter.notifyDataSetChanged();
    }

/*
    private void getCartData() {
        CartService loginApi = retrofit.create(CartService.class);
        final Call<CartData> nicknameChangeCall = loginApi.Cart_List(LoginData.id);
        nicknameChangeCall.enqueue(new Callback<CartData>() {
            @Override
            public void onResponse(Call<CartData> call, Response<CartData> response) {
                CartData ldata = response.body();
                if (response.isSuccessful()) {
                    Log.i("....", "SUCCESS.........1");


                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                    items=new ArrayList<>();

                    cartreclerview.setHasFixedSize(true);
                    cartreclerview.setLayoutManager(layoutManager);


                    try {
                        int sum=0;
                        String jsontext = ldata.message;
                        JSONObject object = null;
                        object = new JSONObject(jsontext);
                        JSONArray accountInfoArray = (JSONArray) object.getJSONArray("accounts");
                        item=new Cart_Item[accountInfoArray.length()];
                        String a=Integer.toString(accountInfoArray.length());
                        UserNameCheck=(TextView)findViewById(R.id.Carttitle);
                        priceview=(TextView)findViewById(R.id.price);



                        for(int i=0; i<accountInfoArray.length(); i++)
                        {
                            JSONObject accountObject = (JSONObject)accountInfoArray.get(i);
                            String url[] = accountObject.getString("imagesrc").split(":");
                            item[i]=new Cart_Item(URL+url[0], accountObject.getString("bookname"), "가격 : "+accountObject.getString("price")+"원");  // drawble.a 를 accountObject.getString("imagsrc")로 이미지 소스 가져와서 이미지 삽입하는 방법.

                            items.add(item[i]);
                            UserNameCheck.setText(accountObject.getString("username") + " 님의 장바구니 목록입니다.");
                            bookname=accountObject.getString("bookname");
                            sum=sum+Integer.parseInt(accountObject.getString("price"));
                        }
                        priceview.setText(sum+"원 "+"  ->구매는 연구실로");
                        cartListAdapter = new CartListAdapter(getApplicationContext(), items, R.layout.activity_my_cart);

                        cartreclerview.setAdapter(cartListAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cartListAdapter.notifyDataSetChanged();
                } else {
                    Log.i("....", "FAILURE..........." + ldata);
                }
            }

            @Override
            public void onFailure(Call<CartData> call, Throwable t) {
                Log.i("test....", "FAILURE...........");
                Log.d("Error", t.getMessage());
            }
        });
    }
*/

    @Override
    public void onRefresh() {
        items.clear();
        getCartData();

        cartListAdapter.set_data(items);
        cartListAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
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
