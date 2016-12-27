package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 희망 근무조건 > 국적, 종교
 */
public class Sitter03NatRlgFragment extends InputBaseFragment{

    private static final String TAG = "jiho";
    private static final int NAT_WRITE_INDEX = 2;
    private static final int RLG_WRITE_INDEX = 4;
    User mUser;
    private TextView id_tv_next_btn;
    private ToggleButton id_tg_nat_kor;
    private ToggleButton id_tg_nat_chi_kor;
    private ToggleButton id_tg_nat_write;
    private SisoEditText id_et_nat;
    private SisoEditText id_et_nat_visa;
    private ToggleButton id_tg_rlg_0;
    private ToggleButton id_tg_rlg_1;
    private ToggleButton id_tg_rlg_2;
    private ToggleButton id_tg_rlg_3;
    private ToggleButton id_tg_rlg_4;
    private SisoEditText id_et_rlg;

    int mRadioNat[] = new int[]{
            R.id.id_tg_nat_kor,
            R.id.id_tg_nat_chi_kor,
            R.id.id_tg_nat_write
    };

    int mRadioRlg[] = new int[]{
            R.id.id_tg_rlg_0,
            R.id.id_tg_rlg_1,
            R.id.id_tg_rlg_2,
            R.id.id_tg_rlg_3,
            R.id.id_tg_rlg_4
    };

    public Sitter03NatRlgFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter03_nat_rlg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_nat_kor = (ToggleButton)view.findViewById(R.id.id_tg_nat_kor);
        id_tg_nat_chi_kor = (ToggleButton)view.findViewById(R.id.id_tg_nat_chi_kor);
        id_tg_nat_write = (ToggleButton)view.findViewById(R.id.id_tg_nat_write);
        id_tg_nat_kor.setOnClickListener(this);
        id_tg_nat_chi_kor.setOnClickListener(this);
        id_tg_nat_write.setOnClickListener(this);

        id_tg_rlg_0 = (ToggleButton)view.findViewById(R.id.id_tg_rlg_0);
        id_tg_rlg_1 = (ToggleButton)view.findViewById(R.id.id_tg_rlg_1);
        id_tg_rlg_2 = (ToggleButton)view.findViewById(R.id.id_tg_rlg_2);
        id_tg_rlg_3 = (ToggleButton)view.findViewById(R.id.id_tg_rlg_3);
        id_tg_rlg_4 = (ToggleButton)view.findViewById(R.id.id_tg_rlg_4);
        id_tg_rlg_0.setOnClickListener(this);
        id_tg_rlg_1.setOnClickListener(this);
        id_tg_rlg_2.setOnClickListener(this);
        id_tg_rlg_3.setOnClickListener(this);
        id_tg_rlg_4.setOnClickListener(this);

        id_et_nat = (SisoEditText)view.findViewById(R.id.id_et_nat);
        id_et_nat_visa = (SisoEditText)view.findViewById(R.id.id_et_nat_visa);
        id_et_rlg = (SisoEditText)view.findViewById(R.id.id_et_rlg);
        loadData();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();

                moveNext();

                break;

            // 국적 Radio 선택
            case R.id.id_tg_nat_kor:
                SisoUtil.selectRadio(mRadioNat, viewId, getView());
                id_et_nat.setVisibility(View.GONE);
                id_et_nat_visa.setVisibility(View.GONE);
                break;
            case R.id.id_tg_nat_chi_kor:
                SisoUtil.selectRadio(mRadioNat, viewId, getView());
                id_et_nat.setVisibility(View.GONE);
                id_et_nat_visa.setVisibility(View.VISIBLE);
                break;
            case R.id.id_tg_nat_write:
                SisoUtil.selectRadio(mRadioNat, viewId, getView());
                id_et_nat.setVisibility(View.VISIBLE);
                id_et_nat_visa.setVisibility(View.VISIBLE);
                break;

            // 종교 Radio 선택
            case R.id.id_tg_rlg_0:
            case R.id.id_tg_rlg_1:
            case R.id.id_tg_rlg_2:
            case R.id.id_tg_rlg_3:
                SisoUtil.selectRadio(mRadioRlg, viewId, getView());
                id_et_rlg.setVisibility(View.GONE);
                break;
            case R.id.id_tg_rlg_4:
                SisoUtil.selectRadio(mRadioRlg, viewId, getView());
                id_et_rlg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void loadData() {

        // 국적
        SisoUtil.loadRadioDataWithWriteContent(mRadioNat, mUser.sitterInfo.nat, NAT_WRITE_INDEX, R.id.id_et_nat, getView());
        if(!TextUtils.isEmpty(mUser.sitterInfo.visa_exp)){
            id_et_nat_visa.setVisibility(View.VISIBLE);
            id_et_nat_visa.setInput(mUser.sitterInfo.visa_exp);
        }
        // 종교
        SisoUtil.loadRadioDataWithWriteContent(mRadioRlg, mUser.sitterInfo.religion, RLG_WRITE_INDEX, R.id.id_et_rlg, getView());
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadioNat, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_nationality_select);
            return false;
        }
        if(SisoUtil.getRadioValue(mRadioNat, getView()) == 2){
            if(TextUtils.isEmpty(id_et_nat.getText())){
                SisoUtil.showErrorMsg(getContext(), R.string.invalid_nationality_write);
                return false;
            }
        }
        if(SisoUtil.getRadioValue(mRadioNat, getView()) != 0){
            if(TextUtils.isEmpty(id_et_nat_visa.getText())){
                SisoUtil.showErrorMsg(getContext(), R.string.invalid_nationality_visa_write);
                return false;
            }
        }
        if(!SisoUtil.isRadioGroupSelected(mRadioRlg, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_religion_select);
            return false;
        }
        if(SisoUtil.getRadioValue(mRadioRlg, getView()) == 4){
            if(TextUtils.isEmpty(id_et_rlg.getText())){
                SisoUtil.showErrorMsg(getContext(), R.string.invalid_religion_write);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveData() {
        int natRadio = SisoUtil.getRadioValue(mRadioNat, getView());
        int rlgRadio = SisoUtil.getRadioValue(mRadioRlg, getView());
        switch (natRadio){
            case 0 :
                mUser.sitterInfo.nat = String.valueOf(natRadio);
                mUser.sitterInfo.visa_exp = "";
                break;
            case 1 :
                mUser.sitterInfo.nat = String.valueOf(natRadio);
                mUser.sitterInfo.visa_exp = id_et_nat_visa.getText().toString();
                break;
            case 2 :
                mUser.sitterInfo.nat = id_et_nat.getText().toString();
                mUser.sitterInfo.visa_exp = id_et_nat_visa.getText().toString();
                break;
        }

        if(rlgRadio!=4){
            mUser.sitterInfo.religion = String.valueOf(rlgRadio);
        }else{
            mUser.sitterInfo.religion= id_et_rlg.getText().toString();
        }
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Sitter03EduLicFragment fragment = new Sitter03EduLicFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_title);

    }
}
