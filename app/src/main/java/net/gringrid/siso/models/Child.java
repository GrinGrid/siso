package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Child {
    // 성명
    public String name;

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
        String str = "name : "+name+", birth : "+birth+", gender : "+gender+", isCare : "+isCare+", isExpect : "+isExpect;
        return str;
    }
}
