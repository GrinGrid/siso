package net.gringrid.siso.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SmsAPI;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Member04PhoneFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SmsAPI mSmsAPI;

    SisoEditText id_et_phone;
    SisoEditText id_et_auth_num;
    TextView id_tv_next_btn;
    TextView id_tv_request_auth_sms;
    TextView id_tv_confirm_auth_num;

    SmsAPI.SMS sms = new SmsAPI.SMS();
    private User mUser;

    public Member04PhoneFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_member4, container, false);
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tv_request_auth_sms = (TextView) view.findViewById(R.id.id_tv_request_auth_sms);
        id_tv_request_auth_sms.setOnClickListener(this);

        id_tv_confirm_auth_num = (TextView) view.findViewById(R.id.id_tv_confirm_auth_num);;
        id_tv_confirm_auth_num.setOnClickListener(this);

        id_et_phone = (SisoEditText) view.findViewById(R.id.id_et_phone);
        id_et_auth_num = (SisoEditText)view.findViewById(R.id.id_et_auth_num);
        setScrollControl(view);
        return view;
    }

    /**
     * 소프트키보드에 따라 스크롤 컨트롤
     */
    private void setScrollControl(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                final ScrollView id_sv = (ScrollView)view.findViewById(R.id.id_sv);
                // softkey visible
                if ( heightDiff > 500 ){
                    id_sv.scrollTo(0, 500);
                }else{
                    id_sv.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO 1. 다음버튼 비활성화
        // TODO 2. 인증번호 요청시 인증번호 요청 버튼 비활성화
        // TODO 3. 인증번호


        switch (v.getId()){
            case R.id.id_tv_confirm_auth_num:
                // TODO 인증번호 입력여부 확인
                String authNum = id_et_auth_num.getText().toString();
                confirmAuthNum(authNum);
                break;

            case  R.id.id_tv_next_btn:
                if ( SharedData.DEBUG_MODE ) {
                    id_et_phone.setInput("01012349090");
                }
                mUser.personalInfo.phone = id_et_phone.getText().toString();

                SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

                if(mUser.personalInfo.user_type.equals(User.USER_TYPE_SITTER)) {
                    Member05AddrFragment member05AddrFragment = new Member05AddrFragment();
                    ((BaseActivity) getActivity()).setFragment(member05AddrFragment, R.string.member_title);

                }else if(mUser.personalInfo.user_type.equals(User.USER_TYPE_PARENT)){
                    Member04JobFragment member04JobFragment = new Member04JobFragment();
                    ((BaseActivity) getActivity()).setFragment(member04JobFragment, R.string.member_title);

                }
                break;

            case  R.id.id_tv_request_auth_sms:
                // TODO 휴대폰번호 입력여부 확인
                String phone = id_et_phone.getText().toString();
                requestAuthSms(phone);
                break;

        }
    }
    private void requestAuthSms(String phone) {
        Log.d(TAG, "requestAuthSms: phone : "+phone);
        sms.phone = phone;
        sms.phone = "01012349090";

        if(mSmsAPI == null){
            mSmsAPI = ServiceGenerator.getInstance(getActivity()).createService(SmsAPI.class);
        }
        Call<SmsAPI.SMS> call = mSmsAPI.requestAuthSms(sms);
        call.enqueue(new Callback<SmsAPI.SMS>() {

            @Override
            public void onResponse(Call<SmsAPI.SMS> call, Response<SmsAPI.SMS> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body().hashKey );
                    sms = response.body();

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SmsAPI.SMS> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    private void confirmAuthNum(String authNum) {
        sms.authNum = authNum;
        if(mSmsAPI == null){
            mSmsAPI = ServiceGenerator.getInstance(getActivity()).createService(SmsAPI.class);
        }
        Call<SmsAPI.SMS> call = mSmsAPI.confirmAuthNum(sms);
        call.enqueue(new Callback<SmsAPI.SMS>() {

            @Override
            public void onResponse(Call<SmsAPI.SMS> call, Response<SmsAPI.SMS> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body());
                }else{
                    Log.d(TAG, "onResponse: EROOR");
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SmsAPI.SMS> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

}