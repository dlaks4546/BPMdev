package com.example.BPMdev;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

/*import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;*/

public class MainActivity extends AppCompatActivity {
    //private static Retrofit retrofit;
    RecyclerView recyclerView;
    List<Recycler_item> items;
    Recycler_item[] item;
    Button b2=null;
    Button BtZzim=null;
    Button BtOrderSearch=null;
    Button BtOlditem=null;
    Button BtPointList=null;
    Button BtCallCenter=null;
    ImageButton IbtPoint_icon;
    ImageButton IbtMall_icon;
    ImageButton IbtCamera_icon;
    String TradingVolume=null;
    String Profit=null;
    String Economy=null;
    String Contribution=null;
    String strLoginID = null;

    /*public static Retrofit getRetrofit() {
        return retrofit;
    }*/

/*    public static void setRetrofit(Retrofit retrofit) {
        MainActivity.retrofit = retrofit;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(LoginData.getUser_company_name()+"-"+LoginData.getUser_name()+ "         " + LoginData.getUser_point()+"PT");

        BtZzim = (Button)findViewById(R.id.zzim_button);
        BtOrderSearch = (Button)findViewById(R.id.ordersearch_button);
        BtOlditem = (Button)findViewById(R.id.olditem_button);
        BtPointList = (Button)findViewById(R.id.point_button);
        BtCallCenter = (Button)findViewById(R.id.callcenter_button);
        IbtPoint_icon = (ImageButton)findViewById(R.id.point_icon);
        IbtMall_icon = (ImageButton)findViewById(R.id.mall_icon);
        IbtCamera_icon = (ImageButton)findViewById(R.id.camera_icon);

        BtZzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "장바구니로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyCart.class);
                startActivity(intent);
                finish();
            }
        });

        BtOrderSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Order.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "주문조회 페이지로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        BtOlditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent(getApplicationContext(), SplashActivity.searchBookActivity.class);
                startActivity(intent);
                finish();*/
                Toast.makeText(getApplicationContext(), "중고장터 등록 페이지로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        BtPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent(getApplicationContext(), SplashActivity.searchBookActivity.class);
                startActivity(intent);
                finish();*/
                Intent intent = new Intent(getApplicationContext(), ReceiptUseList.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "포인트 사용내역으로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        BtCallCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "고객센터 연결", Toast.LENGTH_SHORT).show();
                String tel = "tel:01027160590";
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));

            }
        });

        IbtPoint_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "포인트 내역", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ReceiptList.class);
                startActivity(intent);
                finish();
            }
        });

        IbtMall_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "복포몰", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), StoreMain.class);
                startActivity(intent);
                finish();
            }
        });

        IbtCamera_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "영수증 촬영", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ReceiptCamera.class);
                //intent.putExtra("LoginID", LoginData.getId());
                startActivity(intent);
                finish();
            }
        });

        //getMoneyData();
    }

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
            Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_refresh){
            Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }

        if(id == R.id.action_home){
            Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
/*

    private void getMoneyData() {
        MoneyService loginApi = getRetrofit().create(MoneyService.class);
        final Call<MoneyData> nicknameChangeCall = loginApi.MONEY_DATA_CALL("command");
        nicknameChangeCall.enqueue(new Callback<MoneyData>() {
            @Override
            public void onResponse(Call<MoneyData> call, Response<MoneyData> response) {
                MoneyData ldata = response.body();
                if (response.isSuccessful()) {
                    Log.i("....", "SUCCESS.........1");
                    Log.i("....", ldata.message);
                    TradingVolume=ldata.TradingVolume;
                    Profit=ldata.Profit;
                    Economy=ldata.Economy;
                    Contribution=ldata.Contribution;

                    recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                    items=new ArrayList<>();
                    item=new Recycler_item[4];

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    item[0]=new Recycler_item("현재 총 거래량은 : "+"\\", TradingVolume);
                    item[1]=new Recycler_item("현재 순 수익 : "+"\\", Profit);
                    item[2]=new Recycler_item("현재 총 절약 금액 : "+"\\", Economy);
                    item[3]=new Recycler_item("기부금 : "+"\\", Contribution);

                    for(int i=0;i<item.length;i++) items.add(item[i]);

                    recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));
                } else {
                    Log.i("....", "FAILURE..........." + ldata);
                }
            }

            @Override
            public void onFailure(Call<MoneyData> call, Throwable t) {
                Log.i("test....", "FAILURE...........");
                Log.d("Error", t.getMessage());
            }
        });
*/
}
