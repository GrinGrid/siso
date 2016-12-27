package net.gringrid.siso.fragments;


import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import net.gringrid.siso.PopupContactReject;
import net.gringrid.siso.PopupContactRequest;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.TestimonialAdapter;
import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.Image;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.models.Testimonial;
import net.gringrid.siso.models.User;
import net.gringrid.siso.models.SitterDetail;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.FavoriteAPI;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.network.restapi.SitterAPI;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoDetailItem;
import net.gringrid.siso.views.SisoDetailItem.DetailItem;
import net.gringrid.siso.views.SisoDetailRow;
import net.gringrid.siso.views.SisoTimeTable;

import org.joda.time.LocalDate;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 시터 상세화면
 */
public class SitterDetailFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    public static final String MODE = "MODE";
    public static final String MODE_FROM_SITTER_INPUT = "MODE_FROM_SITTER_INPUT";
    public static final String MODE_FROM_SITTER_LIST = "MODE_FROM_SITTER_LIST";

    private LinearLayout id_ll_skill;
    private LinearLayout id_ll_baby_age;
    private LinearLayout id_ll_baby_gender;
    private LinearLayout id_ll_env;
//    private LinearLayout id_ll_testimonial;
    private LinearLayout id_ll_title;
    private LinearLayout id_ll_detail_row1;
    private LinearLayout id_ll_detail_row2;

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

    private ImageView id_iv_contact1;
    private ImageView id_iv_contact2;
    private ImageView id_iv_favorite;
    private TextView id_tv_contact1;
    private TextView id_tv_contact2;
    private TextView id_tv_favorite;

    // 앱 사용자
    private User mUser;
    // 조회 대상 사용자 원본 data
    private User mUserDisplay;
    private SisoTimeTable id_stt;
    private SitterDetail mSitterDetail;

    // 하단 버튼
    private LinearLayout id_ll_contact1;
    private LinearLayout id_ll_favorite;

    private String mMode;
    private String mTrgEmail;
    private LayoutInflater mInflater;
    private String mContactAction;

    public SitterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        mInflater = LayoutInflater.from(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmenot
        return inflater.inflate(R.layout.fragment_sitter_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_ll_skill = (LinearLayout)view.findViewById(R.id.id_ll_skill);
        id_ll_baby_gender = (LinearLayout)view.findViewById(R.id.id_ll_baby_gender);
        id_ll_baby_age = (LinearLayout)view.findViewById(R.id.id_ll_baby_age);
        id_ll_env = (LinearLayout)view.findViewById(R.id.id_ll_env);
//        id_ll_testimonial = (LinearLayout)view.findViewById(R.id.id_ll_testimonial);
        id_ll_title = (LinearLayout)view.findViewById(R.id.id_ll_title);

        id_ll_detail_row1 = (LinearLayout)view.findViewById(R.id.id_ll_detail_row1);
        id_ll_detail_row2 = (LinearLayout)view.findViewById(R.id.id_ll_detail_row2);

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

        id_stt = (SisoTimeTable)view.findViewById(R.id.id_stt);

        id_ll_contact1 = (LinearLayout)view.findViewById(R.id.id_ll_contact1);
        id_ll_contact1.setOnClickListener(this);
        id_ll_favorite = (LinearLayout)view.findViewById(R.id.id_ll_favorite);
        id_ll_favorite.setOnClickListener(this);

        id_ll_title.setOnClickListener(this);

        id_iv_contact1 = (ImageView)view.findViewById(R.id.id_iv_contact1);
        id_iv_contact2 = (ImageView)view.findViewById(R.id.id_iv_contact2);
        id_tv_contact1 = (TextView) view.findViewById(R.id.id_tv_contact1);
        id_tv_contact2 = (TextView) view.findViewById(R.id.id_tv_contact2);
        id_iv_favorite = (ImageView)view.findViewById(R.id.id_iv_favorite);
        id_tv_favorite = (TextView) view.findViewById(R.id.id_tv_favorite);

        setUserData();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setBottomButton() {
        // 가운데 버튼
        // 부모가 요청한경우
        // 시터가 요청한경우


        if(mSitterDetail!=null){
            setContactButton(mSitterDetail.contactReq, mSitterDetail.contactStatus);
            setFavoriteButton(mSitterDetail.favorite);
        }
    }

    private void setContactButton(String reqEmail, String status){
        Log.d(TAG, "setContactButton: reqEmail : "+reqEmail+", status : "+status);
        LinearLayout id_ll_contact2 = (LinearLayout)getView().findViewById(R.id.id_ll_contact2);
        id_ll_contact2.setVisibility(View.GONE);

        // 최초, 취소, 거절된경우 > 연락처 요청
        if(status.equals("9") || status.equals("2") || status.equals("3")){
            id_iv_contact1.setImageResource(R.drawable.ic_nv_request_normal);
            id_tv_contact1.setText(R.string.sitter_detail_bottom_contact_req);
            mContactAction = Contact.CONTACT_ACTION_REQUEST;
        // 요청중인경우
        //  요청자가 나 인경우 > 연락처 요청 취소
        //  요청자가 상대방인경우 > 수락 / 거절
        }else if(status.equals("0")){
            if(reqEmail.equals(mUser.personalInfo.email)){
                id_iv_contact1.setImageResource(R.drawable.ic_nv_request_over);
                id_tv_contact1.setText(R.string.sitter_detail_bottom_contact_cancel);
                mContactAction = Contact.CONTACT_ACTION_CANCEL;
            }else{
                id_ll_contact2.setVisibility(View.VISIBLE);
                id_iv_contact1.setImageResource(R.drawable.ic_nv_accept_normal);
                id_tv_contact1.setText(R.string.sitter_detail_bottom_contact_accept);
                id_ll_contact1.setTag(Contact.CONTACT_ACTION_ACCEPT);
                id_iv_contact2.setImageResource(R.drawable.ic_nv_reject_normal);
                id_tv_contact2.setText(R.string.sitter_detail_bottom_contact_reject);
                mContactAction = Contact.CONTACT_ACTION_REJECT;
            }

        // 연락처 수락 한 경우 > 전화하기
        }else if(status.equals("1")){
            id_iv_contact1.setImageResource(R.drawable.ic_nv_request_over);
            id_tv_contact1.setText(R.string.sitter_detail_bottom_contact_call);
            mContactAction = Contact.CONTACT_ACTION_CALL;
        }
    }

    private void setFavoriteButton(String value){
        if(mSitterDetail.favorite.equals(value)){
            id_iv_favorite.setImageResource(R.drawable.ic_nv_favorites_over);
            id_tv_favorite.setText(R.string.sitter_detail_bottom_favorite_del);
        }else{
            id_iv_favorite.setImageResource(R.drawable.ic_nv_favorites_normal);
            id_tv_favorite.setText(R.string.sitter_detail_bottom_favorite_add);
        }
    }

    private void loadData() {
        mUserDisplay = SisoUtil.convertDisplayValue(getContext(), mUserDisplay);

        if(mUserDisplay!=null){
            setProfile();
            setSummary();
            setIntroduce();
            setBasic();
            setSchedule();
            setExtra();
            setSkill();
            setBabyGender();
            setBabyAge();
            setEnv();
            //setTestimonial();
        }
    }

    private void setUserData() {
        Bundle bundle = getArguments();
        if(bundle!=null) {

            mMode = bundle.getString(MODE);

            if(!TextUtils.isEmpty(mMode)){
                // Sitter입력완료에서 넘어온경우
                if(mMode.equals(MODE_FROM_SITTER_INPUT)){
                    mUserDisplay = mUser;
                    loadData();

                // Sitter 리스트에서 선택해서 넘어온경우
                }else if(mMode.equals(MODE_FROM_SITTER_LIST)){
                    mTrgEmail = bundle.getString(SharedData.EMAIL);
                    Log.d(TAG, "setUserData: trgEmail : "+mTrgEmail);
                    findUser();
                }
            }
        }
    }

    private void findUser() {
        SitterAPI api = ServiceGenerator.getInstance(getActivity()).createService(SitterAPI.class);
        Call<SitterDetail> call = api.getDetail(mUser.personalInfo.email, mTrgEmail);
        call.enqueue(new Callback<SitterDetail>() {
            @Override
            public void onResponse(Call<SitterDetail> call, Response<SitterDetail> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: SitterDetail : "+response.body().toString());
                    mSitterDetail = response.body();
                    mUserDisplay = response.body().user;
                    loadData();
                    setBottomButton();
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SitterDetail> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void setSchedule() {
        Log.d(TAG, "setSchedule: ");
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.mon)){
            id_stt.setBitString(SisoTimeTable.MON, mUserDisplay.sitterInfo.mon);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.tue)){
            id_stt.setBitString(SisoTimeTable.TUE, mUserDisplay.sitterInfo.tue);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.wed)){
            id_stt.setBitString(SisoTimeTable.WED, mUserDisplay.sitterInfo.wed);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.thu)){
            id_stt.setBitString(SisoTimeTable.THU, mUserDisplay.sitterInfo.thu);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.fri)){
            id_stt.setBitString(SisoTimeTable.FRI, mUserDisplay.sitterInfo.fri);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.sat)){
            id_stt.setBitString(SisoTimeTable.SAT, mUserDisplay.sitterInfo.sat);
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.sun)){
            id_stt.setBitString(SisoTimeTable.SUN, mUserDisplay.sitterInfo.sun);
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
//            id_ll_testimonial.addView(convertView);

        }
    }

    /**
     * 상단 프로필 정보 세팅
     */
    private void setProfile() {
//        String gender = mUser.sitterInfo.gender.equals(User.GENDER_WOMAN)?"여":"남";
        int halfWidth = SisoUtil.getScreenWidth(getContext()) / 2;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(halfWidth, halfWidth);


        id_iv_profile.setLayoutParams(lp);
        Picasso.with(getContext()).load(mUserDisplay.imageInfo.prf_img_url).into(id_iv_profile);

//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
//        imageLoader.displayImage(mUser.imageInfo.prf_img_url, id_iv_profile);
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
        addDetailRow(id_ll_detail_row1, R.string.sitter_detail_basic_commute, mUserDisplay.sitterInfo.commute_type);
        addDetailRow(id_ll_detail_row1, R.string.sitter_detail_basic_commute_limit, mUserDisplay.sitterInfo.distance_limit);
        addDetailRow(id_ll_detail_row1, R.string.sitter_detail_basic_addr, mUserDisplay.personalInfo.addr1);
        String chrnNum = "";
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.sons)){
            if(Integer.valueOf(mUserDisplay.sitterInfo.sons) > 0){
                chrnNum += mUserDisplay.sitterInfo.sons+"남";
            }
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.daughters)){
            if(Integer.valueOf(mUserDisplay.sitterInfo.daughters) > 0){
                chrnNum += mUserDisplay.sitterInfo.daughters+"여";
            }
        }
        addDetailRow(id_ll_detail_row1, R.string.sitter_detail_basic_chdr_num, chrnNum);
        addDetailRow(id_ll_detail_row1, R.string.sitter_detail_basic_period, mUserDisplay.sitterInfo.term_from+"~"+mUserDisplay.sitterInfo.term_to);
    }

    /**
     * 돌봄특기 세팅
     */
    private void setSkill() {
        if(TextUtils.isEmpty(mUserDisplay.sitterInfo.skill)) return;
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
        if(TextUtils.isEmpty(mUserDisplay.sitterInfo.baby_boy) ||
            TextUtils.isEmpty(mUserDisplay.sitterInfo.baby_girl) )
            return;
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
        if(TextUtils.isEmpty(mUserDisplay.sitterInfo.baby_age)) return;

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
        addDetailRow(id_ll_detail_row2, R.string.sitter_detail_basic_nationality, mUserDisplay.sitterInfo.nat);
        addDetailRow(id_ll_detail_row2, R.string.sitter_detail_basic_religion, mUserDisplay.sitterInfo.religion);
        addDetailRow(id_ll_detail_row2, R.string.sitter_detail_basic_license, mUserDisplay.sitterInfo.license);
        //TODO 학력에 따라 학교, 학과 세팅
        addDetailRow(id_ll_detail_row2, R.string.sitter_detail_basic_education, mUserDisplay.sitterInfo.edu);
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
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.env_pet) &&
            mUserDisplay.sitterInfo.env_pet.equals("1")){
            displayList.add(items.get(0));
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.env_cctv) &&
                mUserDisplay.sitterInfo.env_cctv.equals("1")){
            displayList.add(items.get(1));
        }
        if(!TextUtils.isEmpty(mUserDisplay.sitterInfo.env_adult) &&
                mUserDisplay.sitterInfo.env_adult.equals("1")){
            displayList.add(items.get(2));
        }
        setDetailItem(id_ll_env, R.string.sitter_detail_sub_title_env, displayList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            // 연락처 요청, 수락, 전화하기, 요청취소
            case R.id.id_ll_contact1:
            // 연락처 거절
            case R.id.id_ll_contact2:
                if(mContactAction == null){
                    Log.d(TAG, "onClick: action is null");
                }
                actionExecute();
                Log.d(TAG, "contact1 onClick : "+mUserDisplay.toString());
                break;

            case R.id.id_ll_favorite:
                if(mSitterDetail!=null){
                    if(mSitterDetail.favorite.equals("Y")){
                        manageFavorite("DEL");
                    }else{
                        manageFavorite("ADD");
                    }
                }
                break;

            case R.id.id_ll_title:
                getActivity().onBackPressed();
                break;
        }
    }

    private void actionExecute(){
        Intent intent = null;
        if(mContactAction.equals(Contact.CONTACT_ACTION_REQUEST)){
            intent = new Intent(getActivity(), PopupContactRequest.class);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_ACCEPT)){
            intent = new Intent(getActivity(), PopupContactRequest.class);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_CALL)){
            intent = new Intent(getActivity(), PopupContactRequest.class);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_CANCEL)){
            intent = new Intent(getActivity(), PopupContactReject.class);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REJECT)) {
            intent = new Intent(getActivity(), PopupContactReject.class);
        }

        if(intent != null){
            intent.putExtra(Contact.CONTACT_ACTION, mContactAction);
            intent.putExtra(Contact.RCV_EMAIL, mTrgEmail);
            intent.putExtra(Contact.ID, mSitterDetail.contactId);
            Gson gson = new Gson();
            intent.putExtra("USER", gson.toJson(mUserDisplay));
            startActivityForResult(intent, 0);
        }
    }


    private void setDetailItem(LinearLayout container, int titleRsc, ArrayList<DetailItem> items){
        if(items==null || items.size()==0) return;
        SisoDetailItem sisoDetailItem = new SisoDetailItem(getContext());
        sisoDetailItem.setData(titleRsc, items);
        container.addView(sisoDetailItem);
    }


    /**
     * 항목 : 값 으로 표현되는 행 추가
     * @param container : 추가하고자 하는 linearlayout container
     * @param nameId : 항목명 리소스 아이디
     * @param value : 값
     */
    private void addDetailRow(LinearLayout container, int nameId, String value){
        SisoDetailRow sisoDetailCommute = new SisoDetailRow(getContext(), nameId, value);
        // TODO 추가하는 기준
        container.addView(sisoDetailCommute);
    }

    private void manageFavorite(String mode) {

        FavoriteAPI api = ServiceGenerator.getInstance(getActivity()).createService(FavoriteAPI.class);
        Log.d(TAG, "addFavorite: email : "+mUser.personalInfo.email);
        Log.d(TAG, "addFavorite: Target email : "+mTrgEmail);
        Call<Contact> call = null;
        if(mode.equals("ADD")){
            call = api.addFavorite(mUser.personalInfo.email, mTrgEmail);
        }else if(mode.equals("DEL")){
            call = api.delFavorite(mUser.personalInfo.email, mTrgEmail);
        }

        if(call == null) return;

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: contact : "+response.body().toString());
                    if(mSitterDetail.favorite.equals("Y")){
                        mSitterDetail.favorite = "N";
                        SisoUtil.showMsg(getContext(), "관심시터에서 해제 되었습니다");
                    }else{
                        mSitterDetail.favorite = "Y";
                        SisoUtil.showMsg(getContext(), "관심시터로 등록 되었습니다");
                    }
                    setFavoriteButton(mSitterDetail.favorite);

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
