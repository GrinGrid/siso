package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직정보입력 > 기본정보 > 아이돌봄 특기
 * A simple {@link Fragment} subclass.
 */
public class Sitter4Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    Sitter mSitter;
    Gson mGson;

    private TextView id_tv_next_btn;
    private SisoToggleButton id_tg_btn_care;
    private SisoToggleButton id_tg_btn_baby;
    private SisoToggleButton id_tg_btn_outdoor;
    private SisoToggleButton id_tg_btn_house;
    private SisoToggleButton id_tg_btn_homework;
    private SisoToggleButton id_tg_btn_commute;
    private SisoToggleButton id_tg_btn_cook;
    private SisoToggleButton id_tg_btn_foreign_language;
    private SisoToggleButton id_tg_btn_music_physical;

    public Sitter4Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSitter = SharedData.getInstance(getContext()).getSitterData();
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
        id_tg_btn_care= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_care));
        id_tg_btn_baby= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_baby));
        id_tg_btn_outdoor= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_outdoor));
        id_tg_btn_house= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_house));
        id_tg_btn_homework= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_homework));
        id_tg_btn_commute= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_commute));
        id_tg_btn_cook= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_cook));
        id_tg_btn_foreign_language= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_foreign_language));
        id_tg_btn_music_physical= ((SisoToggleButton)getView().findViewById(R.id.id_tg_btn_music_physical));
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();

                Sitter5Fragment fragment = new Sitter5Fragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;
        }
    }

    private void saveData() {
        String skillStr = "";
        boolean isCare = id_tg_btn_care.isChecked();
        boolean isBaby = id_tg_btn_baby.isChecked();
        boolean isOutdoor = id_tg_btn_outdoor.isChecked();
        boolean isHouse = id_tg_btn_house.isChecked();
        boolean isHomework = id_tg_btn_homework.isChecked();
        boolean isCommute = id_tg_btn_commute.isChecked();
        boolean isCook = id_tg_btn_cook.isChecked();
        boolean isForeignLanguage = id_tg_btn_foreign_language.isChecked();
        boolean isMusicPhysical = id_tg_btn_music_physical.isChecked();

        if ( isCare ) skillStr+=Sitter.SKILL_CARE+Sitter.SKILL_DELIMITER;
        if ( isBaby ) skillStr+=Sitter.SKILL_BABY+Sitter.SKILL_DELIMITER;
        if ( isOutdoor ) skillStr+=Sitter.SKILL_OUTDOOR+Sitter.SKILL_DELIMITER;
        if ( isHouse ) skillStr+=Sitter.SKILL_HOUSE_KEEPING+Sitter.SKILL_DELIMITER;
        if ( isHomework ) skillStr+=Sitter.SKILL_HOMEWORK+Sitter.SKILL_DELIMITER;
        if ( isCommute ) skillStr+=Sitter.SKILL_COMMUTE+Sitter.SKILL_DELIMITER;
        if ( isCook ) skillStr+=Sitter.SKILL_COOK+Sitter.SKILL_DELIMITER;
        if ( isForeignLanguage ) skillStr+=Sitter.SKILL_FOREIGN_LANGUAGE+Sitter.SKILL_DELIMITER;
        if ( isMusicPhysical ) skillStr+=Sitter.SKILL_MUSIC_PHYSICAL+Sitter.SKILL_DELIMITER;

        mSitter.skill = skillStr;
        Log.d(TAG, "onClick: mSitter : "+mSitter.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.SITTER, mSitter);
    }

}
