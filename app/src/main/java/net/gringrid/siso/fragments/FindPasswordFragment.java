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
import android.widget.Toast;

import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 비밀번호를 찾기 위해 이메일을 입력하고 해당 이메일로 비밀번호 재설정을 위한 이메일을 보낸다
 */
public class FindPasswordFragment extends InputBaseFragment{

    private static final String TAG = "jiho";
    private TextView id_tv_find_btn;
    private SisoEditText id_et_email;

    public FindPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected boolean isValidInput() {
        if(TextUtils.isEmpty(id_et_email.getText().toString())){
            SisoUtil.showErrorMsg(getContext(),R.string.invalid_email_write);
            return false;
        } else if(!SisoUtil.isEmail(id_et_email.getText().toString())) {
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_email);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {

    }

    @Override
    protected void moveNext() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_et_email = (SisoEditText)view.findViewById(R.id.id_et_email);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        id_tv_find_btn = (TextView)getView().findViewById(R.id.id_tv_find_btn);
        id_tv_find_btn.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_find_btn:
                if(isValidInput()){
                    sendFindMail();
                }
                break;
        }
    }

    private void sendFindMail() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<ResponseBody> call = client.findPassword(id_et_email.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body());
                        Log.d(TAG, "onResponse: success body : "+response.message());
                        SisoUtil.showMsg(getContext(), R.string.login_find_password_success);
                    }

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
