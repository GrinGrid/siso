package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 8. 19..
 */

public class Member {
    // 이메일
    private String  email;
    // 비밀번호
    private String  passwd;
    // 성명
    private String  name;
    // 생년월일
    private int     birthDate;
    // 전화번호
    private String  phone;
    // 주소1
    private String  addr1;
    // 주소2
    private String  addr2;
    // 우편번호
    private double  postNo;
    // 위도
    private double  longitude;
    // 경도
    private double  latitude;
    // 사용자종류(부모, 시터)
    private int     userType;
    // 가입일
    private int     regDate;
    // 마지막 로그인 일시
    private int     lastLogin;
    // 푸시아이디
    private String  pushId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public double getPostNo() {
        return postNo;
    }

    public void setPostNo(double postNo) {
        this.postNo = postNo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getRegDate() {
        return regDate;
    }

    public void setRegDate(int regDate) {
        this.regDate = regDate;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public String toString() {
        return(email);
    }
}