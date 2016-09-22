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
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter3Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    Sitter mSitter;
    Gson mGson;

    private TextView id_tv_next_btn;
    int mRadioTerm[] = new int[]{R.id.id_tg_btn_now, R.id.id_tg_btn_day};
    private SisoToggleButton id_tg_btn_now;
    private SisoToggleButton id_tg_btn_day;
    private SisoPicker id_pk_experience;

    public Sitter3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String sitterStr = SharedData.getInstance(getContext()).getGlobalDataString(SharedData.SITTER);

        mGson = new Gson();
        if ( sitterStr != null ){
            mSitter = mGson.fromJson(sitterStr, Sitter.class);
        }else{
            mSitter = new Sitter();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter3, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_btn_now = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_now);
        id_tg_btn_day = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_day);
        id_tg_btn_now.setOnClickListener(this);
        id_tg_btn_day.setOnClickListener(this);
        id_pk_experience = (SisoPicker)getView().findViewById(R.id.id_pk_experience);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();

                // TODO 근무기간이 즉시 가능일때는 Sitter4
                // TODO 근무기간이 특정기간일때는 Sitter3-1
                int schedule = getRadioValue(mRadioTerm);
                // 즉시가능일경우
                if ( schedule == 0 ){
                    Sitter4Fragment fragment = new Sitter4Fragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                // 특정기간일경우
                }else if (schedule == 1 ){
                    Sitter3Sub1Fragment fragment = new Sitter3Sub1Fragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                }
                break;
            case R.id.id_tg_btn_now:
                selectRadio(R.id.id_tg_btn_now);
                break;
            case R.id.id_tg_btn_day:
                selectRadio(R.id.id_tg_btn_day);
                break;
        }
    }

    private void saveData() {
        int work_year = id_pk_experience.getCurrentIndex();
        mSitter.work_year = work_year;
        int schedule = getRadioValue(mRadioTerm);
        if ( schedule == 0 ){
            mSitter.mon = "1111111";
            mSitter.tue = "1111111";
            mSitter.wed = "1111111";
            mSitter.thu = "1111111";
            mSitter.fri = "1111111";
            mSitter.sat = "1111111";
            mSitter.sun = "1111111";
        }
        Log.d(TAG, "onClick: mSitter : "+mSitter.toString());
        SharedData.getInstance(getContext()).insertGlobalData(SharedData.SITTER, mGson.toJson(mSitter));
    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if (((SisoToggleButton) getView().findViewById(radioList[i])).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private void selectRadio(int selectItem) {
        for(int src:mRadioTerm){
            if(src == selectItem){
                ((SisoToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }
}
