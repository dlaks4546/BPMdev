package com.example.BPMdev;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReceiptUseList extends AppCompatActivity {

    static final String[] LIST_MENU = {"음식-16,000PT-2019년 09월 27일", "애플-150,000PT-2019년 10월 27일", "다이슨-16,000PT-2019년 11월 27일"} ;
    static final String[] ADD_LIST_MENU = {"관리자 추가-150만-2019년 01월 01일", "관리자 추가-20만-2019년 03월 01일", "관리자 추가-10만-2019년 04월 01일"} ;
    ListAdapter adapter;
    AddListAdapter addadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_use_list);


        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        getSupportActionBar().setTitle("페이레터-"+LoginData.getId());

        ListView listview = (ListView) findViewById(R.id.receipt_use_list_view) ;
        ListView addlistview = (ListView) findViewById(R.id.receipt_add_list_view) ;
        adapter = new ListAdapter();
        addadapter = new AddListAdapter();
        listview.setAdapter(adapter) ;
        addlistview.setAdapter(addadapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;
                Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT).show();
                // TODO : use strText
                Intent intent = new Intent(getApplicationContext(),ReceiptinfoActivity.class);
                intent.putExtra("ReceiptSeqno",strText);
                startActivity(intent);
                finish();
            }
        });
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

    class ListAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return  LIST_MENU.length;
        }

        public Object getItem(int position)
        {
            return LIST_MENU[position];
        }

        public long getItemId(int position)
        {
            return position;

        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView view = new TextView(getApplicationContext());
            view.setText(LIST_MENU[position]);
            view.setTextSize(15.0f);
            view.setTextColor(Color.BLUE);

            return view;
        }

        public View getUseList(int position, View convertView, ViewGroup parent)
        {
            TextView view = new TextView(getApplicationContext());
            view.setText(LIST_MENU[position]);
            view.setTextSize(15.0f);
            view.setTextColor(Color.BLUE);

            return view;
        }
    }
    class AddListAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return  ADD_LIST_MENU.length;
        }

        public Object getItem(int position)
        {
            return ADD_LIST_MENU[position];
        }

        public long getItemId(int position)
        {
            return position;

        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView view = new TextView(getApplicationContext());
            view.setText(ADD_LIST_MENU[position]);
            view.setTextSize(15.0f);
            view.setTextColor(Color.BLUE);

            return view;
        }

        public View getUseList(int position, View convertView, ViewGroup parent)
        {
            TextView view = new TextView(getApplicationContext());
            view.setText(ADD_LIST_MENU[position]);
            view.setTextSize(15.0f);
            view.setTextColor(Color.BLUE);

            return view;
        }
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


