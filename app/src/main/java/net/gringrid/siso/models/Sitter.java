package net.gringrid.siso.models;

/**
 * Created by choijiho on 16. 9. 13..
 */
public class Sitter {
    public static final String SKILL_CARE = "CARE";
    public static final String SKILL_BABY = "BABY";
    public static final String SKILL_OUTDOOR = "OUTDOOR";
    public static final String SKILL_HOUSE_KEEPING = "HOUSE_KEEPING";
    public static final String SKILL_HOMEWORK = "HOMEWORK ";
    public static final String SKILL_COMMUTE = "COMMUTE ";
    public static final String SKILL_COOK = "COOK";
    public static final String SKILL_FOREIGN_LANGUAGE = "FOREIGN_LANGUAGE";
    public static final String SKILL_MUSIC_PHYSICAL = "MUSIC_PHYSICAL";
    public static final String SKILL_DELIMITER = ",";


    // 성별
    public int gender;
    // 아들수
    public int sons;
    // 딸 수
    public int daughters;
    // 경력
    public int work_year;
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
    public String evn_adult;
    // 희망보육성별
    public String baby_gender;
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
    // 학교
    public String school;
    // 학과
    public String department;

    @Override
    public String toString() {
        return
        "gender : "+gender+", "+
        "sons : "+sons+", "+
        "daughters : "+daughters+", "+
        "work_year : "+work_year+", "+
        "term_from : "+term_from+", "+
        "term_to : "+term_to+", "+
        "skill : "+skill+", "+
        "commute_type : "+commute_type+", "+
        "distance_limit : "+distance_limit+", "+
        "mon : "+mon+", "+
        "tue : "+tue+", "+
        "wed : "+wed+", "+
        "thu : "+thu+", "+
        "fri : "+fri+", "+
        "sat : "+sat+", "+
        "sun : "+sun+", "+
        "salary : "+salary+", "+
        "env_pet : "+env_pet+", "+
        "env_cctv : "+env_cctv+", "+
        "evn_adult : "+evn_adult+", "+
        "baby_gender : "+baby_gender+", "+
        "baby_age : "+baby_age+", "+
        "religion : "+religion+", "+
        "nat : "+nat+", "+
        "visa_exp : "+visa_exp+", "+
        "brief : "+brief+", "+
        "introduction : "+introduction+", "+
        "license : "+license+", "+
        "school : "+school+", "+
        "department : "+department;
    }
}
