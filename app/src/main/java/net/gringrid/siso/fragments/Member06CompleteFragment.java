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
import net.gringrid.siso.models.Parent;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ParentAPI;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SitterAPI;
import net.gringrid.siso.util.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 회원가입 > 완료
 */
public class Member06CompleteFragment extends InputBaseFragment{

    private TextView id_tv_next_btn;
    private TextView id_tv_welcome;
    private TextView id_tv_guide;
    private String mUserType;


    public Member06CompleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member06_complete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_tv_welcome = (TextView)view.findViewById(R.id.id_tv_welcome);
        id_tv_guide = (TextView)view.findViewById(R.id.id_tv_guide);
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        setText("00");
        loadData();
    }

    private void setText(String boundaryNum) {
        String welcomeStr = "";
        String guideStr = "";
        String nextStr = "";
        if (mUserType.equals(User.USER_TYPE_SITTER)) {
            welcomeStr = "반값습니다 "+mUser.personalInfo.name+"님.\n"+
                    "시소의<시터 회원>이 되셨습니다!";
            guideStr = mUser.personalInfo.name+"님 가까운 곳에 "+boundaryNum+"명의 시터들이 있습니다.\n" +
                    "구인 등록을 통해 원하는 조건을 등록해주시면\n" +
                    "조건에 맞는 시터를 무료로 소개해드립니다";
            nextStr = "구직 정보 등록하기";
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            welcomeStr = "반값습니다 "+mUser.personalInfo.name+"님.\n"+
                    "시소의<부모 회원>이 되셨습니다!";
            guideStr = mUser.personalInfo.name+"님 가까운 곳에 "+boundaryNum+"개의 일자리가 있습니다.\n" +
                    "구직 등록을 통해 원하는 조건을 등록해주시면\n" +
                    "조건에 맞는 일자리를 무료로 소개해드립니다";
            nextStr = "구인 정보 등록하기";
        }
        id_tv_welcome.setText(welcomeStr);
        id_tv_guide.setText(guideStr);
        id_tv_next_btn.setText(nextStr);
    }

    @Override
    protected void loadData() {
        getCountInCircleBoundary();
    }

    @Override
    protected void saveData() {

    }

    @Override
    protected boolean isValidInput() {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_tv_next_btn:
                moveNext();
                break;
        }
    }

    @Override
    protected void moveNext() {
        Fragment fragment = null;
        if (mUserType.equals(User.USER_TYPE_SITTER)) {
            fragment = new Sitter00IndexFragment();
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            fragment = new Parent00IndexFragment();
        }

        ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.parent_title);
    }

    public void getCountInCircleBoundary(){
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            getParentCount();
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            getSitterCount();
        }
    }

    // 사용자 구분이 부모일 경우 주위에 있는 시터숫자 조회
    private void getSitterCount() {
        SitterAPI client = ServiceGenerator.getInstance(getActivity()).createService(SitterAPI.class);
        Call<SitterAPI.SitterCount> call = client.getCountInCircleBoundary(mUser.personalInfo.email);

        call.enqueue(new Callback<SitterAPI.SitterCount>() {
            @Override
            public void onResponse(Call<SitterAPI.SitterCount> call, Response<SitterAPI.SitterCount> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body().toString() );
                    Log.d(TAG, "onResponse: count : "+response.body().count );
                    setText(""+response.body().count);

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SitterAPI.SitterCount> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    // 사용자 구분이 시터일 경우 주위에 있는 부모숫자 조회
    private void getParentCount() {
        ParentAPI client = ServiceGenerator.getInstance(getActivity()).createService(ParentAPI.class);
        Call<ParentAPI.ParentCount> call = client.getCountInCircleBoundary(mUser.personalInfo.email);

        call.enqueue(new Callback<ParentAPI.ParentCount>() {
            @Override
            public void onResponse(Call<ParentAPI.ParentCount> call, Response<ParentAPI.ParentCount> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body().toString() );
                    Log.d(TAG, "onResponse: count : "+response.body().count );
                    setText(""+response.body().count);

                    // TODO 응답받기 전에 next button 눌렀을경우 죽는다
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParentAPI.ParentCount> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

}
