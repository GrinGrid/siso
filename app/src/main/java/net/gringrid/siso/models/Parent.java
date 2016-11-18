package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Parent {

    // 성별
    public String gender;
    // 희망활동기간 시작
    public String term_from;
    // 희망활동기간 끝
    public String term_to;
    // 출퇴근유형
    public String commute_type;
    // 희망거리
    public String distance_limit;
    // 월요일 근무시간
    public String mon;
    // 화요일 근무시간
    public String tue;
    // 수요일 근무시간
    public String wed;
    // 목요일 근무시간
    public String thu;
    // 금요일 근무시간
    public String fri;
    // 토요일 근무시간
    public String sat;
    // 일요일 근무시간
    public String sun;
    // 시급
    public String salary;
    // 보육특기
    public String skill;
    // 애완동물 유무
    public String env_pet;
    // CCTV 유무
    public String env_cctv;
    // 다른어른 유무
    public String env_adult;
    // 국적
    public String nat;
    // 학력
    public String edu;
    // 종교
    public String religion;
    // 자기소개 한줄
    public String brief;
    // 자기소개(200자이내)
    public String introduction;
    // 시터희망연령
    public String sitter_age;
    // 시터희망경력
    public String work_exp;
    // 시터고용사유
    public String reason;
    // 직업
    public String job;
    // 직업상세
    public String job_detail;
    // 아이정보 List
    @SerializedName("children_info")
    public List<Child> children_info;


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
