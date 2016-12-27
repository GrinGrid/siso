package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Sys {
    // 사용자 상태
    public String sys_status;
    // 마지막로그인
    public String sys_last_login;
    // 등록일
    public String sys_reg_date;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
