package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Child {
    // 성명
    public String name;

    // 생년월일
    public String birth;

    // 성별
    public String gender;

    // 돌봄필요여부
    public String is_care;

    // 출산예정여부
    public String is_expect;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
