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
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직정보입력 > 기본정보 > 아이돌봄 특기
 * A simple {@link Fragment} subclass.
 */
public class Sitter04SkillFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;

    private int[] mSkillAry = new int[]{
        R.id.id_tg_btn_care,
        R.id.id_tg_btn_baby,
        R.id.id_tg_btn_outdoor,
        R.id.id_tg_btn_house,
        R.id.id_tg_btn_homework,
        R.id.id_tg_btn_commute,
        R.id.id_tg_btn_cook,
        R.id.id_tg_btn_foreign_language,
        R.id.id_tg_btn_music_physical
    };

    private TextView id_tv_next_btn;

    public Sitter04SkillFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        Log.d(TAG, "onCreate: SITTER4 : "+mUser.toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter4, container, false);
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
                // TODO 입력항목 체크
                saveData();

                Sitter05CommuteFragment fragment = new Sitter05CommuteFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;
        }
    }

    private void saveData() {
        String skillStr = "";
        String[] valueAry = getContext().getResources().getStringArray(R.array.multi_skill);

        boolean isChecked;
        for(int i=0; i<mSkillAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mSkillAry[i])).isChecked();
            if(isChecked){
                skillStr += valueAry[i]+User.DELIMITER;
            }
        }
        mUser.sitterInfo.skill = skillStr;
        Log.d(TAG, "onClick: mSitter : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

}
