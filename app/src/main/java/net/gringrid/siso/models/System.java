package net.gringrid.siso.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class System {
    // 사용자 상태
    public String sys_status;
    // 마지막로그인
    public String sys_last_login;
    // 등록일
    public String sys_reg_date;

    @Override
    public String toString() {
        String str = "STATUS :"+sys_status+", REG_DATE:"+sys_reg_date+", LAST_LOGIN:"+sys_last_login;
        return str;
    }
}
