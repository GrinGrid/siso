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
 * 구직정보입력 > 자기소개 > 학력, 자격증
 * A simple {@link Fragment} subclass.
 */
public class Sitter10EduLicFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private SisoEditText id_et_university_name;
    private SisoEditText id_et_university_dipartment;
    private SisoEditText id_et_license;

    private ToggleButton id_tg_edu_0;
    private ToggleButton id_tg_edu_1;
    private ToggleButton id_tg_edu_2;
    private ToggleButton id_tg_edu_3;
    private ToggleButton id_tg_edu_4;
    private ToggleButton id_tg_edu_5;

    int mRadioEdu[] = new int[]{
            R.id.id_tg_edu_0,
            R.id.id_tg_edu_1,
            R.id.id_tg_edu_2,
            R.id.id_tg_edu_3,
            R.id.id_tg_edu_4,
            R.id.id_tg_edu_5
    };

    public Sitter10EduLicFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter10, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_university_name = (SisoEditText) getView().findViewById(R.id.id_et_university_name);
        id_et_university_dipartment = (SisoEditText) getView().findViewById(R.id.id_et_university_dipartment);
        id_et_license = (SisoEditText) getView().findViewById(R.id.id_et_license);

        id_tg_edu_0 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_0);
        id_tg_edu_1 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_1);
        id_tg_edu_2 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_2);
        id_tg_edu_3 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_3);
        id_tg_edu_4 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_4);
        id_tg_edu_5 = (ToggleButton)getView().findViewById(R.id.id_tg_edu_5);
        id_tg_edu_0.setOnClickListener(this);
        id_tg_edu_1.setOnClickListener(this);
        id_tg_edu_2.setOnClickListener(this);
        id_tg_edu_3.setOnClickListener(this);
        id_tg_edu_4.setOnClickListener(this);
        id_tg_edu_5.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            // 학력 Radio 선택
            case R.id.id_tg_edu_0:
            case R.id.id_tg_edu_1:
            case R.id.id_tg_edu_2:
            case R.id.id_tg_edu_3:
                selectRadio(mRadioEdu, viewId);
                id_et_university_name.setVisibility(View.GONE);
                id_et_university_dipartment.setVisibility(View.GONE);
                break;
            case R.id.id_tg_edu_4:
            case R.id.id_tg_edu_5:
                selectRadio(mRadioEdu, viewId);
                id_et_university_name.setVisibility(View.VISIBLE);
                id_et_university_dipartment.setVisibility(View.VISIBLE);
                break;
            case R.id.id_tv_next_btn:
                // TODO 입력값 체크
                saveData();
                executeSave();
                Sitter01IndexFragment fragment = new Sitter01IndexFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_introduce_title);
                break;

        }
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

    private void saveData() {
        int eduRadio = getRadioValue(mRadioEdu);
        mUser.sitterInfo.school = String.valueOf(getRadioValue(mRadioEdu));
        if(eduRadio!=4 && eduRadio!=5){
            mUser.sitterInfo.school = String.valueOf(eduRadio);
        }else{
            mUser.sitterInfo.school = id_et_university_name.getText().toString();
            mUser.sitterInfo.department = id_et_university_dipartment.getText().toString();
        }

        mUser.sitterInfo.school = String.valueOf(getRadioValue(mRadioEdu));
        mUser.sitterInfo.department = id_et_university_dipartment.getText().toString();
        mUser.sitterInfo.license = id_et_license.getText().toString();

        Log.d(TAG, "fragment10 : mUser.sitterInfo : "+mUser.sitterInfo.toString());
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

}
