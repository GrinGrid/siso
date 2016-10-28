package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직정보입력 > 희망 근무조건 > 근무환경(애완동물, CCTV, 다른어른), 희망돌봄아이성별
 * A simple {@link Fragment} subclass.
 */
public class Sitter06EnvFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private SisoToggleButton id_tg_btn_pet;
    private SisoToggleButton id_tg_btn_cctv;
    private SisoToggleButton id_tg_btn_adult;
    private SisoToggleButton id_tg_btn_boy;
    private SisoToggleButton id_tg_btn_girl;

    public Sitter06EnvFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter06_env, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_pet = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_pet);
        id_tg_btn_cctv = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_cctv);
        id_tg_btn_adult = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_adult);
        id_tg_btn_boy = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_boy);
        id_tg_btn_girl = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_girl);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                //TODO 데이타 체크
                saveData();
                Sitter07BabyInfoFragment fragment = new Sitter07BabyInfoFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_wish_title);
                break;
        }
    }

    private void saveData() {
        mUser.sitterInfo.env_pet = id_tg_btn_pet.isChecked()?"1":"0";
        mUser.sitterInfo.env_cctv = id_tg_btn_cctv.isChecked()?"1":"0";
        mUser.sitterInfo.env_adult = id_tg_btn_adult.isChecked()?"1":"0";

        mUser.sitterInfo.baby_boy = id_tg_btn_boy.isChecked()?"1":"0";
        mUser.sitterInfo.baby_girl = id_tg_btn_girl.isChecked()?"1":"0";

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }
}
