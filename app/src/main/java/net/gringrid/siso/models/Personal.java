package net.gringrid.siso.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 8. 19..
 */

public class Personal{

    // 이메일
    public String  email;
    // 비밀번호
    public String  passwd;
    // 성명
    public String  name;
    // 생년월일
    public String birth_date;
    // 전화번호
    public String  phone;
    // 주소1
    public String  addr1;
    // 주소2
    public String  addr2;
    // 우편번호
    public String post_no;
    // 위도
    public String lng; //longitude;
    // 경도
    public String lat; //latitude;
    // 사용자구분(부모, 시터)
    public String user_type;
    // 가입일
    public String reg_date;
    // 마지막 로그인 일시
    public String last_login;
    // 푸시아이디
    public String push_id;
    // 직업 (부모만 사용)
    public String job;
    // 회사명 (부모만 사용)
    public String job_detail;

    @Override
    public String toString() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String str = "Email:"+email+", PASSWD:"+passwd+", NAME:"+name+", BIRTH_DATE:"+
//                birth_date+", PHONE:"+phone+", ADDR1:"+addr1+", ADDR2:"+addr2+", POST_NO:"+
//                post_no+", LONGITUDE:"+lng+", LATITUDE:"+lat+", USER_TYPE:"+
//                user_type+", REG_DATE:"+reg_date+", LAST_LOGIN:"+last_login+", PUSH_ID:"+push_id;
        return gson.toJson(this);
    }

}