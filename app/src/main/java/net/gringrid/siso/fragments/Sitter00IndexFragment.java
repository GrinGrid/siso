package net.gringrid.siso.fragments;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.SitterDetail;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;

import org.w3c.dom.Text;


/**
 * 구직정보 등록 > 인덱스
 */
public class Sitter00IndexFragment extends InputBaseFragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_work_env;
    SisoCheckBox id_cb_sitter_info;
    SisoCheckBox id_cb_photo;
    SisoCheckBox id_cb_id;

    User mUser;
    TextView id_tv_next_btn;
    TextView id_tv_percent;
    TextView id_tv_remain;
    LinearLayout id_ll_percent;
    TextView id_tv_title;
    TextView id_tv_content;

    public Sitter00IndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sitter00_index, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_cb_basic = (SisoCheckBox)view.findViewById(R.id.id_cb_basic);
        id_cb_work_env = (SisoCheckBox)view.findViewById(R.id.id_cb_work_env);
        id_cb_sitter_info = (SisoCheckBox)view.findViewById(R.id.id_cb_introduce);
        id_cb_photo = (SisoCheckBox)view.findViewById(R.id.id_cb_photo);
        id_cb_id = (SisoCheckBox)view.findViewById(R.id.id_cb_id);

        id_cb_basic.setOnClickListener(this);
        id_cb_work_env.setOnClickListener(this);
        id_cb_sitter_info.setOnClickListener(this);
        id_cb_photo.setOnClickListener(this);
        id_cb_id.setOnClickListener(this);

        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tv_percent = (TextView) view.findViewById(R.id.id_tv_percent);
        id_tv_remain = (TextView) view.findViewById(R.id.id_tv_remain);
        id_ll_percent = (LinearLayout) view.findViewById(R.id.id_ll_percent);
        id_tv_title = (TextView) view.findViewById(R.id.id_tv_title);
        id_tv_content = (TextView) view.findViewById(R.id.id_tv_content);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {
        if (mUser==null) return;
        id_cb_basic.setCheck(isBasicInfoInsert());
        id_cb_work_env.setCheck(isWishInfoInsert());
        id_cb_sitter_info.setCheck(isIntroduceInfoInsert());
        id_cb_photo.setCheck(isPhotoInfoInsert());
        id_cb_id.setCheck(isPhotoInfoInsert());

        if(id_cb_basic.isChecked() &&
                id_cb_work_env.isChecked() &&
                id_cb_sitter_info.isChecked() &&
                id_cb_photo.isChecked() &&
                id_cb_id.isChecked()
                ){
            id_tv_next_btn.setVisibility(View.VISIBLE);
        }else{
            id_tv_next_btn.setVisibility(View.GONE);
        }

        setPercentImage();
    }


    /**
     * 입력 상태에 따라 % 이미지를 설정한다.
     */
    private void setPercentImage() {
        int checkedCount = 0;
        if(id_cb_basic.isChecked()) checkedCount++;
        if(id_cb_work_env.isChecked()) checkedCount++;
        if(id_cb_sitter_info.isChecked()) checkedCount++;
        if(id_cb_photo.isChecked()) checkedCount++;
        if(id_cb_id.isChecked()) checkedCount++;
        int percent = checkedCount * 20;
        int remain = 100 - (checkedCount * 20);

        if(percent == 100){
            id_tv_next_btn.setVisibility(View.VISIBLE);
            id_ll_percent.setVisibility(View.GONE);
        }else if(percent > 0){
            String titleIdStr = "sitter00_title_percent"+percent;
            String contentIdStr = "sitter00_title_percent"+percent;
            Log.d(TAG, "setPercentImage: dtitleIdStr : "+titleIdStr);
            Log.d(TAG, "setPercentImage: contentIdStr : "+contentIdStr);
            String packageName = getContext().getPackageName();
            int titleId = getResources().getIdentifier(titleIdStr, "string", packageName);
            int contentId = getResources().getIdentifier(contentIdStr, "string", packageName);
            id_tv_title.setText(titleId);
            id_tv_content.setText(contentId);

            id_tv_next_btn.setVisibility(View.GONE);
            id_ll_percent.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams percentLp = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    percent
            );
            LinearLayout.LayoutParams remainLp = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    remain
            );
            id_tv_percent.setLayoutParams(percentLp);
            id_tv_remain.setLayoutParams(remainLp);
            id_tv_percent.setText(percent+"% 완성");
//            String imgIdStr = "percent"+(checkedCount*25);
//            String packageName = getContext().getPackageName();
//            int imgDrawable= getResources().getIdentifier(imgIdStr, "drawable", packageName);
//            id_iv_percent.setImageResource(imgDrawable);

        }

    }

    @Override
    protected boolean isValidInput() {
        return false;
    }

    @Override
    protected void saveData() {}

    @Override
    protected void moveNext() {
        Bundle bundle = new Bundle();
        bundle.putString(SitterDetailFragment.MODE, SitterDetailFragment.MODE_FROM_SITTER_INPUT);
        Fragment fragment = new SitterDetailFragment();
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, BaseActivity.ACTIONBAR_HIDE);
    }

    @Override
    public void onClick(View v) {

        Fragment fragment;
        switch (v.getId()) {

            // 돌봄기본정보
            case R.id.id_cb_basic:
                fragment = new Sitter01ExpPeriodFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage1);
                break;

            // 희망근무조건
            case R.id.id_cb_work_env:
                fragment = new Sitter02EnvBabygenderFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage2);
                break;

            // 시터기본정보
            case R.id.id_cb_introduce:
                fragment = new Sitter03GenderChildrenFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
                break;

            // 프로필사진등록
            case R.id.id_cb_photo:
                fragment = new CommonPhotoFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage4);
                break;

            // 신분증 촬영
            case R.id.id_cb_id:
                fragment = new CommonPhotoFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage4);
                break;

            case R.id.id_tv_next_btn:
                moveNext();
                break;
        }
    }

    private boolean isBasicInfoInsert(){
        // 자녀정보
        // 시터경력
        // *근무기간
        // *출퇴근방법
        // 출퇴근 가능거리
        // *근무가능시간
        // 희망시급
        // 희망월급

        if(TextUtils.isEmpty(mUser.sitterInfo.term_from)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.term_to)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.commute_type)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.mon)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.tue)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.wed)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.thu)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.fri)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.sat)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.sun)) return false;
        return true;
    }

    private boolean isWishInfoInsert(){
        // 근무조건 - 애완견
        // 근무조건 - CCTV
        // 근무조건 - 다른어른
        // 아이성별
        // 아이연령대
        if(TextUtils.isEmpty(mUser.sitterInfo.baby_boy) && TextUtils.isEmpty(mUser.sitterInfo.baby_girl)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.baby_age)) return false;
        return true;
    }

    private boolean isIntroduceInfoInsert(){
        // *성별
        // 자녀정보
        // *아이돌봄특기
        // *국적
        // *종교
        // *학력
        // 자격증
        // 자기소개 한줄
        // 자기소개
        if(TextUtils.isEmpty(mUser.sitterInfo.gender)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.skill)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.nat)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.religion)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.edu)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.brief)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.introduction)) return false;
        return true;
    }

    private boolean isPhotoInfoInsert(){
        // 사진
        if(TextUtils.isEmpty(mUser.imageInfo.prf_img_url)) return false;
        return true;
    }

    private boolean isIdPhotoInfoInsert(){
        // 신분증
//        if(TextUtils.isEmpty(mUser.imageInfo.id_img_url)) return false;
        return true;
    }
}
