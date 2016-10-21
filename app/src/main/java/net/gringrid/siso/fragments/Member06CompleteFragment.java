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
import net.gringrid.siso.network.restapi.SitterAPI;
import net.gringrid.siso.util.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 회원가입 > 완료
 */
public class Member06CompleteFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "jiho";
    private TextView id_tv_next_btn;
    private User mUser;

    public Member06CompleteFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_member6, container, false);
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        final String email = mUser.personalInfo.email;
        getCountInCircleBoundary(email);
        view.findViewById(R.id.id_tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCountInCircleBoundary(email);
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case  R.id.id_tv_next_btn:
                Log.d(TAG, "onClick: nextbtn");
                // TODO 변경
                User user = SharedData.getInstance(getContext()).getUserData();

                if ( user.personalInfo == null ){

                }
                Fragment fragment = null;
                int titleId = 0;
                if (user.personalInfo.user_type.equals(User.USER_TYPE_SITTER)) {
                    fragment = new Sitter01IndexFragment();
                    titleId = R.string.sitter_title;

                }else if(user.personalInfo.user_type.equals(User.USER_TYPE_PARENT)){
                    fragment = new Parent01IndexFragment();
                    titleId = R.string.parent_title;
                }

                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, titleId);
                break;
        }
    }
    // 특정 범위 안에 있는 부모 수
    public void getCountInCircleBoundary(String email){
        SitterAPI client = ServiceGenerator.getInstance(getActivity()).createService(SitterAPI.class);
        Call<SitterAPI.Sitter> call = client.getCountInCircleBoundary(email);

        call.enqueue(new Callback<SitterAPI.Sitter>() {
            @Override
            public void onResponse(Call<SitterAPI.Sitter> call, Response<SitterAPI.Sitter> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body().toString() );
                    Log.d(TAG, "onResponse: count : "+response.body().count );
                    TextView id_tv_test = (TextView)getView().findViewById(R.id.id_tv_test);
                    id_tv_test.setText("축하합니다 주위에 "+response.body().count+"명 있어유 ~ ");

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SitterAPI.Sitter> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

}
