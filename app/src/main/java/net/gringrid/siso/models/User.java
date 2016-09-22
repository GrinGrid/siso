package net.gringrid.siso.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class User {
    public static final int USER_TYPE_PARENT = 0;
    public static final int USER_TYPE_SITTER = 1;

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

    @Override
    public String toString() {
        String str = "personal_info : "+personalInfo.toString()+
                "sitter_info : "+sitterInfo.toString()+
                "parent_info : "+parentInfo.toString();
        return str;
    }
}
