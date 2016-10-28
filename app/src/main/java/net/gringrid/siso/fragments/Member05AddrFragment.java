package net.gringrid.siso.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.PopupAddr;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.AddrAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * 회원가입 > 주소
 */
public class Member05AddrFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "jiho";
    private static final int ACTIVITY_ADDR_REQUEST_CODE = 0;

    TextView id_tv_next_btn;
    SisoEditText id_et_post_no;
    SisoEditText id_et_addr1;
    SisoEditText id_et_addr2;


    TextView id_tv_post_search_btn;
    private User mUser;

    public Member05AddrFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_member5, container, false);
        setScrollControl(view);

        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_et_post_no = (SisoEditText)view.findViewById(R.id.id_et_post_no);
        id_et_addr1 = (SisoEditText)view.findViewById(R.id.id_et_addr1);
        id_et_addr2 = (SisoEditText)view.findViewById(R.id.id_et_addr2);

        TextView id_tv_test = (TextView) view.findViewById(R.id.id_tv_test);
        id_tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        id_tv_post_search_btn = (TextView)view.findViewById(R.id.id_tv_post_search_btn);
        id_tv_post_search_btn.setOnClickListener(this);
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

    public void test(){
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<User> call = client.getUser(mUser.personalInfo.email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body().toString() );
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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_tv_post_search_btn:
//                Intent intent = new Intent(getActivity(), Popup.class);
//                startActivityForResult(intent, 0);
//                searchJuso();
                Intent intent = new Intent(getActivity(), PopupAddr.class);
                startActivityForResult(intent, 0);
                break;

            case  R.id.id_tv_next_btn:
                if ( SharedData.DEBUG_MODE ) {
                    id_et_addr2.setInput("상세주소 4504호");
                    mUser.personalInfo.addr2 = id_et_addr2.getText().toString();
                }
                Log.d(TAG, "onClick: call Member : "+mUser.personalInfo.toString());

                mUser.personalInfo.push_id = FirebaseInstanceId.getInstance().getToken();
                // TODO input 체크
                executeSignUp();
                break;
        }
    }

    private void searchJuso() {
        AddrAPI.AddrInput input = new AddrAPI.AddrInput();
        input.confmKey = "U01TX0FVVEgyMDE2MTAxMDE0MjI1MTE1NjQx";
        input.keyword = "덕소로73";
        input.currentPage = 1;
        input.countPerPage = 10;
        String url = "http://www.juso.go.kr/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        AddrAPI api = retrofit.create(AddrAPI.class);
        Call<AddrAPI.AddrOutput> call = api.getAddr(input.currentPage, input.countPerPage, input.keyword, input.confmKey);
//        Call<AddrAPI.AddrOutput> call = api.getAddr(input);
        call.enqueue(new Callback<AddrAPI.AddrOutput>() {
            @Override
            public void onResponse(Call<AddrAPI.AddrOutput> call, Response<AddrAPI.AddrOutput> response) {
                Log.d(TAG, "onResponse: ADDR onResponse : "+response.body().toString());
                AddrAPI.AddrOutput output = response.body();

//                Log.d(TAG, "onResponse: size : "+output.juso.size());
            }

            @Override
            public void onFailure(Call<AddrAPI.AddrOutput> call, Throwable t) {
                Log.d(TAG, "onFailure: ADDR onResponse : :"+t.getMessage());
            }
        });
    }

    private void executeSignUp() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<User> call = client.signUp(mUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, response.body());
                        Log.d(TAG, "onResponse: success body : "+response.body().personalInfo.email );
                    }
                    Log.d(TAG, "onResponse session-key : "+response.headers().get(SharedData.SESSION_KEY));
                    SharedData.getInstance(getContext()).insertGlobalData(SharedData.SESSION_KEY, response.headers().get(SharedData.SESSION_KEY));
                    SharedData.getInstance(getContext()).setObjectData(SharedData.USER, response.body());

                    Log.d(TAG, "onResponse message : "+response.code());
                    Log.d(TAG, "onResponse message : "+response.message());
                    Member06CompleteFragment fragment = new Member06CompleteFragment();
                    ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.member_title);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case Activity.RESULT_OK:
                id_et_post_no.setInput(data.getStringExtra(User.DATA_POST_NO));
                id_et_addr1.setInput(data.getStringExtra(User.DATA_ADDR));
                Log.d(TAG, "onActivityResult: post_no : "+data.getStringExtra(User.DATA_POST_NO));
                Log.d(TAG, "onActivityResult: addr : "+data.getStringExtra(User.DATA_ADDR));
                Log.d(TAG, "onActivityResult: latitude : "+data.getStringExtra(User.DATA_LATITUDE));
                Log.d(TAG, "onActivityResult: longitude : "+data.getStringExtra(User.DATA_LONGITUDE));

                mUser.personalInfo.post_no = data.getStringExtra(User.DATA_POST_NO);
                mUser.personalInfo.addr1 = data.getStringExtra(User.DATA_ADDR);

                mUser.personalInfo.lat = data.getStringExtra(User.DATA_LATITUDE);
                mUser.personalInfo.lng = data.getStringExtra(User.DATA_LONGITUDE);
                SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

                ScrollView id_sv = (ScrollView)getView().findViewById(R.id.id_sv);
                id_sv.fullScroll(ScrollView.FOCUS_DOWN);

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
