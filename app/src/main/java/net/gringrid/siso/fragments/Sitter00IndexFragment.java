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
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;


/**
 * 구직정보 등록 > 인덱스
 */
public class Sitter00IndexFragment extends InputBaseFragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    ImageView id_iv_percent;
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_work_env;
    SisoCheckBox id_cb_sitter_info;
    SisoCheckBox id_cb_photo;

    User mUser;
    TextView id_tv_next_btn;

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
        id_iv_percent = (ImageView)view.findViewById(R.id.id_iv_percent);
        id_cb_basic = (SisoCheckBox)view.findViewById(R.id.id_cb_basic);
        id_cb_work_env = (SisoCheckBox)view.findViewById(R.id.id_cb_work_env);
        id_cb_sitter_info = (SisoCheckBox)view.findViewById(R.id.id_cb_introduce);
        id_cb_photo = (SisoCheckBox)view.findViewById(R.id.id_cb_photo);

        id_cb_basic.setOnClickListener(this);
        id_cb_work_env.setOnClickListener(this);
        id_cb_sitter_info.setOnClickListener(this);
        id_cb_photo.setOnClickListener(this);

        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
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

        if(id_cb_basic.isChecked() &&
                id_cb_work_env.isChecked() &&
                id_cb_sitter_info.isChecked() &&
                id_cb_photo.isChecked()){
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

        String imgIdStr = "percent"+(checkedCount*25);
        String packageName = getContext().getPackageName();
        int imgDrawable= getResources().getIdentifier(imgIdStr, "drawable", packageName);
        id_iv_percent.setImageResource(imgDrawable);
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
        bundle.putString(SharedData.USER, SharedData.getInstance(getContext()).getGlobalDataString(SharedData.USER));
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
}
