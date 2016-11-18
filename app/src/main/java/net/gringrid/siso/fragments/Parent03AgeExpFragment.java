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
import net.gringrid.siso.views.SisoPicker;

/**
 * 구인정보 입력 > 시터나이, 경력
 */
public class Parent03AgeExpFragment extends InputBaseFragment{

    private TextView id_tv_next_btn;
    private SisoPicker id_pk_age;
    private SisoPicker id_pk_work_exp;

    public Parent03AgeExpFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent03_age_exp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_pk_age = (SisoPicker)view.findViewById(R.id.id_pk_age);
        id_pk_work_exp = (SisoPicker)view.findViewById(R.id.id_pk_work_exp);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;
                saveData();
                moveNext();
                break;
        }
    }

    @Override
    protected void moveNext() {
        CommonSkillFragment fragment = new CommonSkillFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
    }

    @Override
    protected void loadData() {
        // 시텅연령
        if(!TextUtils.isEmpty(mUser.parentInfo.sitter_age)){
            id_pk_age.setIndex(Integer.parseInt(mUser.parentInfo.sitter_age));
        }

        // 경력
        if(!TextUtils.isEmpty(mUser.parentInfo.work_exp)){
            id_pk_work_exp.setIndex(Integer.parseInt(mUser.parentInfo.work_exp));
        }

    }

    @Override
    protected boolean isValidInput() {
        // TODO
        return true;
    }

    @Override
    protected void saveData() {
        int work_exp = id_pk_work_exp.getCurrentIndex();
        mUser.parentInfo.work_exp = String.valueOf(work_exp);
        int sitter_age = id_pk_age.getCurrentIndex();
        mUser.parentInfo.sitter_age= String.valueOf(sitter_age);

        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

}
