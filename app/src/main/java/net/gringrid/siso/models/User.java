package net.gringrid.siso.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class User {
    // 사용자 구분 0:부모, 1:시터
    public static final int USER_TYPE_PARENT = 0;
    public static final int USER_TYPE_SITTER = 1;

    // 사용자 성별 0:여성, 1:남성
    public static final int GENDER_WOMAN = 0;
    public static final int GENDER_MAN = 1;

    public static final String DATA_POST_NO = "POST_NO";
    public static final String DATA_ADDR = "ADDR";
    public static final String DATA_LATITUDE = "LATITUDE";
    public static final String DATA_LONGITUDE = "LONGITUDE";

    @SerializedName("personal_info")
    public Personal personalInfo;

    @SerializedName("sitter_info")
    public Sitter sitterInfo;

    @SerializedName("parent_info")
    public Parent parentInfo;

    @SerializedName("sys_info")
    public System sysInfo;

    @Override
    public String toString() {
        String str = "";
        if(personalInfo!=null){
            str+="personal_info : "+personalInfo.toString();
        }
        if(sitterInfo!=null){
            str+="sitterInfo: "+sitterInfo.toString();
        }
        if(parentInfo!=null){
            str+="personal_info : "+personalInfo.toString();
        }
        if(sysInfo!=null){
            str+="sys_info : "+sysInfo.toString();
        }

        return str;
    }
}
