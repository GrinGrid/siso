package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Image {

    // 프로필 사진
    public String prf_img_url;
    // 신분증 사진
    public String id_img_url;

    @Override
    public String toString() {
        String str = "profile:"+prf_img_url+", id image : "+id_img_url;
        return str;
    }
}
