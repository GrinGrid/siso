package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Status {

    public static final String ACTION_INPUT_COMPLETE = "00";
    public static final String ACTION_REJECT = "01";
    public static final String ACTION_ACCEPT = "02";
    public static final String ACTION_SITTER_EXPIRED = "03";
    public static final String ACTION_MATCH_COMPLETE = "04";
    public static final String ACTION_REQUEST_PAUSE = "05";
    public static final String ACTION_REQUEST_ACTIVE = "06";

    // 이메일
    public String email;

    /**
     *  변경종류
     * 00 : C 구인구직정보입력완료
     * 01 : A 자료불충분 거부
     * 02 : A 승인
     * 03 : S 시터풀 유효시간 만료
     * 04 : C 매칭완료로 정지요청
     * 05 : C 개인사정으로 정지요청
     * 06 : C 활성화요청
     */
    public String action_type;

    // 내용
    public String content;

    // 등록일
    public String reg_date;


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
