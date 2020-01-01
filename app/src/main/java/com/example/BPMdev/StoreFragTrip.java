package com.example.BPMdev;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.etsy.android.grid.StaggeredGridView;
import com.example.BPMdev.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class StoreFragTrip extends Fragment {
    public static Retrofit retrofit;
    RecyclerView StoreFragTripRecview;
    private SwipeRefreshLayout mSwipeRefresh;
    List<Store_Item> items;
    Store_Item[] item;
    StoreListAdapter StoreListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_storefrag_trip, container, false);

        StoreFragTripRecview=(RecyclerView)view.findViewById(R.id.store_trip_view);
        getCartData();

        return view;
    }

    private void getCartData() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        items=new ArrayList<>();
        StoreFragTripRecview.setHasFixedSize(true);
        StoreFragTripRecview.setLayoutManager(layoutManager);
        try {
            int sum=0;
            String jsontext = "{\"cartlist\":[{\"itemno\":\"1\", \"productname\":\"Mouse\", \"price\":\"23000\"},"+"{\"itemno\":\"2\",\"productname\":\"KeyBoard\", \"price\":\"12000\"},"+"{\"itemno\":\"3\",\"productname\":\"HDD\", \"price\":\"156000\"}]}";
            //String jsontext = '{"cartlist":[{"productname":"미션임파서블","price" : "3000"},{"productname":"장난감","price":"5000"},{"productname" : "범죄도시","price":"10000"},{"productname":"아저씨","price":"100"}]}';
            JSONObject object = null;
            object = new JSONObject(jsontext);
            //JSONObject object = new JSONObject(jsontext);
            JSONArray CartInfoArray = (JSONArray) object.getJSONArray("cartlist");
            item=new Store_Item[CartInfoArray.length()];
            String a=Integer.toString(CartInfoArray.length());

            Log.i("....", jsontext);
            Log.i("index",a);
            for(int i=0; i<CartInfoArray.length(); i++)
            {
                JSONObject cartObject = (JSONObject)CartInfoArray.get(i);
                //String url[] = accountObject.getString("imagesrc").split(":");
                item[i]=new Store_Item(cartObject.getString("itemno"),"https://bit.ly/2V1ipNj", cartObject.getString("productname"), "가격 : "+cartObject.getString("price")+"원");  // drawble.a 를 accountObject.getString("imagsrc")로 이미지 소스 가져와서 이미지 삽입하는 방법.

                items.add(item[i]);
            }
            StoreListAdapter = new StoreListAdapter(getActivity(), items, R.layout.activity_storefrag_trip);

            StoreFragTripRecview.setAdapter(StoreListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //cartListAdapter.notifyDataSetChanged();
    }

    public void onRefresh() {
        items.clear();
        getCartData();

        StoreListAdapter.set_data(items);
        StoreListAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }
}
