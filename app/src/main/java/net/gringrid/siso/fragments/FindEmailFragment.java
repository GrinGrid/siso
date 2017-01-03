package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Personal;
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
 * A simple {@link Fragment} subclass.
 */
public class FindEmailFragment extends InputBaseFragment{

    private static final String TAG = "jiho";
    TextView id_tv_find_btn;
    SisoEditText id_et_name;
    SisoEditText id_et_phone;


    public FindEmailFragment() {
        // Required empty public constructor
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected boolean isValidInput() {

        if(TextUtils.isEmpty(id_et_name.getText().toString())){
            SisoUtil.showErrorMsg(getContext(),R.string.invalid_name_write);
            return false;
        }else if(TextUtils.isEmpty(id_et_phone.getText().toString())){
            SisoUtil.showErrorMsg(getContext(),R.string.invalid_phone_write);
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
        return inflater.inflate(R.layout.fragment_find_email, container, false);
    }

    @Override
    public void onResume() {
        id_et_name = (SisoEditText)getView().findViewById(R.id.id_et_name);
        id_et_phone = (SisoEditText)getView().findViewById(R.id.id_et_phone);
        id_tv_find_btn = (TextView)getView().findViewById(R.id.id_tv_find_btn);
        id_tv_find_btn.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_find_btn:
                String name = id_et_name.getText().toString();
                String phone = id_et_phone.getText().toString();
                if(isValidInput()){
                    findEmail(name, phone);
                }
                // 성명, 전화번호 입력여부 체크
                break;
        }

    }

    private void findEmail(String name, String phone) {

        final Personal personal = new Personal();
        personal.name = name;
        personal.phone = phone;

        personal.name = "최지호";
        personal.phone = "01012349090";

        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<Personal> call = client.findEmail(personal);

        call.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body().toString() );
                    Personal output = response.body();
                    Toast.makeText(getContext(), personal.name+"님의 이메일 주소는 "+output.email+" 입니다", Toast.LENGTH_SHORT).show();

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
