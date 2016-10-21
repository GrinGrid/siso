package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 희망 근무조건 > 국적, 종교
 * A simple {@link Fragment} subclass.
 */
public class Sitter08NatRlgFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private ToggleButton id_tg_nat_kor;
    private ToggleButton id_tg_nat_chi_kor;
    private ToggleButton id_tg_nat_write;
    private ToggleButton id_tg_rlg_0;
    private ToggleButton id_tg_rlg_1;
    private ToggleButton id_tg_rlg_2;
    private ToggleButton id_tg_rlg_3;
    private ToggleButton id_tg_rlg_4;
    private SisoEditText id_et_nat;
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

    public Sitter08NatRlgFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter8, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_nat_kor = (ToggleButton)getView().findViewById(R.id.id_tg_nat_kor);
        id_tg_nat_chi_kor = (ToggleButton)getView().findViewById(R.id.id_tg_nat_chi_kor);
        id_tg_nat_write = (ToggleButton)getView().findViewById(R.id.id_tg_nat_write);
        id_tg_nat_kor.setOnClickListener(this);
        id_tg_nat_chi_kor.setOnClickListener(this);
        id_tg_nat_write.setOnClickListener(this);

        id_tg_rlg_0 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_0);
        id_tg_rlg_1 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_1);
        id_tg_rlg_2 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_2);
        id_tg_rlg_3 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_3);
        id_tg_rlg_4 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_4);
        id_tg_rlg_0.setOnClickListener(this);
        id_tg_rlg_1.setOnClickListener(this);
        id_tg_rlg_2.setOnClickListener(this);
        id_tg_rlg_3.setOnClickListener(this);
        id_tg_rlg_4.setOnClickListener(this);

        id_et_nat = (SisoEditText) getView().findViewById(R.id.id_et_nat);
        id_et_rlg = (SisoEditText) getView().findViewById(R.id.id_et_rlg);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:
                // TODO 입력값 체크
                saveData();
                executeSave();
                Sitter01IndexFragment fragment = new Sitter01IndexFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_title);
                break;

            // 국적 Radio 선택
            case R.id.id_tg_nat_kor:
            case R.id.id_tg_nat_chi_kor:
                selectRadio(mRadioNat, viewId);
                id_et_nat.setVisibility(View.GONE);
                break;
            case R.id.id_tg_nat_write:
                selectRadio(mRadioNat, R.id.id_tg_nat_write);
                id_et_nat.setVisibility(View.VISIBLE);
                break;

            // 종교 Radio 선택
            case R.id.id_tg_rlg_0:
            case R.id.id_tg_rlg_1:
            case R.id.id_tg_rlg_2:
            case R.id.id_tg_rlg_3:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.GONE);
                break;
            case R.id.id_tg_rlg_4:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void saveData() {
        int natRadio = getRadioValue(mRadioNat);
        int rlgRadio = getRadioValue(mRadioRlg);
        if(natRadio!=2){
            mUser.sitterInfo.nat = String.valueOf(natRadio);
        }else{
            mUser.sitterInfo.nat = id_et_nat.getText().toString();
        }
        if(natRadio!=4){
            mUser.sitterInfo.religion = String.valueOf(rlgRadio);
        }else{
            mUser.sitterInfo.religion= id_et_rlg.getText().toString();
        }
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void executeSave() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        User user = SharedData.getInstance(getContext()).getUserData();
        user.sitterInfo = mUser.sitterInfo;
        Call<User> call = client.modify(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
//                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.d(TAG, "onResponse: "+gson.toJson(response.body()));
                        Log.d(TAG, "original Sitter : "+gson.toJson(mUser.sitterInfo));

                    }

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void selectRadio(int[] radioList, int selectItem) {
        for(int src:radioList){
            if(src == selectItem){
                ((ToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((ToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if( ((ToggleButton)getView().findViewById(radioList[i])).isChecked() ){
                return i;
            }
        }
        return 0;
    }
}
