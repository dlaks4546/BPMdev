package com.example.BPMdev;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreFragGift extends Fragment {
    RecyclerView StoreFragGiftRecview;
    private SwipeRefreshLayout mSwipeRefresh;
    List<Store_Item> items;
    Store_Item[] item;
    StoreListAdapter StoreListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_storefrag_gift, container, false);

        StoreFragGiftRecview = (RecyclerView)view.findViewById(R.id.store_gift_view);
        getCartData();

        return view;
    }

    private void getCartData() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        items = new ArrayList<>();
        StoreFragGiftRecview.setHasFixedSize(true);
        StoreFragGiftRecview.setLayoutManager(layoutManager);
        try {
            int sum=0;
            String jsontext = "{\"cartlist\":[{\"productname\":\"명그누\", \"price\":\"2300\"},"+"{\"productname\":\"신예디\", \"price\":\"1200\"},"+"{\"productname\":\"리동무\", \"price\":\"1560\"}]}";
            //String jsontext = '{"cartlist":[{"productname":"미션임파서블","price" : "3000"},{"productname":"장난감","price":"5000"},{"productname" : "범죄도시","price":"10000"},{"productname":"아저씨","price":"100"}]}';
            JSONObject object = null;
            object = new JSONObject(jsontext);
            //JSONObject object = new JSONObject(jsontext);
            JSONArray CartInfoArray = (JSONArray) object.getJSONArray("cartlist");
            item = new Store_Item[CartInfoArray.length()];
            String a=Integer.toString(CartInfoArray.length());

            Log.i("....", jsontext);
            Log.i("index",a);
            for(int i=0; i<CartInfoArray.length(); i++)
            {
                JSONObject cartObject = (JSONObject)CartInfoArray.get(i);
                //String url[] = accountObject.getString("imagesrc").split(":");
                item[i] = new Store_Item("1",getURLForResource(R.drawable.a), cartObject.getString("productname"), "가격 : "+cartObject.getString("price")+"원");  // drawble.a 를 accountObject.getString("imagsrc")로 이미지 소스 가져와서 이미지 삽입하는 방법.

                items.add(item[i]);
            }
            StoreListAdapter = new StoreListAdapter(getActivity(), items, R.layout.activity_storefrag_gift);

            StoreFragGiftRecview.setAdapter(StoreListAdapter);
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
