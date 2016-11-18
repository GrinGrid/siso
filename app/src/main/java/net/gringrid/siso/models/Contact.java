package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 연락처 요청/수락/거절/취소/삭제
 */
public class Contact {

    public static final String RCV_EMAIL = "RCV_EMAIL";
    public static final String CONTACT_STATUS = "CONTACT_STATUS";
    public static final String CONTACT_STATUS_REQUEST = "0";
    public static final String CONTACT_STATUS_ACCEPT = "1";
    public static final String CONTACT_STATUS_REJECT = "2";
    public static final String CONTACT_STATUS_DELETE = "3";

    // 요청자 이메일
    public String req_email;
    // 수신자 이메일
    public String rcv_email;
    // 요청 메시지
    public String req_msg;
    // 응답 메시지
    public String rcv_msg;
    // 요청일시
    public String req_date;
    // 응답일시
    public String rcv_date;
//    0 : 요청중
//    1 : 요청수락
//    2 : 요청거절
//    3 : 삭제
    // 상태
    public String status;
    // 마지막 수정일시
    public String last_update;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
