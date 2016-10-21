package net.gringrid.siso.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Sitter {


    // 성별
    public String gender;
    // 아들수
    public String sons;
    // 딸 수
    public String daughters;
    // 경력
    public String work_year;
    // 희망활동기간 시작
    public String term_from;
    // 희망활동기간 끝
    public String term_to;
    // 보육특기
    public String skill;
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
    // 애완동물 유무
    public String env_pet;
    // CCTV 유무
    public String env_cctv;
    // 다른어른 유무
    public String env_adult;
    // 남자아이 선호
    public String baby_boy;
    // 여자아이 선호
    public String baby_girl;
    // 희망보육연령
    public String baby_age;
    // 종교
    public String religion;
    // 국적
    public String nat;
    // 비자만료일
    public String visa_exp;
    // 자기소개한줄
    public String brief;
    // 자기소개(200자이내)
    public String introduction;
    // 자격증
    public String license;
    // 학력
    public String edu;
    // 학교
    public String school;
    // 학과
    public String department;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
