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
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

/**
 * 구인정보입력 > 국적, 학력 입력
 */
public class Parent03NatEduFragment extends InputBaseFragment{

    private static final int NAT_WRITE_INDEX = 2;
    private TextView id_tv_next_btn;
    private SisoEditText id_et_nat;

    int mRadioNat[] = new int[]{
            R.id.id_tg_nat_kor,
            R.id.id_tg_nat_chi_kor,
            R.id.id_tg_nat_write
    };

    int mRadioEdu[] = new int[]{
            R.id.id_tg_edu_0,
            R.id.id_tg_edu_1,
            R.id.id_tg_edu_2,
            R.id.id_tg_edu_3,
            R.id.id_tg_edu_4
    };

    public Parent03NatEduFragment() {
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
        return inflater.inflate(R.layout.fragment_parent03_nat_edu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        SisoUtil.setArrayClickListener(mRadioNat, view, this);
        SisoUtil.setArrayClickListener(mRadioEdu, view, this);

        id_et_nat = (SisoEditText) getView().findViewById(R.id.id_et_nat);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;
                saveData();
                moveNext();
                break;

            // 국적 Radio 선택
            case R.id.id_tg_nat_kor:
            case R.id.id_tg_nat_chi_kor:
                SisoUtil.selectRadio(mRadioNat, viewId, getView());
                id_et_nat.setVisibility(View.GONE);
                break;
            case R.id.id_tg_nat_write:
                SisoUtil.selectRadio(mRadioNat, viewId, getView());
                id_et_nat.setVisibility(View.VISIBLE);
                break;

            // 학력 Radio 선택
            case R.id.id_tg_edu_0:
            case R.id.id_tg_edu_1:
            case R.id.id_tg_edu_2:
            case R.id.id_tg_edu_3:
            case R.id.id_tg_edu_4:
                SisoUtil.selectRadio(mRadioEdu, viewId, getView());
                break;
        }
    }

    @Override
    protected void loadData() {
        SisoUtil.loadRadioDataWithWriteContent(mRadioNat, mUser.parentInfo.nat, NAT_WRITE_INDEX, R.id.id_et_nat, getView());

        if(!TextUtils.isEmpty(mUser.parentInfo.edu)){
            int eduType = Integer.parseInt(mUser.parentInfo.edu);
            SisoUtil.selectRadio(mRadioEdu, mRadioEdu[eduType], getView());
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioNat, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_nationality_select);
            return false;
        }
        if(!SisoUtil.isRadioGroupSelected(mRadioEdu, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_edu_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        int natRadio = SisoUtil.getRadioValue(mRadioNat, getView());
        int eduRadio = SisoUtil.getRadioValue(mRadioEdu, getView());
        mUser.parentInfo.nat = String.valueOf(natRadio);
        mUser.parentInfo.edu = String.valueOf(eduRadio);

        Log.d(TAG, "onClick: mUser: "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        CommonIntroductionFragment fragment = new CommonIntroductionFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_title);
    }

}
