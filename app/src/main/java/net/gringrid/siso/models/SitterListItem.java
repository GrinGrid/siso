package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class SitterListItem {
    // 이메일
    public String email;

    // 성명
    public String name;

    // IMAGE URL
    public String img;

    // 나이
    public String age;

    // 한줄요구사항
    public String brief;

    // 주소
    public String addr;

    // 급여
    public String salary;

    // 출퇴근 유형
    public String commute;

    // 리뷰숫자
    public String testimonial_cnt;

    // 관심 여부
    public String favorite;

    // 거리
    public String distance;

    // 연락처요청 상태
    public String contact_status;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
