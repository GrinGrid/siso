package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Personal;
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
 * A simple {@link Fragment} subclass.
 */
public class Member6Fragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "jiho";
    private TextView id_tv_next_btn;

    public Member6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member6, container, false);
    }

    @Override
    public void onResume() {

        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        final String email = SharedData.getInstance(getContext()).getUserLoginData().email;
        getCountInCircleBoundary(email);
        getView().findViewById(R.id.id_tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCountInCircleBoundary(email);
            }
        });

        super.onResume();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case  R.id.id_tv_next_btn:
                Personal personal = SharedData.getInstance(getContext()).getUserLoginData();
                if ( personal == null ){

                }
                Fragment fragment;
                int titleId;
                if ( personal.userType == User.USER_TYPE_SITTER ){
                    fragment = new Sitter1Fragment();
                    titleId = R.string.sitter_title;

                }else{
                    fragment = new Parent1Fragment();
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
