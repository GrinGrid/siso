package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

/**
 * 구인정보입력 > 시터를 구하는 이유
 */
public class Parent02ReasonFragment extends InputBaseFragment implements InputBaseFragment.OnSaveCompleteListener {

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
    private final int WRITE_INDEX = 7;

    public Parent02ReasonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent02_reason, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_reason_0 = (ToggleButton)view.findViewById(R.id.id_tg_reason_0);
        id_tg_reason_1 = (ToggleButton)view.findViewById(R.id.id_tg_reason_1);
        id_tg_reason_2 = (ToggleButton)view.findViewById(R.id.id_tg_reason_2);
        id_tg_reason_3 = (ToggleButton)view.findViewById(R.id.id_tg_reason_3);
        id_tg_reason_4 = (ToggleButton)view.findViewById(R.id.id_tg_reason_4);
        id_tg_reason_5 = (ToggleButton)view.findViewById(R.id.id_tg_reason_5);
        id_tg_reason_6 = (ToggleButton)view.findViewById(R.id.id_tg_reason_6);
        id_tg_reason_7 = (ToggleButton)view.findViewById(R.id.id_tg_reason_7);
        id_et_reason = (SisoEditText)view.findViewById(R.id.id_et_reason);
        id_tg_reason_0.setOnClickListener(this);
        id_tg_reason_1.setOnClickListener(this);
        id_tg_reason_2.setOnClickListener(this);
        id_tg_reason_3.setOnClickListener(this);
        id_tg_reason_4.setOnClickListener(this);
        id_tg_reason_5.setOnClickListener(this);
        id_tg_reason_6.setOnClickListener(this);
        id_tg_reason_7.setOnClickListener(this);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;
                saveData();
                moveNext();
                //executeSave(this);
                break;

            // 시터구인이유 Radio 선택
            case R.id.id_tg_reason_0:
            case R.id.id_tg_reason_1:
            case R.id.id_tg_reason_2:
            case R.id.id_tg_reason_3:
            case R.id.id_tg_reason_4:
            case R.id.id_tg_reason_5:
            case R.id.id_tg_reason_6:
                SisoUtil.selectRadio(mRadioReason, viewId, getView());
                id_et_reason.setVisibility(View.VISIBLE);
                break;
            case R.id.id_tg_reason_7:
                SisoUtil.selectRadio(mRadioReason, viewId, getView());
                id_et_reason.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    protected void loadData() {
        if(!TextUtils.isEmpty(mUser.parentInfo.reason)){
            String reason = mUser.parentInfo.reason;
            int natType;
            if(reason.equals("0") ||
                    reason.equals("1") ||
                    reason.equals("2") ||
                    reason.equals("3") ||
                    reason.equals("4") ||
                    reason.equals("5") ||
                    reason.equals("6")){
                natType = Integer.parseInt(mUser.parentInfo.reason);
                SisoUtil.selectRadio(mRadioReason, mRadioReason[natType], getView());
            }else{
                natType = WRITE_INDEX;
                SisoUtil.selectRadio(mRadioReason, mRadioReason[natType], getView());
                id_et_reason.setVisibility(View.VISIBLE);
                id_et_reason.setInput(mUser.parentInfo.reason);
            }
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioReason, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_religion_select);
            return false;
        }
        if(SisoUtil.getRadioValue(mRadioReason, getView()) == WRITE_INDEX){
            if(TextUtils.isEmpty(id_et_reason.getText())){
                SisoUtil.showErrorMsg(getContext(), R.string.invalid_reason_write);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveData() {
        int reasonRadio = SisoUtil.getRadioValue(mRadioReason, getView());
        if(reasonRadio!=WRITE_INDEX){
            mUser.parentInfo.reason = String.valueOf(reasonRadio);
        }else{
            mUser.parentInfo.reason = id_et_reason.getText().toString();
        }
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Log.d(TAG, "moveNext: ");
        Parent02JobFragment fragment = new Parent02JobFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage2);
    }

    @Override
    public void onSaveComplete() {
        moveNext();
    }
}
