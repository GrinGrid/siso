package net.gringrid.siso.fragments;


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
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;

/**
 * 구인정보입력 > Index
 */
public class Parent00IndexFragment extends InputBaseFragment{

    private static final String TAG = "jiho";
    ImageView id_iv_percent;
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_env;
    SisoCheckBox id_cb_sitter;
    SisoCheckBox id_cb_photo;

    TextView id_tv_next_btn;

    public Parent00IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: fragment1");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent00_index, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_iv_percent = (ImageView)view.findViewById(R.id.id_iv_percent);
        id_cb_basic = (SisoCheckBox)getView().findViewById(R.id.id_cb_basic);
        id_cb_env = (SisoCheckBox)getView().findViewById(R.id.id_cb_env);
        id_cb_sitter = (SisoCheckBox)getView().findViewById(R.id.id_cb_sitter);
        id_cb_photo = (SisoCheckBox)getView().findViewById(R.id.id_cb_photo);

        id_cb_basic.setOnClickListener(this);
        id_cb_env.setOnClickListener(this);
        id_cb_sitter.setOnClickListener(this);
        id_cb_photo.setOnClickListener(this);

        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View v) {

        Fragment fragment;

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                moveNext();
                //((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;

            case R.id.id_cb_basic:
                fragment = new CommonWorkPeriodFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage1);
                break;

            case R.id.id_cb_env:
                fragment = new Parent02ChildrenFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage2);
                break;

            case R.id.id_cb_sitter:
                fragment = new Parent03AgeExpFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
                break;

            case R.id.id_cb_photo:
                CommonPhotoFragment commonPhotoFragment = new CommonPhotoFragment();
                ((BaseActivity) getActivity()).setFragment(commonPhotoFragment, R.string.sitter00_stage4);
                break;

        }
    }

    @Override
    protected void loadData() {
        id_cb_basic.setCheck(isBasicInfoInsert());
        id_cb_env.setCheck(isEnvInfoInsert());
        id_cb_sitter.setCheck(isSitterInfoInsert());
        id_cb_photo.setCheck(isPhotoInfoInsert());
        setPercentImage();
    }

    /**
     * 입력 상태에 따라 % 이미지를 설정한다.
     */
    private void setPercentImage() {
        int checkedCount = 0;
        if(id_cb_basic.isChecked()) checkedCount++;
        if(id_cb_env.isChecked()) checkedCount++;
        if(id_cb_sitter.isChecked()) checkedCount++;
        if(id_cb_photo.isChecked()) checkedCount++;

        String imgIdStr = "percent"+(checkedCount*25);
        String packageName = getContext().getPackageName();
        int imgDrawable= getResources().getIdentifier(imgIdStr, "drawable", packageName);
        id_iv_percent.setImageResource(imgDrawable);
    }

    private boolean isBasicInfoInsert(){

        // *근무기간설정
        // *출퇴근방법
        // 출퇴근 가능거리
        // *스케줄
        // 임금
        if(TextUtils.isEmpty(mUser.parentInfo.term_from)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.term_to)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.commute_type)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.mon)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.tue)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.wed)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.thu)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.fri)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.sat)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.sun)) return false;
        return true;
    }

    private boolean isEnvInfoInsert(){
        // 돌봄필요한 아이정보
        // 근무조건 - 애완견
        // 근무조건 - CCTV
        // 근무조건 - 다른어른
        // *종교
        // 구인을 하는 이유
        // 직업
        if(mUser.parentInfo.children_info == null || mUser.parentInfo.children_info.size()==0){
            Log.d(TAG, "isEnvInfoInsert: children");
            return false;
        }
        if(TextUtils.isEmpty(mUser.parentInfo.religion)){
            Log.d(TAG, "isEnvInfoInsert: religion");
            return false;
        }
        if(TextUtils.isEmpty(mUser.parentInfo.reason)){
            Log.d(TAG, "isEnvInfoInsert: reason");
            return false;
        }
        if(TextUtils.isEmpty(mUser.parentInfo.job)){
            Log.d(TAG, "isEnvInfoInsert: job");
            return false;
        }
        return true;
    }

    private boolean isSitterInfoInsert(){
        // 시터연령
        // 시터경력
        // 시터특기
        // 시터국적
        // 시터학력
        // 구인조건 한줄
        // 구인조건 소개
        if(TextUtils.isEmpty(mUser.parentInfo.sitter_age)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.work_exp)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.skill)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.nat)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.edu)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.brief)) return false;
        if(TextUtils.isEmpty(mUser.parentInfo.introduction)) return false;
        return true;
    }

    private boolean isPhotoInfoInsert(){
        // 사진
//        if(TextUtils.isEmpty(mUser.personalInfo.)) return false;
        return true;
    }

    @Override
    protected boolean isValidInput() {
        return false;
    }

    @Override
    protected void saveData() { }

    @Override
    protected void moveNext() {
        Bundle bundle = new Bundle();
        bundle.putString(ParentDetailFragment.MODE, ParentDetailFragment.MODE_FROM_PARENT_INPUT);
        Fragment fragment = new ParentDetailFragment();
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, BaseActivity.ACTIONBAR_HIDE);

    }
}
