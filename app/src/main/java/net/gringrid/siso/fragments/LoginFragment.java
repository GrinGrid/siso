package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SisoEditText id_et_email;
    SisoEditText id_et_passwd;
    TextView id_tv_login_btn;
    TextView id_tv_find_email;
    TextView id_tv_find_password;
    Gson mGson;

    User mUser;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "login fragment onResume: ");
        id_et_email = (SisoEditText) getView().findViewById(R.id.id_et_email);
        id_et_passwd = (SisoEditText) getView().findViewById(R.id.id_et_passwd);
        id_tv_login_btn = (TextView) getView().findViewById(R.id.id_tv_login_btn);
        id_tv_find_email = (TextView) getView().findViewById(R.id.id_tv_find_email);
        id_tv_find_password = (TextView) getView().findViewById(R.id.id_tv_find_password);

        id_tv_login_btn.setOnClickListener(this);
        id_tv_find_email.setOnClickListener(this);
        id_tv_find_password.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case  R.id.id_tv_login_btn:
                if ( SharedData.DEBUG_MODE ){
                    id_et_email.setInput("nisclan1475742432860@hotmail.com");
                    id_et_passwd.setInput("tjswndqkqh");
                }
                executeLogin();
                break;

            case R.id.id_tv_find_email:
                FindEmailFragment findEmailFragment = new FindEmailFragment();
                ((BaseActivity)getActivity()).setFragment(findEmailFragment, R.string.find_email_title);
                break;

            case R.id.id_tv_find_password:
                FindPasswordFragment findPasswordFragment = new FindPasswordFragment();
                ((BaseActivity)getActivity()).setFragment(findPasswordFragment, R.string.find_password_title);
                break;
        }
    }

    private void executeLogin() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Personal personal = new Personal();
        personal.email = id_et_email.getText().toString();
        personal.passwd = id_et_passwd.getText().toString();
        Call<User> call = client.login(personal);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: login : "+response.body().toString());
                    Log.d(TAG, "onResponse session-key : "+response.headers().get(SharedData.SESSION_KEY));
                    SharedData.getInstance(getContext()).setObjectData(SharedData.USER, response.body());
                    SharedData.getInstance(getContext()).insertGlobalData(SharedData.SESSION_KEY, response.headers().get(SharedData.SESSION_KEY));
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "fragments onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: ");
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getActivity().setTitle(getString(R.string.login_title));
        }
    }
}
