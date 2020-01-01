package com.example.BPMdev;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SampleData {

    int BookNum;

//    public static final int SAMPLE_DATA_ITEM_COUNT = 6;

    public static ArrayList<String> generateSampleData(int size, JSONArray accountInfoArray) {
        final ArrayList<String> data = new ArrayList<String>(size);
        JSONObject accountObject;
        String url[];
        for (int i = 0; i < size; i++) {
            try {
                accountObject= (JSONObject) accountInfoArray.get(i);
                url = accountObject.getString("BookUrl").split(":");
                data.add("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080/img/"
                                +url[0]
                                +","
                                +accountObject.getString("BookListNumber")
                                +","
                                +accountObject.getString("BookNumber")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
