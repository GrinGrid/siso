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

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 구직정보입력 > 기본정보 > 출퇴근방법 입주형
 * A simple {@link Fragment} subclass.
 */
public class Sitter05ScheduleSub2Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;

    private TextView id_tv_next_btn;
    private SisoPicker id_pk_salary_month;
    private int[] mRadioWeek = new int[]{R.id.id_tg_btn_week, R.id.id_tg_btn_weekend};
    private SisoToggleButton id_tg_btn_weekend;
    private SisoToggleButton id_tg_btn_week;

    public Sitter05ScheduleSub2Fragment() {
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
        return inflater.inflate(R.layout.fragment_sitter5_sub2, container, false);
    }

    @Override
    public void onResume() {
        id_tg_btn_week = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_week);
        id_tg_btn_weekend = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_weekend);
        id_pk_salary_month = (SisoPicker)getView().findViewById(R.id.id_pk_salary_month);
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_week.setOnClickListener(this);
        id_tg_btn_weekend.setOnClickListener(this);
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();
                executeSave();
                Sitter01IndexFragment fragment = new Sitter01IndexFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_basic_title);
                break;
            case R.id.id_tg_btn_week:
                selectRadio(R.id.id_tg_btn_week);
                break;

            case R.id.id_tg_btn_weekend:
                selectRadio(R.id.id_tg_btn_weekend);
                break;
        }
    }

    private void saveData() {
        int weekWorkValue = getRadioValue(mRadioWeek);
        mUser.sitterInfo.mon = "1111111";
        mUser.sitterInfo.tue = "1111111";
        mUser.sitterInfo.wed = "1111111";
        mUser.sitterInfo.thu = "1111111";
        mUser.sitterInfo.fri = "1111111";
        mUser.sitterInfo.sat = "0000000";
        mUser.sitterInfo.sun = "0000000";

        if(weekWorkValue==1){
            mUser.sitterInfo.sat = "1111111";
            mUser.sitterInfo.sun = "1111111";
        }
        String[] arrSalaryValues = getResources().getStringArray(R.array.picker_salary_month_value);
        String selectedValue = arrSalaryValues[id_pk_salary_month.getCurrentIndex()];
        mUser.sitterInfo.salary= selectedValue;

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
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
        for(int src:mRadioWeek){
            if(src == selectItem){
                ((SisoToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
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
                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
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
}
