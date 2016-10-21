package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
