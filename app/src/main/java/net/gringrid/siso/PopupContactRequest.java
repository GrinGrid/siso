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
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ContactAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 연락처 요청/수락/전화하기
 */
public class PopupContactRequest extends Popup implements View.OnClickListener {

    private static final String TAG = "jiho";
    private String mId;
    private String mRcvEmail;
    private TextView id_tv_main_question;
    private User mReceiverUser;

    private TextView id_tv_name;
    private TextView id_tv_brief;
    private TextView id_tv_salary;
    private TextView id_tv_testimonial_cnt;
    private TextView id_tv_commute;
    private TextView id_tv_distance;
    private String mContactAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 16. 11. 17. 호출순서 확인
    }

    @Override
    protected void initializeData() {
        Log.d(TAG, "initializeData: PopupRequest");
        super.initializeData();
        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        String strObj = bundle.getString("USER");
        mReceiverUser = gson.fromJson(strObj, User.class);
        mContactAction = bundle.getString(Contact.CONTACT_ACTION);
        mId = bundle.getString(Contact.ID);
        mRcvEmail = bundle.getString(Contact.RCV_EMAIL);
        if(TextUtils.isEmpty(mRcvEmail)){
            Toast.makeText(this, R.string.err_contact_rcv_email, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    protected void initializeView() {
        Log.d(TAG, "initializeView: PopupRequest");
        super.initializeView();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_contact_request, null);
        setPopupContent(view);

        id_tv_name = (TextView)findViewById(R.id.id_tv_name);
        id_tv_brief = (TextView)findViewById(R.id.id_tv_brief);
        id_tv_commute = (TextView)findViewById(R.id.id_tv_commute);
        id_tv_distance = (TextView)findViewById(R.id.id_tv_distance);
        id_tv_salary = (TextView)findViewById(R.id.id_tv_salary);
        id_tv_main_question = (TextView)findViewById(R.id.id_tv_main_question);

        initializeCommonView();

        Bundle bundle = getIntent().getExtras();
        if(!TextUtils.isEmpty(mContactAction)){
            if(mContactAction.equals(Contact.CONTACT_STATUS_ACCEPT)){
                initializeAcceptView();
            }else if(mContactAction.equals(Contact.CONTACT_STATUS_REQUEST)){
                initializeRequestView();
            }
        }
    }

    private void initializeCommonView() {
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_cancel, this);
    }

    private void initializeRequestView() {
        id_tv_title.setText(R.string.contact_request_title);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_request, this);

        // TODO: 16. 12. 9. 시터/부모에 따라.
        if(mReceiverUser.personalInfo.user_type.equals(User.USER_TYPE_SITTER)){
            initializeSitterData();
        }else if(mUser.personalInfo.user_type.equals(User.USER_TYPE_PARENT)){
            initializeParentData();
        }
    }

    private void initializeAcceptView() {
        id_tv_name.setVisibility(View.GONE);
        id_tv_title.setText(R.string.contact_accept_title);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_accept, this);
        id_tv_main_question.setText(R.string.contact_accept_question);
    }

    private void initializeSitterData(){
        id_tv_name.setText(mReceiverUser.personalInfo.name);
        id_tv_brief.setText(mReceiverUser.sitterInfo.brief);
        id_tv_commute.setText(mReceiverUser.sitterInfo.commute_type);
        id_tv_distance.setText(mReceiverUser.sitterInfo.distance_limit);
        id_tv_salary.setText(mReceiverUser.sitterInfo.salary);
        id_tv_main_question.setText(mReceiverUser.personalInfo.name+" 시터 "+R.string.contact_request_question);
    }

    private void initializeParentData() {
        id_tv_name.setText(mReceiverUser.personalInfo.name);
        id_tv_brief.setText(mReceiverUser.sitterInfo.brief);
        id_tv_commute.setText(mReceiverUser.sitterInfo.commute_type);
        id_tv_distance.setText(mReceiverUser.sitterInfo.distance_limit);
        id_tv_salary.setText(SisoUtil.getListSalary(this, mReceiverUser.sitterInfo.salary));
        id_tv_main_question.setText(mReceiverUser.personalInfo.name+" "+R.string.contact_request_question);
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
        if(mContactAction.equals(Contact.CONTACT_ACTION_ACCEPT)){
            Toast.makeText(this, R.string.contact_accept_complete, Toast.LENGTH_SHORT).show();
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REQUEST)){
            Toast.makeText(this, R.string.contact_request_complete, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void execute() {
        ServiceGenerator.getInstance(this);
        ContactAPI api = ServiceGenerator.getInstance(PopupContactRequest.this).createService(ContactAPI.class);
        Contact contact = new Contact();
        contact.id = mId;
        contact.req_email = mUser.personalInfo.email;
        contact.rcv_email = mRcvEmail;
        contact.req_msg = "안녕하세요 천재 시터 최순실 입니다";

        Call<Contact> call = null;

        if(mContactAction.equals(Contact.CONTACT_ACTION_ACCEPT)){
            call = api.accept(contact);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_REQUEST)){
            call = api.request(contact);
        }else if(mContactAction.equals(Contact.CONTACT_ACTION_CALL)){
            // TODO 전화하기
        }

        if(call == null) return;

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
