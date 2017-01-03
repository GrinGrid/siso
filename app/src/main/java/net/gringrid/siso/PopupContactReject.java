package net.gringrid.siso;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ContactAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 연락처 요청 취소/거절
 */
public class PopupContactReject extends Popup implements View.OnClickListener {

    private static final String TAG = "jiho";

    private TextView id_tv_main_question;
    private TextView id_tv_sub_question;
    private User mReceiverUser;
    private String mId;
    private String mRcvEmail;
    private String mContactAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_contact_reject, null);

        setPopupContent(view);

        id_tv_main_question = (TextView)findViewById(R.id.id_tv_main_question);
        id_tv_sub_question = (TextView)findViewById(R.id.id_tv_main_question);

        if(mContactAction.equals(Contact.CONTACT_ACTION_CANCEL)){
            initializeCancelView();
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REJECT)){
            initializeRejectView();
        }
    }

    @Override
    protected void initializeData() {
        super.initializeData();
        Gson gson = new Gson();
        Bundle bundle = getIntent().getExtras();
        String strObj = bundle.getString("USER");
        mContactAction = bundle.getString(Contact.CONTACT_ACTION);
        mReceiverUser = gson.fromJson(strObj, User.class);
        mRcvEmail = bundle.getString(Contact.RCV_EMAIL);
        mId = bundle.getString(Contact.ID);

        if(TextUtils.isEmpty(mRcvEmail)){
            Toast.makeText(this, R.string.err_contact_rcv_email, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    private void initializeRejectView() {
        id_tv_main_question.setText(R.string.contact_cancel_question);
        id_tv_sub_question.setText(R.string.contact_cancel_reason);
        id_tv_main_question.setText("");
        id_tv_sub_question.setText("");
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_cancel, this);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_reject, this);
    }

    private void initializeCancelView() {
        id_tv_main_question.setText(R.string.contact_cancel_question);
        id_tv_sub_question.setText(R.string.contact_cancel_reason);
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_no, this);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_yes, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_btn_first:
                break;
            case R.id.id_tv_btn_second:
                execute();
                break;
        }
    }


    protected void moveNext() {
        Toast.makeText(this, R.string.contact_request_complete, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void execute() {
        ServiceGenerator.getInstance(this);
        ContactAPI api = ServiceGenerator.getInstance(PopupContactReject.this).createService(ContactAPI.class);
        Contact contact = new Contact();
        contact.id = mId;
        if(mContactAction.equals(Contact.CONTACT_ACTION_CANCEL)){
            contact.req_email = mUser.personalInfo.email;
            contact.rcv_email = mRcvEmail;
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REJECT)){
            contact.req_email = mRcvEmail;
            contact.rcv_email = mUser.personalInfo.email;
        }
        contact.req_msg = "안녕하세요 천재 시터 최순실 입니다";

        Log.d(TAG, "execute: id : "+mId);


        Call<Contact> call = null;

        if(mContactAction.equals(Contact.CONTACT_ACTION_CANCEL)){
            call = api.cancel(contact);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REJECT)){
            call = api.reject(contact);
        }

        if(call == null) return;

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: reject : "+response.body().toString());
                    moveNext();
//                    Log.d(TAG, "onResponse session-key : "+response.headers().get(SharedData.SESSION_KEY));
//                    SharedData.getInstance(getContext()).setObjectData(SharedData.USER, response.body());
//                    SharedData.getInstance(getContext()).insertGlobalData(SharedData.SESSION_KEY, response.headers().get(SharedData.SESSION_KEY));
//                    SisoUtil.showMsg(getContext(), response.body().personalInfo.name+"님 정상적으로 로그인 되었습니다.");
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getApplicationContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
