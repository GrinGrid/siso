package net.gringrid.siso;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.Contact;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ContactAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 연락처 요청
 */
public class PopupContactReject extends Popup implements View.OnClickListener {

    private static final String TAG = "jiho";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_contact_request, null);
        setPopupContent(view);
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_cancel, this);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_request, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_btn_first:
                break;
            case R.id.id_tv_btn_second:
                break;
        }
    }

    private void execute() {
        ServiceGenerator.getInstance(this);
        ContactAPI api = ServiceGenerator.getInstance(PopupContactReject.this).createService(ContactAPI.class);
        Contact contact = new Contact();
        Call<Contact> call = api.request(contact);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: login : "+response.body().toString());
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
