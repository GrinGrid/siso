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
 * 구직정보입력 > 희망 근무조건 > 희망 돌봄아이 연령
 * A simple {@link Fragment} subclass.
 */
public class Sitter07BabyInfoFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private int[] mAry = new int[]{
            R.id.id_tg_btn_age_0,
            R.id.id_tg_btn_age_1,
            R.id.id_tg_btn_age_2,
            R.id.id_tg_btn_age_3,
            R.id.id_tg_btn_age_4
    };

    public Sitter07BabyInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        Log.d(TAG, "onCreate: fragment1");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter7, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                saveData();
                Sitter08NatRlgFragment fragment = new Sitter08NatRlgFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_wish_title);
                break;
        }
    }

    private void saveData() {
        boolean isChecked;
        String ageStr = "";
        for(int i=0; i<mAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mAry[i])).isChecked();
            if(isChecked){
                ageStr += i+User.DELIMITER;
            }
        }
        mUser.sitterInfo.baby_age = ageStr;

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }
}
