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
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter2Fragment extends Fragment implements View.OnClickListener {

    Sitter mSitter;
    Gson mGson;

    SisoToggleButton id_tg_btn_woman;
    SisoToggleButton id_tg_btn_man;

    int mRadioGender[] = new int[]{R.id.id_tg_btn_woman, R.id.id_tg_btn_man};
    private String TAG = "jiho";
    private TextView id_tv_next_btn;
    private SisoPicker id_pk_daughter;
    private SisoPicker id_pk_son;

    public Sitter2Fragment() {
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
        return inflater.inflate(R.layout.fragment_sitter2, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_btn_woman = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_woman);
        id_tg_btn_man = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_man);
        id_tg_btn_man.setOnClickListener(this);
        id_tg_btn_woman.setOnClickListener(this);

        id_pk_daughter = (SisoPicker)getView().findViewById(R.id.id_pk_daughter);
        id_pk_son = (SisoPicker) getView().findViewById(R.id.id_pk_son);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: sitter2");
        switch (v.getId()){
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                int gender = getRadioValue(mRadioGender);
                int daughterNum = id_pk_daughter.getCurrentIndex();
                int sonNum = id_pk_son.getCurrentIndex();

                mSitter.gender = gender;
                mSitter.daughters = daughterNum;
                mSitter.sons = sonNum;
                Log.d(TAG, "onClick: mSitter : "+mSitter.toString());

                SharedData.getInstance(getContext()).insertGlobalData(SharedData.SITTER, mGson.toJson(mSitter));
                Sitter3Fragment fragment = new Sitter3Fragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;
            case R.id.id_tg_btn_woman:
                selectRadio(R.id.id_tg_btn_woman);
                break;
            case R.id.id_tg_btn_man:
                selectRadio(R.id.id_tg_btn_man);
                break;
        }
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
        for(int src:mRadioGender){
            if(src == selectItem){
                ((SisoToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }
}
