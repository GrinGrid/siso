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

/**
 * A simple {@link Fragment} subclass.
 */
public class Parent04ReasonFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private ToggleButton id_tg_reason_0;
    private ToggleButton id_tg_reason_1;
    private ToggleButton id_tg_reason_2;
    private ToggleButton id_tg_reason_3;
    private ToggleButton id_tg_reason_4;
    private ToggleButton id_tg_reason_5;
    private ToggleButton id_tg_reason_6;
    private ToggleButton id_tg_reason_7;
    private SisoEditText id_et_reason;

    int mRadioReason[] = new int[]{
            R.id.id_tg_reason_0,
            R.id.id_tg_reason_1,
            R.id.id_tg_reason_2,
            R.id.id_tg_reason_3,
            R.id.id_tg_reason_4,
            R.id.id_tg_reason_5,
            R.id.id_tg_reason_6,
            R.id.id_tg_reason_7
    };

    public Parent04ReasonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent04_reason, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        id_tg_reason_0 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_0);
        id_tg_reason_1 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_1);
        id_tg_reason_2 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_2);
        id_tg_reason_3 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_3);
        id_tg_reason_4 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_4);
        id_tg_reason_5 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_5);
        id_tg_reason_6 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_6);
        id_tg_reason_7 = (ToggleButton)getView().findViewById(R.id.id_tg_reason_7);
        id_tg_reason_0.setOnClickListener(this);
        id_tg_reason_1.setOnClickListener(this);
        id_tg_reason_2.setOnClickListener(this);
        id_tg_reason_3.setOnClickListener(this);
        id_tg_reason_4.setOnClickListener(this);
        id_tg_reason_5.setOnClickListener(this);
        id_tg_reason_6.setOnClickListener(this);
        id_tg_reason_7.setOnClickListener(this);
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
            case R.id.id_tg_reason_0:
            case R.id.id_tg_reason_1:
            case R.id.id_tg_reason_2:
            case R.id.id_tg_reason_3:
            case R.id.id_tg_reason_4:
            case R.id.id_tg_reason_5:
            case R.id.id_tg_reason_6:
                selectRadio(mRadioReason, viewId);
                id_et_reason.setVisibility(View.GONE);
                break;
            case R.id.id_tg_reason_7:
                selectRadio(mRadioReason, viewId);
                id_et_reason.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void saveData() {

        int rlgRadio = getRadioValue(mRadioReason);
        if(rlgRadio!=4){
            mUser.parentInfo.religion = String.valueOf(rlgRadio);
        }else{
            mUser.parentInfo.religion= id_et_reason.getText().toString();
        }
        Log.d(TAG, "onClick: mUser.parentInfo : "+mUser.parentInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
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
}
