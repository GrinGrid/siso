package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 8. 19..
 */

public class Personal{

    // 이메일
    public String  email;
    // 비밀번호
    public String  passwd;
    // 성명
    public String  name;
    // 생년월일
    public int     birthDate;
    // 전화번호
    public String  phone;
    // 주소1
    public String  addr1;
    // 주소2
    public String  addr2;
    // 우편번호
    public String postNo;
    // 위도
    public String lng; //longitude;
    // 경도
    public String lat; //latitude;
    // 사용자종류(부모, 시터)
    public int userType;
    // 가입일
    public String regDate;
    // 마지막 로그인 일시
    public String lastLogin;
    // 푸시아이디
    public String  pushId;

    public String  sessionKey;

    @Override
    public String toString() {
        String str = "Email:"+email+", PASSWD:"+passwd+", NAME:"+name+", BIRTH_DATE:"+
                birthDate+", PHONE:"+phone+", ADDR1:"+addr1+", ADDR2:"+addr2+", POST_NO:"+
                postNo+", LONGITUDE:"+lng+", LATITUDE:"+lat+", USER_TYPE:"+
                userType+", REG_DATE:"+regDate+", LAST_LOGIN:"+lastLogin+", PUSH_ID:"+pushId;
        return str;
    }

}