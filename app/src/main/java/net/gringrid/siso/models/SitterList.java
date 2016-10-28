package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class SitterList {
    // 성명
    public String name;

    // 한줄요구사항
    public String brief;

    // 주소1
    public String addr1;

    // 생년월일
    public int birth;

    // 성별
    public int gender;

    // 돌봄필요여부
    public int isCare;

    // 출산예정여부
    public int isExpect;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
