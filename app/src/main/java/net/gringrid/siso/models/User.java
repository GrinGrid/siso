package net.gringrid.siso.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class User {
    public static final String DELIMITER = ",";

    public static final int NULL = Integer.MAX_VALUE;
    // 사용자 구분 0:부모, 1:시터
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_TYPE_PARENT = "0";
    public static final String USER_TYPE_SITTER = "1";

    public static final String COMMUTE_TYPE = "COMMUTE_TYPE";
    public static final String COMMUTE_TYPE_COMMUTE  = "0";
    public static final String COMMUTE_TYPE_HOME = "1";
    public static final String COMMUTE_TYPE_REGIDENT = "2";

    // 사용자 성별 0:여성, 1:남성
    public static final String GENDER_WOMAN = "0";
    public static final String GENDER_MAN = "1";

    public static final String DATA_POST_NO = "POST_NO";
    public static final String DATA_ADDR = "ADDR";
    public static final String DATA_LATITUDE = "LATITUDE";
    public static final String DATA_LONGITUDE = "LONGITUDE";

    public static final String TERM_MIN = "17000101";
    public static final String TERM_MAX = "30000101";

    public User(){
        personalInfo = new Personal();
        sitterInfo = new Sitter();
        parentInfo = new Parent();
        sysInfo = new System();
        imageInfo = new Image();
    }

    @SerializedName("personal_info")
    public Personal personalInfo;

    @SerializedName("sitter_info")
    public Sitter sitterInfo;

    @SerializedName("parent_info")
    public Parent parentInfo;

    @SerializedName("sys_info")
    public System sysInfo;

    @SerializedName("image_info")
    public Image imageInfo;

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
            str+="parent_info: "+parentInfo.toString();
        }
        if(sysInfo!=null){
            str+="sys_info : "+sysInfo.toString();
        }
        if(imageInfo!=null){
            str+="image_info : "+imageInfo.toString();
        }

        return str;
    }
}
