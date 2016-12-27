package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * 부모 상세정보에 보여질 정보
 */
public class ParentDetail {

    public ParentDetail(){
        user = new User();
    }

    @SerializedName("user")
    public User user;

    // 조회한 사람과의 거리
    public String distance;
    // Contact row id
    public String contactId;
    // 연락요청한 사람
    public String contactReq;
    // 관심여부
    public String favorite;
    // 연락요청 상태
    public String contactStatus;


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
