package net.gringrid.siso;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.models.Contact;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ContactAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 연락처 요청/수락
 */
public class PopupContactRequest extends Popup implements View.OnClickListener {

    private static final String TAG = "jiho";
    private String mRcvEmail;
    private String mMode;
    private TextView id_tv_main_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 16. 11. 17. 호출순서 확인
        initializeData();
        initializeView();
    }

    @Override
    protected void initializeData() {
        super.initializeData();
        Bundle bundle = getIntent().getExtras();
        mRcvEmail = bundle.getString(Contact.RCV_EMAIL);
        if(TextUtils.isEmpty(mRcvEmail)){
            Toast.makeText(this, R.string.err_contact_rcv_email, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mMode = bundle.getString(Contact.CONTACT_STATUS);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_contact_request, null);
        setPopupContent(view);

        id_tv_main_question = (TextView)view.findViewById(R.id.id_tv_main_question);

        initializeCommonView();
        initializeRequestView();
        initializeAcceptView();
    }

    private void initializeCommonView() {
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_cancel, this);
    }
    private void initializeRequestView() {
        id_tv_title.setText(R.string.contact_request_title);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_request, this);
        id_tv_main_question.setText(R.string.contact_request_question);
    }
    private void initializeAcceptView() {
        id_tv_title.setText(R.string.contact_accept_title);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_accept, this);
        id_tv_main_question.setText(R.string.contact_accept_question);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_btn_first:
                finish();
                break;
            case R.id.id_tv_btn_second:
                execute();
                break;
        }
    }

    private void moveNext(){
        Toast.makeText(this, R.string.contact_request_complete, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void execute() {
        ServiceGenerator.getInstance(this);
        ContactAPI api = ServiceGenerator.getInstance(PopupContactRequest.this).createService(ContactAPI.class);
        Contact contact = new Contact();
        contact.req_email = mUser.personalInfo.email;
        contact.rcv_email = mRcvEmail;
        contact.req_msg = "안녕하세요 천재 시터 최순실 입니다";
        Call<Contact> call = api.request(contact);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: contact : "+response.body().toString());
                    moveNext();
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
