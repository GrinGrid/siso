package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 구직 / 구인정보 입력 공통 > 스케줄/월급 설정
 */
public class CommonScheduleSalaryFragment extends InputBaseFragment implements InputBaseFragment.OnSaveCompleteListener {

    private String mUserType;

    private TextView id_tv_next_btn;
    private SisoPicker id_pk_salary_month;
    private int[] mRadioWeek = new int[]{R.id.id_tg_btn_week, R.id.id_tg_btn_weekend};
    private SisoToggleButton id_tg_btn_weekend;
    private SisoToggleButton id_tg_btn_week;

    public CommonScheduleSalaryFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter05_sub2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tg_btn_week = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_week);
        id_tg_btn_weekend = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_weekend);
        id_pk_salary_month = (SisoPicker)view.findViewById(R.id.id_pk_salary_month);
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_week.setOnClickListener(this);
        id_tg_btn_weekend.setOnClickListener(this);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;

                saveData();

                executeSave(this);

                break;
            case R.id.id_tg_btn_week:
                SisoUtil.selectRadio(mRadioWeek, R.id.id_tg_btn_week, getView());
                break;

            case R.id.id_tg_btn_weekend:
                SisoUtil.selectRadio(mRadioWeek, R.id.id_tg_btn_weekend, getView());
                break;
        }
    }

    @Override
    protected void loadData() {
        if(!TextUtils.isEmpty(mUser.sitterInfo.salary)){
            int idx = SisoUtil.findIndexFromArrayString(getContext(),
                    R.array.picker_salary_month, mUser.sitterInfo.salary);
            id_pk_salary_month.setIndex(idx);
        }

    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioWeek, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_schedule_weekend_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        int weekWorkValue = SisoUtil.getRadioValue(mRadioWeek,getView());
        String[] arrSalaryValues = getResources().getStringArray(R.array.picker_salary_month_value);
        String selectedValue = arrSalaryValues[id_pk_salary_month.getCurrentIndex()];

        if(mUserType.equals(User.USER_TYPE_SITTER)) {
            mUser.sitterInfo.mon = "1111111";
            mUser.sitterInfo.tue = "1111111";
            mUser.sitterInfo.wed = "1111111";
            mUser.sitterInfo.thu = "1111111";
            mUser.sitterInfo.fri = "1111111";
            mUser.sitterInfo.sat = "0000000";
            mUser.sitterInfo.sun = "0000000";

            if (weekWorkValue == 1) {
                mUser.sitterInfo.sat = "1111111";
                mUser.sitterInfo.sun = "1111111";
            }
            mUser.sitterInfo.salary= selectedValue;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.mon = "1111111";
            mUser.parentInfo.tue = "1111111";
            mUser.parentInfo.wed = "1111111";
            mUser.parentInfo.thu = "1111111";
            mUser.parentInfo.fri = "1111111";
            mUser.parentInfo.sat = "0000000";
            mUser.parentInfo.sun = "0000000";

            if (weekWorkValue == 1) {
                mUser.parentInfo.sat = "1111111";
                mUser.parentInfo.sun = "1111111";
            }
            mUser.parentInfo.salary= selectedValue;
        }

        Log.d(TAG, "onClick: mUser : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        int titleId = 0;
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            titleId = R.string.sitter_title;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            titleId = R.string.parent_title;
        }

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            Sitter00IndexFragment fragment = new Sitter00IndexFragment();
            ((BaseActivity) getActivity()).setCleanUpFragment(fragment, titleId);
        }else if(mUserType.equals(User.USER_TYPE_SITTER)) {
            Parent00IndexFragment fragment = new Parent00IndexFragment();
            ((BaseActivity) getActivity()).setCleanUpFragment(fragment, titleId);
        }
    }

    @Override
    public void onSaveComplete() {
        moveNext();
    }
}
