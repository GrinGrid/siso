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
import net.gringrid.siso.models.SitterList;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ParentAPI;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SitterAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구인정보 등록 완료
 */
public class ParentCompleteFragment extends InputBaseFragment{

    private TextView id_tv_next_btn;
    private TextView id_tv_welcome;
    private TextView id_tv_guide;
    private String mUserType;
    private TextView id_tv_btn_sitter_list;
    private TextView id_tv_btn_guide;


    public ParentCompleteFragment() {
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
        return inflater.inflate(R.layout.fragment_parent_complete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_tv_welcome = (TextView)view.findViewById(R.id.id_tv_welcome);
        id_tv_guide = (TextView)view.findViewById(R.id.id_tv_guide);
        id_tv_btn_sitter_list = (TextView)view.findViewById(R.id.id_tv_btn_sitter_list);
        id_tv_btn_guide = (TextView)view.findViewById(R.id.id_tv_btn_guide);
        id_tv_btn_sitter_list.setOnClickListener(this);
        id_tv_btn_guide.setOnClickListener(this);
        loadData();
    }

    private void setText(String boundaryNum) {
        String welcomeStr = "";
        String guideStr = "";
        String nextStr = "";
        if (mUserType.equals(User.USER_TYPE_SITTER)) {
            welcomeStr = mUser.personalInfo.name+"님.\n"+
                    "시소에 부모정보 입력이 완료되었습니다";
            guideStr = "자 이제  우리 아이를\n"+
                    "돌봐줄 시터를 찾을 준비가 되었습니다."+
                    "프로필 내용을 확인하고"+
                    "마음에 드는 시터가 있다면 연락처 공개를"+
                    "요청해보세요";
        }
        id_tv_welcome.setText(welcomeStr);
        id_tv_guide.setText(guideStr);
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

        Fragment fragment;
        switch (v.getId()){
            case R.id.id_tv_btn_sitter_list:
                fragment = new SitterListFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, BaseActivity.TITLE_NONE);
                break;

            case R.id.id_tv_btn_guide:
                fragment = new SitterListFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, BaseActivity.TITLE_NONE);
                break;
        }
    }

    @Override
    protected void moveNext() {
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
