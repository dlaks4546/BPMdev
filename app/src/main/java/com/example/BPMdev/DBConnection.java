package com.example.BPMdev;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection {
    private Connection connection = null;
    private String ConnectionURL = null;
    private String errMsg = "";

    @SuppressLint("NewApi")
    public Connection getConnection(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try
        {
            DriverManager.registerDriver((Driver)Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance());
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user+ ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
            Log.d("#DB", "after connection");
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
            errMsg = se.getMessage();
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
            errMsg = e.getMessage();
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
            errMsg = e.getMessage();
        }
        return connection;
    }

    public String getLastErrMsg(){
        return errMsg;
    }
}
