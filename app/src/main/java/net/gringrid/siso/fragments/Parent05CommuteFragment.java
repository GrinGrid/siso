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
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Parent05CommuteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private SisoPicker id_pk_commute_distance;
    private int[] mRadioCommute = new int[]{R.id.id_tg_btn_commute, R.id.id_tg_btn_myhome, R.id.id_tg_btn_regident};

    public Parent05CommuteFragment() {
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
        return inflater.inflate(R.layout.fragment_parent05_commute, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_pk_commute_distance = (SisoPicker)getView().findViewById(R.id.id_pk_commute_distance);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();
                // 출퇴근형, 재택형, 입주형에 따라 이동
                int commuteMethodValue = getRadioValue(mRadioCommute);
                if(commuteMethodValue <= 1){
                    Sitter05ScheduleSub1Fragment fragment = new Sitter05ScheduleSub1Fragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                }else{
                    Sitter05ScheduleSub2Fragment fragment = new Sitter05ScheduleSub2Fragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                }
                break;
        }
    }

    private void saveData() {
        int commuteMethodValue = getRadioValue(mRadioCommute);
        String[] arrCommuteDistanceValues = getResources().getStringArray(R.array.picker_commute_distance_value);
        String selectedValue = arrCommuteDistanceValues[id_pk_commute_distance.getCurrentIndex()];

        mUser.sitterInfo.commute_type = String.valueOf(commuteMethodValue);
        mUser.sitterInfo.distance_limit = selectedValue;
        Log.d(TAG, "onClick: mSitter : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if (((SisoToggleButton) getView().findViewById(radioList[i])).isChecked()) {
                return i;
            }
        }
        return 0;
    }


}
