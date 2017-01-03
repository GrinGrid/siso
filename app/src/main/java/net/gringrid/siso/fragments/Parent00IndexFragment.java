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
import android.widget.LinearLayout;
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
    TextView id_tv_percent;
    TextView id_tv_remain;
    TextView id_tv_title;
    TextView id_tv_content;
    LinearLayout id_ll_percent;

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

        float percent = checkedCount * 25;
        float remain = 100 - (checkedCount * 25);

        if(percent == 100){
            id_tv_next_btn.setVisibility(View.VISIBLE);
            id_ll_percent.setVisibility(View.GONE);
        }else if(percent > 0){
            String titleIdStr = "parent00_title_percent"+(checkedCount*25);
            String contentIdStr = "parent00_content_percent"+(checkedCount*25);
            Log.d(TAG, "setPercentImage: titleIdStr : "+titleIdStr);
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
            Log.d(TAG, "setPercentImage: percent : "+percent);
            Log.d(TAG, "setPercentImage: remain : "+remain);
            id_tv_percent.setLayoutParams(percentLp);
            id_tv_remain.setLayoutParams(remainLp);
            id_tv_percent.setText(percent+"% 완성");
        }
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
