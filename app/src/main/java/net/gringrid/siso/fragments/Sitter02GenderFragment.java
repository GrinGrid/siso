package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;


/**
 * 구직정보 등록 > 기본정보 > 성별, 자녀정보
 * A simple {@link Fragment} subclass.
 */
public class Sitter02GenderFragment extends Fragment implements View.OnClickListener {

    User mUser;

    SisoToggleButton id_tg_btn_woman;
    SisoToggleButton id_tg_btn_man;

    int mRadioGender[] = new int[]{R.id.id_tg_btn_woman, R.id.id_tg_btn_man};
    private String TAG = "jiho";
    private TextView id_tv_next_btn;
    private SisoPicker id_pk_daughter;
    private SisoPicker id_pk_son;

    public Sitter02GenderFragment() {
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
        loadData();
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: sitter2");
        switch (v.getId()){
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();

                Sitter03WorkYearFragment fragment = new Sitter03WorkYearFragment();
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

    private void saveData() {
        int gender = getRadioValue(mRadioGender);
        int daughterNum = id_pk_daughter.getCurrentIndex();
        int sonNum = id_pk_son.getCurrentIndex();

        mUser.sitterInfo.gender = String.valueOf(gender);
        mUser.sitterInfo.daughters = String.valueOf(daughterNum);
        mUser.sitterInfo.sons = String.valueOf(sonNum);
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());

        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void loadData() {
        if(mUser.sitterInfo==null) return;
        if(!TextUtils.isEmpty(mUser.sitterInfo.gender)){
            if(mUser.sitterInfo.gender.equals(User.GENDER_WOMAN)){
                id_tg_btn_woman.setChecked(true);
            }else if(mUser.sitterInfo.gender.equals(User.GENDER_MAN)){
                id_tg_btn_man.setChecked(true);
            }
        }

        if(!TextUtils.isEmpty(mUser.sitterInfo.daughters)){
            id_pk_daughter.setIndex(Integer.parseInt(mUser.sitterInfo.daughters));
        }

        if(!TextUtils.isEmpty(mUser.sitterInfo.sons)) {
            id_pk_son.setIndex(Integer.parseInt(mUser.sitterInfo.sons));
        }
    }
}
