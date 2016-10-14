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

import com.google.gson.Gson;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.RootActivity;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.network.restapi.TransactionManager;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Member3Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";

    TextView id_tv_next_btn;
    Personal mPersonal;
    Gson mGson;
    SisoClient client;

    SisoEditText id_et_email;
    SisoEditText id_et_passwd;

    public Member3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        String memberStr = SharedData.getInstance(getContext()).getGlobalDataString(SharedData.PERSONAL);

        mGson = new Gson();
        if ( memberStr != null ){
            mPersonal = mGson.fromJson(memberStr, Personal.class);
        }else{
            mPersonal = new Personal();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_member3, container, false);
        setScrollControl(view);
        return view;
    }


    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_email = (SisoEditText) getView().findViewById(R.id.id_et_email);
        id_et_passwd = (SisoEditText) getView().findViewById(R.id.id_et_passwd);

        super.onResume();
    }

    /**
     * 소프트키보드에 따라 스크롤 컨트롤
     */
    private void setScrollControl(final View view){
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

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if (SharedData.DEBUG_MODE) {
                    Date now = new Date();
                    now.getTime();
                    id_et_email.setInput("nisclan"+now.getTime()+"@hotmail.com");
//                    id_et_email.setInput("nisclan@hotmail.com");
                    id_et_passwd.setInput("tjswndqkqh");
                }
                checkUserEmail();
                break;
        }
    }

    private void checkUserEmail() {
        if (client == null )
            client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<User> call = client.checkUser(id_et_email.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Member4Fragment fragment = new Member4Fragment();
                    mPersonal.email = id_et_email.getText().toString();
                    mPersonal.passwd = id_et_passwd.getText().toString();
                    SharedData.getInstance(getContext()).insertGlobalData(SharedData.PERSONAL, mGson.toJson(mPersonal));
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.member_title);
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

}
