package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;


/**
 * 구직정보 등록 > 기본정보 > 성별, 자녀정보
 */
public class Sitter03GenderChildrenFragment extends InputBaseFragment{


    SisoToggleButton id_tg_btn_woman;
    SisoToggleButton id_tg_btn_man;

    int mRadioGender[] = new int[]{R.id.id_tg_btn_woman, R.id.id_tg_btn_man};
    private TextView id_tv_next_btn;
    private SisoPicker id_pk_daughter;
    private SisoPicker id_pk_son;

    public Sitter03GenderChildrenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter03_gender_children, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_btn_woman = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_woman);
        id_tg_btn_man = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_man);
        id_tg_btn_man.setOnClickListener(this);
        id_tg_btn_woman.setOnClickListener(this);

        id_pk_daughter = (SisoPicker)view.findViewById(R.id.id_pk_daughter);
        id_pk_son = (SisoPicker)view.findViewById(R.id.id_pk_son);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: sitter2");
        switch (v.getId()){
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();
                moveNext();

                break;
            case R.id.id_tg_btn_woman:
                SisoUtil.selectRadio(mRadioGender, R.id.id_tg_btn_woman, getView());
                break;
            case R.id.id_tg_btn_man:
                SisoUtil.selectRadio(mRadioGender, R.id.id_tg_btn_man, getView());
                break;
        }
    }


    @Override
    protected void loadData() {
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

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioGender, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_gender_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        int gender = SisoUtil.getRadioValue(mRadioGender, getView());
        int daughterNum = id_pk_daughter.getCurrentIndex();
        int sonNum = id_pk_son.getCurrentIndex();

        mUser.sitterInfo.gender = String.valueOf(gender);
        mUser.sitterInfo.daughters = String.valueOf(daughterNum);
        mUser.sitterInfo.sons = String.valueOf(sonNum);
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());

        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

    }

    @Override
    protected void moveNext() {
        CommonSkillFragment fragment = new CommonSkillFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
    }
}
