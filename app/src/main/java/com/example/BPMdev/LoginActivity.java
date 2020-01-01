package com.example.BPMdev;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import retrofit2.Retrofit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {
    public static Retrofit retrofit;
    EditText EtUserName = null;
    EditText EtPassWord = null;
    Button BtLogin = null;
    Button Btregister = null;
    private Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BtLogin = (Button)findViewById(R.id.login_button);
        Btregister = (Button)findViewById(R.id.registerpage_button);
        EtUserName = (EditText)findViewById(R.id.login_id_edit);
        EtPassWord = (EditText)findViewById(R.id.login_password_edit);
/*
        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
*/

        BtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 정보 확인 및 사용자 정보 GET
                if( tryConnect(true) ){
                    getLoginData(EtUserName.getText().toString(), EtPassWord.getText().toString());
                }
            }
        });
        Btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( tryConnect(true) ){
                    insertTest();
                }

                //Intent intent = new Intent(getApplicationContext(), Register.class);
                //startActivity(intent);
            }
        });
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

    private void insertTest()
    {
        String regDate = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString();
        String query = String.format("SELECT * FROM Tmp WHERE " +
                "userid="+"'"+EtUserName.getText()+"'"+" and " +
                "password="+"'"+EtPassWord.getText()+"'");

        ResultSet result = null;
        Connection conn = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://121.254.205.250;databaseName=BPM_DB","sa","rhdahwjs12#$");
            Statement stmt = conn.createStatement();


            result = stmt.executeQuery(query);

            while(result.next()){
                String id = result.getString("test");
                //String pwd = result.getString("pwd");
                //String name = result.getString("name");
                //String phone = result.getString("reg_date");
                //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                if (id.equals(EtUserName.getText())) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }
            conn.close();
        }

        catch (Exception e)
        {
            Log.w("111Error connection", "" + e.getMessage());
        }
    }

    /* private DBStatus executeQuery(String query)
    {
        DBStatus objStatus = new DBStatus();
        try
        {
            if( tryConnect(true) )
            {
                PreparedStatement preStmt = conn.prepareStatement(query);
                objStatus.intRetVal = preStmt.executeUpdate();
                objStatus.strErrMsg = "";
                return objStatus;
            }
            else {
                objStatus.intRetVal = -1;
                objStatus.strErrMsg = "DB 연결 오류";
                return objStatus;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            objStatus.intRetVal = e.getErrorCode();
            objStatus.strErrMsg = e.getMessage();

            return objStatus;
        }
    }*/


    private void getLoginData(String userid, String password) {
        String LoginFlag = "N";
        ResultSet result = null;
        Connection conn = null;

        String query = String.format(
                "SELECT TUM.userid,TUM.userno,TUM.username,TUM.teamname,TUM.memberstoreno,TUBPM.remainamt FROM TUserMst TUM " +
                        "INNER JOIN TUserBPMst TUBPM " +
                        "ON TUM.UserNo = TUBPM.UserNo WHERE TUM.userID="+"'"+userid+"'"+" and TUM.PassWord=" + "'"+password+"'");

        Log.w("시스템 오류 입니다.", "" + query);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://121.254.205.250;databaseName=BPM_DB","sa","rhdahwjs12#$");
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(query);

            while(result.next()){
                LoginFlag = "Y";

                LoginData.user_id = result.getString("userid");                      //로그인 된 아이디 값 저장해 놓기. @@@@@@@@@@@@@@@
                LoginData.user_no = result.getString("userno");                      //유저 번호
                LoginData.user_name = result.getString("username");                  //유저 이름
                LoginData.user_point = Integer.parseInt(result.getString("remainamt"));                   //유저 잔액
                LoginData.user_company_name = result.getString("teamname");          //유저가 속한 회사
                LoginData.user_company_no = result.getString("memberstoreno");      //회사 번호
            }
            conn.close();
        }
        catch (Exception e)
        {
            Log.w("시스템 오류 입니다.", "" + e.getMessage());
        }

        //TODO 로그인 확인 및 정보 가져오기.
        if(userid.equals("jmlee") && password.equals("1"))
        {
            LoginData.user_id = userid;                             //로그인 된 아이디 값 저장해 놓기. @@@@@@@@@@@@@@@
            LoginData.user_no = "1";                               //유저 번호
            LoginData.user_name = "이재민";                        //유저 이름
            LoginData.user_point = 1000000;                        //유저 잔액
            LoginData.user_company_name = "페이레터";             //유저가 속한 회사
            LoginData.user_company_no = "1";                      //회사 번호
            Toast.makeText(getApplicationContext(), userid+"님 환영합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        if (LoginFlag.equals("Y")) {
            Toast.makeText(getApplicationContext(), userid+"님 환영합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "올바른 계정 정보가 아닙니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            EtUserName.setText("");
            EtPassWord.setText("");
        }

    }

    private void showToast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
