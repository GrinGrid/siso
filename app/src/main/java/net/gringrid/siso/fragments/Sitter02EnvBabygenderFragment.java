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
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직정보입력 > 희망 근무조건 > 근무환경(애완동물, CCTV, 다른어른), 희망돌봄아이성별
 */
public class Sitter02EnvBabygenderFragment extends InputBaseFragment {


    private static final String TAG = "jiho";
    private TextView id_tv_next_btn;
    private SisoToggleButton id_tg_btn_pet;
    private SisoToggleButton id_tg_btn_cctv;
    private SisoToggleButton id_tg_btn_adult;
    private SisoToggleButton id_tg_btn_boy;
    private SisoToggleButton id_tg_btn_girl;

    public Sitter02EnvBabygenderFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter02_env_babygender, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_pet = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_pet);
        id_tg_btn_cctv = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_cctv);
        id_tg_btn_adult = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_adult);
        id_tg_btn_boy = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_boy);
        id_tg_btn_girl = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_girl);

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
    protected void loadData() {
        if(!TextUtils.isEmpty(mUser.sitterInfo.env_pet) && mUser.sitterInfo.env_pet.equals("1")){
            id_tg_btn_pet.setChecked(true);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.env_cctv) && mUser.sitterInfo.env_cctv.equals("1")){
            id_tg_btn_cctv.setChecked(true);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.env_adult) && mUser.sitterInfo.env_adult.equals("1")){
            id_tg_btn_adult.setChecked(true);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.baby_girl) && mUser.sitterInfo.baby_girl.equals("1")){
            id_tg_btn_girl.setChecked(true);
        }
        if(!TextUtils.isEmpty(mUser.sitterInfo.baby_boy) && mUser.sitterInfo.baby_boy.equals("1")){
            id_tg_btn_boy.setChecked(true);
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!id_tg_btn_girl.isChecked() && !id_tg_btn_boy.isChecked()){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_children_age);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        mUser.sitterInfo.env_pet = id_tg_btn_pet.isChecked()?"1":"0";
        mUser.sitterInfo.env_cctv = id_tg_btn_cctv.isChecked()?"1":"0";
        mUser.sitterInfo.env_adult = id_tg_btn_adult.isChecked()?"1":"0";

        mUser.sitterInfo.baby_boy = id_tg_btn_boy.isChecked()?"1":"0";
        mUser.sitterInfo.baby_girl = id_tg_btn_girl.isChecked()?"1":"0";

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Sitter02BabyageFragment fragment = new Sitter02BabyageFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage2);
    }
}
