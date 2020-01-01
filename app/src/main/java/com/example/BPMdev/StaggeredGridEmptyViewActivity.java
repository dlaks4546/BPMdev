package com.example.BPMdev;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class StaggeredGridEmptyViewActivity extends Activity implements AbsListView.OnItemClickListener {

    int position;
    public static Retrofit retrofit;
    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    private static final int FETCH_DATA_TASK_DURATION = 2000;

    private StaggeredGridView mGridView;
    private SampleAdapter mAdapter;
    JSONArray ProductInfoArray;
    private ArrayList<String> mData;
    private String search;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgv_empy_view);

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        LayoutInflater layoutInflater = getLayoutInflater();

        View header = layoutInflater.inflate(R.layout.list_item_header_footer, null);
        View footer = layoutInflater.inflate(R.layout.list_item_header_footer, null);
        TextView txtHeaderTitle = (TextView) header.findViewById(R.id.txt_title);
        TextView txtFooterTitle =  (TextView) footer.findViewById(R.id.txt_title);

        txtHeaderTitle.setText("뒤로가기");
        txtFooterTitle.setText("THE FOOTER!");

        mGridView.addHeaderView(header);
        mGridView.addFooterView(footer);
        mGridView.setEmptyView(findViewById(android.R.id.empty));
        mAdapter = new SampleAdapter(this, R.id.txt_line1);

        // do we have saved data?

        ////////////////////////////////////changs/////////////////////////////////////////
/*        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        Intent intent = getIntent();
        search = intent.getExtras().getString("search");

        try {
            String jsontext = "{\"productlist\":[{\"productname\":\"Mouse\", \"price\":\"23000\"},"+"{\"productname\":\"KeyBoard\", \"price\":\"12000\"},"+"{\"productname\":\"HDD\", \"price\":\"156000\"}]}";
            JSONObject object = null;
            object = new JSONObject(jsontext);
            ProductInfoArray = (JSONArray) object.getJSONArray("productlist");

//                        JSONObject accountObject = (JSONObject)accountInfoArray.get(1);

            if (savedInstanceState != null) {
                mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
                fillAdapter();
            }

            if (mData == null) {
                mData = SampleData.generateSampleData(ProductInfoArray.length(),ProductInfoArray);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////////////////////////////////////////
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);

        fetchData();
        mData= null;
    }

    private void fillAdapter() throws JSONException {
        ////////////////////////////////뤂 ///////////////////////////////////
        for (String data : mData) {
            mAdapter.add(data);
        }
        mAdapter.accountInfoArray=ProductInfoArray;
    }

    private void fetchData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(FETCH_DATA_TASK_DURATION);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                try {
                    fillAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sgv_empty_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.clear();
        fetchData();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


        if(position == 0)
        {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.searchBookActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {

        }

    }


    public int getpos()
    {
        return this.position;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }
}
