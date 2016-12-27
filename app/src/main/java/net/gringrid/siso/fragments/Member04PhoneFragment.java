package net.gringrid.siso.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.text.TextUtils;
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
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

import org.simpleframework.xml.util.Match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 회원가입 > 전화번호
 */
public class Member04PhoneFragment extends InputBaseFragment{

    SmsAPI mSmsAPI;

    SisoEditText id_et_phone;
    SisoEditText id_et_auth_num;
    TextView id_tv_next_btn;
    TextView id_tv_request_auth_sms;

    SmsAPI.SMS sms = new SmsAPI.SMS();

    private final String SMS_RECEIVE_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private BroadcastReceiver mSmsReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: SMS");
            Bundle bundle = intent.getExtras();
            if ( bundle == null )
                return;

            Object[] pdusObj = (Object[]) bundle.get("pdus");
            if ( pdusObj == null )	return;

            //message 처리
            for (int i = 0; i < pdusObj.length; i++) {
                SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                String stringMsg = smsMsg.getMessageBody();
                stringMsg = stringMsg.replace("\n", "");

                // 메시지가 유안타증권 본인확인 메시지 인지 확인한다.
                if ( isAuthSMS(stringMsg) ) {
                    // 인증번호란에 세팅한다.
                    setSmsAuthNum( getAuthNum(stringMsg) );
                }else{
                    Log.d(TAG, "onReceive: is Not SMS");
                }
            }
        }
    };


    public Member04PhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(SMS_RECEIVE_ACTION);
        getContext().registerReceiver(mSmsReceiver, filter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member04_phone, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tv_request_auth_sms = (TextView) view.findViewById(R.id.id_tv_request_auth_sms);
        id_tv_request_auth_sms.setOnClickListener(this);

        id_et_phone = (SisoEditText) view.findViewById(R.id.id_et_phone);
        id_et_auth_num = (SisoEditText)view.findViewById(R.id.id_et_auth_num);
        setScrollControl(view);
        loadData();
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

            case  R.id.id_tv_next_btn:
                if (SharedData.DEBUG_MODE) {
                    saveData();
                }
                // TODO 인증번호 확인
                if(!isValidInput()) return;
                confirmAuthNum();

                break;

            case  R.id.id_tv_request_auth_sms:
                // TODO 휴대폰번호 입력여부 확인
                if(!isValidPhoneInput()) return;
                String phone = id_et_phone.getText().toString();
                requestAuthSms(phone);
                break;

        }
    }

    @Override
    protected void loadData() {

        /*
        if ( SharedData.DEBUG_MODE ) {
            id_et_phone.setInput("01045097914");
        }
        */



        if(!TextUtils.isEmpty(mUser.personalInfo.phone)){
            id_et_phone.setInput(mUser.personalInfo.phone);
        }
    }

    private boolean isValidPhoneInput(){
        if (TextUtils.isEmpty(id_et_phone.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_phone_write);
            return false;
        }
        return true;
    }


    @Override
    protected boolean isValidInput() {
        if(!isValidPhoneInput()) return false;

        if (TextUtils.isEmpty(id_et_auth_num.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_auth_num_write);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        mUser.personalInfo.phone = id_et_phone.getText().toString();
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
        moveNext();
    }

    @Override
    protected void moveNext() {
        Member05AddrFragment member05AddrFragment = new Member05AddrFragment();
        ((BaseActivity) getActivity()).setFragment(member05AddrFragment, BaseActivity.TITLE_KEEP);
    }

    private void requestAuthSms(String phone) {
        Log.d(TAG, "requestAuthSms: phone : "+phone);
        sms.phone = phone;

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

    private void confirmAuthNum() {
        sms.authNum = id_et_auth_num.getText().toString();
        // TODO 6자리 숫자 확인
        if(mSmsAPI == null){
            mSmsAPI = ServiceGenerator.getInstance(getActivity()).createService(SmsAPI.class);
        }
        Call<SmsAPI.SMS> call = mSmsAPI.confirmAuthNum(sms);
        call.enqueue(new Callback<SmsAPI.SMS>() {

            @Override
            public void onResponse(Call<SmsAPI.SMS> call, Response<SmsAPI.SMS> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: success body : "+response.body());
                    saveData();
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

    private boolean isAuthSMS(String stringMsg) {
        Log.d(TAG, "isAuthSMS: msg : "+stringMsg);
        Pattern pattern = Pattern.compile("^.*시소.*인증번호.*[\\d].*");
        Matcher matcher = pattern.matcher(stringMsg);
        return (matcher.matches());
    }

    private void setSmsAuthNum(String stringMsg) {
        id_et_auth_num.setInput(stringMsg);
    }

    /**
     * SMS 문자로부터 인증번호를 추출한다.
     * @param msg
     * @return
     */
    private String getAuthNum(String msg){
        String result = null;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(msg);
        if ( matcher.find() ){
            result = matcher.group(0);
        }
        return result;
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mSmsReceiver);
        super.onDestroy();
    }
}
