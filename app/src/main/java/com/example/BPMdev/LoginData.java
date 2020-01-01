package com.example.BPMdev;

/**
 * Created by dongkwan on 2016-04-05.
 */
public class LoginData {
    public static String user_id; //로그인 된 아이디 값 저장된 변수 @@@@@@@@@
    public static String user_name;
    public static String user_no;
    public static int user_point;
    public static String user_company_no;
    public static String user_company_name;
    public String message;

    public static String getId() {
        return user_id;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static String getUser_no() {
        return user_no;
    }

    public static String getUser_company_name() {
        return user_company_name;
    }

    public static String getUser_company_no() {
        return user_company_no;
    }

    public static int getUser_point() {
        return user_point;
    }
}
