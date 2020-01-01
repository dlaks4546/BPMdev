package com.example.BPMdev;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReceiptList extends AppCompatActivity {
    ListAdapter adapter;
    private Connection conn;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);


        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        getSupportActionBar().setTitle("페이레터-"+LoginData.getId());
        if( tryConnect(true) ){
            list = getReceiptList();
        }

        ListView listview = (ListView) findViewById(R.id.receipt_list_view) ;
        adapter = new ListAdapter(list);
        listview.setAdapter(adapter) ;

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
        String LIST_MENU[];

        public ListAdapter(String[] List)
        {
            this.LIST_MENU = List;
        }

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
    }

    public String[] getReceiptList()
    {
        String[] templist = null;
        ResultSet result = null;
        Connection conn = null;
        ArrayList<String> list;

        String query = String.format("SELECT * FROM TBPApplyMst WHERE userno = "+"'"+LoginData.getUser_no()+"'");

        Log.w("시스템 오류 입니다.", "" + query);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://121.254.205.250;databaseName=BPM_DB","sa","rhdahwjs12#$");
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            list = new ArrayList<String>();

            while(result.next()){
                list.add(result.getString("Description") + " - " + result.getString("ApplyAmt") + "PT" + " - " + result.getString("RegDate"));
            }

            templist = list.toArray(new String[0]);
            conn.close();
        }
        catch (Exception e)
        {
            Log.w("시스템 오류 입니다.", "" + e.getMessage());
        }

        return templist;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private boolean tryConnect(boolean showMessage)
    {
        try {
            if(conn != null && !conn.isClosed())
            {
                return  true;
            }
            DBConnection connClass = new DBConnection();
            conn = connClass.getConnection(DBConnData.dbUser,DBConnData.dbUserPass,DBConnData.dbName,DBConnData.dbIp);

            if(conn == null)
            {
                if(showMessage)
                {
                    showToast(connClass.getLastErrMsg());
                }
                return false;
            }
            else
            {
                if(conn.isClosed())
                {
                    if(showMessage)
                    {
                        showToast("DataBase 연결 실패.");
                    }
                    return false;
                }
                else
                {
                    if(showMessage)
                    {
                        showToast("DataBase 연결 성공.");
                    }
                    return true;
                }

            }
        }
        catch (SQLException e)
        {
            if(showMessage)
            {
                showToast(e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    private void showToast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}


