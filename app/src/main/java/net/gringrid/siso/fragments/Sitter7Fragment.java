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
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직정보입력 > 희망 근무조건 > 희망 돌봄아이 연령
 * A simple {@link Fragment} subclass.
 */
public class Sitter7Fragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "jiho";
    Sitter mSitter;
    private TextView id_tv_next_btn;
    private SisoToggleButton id_tg_btn_age1;
    private SisoToggleButton id_tg_btn_age2;
    private SisoToggleButton id_tg_btn_age3;
    private SisoToggleButton id_tg_btn_age4;
    private SisoToggleButton id_tg_btn_age5;

    public Sitter7Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSitter = SharedData.getInstance(getContext()).getSitterData();
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

        id_tg_btn_age1 = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_age_1);
        id_tg_btn_age2 = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_age_2);
        id_tg_btn_age3 = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_age_3);
        id_tg_btn_age4 = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_age_4);
        id_tg_btn_age5 = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_age_5);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                saveData();
                Sitter8Fragment fragment = new Sitter8Fragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_wish_title);
                break;
        }
    }

    private void saveData() {
        String babyAge = "";
        if(id_tg_btn_age1.isChecked()){
            babyAge+="1,";
        }
        if(id_tg_btn_age2.isChecked()){
            babyAge+="2,";
        }
        if(id_tg_btn_age3.isChecked()){
            babyAge+="3,";
        }
        if(id_tg_btn_age4.isChecked()){
            babyAge+="4,";
        }
        if(id_tg_btn_age5.isChecked()){
            babyAge+="5,";
        }
        mSitter.baby_age = babyAge;

        Log.d(TAG, "onClick: mSitter : "+mSitter.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.SITTER, mSitter);
    }
}
