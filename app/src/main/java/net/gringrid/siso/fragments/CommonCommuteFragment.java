package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoPicker;

/**
 * 구직 / 구인정보 입력 공통 > 출퇴근방법, 거리
 */
public class CommonCommuteFragment extends InputBaseFragment {

    private TextView id_tv_next_btn;
    private SisoPicker id_pk_commute_distance;
    private int[] mRadioCommute = new int[]{R.id.id_tg_btn_commute, R.id.id_tg_btn_myhome, R.id.id_tg_btn_regident};
    private String mUserType;


    public CommonCommuteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_commute, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_pk_commute_distance = (SisoPicker)view.findViewById(R.id.id_pk_commute_distance);
        SisoUtil.setArrayClickListener(mRadioCommute, getView(), this);
        initViewString(view);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViewString(View view) {
        TextView id_tv_commute_title = (TextView)view.findViewById(R.id.id_tv_commute_title);
        TextView id_tv_commute_title_comment = (TextView)view.findViewById(R.id.id_tv_commute_title_comment);
        TextView id_tv_distance = (TextView)view.findViewById(R.id.id_tv_distance);
        TextView id_tv_distance_comment = (TextView)view.findViewById(R.id.id_tv_distance_comment);

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            id_tv_commute_title.setText(R.string.common_sitter_commute);
            id_tv_commute_title_comment.setText(R.string.common_sitter_commute_comment);
            id_tv_distance.setText(R.string.common_sitter_distance);
            id_tv_distance_comment.setText(R.string.common_sitter_distance_comment);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            id_tv_commute_title.setText(R.string.common_parent_commute);
            id_tv_commute_title_comment.setText(R.string.common_parent_commute_comment);
            id_tv_distance.setText(R.string.common_parent_distance);
            id_tv_distance_comment.setText(R.string.common_parent_distance_comment);
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (v.getId()) {

            case R.id.id_tg_btn_commute:
            case R.id.id_tg_btn_myhome:
            case R.id.id_tg_btn_regident:
                SisoUtil.selectRadio(mRadioCommute, viewId, getView());
                break;

            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;

                saveData();

                moveNext();
                break;
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioCommute, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_commute_type_select);
            return false;
        }
        return true;
    }

    @Override
    protected void loadData() {

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            // 출퇴근 유형
            if(!TextUtils.isEmpty(mUser.sitterInfo.commute_type)){
                int commuteType = Integer.parseInt(mUser.sitterInfo.commute_type);
                SisoUtil.selectRadio(mRadioCommute, mRadioCommute[commuteType], getView());
            }

            // 출퇴근 희망거리
            if(!TextUtils.isEmpty(mUser.sitterInfo.distance_limit)){
                id_pk_commute_distance.setIndex(Integer.parseInt(mUser.sitterInfo.distance_limit));
            }

        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            // 출퇴근 유형
            if(!TextUtils.isEmpty(mUser.parentInfo.commute_type)){
                int commuteType = Integer.parseInt(mUser.parentInfo.commute_type);
                SisoUtil.selectRadio(mRadioCommute, mRadioCommute[commuteType], getView());
            }

            // 출퇴근 희망거리
            if(!TextUtils.isEmpty(mUser.parentInfo.distance_limit)){
                id_pk_commute_distance.setIndex(Integer.parseInt(mUser.parentInfo.distance_limit));
            }

        }
    }

    @Override
    protected void saveData() {
        int commuteType = SisoUtil.getRadioValue(mRadioCommute, getView());
        String[] arrCommuteDistanceValues = getResources().getStringArray(R.array.picker_commute_distance_value);
        String selectedValue = arrCommuteDistanceValues[id_pk_commute_distance.getCurrentIndex()];

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            mUser.sitterInfo.commute_type = String.valueOf(commuteType);
            mUser.sitterInfo.distance_limit = selectedValue;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.commute_type = String.valueOf(commuteType);
            mUser.parentInfo.distance_limit = selectedValue;
        }
        Log.d(TAG, "onClick: "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

    }

    @Override
    protected void moveNext() {
        int titleId = 0;
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            titleId = R.string.sitter00_stage1;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            titleId = R.string.parent00_stage1;
        }

        // 출퇴근형, 재택형, 입주형에 따라 이동
        int commuteMethodValue = SisoUtil.getRadioValue(mRadioCommute, getView());
        if(commuteMethodValue <= 1){
            CommonScheduleHourlyFragment fragment = new CommonScheduleHourlyFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, titleId);
        }else{
            CommonScheduleSalaryFragment fragment = new CommonScheduleSalaryFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, titleId);
        }
    }
}
