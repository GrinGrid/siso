package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class PushListItem {

    /**
     송신자            수신자	      발송시점	      받은 후 이동화면
     부모 / 시터	부모 / 시터	    연락처 요청시     연락처 요청 받은 리스트 or 송신자 상세화면으로 이동
     부모 / 시터	부모 / 시터	    연락처 수락시	  연락처 요청 한 리스트 or 송신자 상세화면으로 이동
     부모 / 시터	부모 / 시터	    연락처 거부시	  연락처 요청 한 리스트 or 송신자 상세화면으로 이동
     관리자         시터	        승인완료	      승인 안내 화면
     관리자         시터	        승인거부	      거부 안내 화면
     시터           관리자	        승인요청	      N/A
     부모 / 시터    부모 / 시터	    관심등록	      필요여부판단

     SENDER|#$%|PUSH_TYPE|#$%|CONTENT
     ex) nisclan@hotmail.com|#$%|10|#$%|안녕하세요 홍길순 시터 입니다 블라블라
     */
    public static final int PUSH_TYPE_CONTACT_REQUEST = 10;
    public static final int PUSH_TYPE_CONTACT_ACCEPT = 11;
    public static final int PUSH_TYPE_CONTACT_REJECT = 12;
    public static final int PUSH_TYPE_APROVAL_REQUEST = 20;
    public static final int PUSH_TYPE_APROVAL_ACCEPT = 21;
    public static final int PUSH_TYPE_APROVAL_REJECT = 22;
    public static final int PUSH_TYPE_ADD_FAVORITE = 30;

    // id
    public String id;

    // push 종류
    public String type;

    // 송신자
    public String sender;

    // 수신자
    public String receiver;

    // 메시지 내용
    public String msg;

    // 수신확인여부
    public String is_read;

    // 요청일시
   public String req_date;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
