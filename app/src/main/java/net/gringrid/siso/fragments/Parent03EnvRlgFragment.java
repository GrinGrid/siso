package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;
import net.gringrid.siso.views.SisoToggleButton;


/**
 * 구인정보 입력 > 돌봄아동 환경, 종교
 */
public class Parent03EnvRlgFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private SisoToggleButton id_tg_btn_pet;
    private SisoToggleButton id_tg_btn_cctv;
    private SisoToggleButton id_tg_btn_adult;
    private ToggleButton id_tg_rlg_0;
    private ToggleButton id_tg_rlg_1;
    private ToggleButton id_tg_rlg_2;
    private ToggleButton id_tg_rlg_3;
    private ToggleButton id_tg_rlg_4;
    private SisoEditText id_et_rlg;

    int mRadioRlg[] = new int[]{
            R.id.id_tg_rlg_0,
            R.id.id_tg_rlg_1,
            R.id.id_tg_rlg_2,
            R.id.id_tg_rlg_3,
            R.id.id_tg_rlg_4
    };

    public Parent03EnvRlgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent03_env_rlg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_pet = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_pet);
        id_tg_btn_cctv = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_cctv);
        id_tg_btn_adult = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_adult);

        id_tg_rlg_0 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_0);
        id_tg_rlg_1 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_1);
        id_tg_rlg_2 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_2);
        id_tg_rlg_3 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_3);
        id_tg_rlg_4 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_4);
        id_tg_rlg_0.setOnClickListener(this);
        id_tg_rlg_1.setOnClickListener(this);
        id_tg_rlg_2.setOnClickListener(this);
        id_tg_rlg_3.setOnClickListener(this);
        id_tg_rlg_4.setOnClickListener(this);

        id_et_rlg = (SisoEditText) getView().findViewById(R.id.id_et_rlg);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                //TODO 데이타 체크
                saveData();
                Parent04ReasonFragment fragment = new Parent04ReasonFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_wish_title);
                break;

            // 종교 Radio 선택
            case R.id.id_tg_rlg_0:
            case R.id.id_tg_rlg_1:
            case R.id.id_tg_rlg_2:
            case R.id.id_tg_rlg_3:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.GONE);
                break;
            case R.id.id_tg_rlg_4:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void selectRadio(int[] radioList, int selectItem) {
        for(int src:radioList){
            if(src == selectItem){
                ((ToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((ToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if( ((ToggleButton)getView().findViewById(radioList[i])).isChecked() ){
                return i;
            }
        }
        return 0;
    }

    private void saveData() {
        mUser.parentInfo.env_pet = id_tg_btn_pet.isChecked()?"1":"0";
        mUser.parentInfo.env_cctv = id_tg_btn_cctv.isChecked()?"1":"0";
        mUser.parentInfo.env_adult = id_tg_btn_adult.isChecked()?"1":"0";

        int rlgRadio = getRadioValue(mRadioRlg);
        if(rlgRadio!=4){
            mUser.sitterInfo.religion = String.valueOf(rlgRadio);
        }else{
            mUser.sitterInfo.religion= id_et_rlg.getText().toString();
        }
        Log.d(TAG, "onClick: mUser.parentInfo : "+mUser.parentInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

}
