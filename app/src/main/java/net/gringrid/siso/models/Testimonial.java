package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Testimonial {
    // 성명
    public String name;

    // 작성일
    public int write_date;

    // 내용
    public String content;

    // 사진 URL
    public String photo_url;


    @Override
    public String toString() {
        String str = "name : "+name+", write_date : "+write_date+", content : "+content+", photo_url : "+photo_url;
        return str;
    }
}
