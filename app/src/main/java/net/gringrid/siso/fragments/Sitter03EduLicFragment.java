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
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 자기소개 > 학력, 자격증
 */
public class Sitter03EduLicFragment extends InputBaseFragment{

    private TextView id_tv_next_btn;
    private SisoEditText id_et_university_name;
    private SisoEditText id_et_university_dipartment;
    private SisoEditText id_et_license;

    private ToggleButton id_tg_edu_0;
    private ToggleButton id_tg_edu_1;
    private ToggleButton id_tg_edu_2;
    private ToggleButton id_tg_edu_3;
    private ToggleButton id_tg_edu_4;
    private ToggleButton id_tg_edu_5;

    int mRadioEdu[] = new int[]{
            R.id.id_tg_edu_0,
            R.id.id_tg_edu_1,
            R.id.id_tg_edu_2,
            R.id.id_tg_edu_3,
            R.id.id_tg_edu_4,
            R.id.id_tg_edu_5
    };

    public Sitter03EduLicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter03_edu_lic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_university_name = (SisoEditText) getView().findViewById(R.id.id_et_university_name);
        id_et_university_dipartment = (SisoEditText) getView().findViewById(R.id.id_et_university_dipartment);
        id_et_license = (SisoEditText) getView().findViewById(R.id.id_et_license);

        id_tg_edu_0 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_0);
        id_tg_edu_1 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_1);
        id_tg_edu_2 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_2);
        id_tg_edu_3 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_3);
        id_tg_edu_4 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_4);
        id_tg_edu_5 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_5);
        id_tg_edu_0.setOnClickListener(this);
        id_tg_edu_1.setOnClickListener(this);
        id_tg_edu_2.setOnClickListener(this);
        id_tg_edu_3.setOnClickListener(this);
        id_tg_edu_4.setOnClickListener(this);
        id_tg_edu_5.setOnClickListener(this);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            // 학력 Radio 선택
            case R.id.id_tg_edu_0:
            case R.id.id_tg_edu_1:
            case R.id.id_tg_edu_2:
            case R.id.id_tg_edu_3:
                SisoUtil.selectRadio(mRadioEdu, viewId, getView());
                id_et_university_name.setVisibility(View.GONE);
                id_et_university_dipartment.setVisibility(View.GONE);
                break;
            case R.id.id_tg_edu_4:
            case R.id.id_tg_edu_5:
                SisoUtil.selectRadio(mRadioEdu, viewId, getView());
                id_et_university_name.setVisibility(View.VISIBLE);
                id_et_university_dipartment.setVisibility(View.VISIBLE);
                break;
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;

                saveData();

                moveNext();
                break;

        }
    }

    @Override
    protected void loadData() {
        if(!TextUtils.isEmpty(mUser.sitterInfo.edu)){
            int eduIdx = Integer.parseInt(mUser.sitterInfo.edu);
            SisoUtil.selectRadio(mRadioEdu, mRadioEdu[eduIdx], getView());

            if(eduIdx == 4 || eduIdx == 5){
                id_et_university_name.setVisibility(View.VISIBLE);
                id_et_university_dipartment.setVisibility(View.VISIBLE);
                id_et_university_name.setInput(mUser.sitterInfo.school);
                id_et_university_dipartment.setInput(mUser.sitterInfo.department);
            }
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.license)){
            id_et_license.setInput(mUser.sitterInfo.license);
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioEdu, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_edu_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        int eduRadio = SisoUtil.getRadioValue(mRadioEdu, getView());
        mUser.sitterInfo.edu = String.valueOf(eduRadio);
        if(eduRadio==4 || eduRadio==5){
            mUser.sitterInfo.school = id_et_university_name.getText().toString();
            mUser.sitterInfo.department = id_et_university_dipartment.getText().toString();
        }

        mUser.sitterInfo.license = id_et_license.getText().toString();

        Log.d(TAG, "fragment10 : mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        CommonIntroductionFragment fragment = new CommonIntroductionFragment();
        ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter00_stage3);
    }
}
