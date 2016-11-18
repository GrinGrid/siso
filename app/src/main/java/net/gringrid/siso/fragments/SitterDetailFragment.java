package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.gringrid.siso.R;
import net.gringrid.siso.adapter.TestimonialAdapter;
import net.gringrid.siso.models.Image;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.models.Testimonial;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoDetailItem;
import net.gringrid.siso.views.SisoDetailItem.DetailItem;
import net.gringrid.siso.views.SisoTimeTable;

import org.joda.time.LocalDate;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SitterDetailFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    private LinearLayout id_ll_skill;
    private LinearLayout id_ll_baby_age;
    private LinearLayout id_ll_baby_gender;
    private LinearLayout id_ll_env;
    private LinearLayout id_ll_testimonial;
    private LinearLayout id_ll_title;

    private TextView id_tv_name;
    private TextView id_tv_name_gender;

    private ImageView id_iv_profile;

    private TextView id_tv_age;
    private TextView id_tv_salary;
    private TextView id_tv_exp;
    private TextView id_tv_distance;
    private TextView id_tv_testimonial;

    private TextView id_tv_brief;
    private TextView id_tv_introduce;

    private TextView id_tv_commute;
    private TextView id_tv_commute_limit;
    private TextView id_tv_addr;
    private TextView id_tv_period;

    private TextView id_tv_cdrn_num;
    private TextView id_tv_nat;
    private TextView id_tv_rlg;
    private TextView id_tv_license;
    private TextView id_tv_edu;

    private User mUser;
    private User mUserDisplay;
    private SisoTimeTable id_stt;

    public SitterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments()!=null) {
            String userData = getArguments().getString(SharedData.USER);
            Gson gson = new Gson();
            mUser = gson.fromJson(userData, User.class);
            mUserDisplay = SisoUtil.convertDisplayValue(getContext(), mUser);
            Log.d(TAG, "onCreateView: receive mUser : "+mUser.toString());
        }

        return inflater.inflate(R.layout.fragment_sitter_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_ll_skill = (LinearLayout)view.findViewById(R.id.id_ll_skill);
        id_ll_baby_gender = (LinearLayout)view.findViewById(R.id.id_ll_baby_gender);
        id_ll_baby_age = (LinearLayout)view.findViewById(R.id.id_ll_baby_age);
        id_ll_env = (LinearLayout)view.findViewById(R.id.id_ll_env);
        id_ll_testimonial = (LinearLayout)view.findViewById(R.id.id_ll_testimonial);
        id_ll_title = (LinearLayout)view.findViewById(R.id.id_ll_title);

        id_tv_name = (TextView)view.findViewById(R.id.id_tv_name);
        id_tv_name_gender = (TextView)view.findViewById(R.id.id_tv_name_gender);

        id_iv_profile = (ImageView)view.findViewById(R.id.id_iv_profile);

        id_tv_age = (TextView)view.findViewById(R.id.id_tv_age);
        id_tv_salary = (TextView)view.findViewById(R.id.id_tv_salary);
        id_tv_exp = (TextView)view.findViewById(R.id.id_tv_exp);
        id_tv_distance = (TextView)view.findViewById(R.id.id_tv_distance);
        id_tv_testimonial = (TextView)view.findViewById(R.id.id_tv_testimonial);

        id_tv_brief = (TextView)view.findViewById(R.id.id_tv_brief);
        id_tv_introduce = (TextView)view.findViewById(R.id.id_tv_introduce);

        id_tv_commute = (TextView)view.findViewById(R.id.id_tv_commute);
        id_tv_commute_limit = (TextView)view.findViewById(R.id.id_tv_commute_limit);
        id_tv_addr = (TextView)view.findViewById(R.id.id_tv_addr);
        id_tv_period = (TextView)view.findViewById(R.id.id_tv_period);
        id_stt = (SisoTimeTable)view.findViewById(R.id.id_stt);

        id_tv_cdrn_num = (TextView)view.findViewById(R.id.id_tv_cdrn_num);
        id_tv_nat = (TextView)view.findViewById(R.id.id_tv_nat);
        id_tv_rlg = (TextView)view.findViewById(R.id.id_tv_rlg);
        id_tv_license = (TextView)view.findViewById(R.id.id_tv_license);
        id_tv_edu = (TextView)view.findViewById(R.id.id_tv_edu);

        id_ll_title.setOnClickListener(this);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    private  void loadData() {
        if(mUserDisplay!=null){
            setProfile();
            setSummary();
            setIntroduce();
            setBasic();
            setExtra();
        }
        setSkill();
        setBabyGender();
        setBabyAge();
        setEnv();
        setTestimonial();
        setSchedule();
    }

    private void setSchedule() {
        if(!TextUtils.isEmpty(mUser.sitterInfo.mon)){
            id_stt.setBitString(SisoTimeTable.MON, mUser.sitterInfo.mon);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.tue)){
            id_stt.setBitString(SisoTimeTable.TUE, mUser.sitterInfo.tue);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.wed)){
            id_stt.setBitString(SisoTimeTable.WED, mUser.sitterInfo.wed);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.thu)){
            id_stt.setBitString(SisoTimeTable.THU, mUser.sitterInfo.thu);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.fri)){
            id_stt.setBitString(SisoTimeTable.FRI, mUser.sitterInfo.fri);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.sat)){
            id_stt.setBitString(SisoTimeTable.SAT, mUser.sitterInfo.sat);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.sun)){
            id_stt.setBitString(SisoTimeTable.SUN, mUser.sitterInfo.sun);
        }
    }


    private void setTestimonial() {
        ArrayList<Testimonial> list = new ArrayList<>();

        String[] str = new String[]{
                "정말 감사합니다 덕분에 블라 블라 할 수 있네요1, 정말 감사합니다 덕분에 블라 블라 할 수 있네요1",
                "정말 감사합니다 덕분에 블라 블라 할 수 있네요2",
                "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3"+
                        "정말 감사합니다 덕분에 블라 블라 할 수 있네요3",
                "정말 감사합니다 덕분에 블라 블라 할 수 있네요4",
                "정말 감사합니다 덕분에 블라 블라 할 수 있네요5"
        };
        LayoutInflater mInflater = LayoutInflater.from(getContext());

        for(int i=0; i<5; i++){
            View convertView = mInflater.inflate(R.layout.adapter_testimonial_list_row, null);
            TextView content = (TextView)convertView.findViewById(R.id.id_tv_content);
            content.setText(str[i]);
            id_ll_testimonial.addView(convertView);

        }
    }

    /**
     * 상단 프로필 정보 세팅
     */
    private void setProfile() {
//        String gender = mUser.sitterInfo.gender.equals(User.GENDER_WOMAN)?"여":"남";
        int halfWidth = SisoUtil.getScreenWidth(getContext()) / 2;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(halfWidth, halfWidth);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        imageLoader.displayImage(mUser.imageInfo.prf_img_url, id_iv_profile);
        id_iv_profile.setLayoutParams(lp);
        id_tv_name.setText(mUserDisplay.personalInfo.name);
        id_tv_name_gender.setText(mUserDisplay.personalInfo.name+" 시터"+"("+mUserDisplay.sitterInfo.gender+")");
    }

    /**
     * 상단 요약정보 세팅
     */
    private void setSummary() {
//        id_tv_age.setText(String.valueOf(SisoUtil.convertBirthToAge(mUserDisplay.personalInfo.birth_date)));
        id_tv_age.setText(String.valueOf(SisoUtil.convertBirthToAge(mUserDisplay.personalInfo.birth_date)));
        // TODO 급여 다시 계산
        id_tv_salary.setText(mUserDisplay.sitterInfo.salary);
//        id_tv_salary.setText(String.format("%,d",12000));
//        id_tv_exp.setText(String.valueOf(mUser.sitterInfo.work_exp));
//        id_tv_exp.setText("10+");
        id_tv_exp.setText(mUserDisplay.sitterInfo.work_exp);
        // TODO 나와의 거리로 세팅
        id_tv_distance.setText("0.8");
        // TODO 후기갯수 세팅
        id_tv_testimonial.setText("2");
    }

    /**
     * 소개정보 세팅
     */
    private void setIntroduce() {
        id_tv_brief.setText(mUserDisplay.sitterInfo.brief);
        id_tv_introduce.setText(mUserDisplay.sitterInfo.introduction);
    }

    /**
     * 기본정보 세팅
     */
    private void setBasic(){

        id_tv_commute.setText(mUserDisplay.sitterInfo.commute_type);
        id_tv_commute_limit.setText(mUserDisplay.sitterInfo.distance_limit);
        id_tv_addr.setText(mUserDisplay.personalInfo.addr1);
        id_tv_period.setText(mUserDisplay.sitterInfo.term_from+"~"+mUserDisplay.sitterInfo.term_to);
    }

    /**
     * 돌봄특기 세팅
     */
    private void setSkill() {

        ArrayList<DetailItem> items = new ArrayList<>();
        items.add(new DetailItem(R.drawable.tg_ic_babycare_selected,    R.string.common_sitter_tg_skill_care));
        items.add(new DetailItem(R.drawable.tg_ic_baby_selected,        R.string.common_sitter_tg_skill_baby));
        items.add(new DetailItem(R.drawable.tg_ic_outdoor_selected,     R.string.common_sitter_tg_skill_outdoor));
        items.add(new DetailItem(R.drawable.tg_ic_housework_selected,   R.string.common_sitter_tg_skill_housekeeping));
        items.add(new DetailItem(R.drawable.tg_ic_study_selected,       R.string.common_sitter_tg_skill_homework));
        items.add(new DetailItem(R.drawable.tg_ic_escort_selected,      R.string.common_sitter_tg_skill_commute));
        items.add(new DetailItem(R.drawable.tg_ic_cook_selected,        R.string.common_sitter_tg_skill_cook));
        items.add(new DetailItem(R.drawable.tg_ic_english_selected,     R.string.common_sitter_tg_skill_foreign_language));
        items.add(new DetailItem(R.drawable.tg_ic_music_selected,       R.string.common_sitter_tg_skill_music_physical));


        // index가 , 로 구분되어 String으로 되어 있다
        String[] idxList = mUserDisplay.sitterInfo.skill.split(User.DELIMITER);
        ArrayList<DetailItem> displayItems = new ArrayList<>();

        for(int i=0; i<idxList.length; i++){
            displayItems.add(items.get(Integer.parseInt(idxList[i])));
        }

        setDetailItem(id_ll_skill, R.string.sitter_detail_sub_title_skill, displayItems);
    }
    /**
     * 돌봄 선호 아동 성별
     */
    private void setBabyGender() {
        ArrayList<DetailItem> items = new ArrayList<>();
        items.add(new DetailItem(R.drawable.tg_ic_boy_selected,         R.string.sitter02_tg_baby_gender_boy));
        items.add(new DetailItem(R.drawable.tg_ic_girl_selected,        R.string.sitter02_tg_baby_gender_girl));

        ArrayList<DetailItem> displayList = new ArrayList<>();
        if(mUserDisplay.sitterInfo.baby_boy.equals("1")){
            displayList.add(items.get(0));
        }
        if(mUserDisplay.sitterInfo.baby_girl.equals("1")){
            displayList.add(items.get(1));
        }

        setDetailItem(id_ll_baby_gender, R.string.sitter_detail_sub_title_baby_gender, displayList);
    }

    /**
     * 돌봄 선호 아동 연령
     */
    private void setBabyAge() {
        ArrayList<DetailItem> items = new ArrayList<>();
        items.add(new DetailItem(R.drawable.tg_ic_newborn_selected,     R.string.sitter7_tg_baby_age_0));
        items.add(new DetailItem(R.drawable.tg_ic_baby_selected,        R.string.sitter7_tg_baby_age_1));
        items.add(new DetailItem(R.drawable.tg_ic_enfant_selected,      R.string.sitter7_tg_baby_age_2));
        items.add(new DetailItem(R.drawable.tg_ic_kids_selected,        R.string.sitter7_tg_baby_age_3));
        items.add(new DetailItem(R.drawable.tg_ic_school_selected,      R.string.sitter7_tg_baby_age_4));

        // index가 , 로 구분되어 있다
        String[] idxList = mUserDisplay.sitterInfo.baby_age.split(User.DELIMITER);
        ArrayList<DetailItem> displayItems = new ArrayList<>();

        for(int i=0; i<idxList.length; i++){
            displayItems.add(items.get(Integer.parseInt(idxList[i])));
        }

        setDetailItem(id_ll_baby_age, R.string.sitter_detail_sub_title_baby_age, displayItems);
    }

    /**
     * 부가 정보
     */
    private void setExtra(){
//        id_tv_cdrn_num.setText(String.valueOf(mUser.sitterInfo.sons+mUser.sitterInfo.daughters));
        id_tv_nat.setText(mUserDisplay.sitterInfo.nat);
        id_tv_rlg.setText(mUserDisplay.sitterInfo.religion);
        id_tv_license.setText(mUserDisplay.sitterInfo.license);
        //TODO 학력에 따라 학교, 학과 세팅
        id_tv_edu.setText(mUserDisplay.sitterInfo.edu);
    }

    /**
     * 희망 근무 환경
     */
    private void setEnv() {
        ArrayList<DetailItem> items = new ArrayList<>();
        items.add(new DetailItem(R.drawable.tg_ic_pet_selected,     R.string.sitter02_tg_env_pet));
        items.add(new DetailItem(R.drawable.tg_ic_cctv_selected,    R.string.sitter02_tg_env_cctv));
        items.add(new DetailItem(R.drawable.tg_ic_adult_selected,   R.string.sitter02_tg_env_adult));

        ArrayList<DetailItem> displayList = new ArrayList<>();
        if(mUserDisplay.sitterInfo.env_pet.equals("1")){
            displayList.add(items.get(0));
        }
        if(mUserDisplay.sitterInfo.env_cctv.equals("1")){
            displayList.add(items.get(1));
        }
        if(mUserDisplay.sitterInfo.env_adult.equals("1")){
            displayList.add(items.get(2));
        }

        setDetailItem(id_ll_env, R.string.sitter_detail_sub_title_env, displayList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_ll_title:
                getActivity().onBackPressed();
                break;
        }
    }

    private void setDetailItem(LinearLayout container, int titleRsc, ArrayList<DetailItem> items){
        SisoDetailItem sisoDetailItem = new SisoDetailItem(getContext());
        sisoDetailItem.setData(titleRsc, items);
        container.addView(sisoDetailItem);
    }
}
