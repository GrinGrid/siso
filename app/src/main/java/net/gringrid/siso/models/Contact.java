package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 연락처 요청/수락/거절/취소/삭제
 */
public class Contact {

    public static final String ID = "ID";
    public static final String RCV_EMAIL = "RCV_EMAIL";
    public static final String CONTACT_ACTION = "CONTACT_ACTION";
    public static final String CONTACT_STATUS_REQUEST = "0";
    public static final String CONTACT_STATUS_ACCEPT = "1";
    public static final String CONTACT_STATUS_REJECT = "2";
    public static final String CONTACT_STATUS_DELETE = "3";
    public static final String CONTACT_STATUS_RECEIVE = "4";

    public static final String CONTACT_ACTION_REQUEST = "0";
    public static final String CONTACT_ACTION_CANCEL = "1";
    public static final String CONTACT_ACTION_DELETE = "2";
    public static final String CONTACT_ACTION_ACCEPT = "3";
    public static final String CONTACT_ACTION_REJECT = "4";
    public static final String CONTACT_ACTION_CALL   = "5";

    // contact collection id
    public String id;
    // 요청자 이메일
    public String req_email;
    // 수신자 이메일
    public String rcv_email;
    // 요청 메시지
    public String req_msg;
    // 요청일시
    public String req_date;
    // 응답 메시지
    public String answ_msg;
    // 응답일시
    public String answ_date;
    // 취소내용
    public String cancel_msg;
    // 취소일시
    public String cancel_date;
    // 최초 읽은 시간
    public String read_date;
    // 상태
    //    0 : 요청중
    //    1 : 요청수락
    //    2 : 요청거절
    //    3 : 삭제
    public String status;
    // 요청자 리스트출력여부
    public String req_list_yn;
    // 수신자 리스트출력여부
    public String rcv_list_yn;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
