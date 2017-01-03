package net.gringrid.siso;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ConfigAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SessionAPI;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoIndicator;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RootActivity extends AppCompatActivity implements IndicatorDialog.UseIndicator {

    private static final String TAG = "jiho";
    protected Dialog loadingDialog = null;
    private boolean mIsShowIndicatorDialog;
    protected Handler mHandler = null;
    IndicatorDialog mIndicatorDialog;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: this : "+this.getClass().getSimpleName());
        mUser = SharedData.getInstance(this).getUserData();
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mIndicatorDialog = new IndicatorDialog();

        // 앱이 완전히 종료 되었다 시작될때는 Config check time reset
        if(this instanceof SplashActivity){
            long resetCheckTime = System.currentTimeMillis() - SharedData.LOCAL_CONFIG_EXPIRE_LENGTH;
            SharedData.getInstance(this).insertGlobalData(SharedData.CONFIG_LAST_CHECK_TIME, resetCheckTime);
        }

        Log.d(TAG, "================================");
        Log.d(TAG, "RootActivity onCreate");
        Log.d(TAG, "================================");

        Log.d(TAG, "RootActivity onCreate: FireBase");
        Log.d(TAG, "RootActivity onCreate: FireBase");
        //FirebaseMessaging.getInstance().subscribeToTopic("news");
        //String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "onCreate: RootActivity token : "+token);
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "================================");
        Log.d(TAG, "RootActivity onResume");
        Log.d(TAG, "================================");
        checkRun();
        // TODO Splash 에서 넘어왔을때와 재실행 했을때를 구분해야 함
        super.onResume();
    }

    private void checkRun() {
        Log.d(TAG, "checkRun: ");
        checkConfigExpireTime();
    }

    /**
     * Config check 만료시간 체크
     */
    private void checkConfigExpireTime(){
        Log.d(TAG, "checkConfigExpireTime: ");
        long currentTime = System.currentTimeMillis();
        long configLastCheckTime = SharedData.getInstance(this).getGlobalDataLong(SharedData.CONFIG_LAST_CHECK_TIME);
        String sessionKey = SharedData.getInstance(this).getGlobalDataString(SharedData.SESSION_KEY);
        if((currentTime - configLastCheckTime) < SharedData.LOCAL_CONFIG_EXPIRE_LENGTH){
            if(!TextUtils.isEmpty(sessionKey)){
                checkSessionExpireTime();
            }else{
                moveNext();
            }
        }else{
            checkConfig();
        }
    }

    /**
     * Session check 만료 시간 체크
     */
    private void checkSessionExpireTime() {
        Log.d(TAG, "checkSessionExpireTime: ");
        long currentTime = System.currentTimeMillis();
        long sessionLastCheckTime = SharedData.getInstance(this).getGlobalDataLong(SharedData.SESSION_LAST_CHECK_TIME);
        if((currentTime - sessionLastCheckTime) < SharedData.LOCAL_SESSION_EXPIRE_LENGTH){
            renewSession();
        }else{
            checkSession();
        }
    }


    /**
     * Config 내용 받아 온 후 처리
     */
    private void checkConfigCallback(ConfigAPI.Config config) {
        Log.d(TAG, "checkConfigCallback: ");
        // config check 유효기간 설정
        SharedData.getInstance(this).insertGlobalData(SharedData.CONFIG_LAST_CHECK_TIME, System.currentTimeMillis());

        // TODO Config 파일을 가지고 무엇을 할 것인지 판단
        // 버전체크
        // 비상메시지
        //  서버점검중
        //  공지사항
        String sessionKey = SharedData.getInstance(this).getGlobalDataString(SharedData.SESSION_KEY);
        if(!TextUtils.isEmpty(sessionKey)){
            checkSessionExpireTime();
        }else{
            moveNext();
        }
    }

    /**
     * session 유효 여부를 서버에서 확인 후 처리
     * @param result
     */
    private void checkSessionCallback(String result) {
        Log.d(TAG, "checkSessionCallback: result : "+result);
        if(result.equals(SessionAPI.RESULT_NONE)){
            if(mUser!=null){
                if(!(TextUtils.isEmpty(mUser.personalInfo.email)) &&
                        !(TextUtils.isEmpty(mUser.personalInfo.passwd))){
                    excuteAutoLogin();
                }else{
                    moveNext();
                }
            }else{
                moveNext();
            }
        }else if(result.equals(SessionAPI.RESULT_RENEWAL)){
            renewSession();
        }
    }

    /**
     * session 만료 시간 갱신
     */
    private void renewSession() {
        Log.d(TAG, "renewSession: ");
        // TODO 부모/시터에 따라 USER data 유효성 여부 체크
        SharedData.getInstance(this).insertGlobalData(SharedData.SESSION_LAST_CHECK_TIME, System.currentTimeMillis());
        moveNext();
    }

    protected void moveNext() {
        Log.d(TAG, "moveNext: ROOTACTIVITY");
        
        if(this instanceof SplashActivity){
            Log.d(TAG, "checkSessionCallback: GO Guide");
        }else{
            // Splash 화면으로 이동
            // TODO 앱 완전히 종료 후 실행
//            Intent intent = new Intent(this, SplashActivity.class);
//            startActivity(intent);
        }
    }

    private void excuteAutoLogin() {
        Log.d(TAG, "excuteAutoLogin: ");
        SisoClient client = ServiceGenerator.getInstance(this).createService(SisoClient.class);
        Personal personal = new Personal();
        personal.email = mUser.personalInfo.email;
        personal.passwd = mUser.personalInfo.passwd;
        Call<User> call = client.relogin(personal);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: login : "+response.body().toString());
                    Log.d(TAG, "onResponse session-key : "+response.headers().get(SharedData.SESSION_KEY));
                    SharedData.getInstance(RootActivity.this).setObjectData(SharedData.USER, response.body());
                    SharedData.getInstance(RootActivity.this).insertGlobalData(SharedData.SESSION_KEY, response.headers().get(SharedData.SESSION_KEY));
                    renewSession();
                    SisoUtil.showMsg(RootActivity.this, response.body().personalInfo.name+"님 정상적으로 로그인 되었습니다.");
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(RootActivity.this, msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void checkConfig(){
        Log.d(TAG, "checkConfig: ");
        ConfigAPI client = ServiceGenerator.getInstance(this).createService(ConfigAPI.class);
        Call<ConfigAPI.Config> call = client.getConfig();
        call.enqueue(new Callback<ConfigAPI.Config>() {
            @Override
            public void onResponse(Call<ConfigAPI.Config> call, Response<ConfigAPI.Config> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "checkConfig onResponse: success");
                    checkConfigCallback(response.body());
                }else{
                    Log.d(TAG, "checkConfig onResponse: fail");
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getApplicationContext(), msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ConfigAPI.Config> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    /**
     * 세션유효성 여부 체크
     */
    private void checkSession() {
        Log.d(TAG, "checkSession: ");
        SessionAPI client = ServiceGenerator.getInstance(this).createService(SessionAPI.class);
        Call<SessionAPI.Session> call = client.isVaildSession();
        call.enqueue(new Callback<SessionAPI.Session>() {
            @Override
            public void onResponse(Call<SessionAPI.Session> call, Response<SessionAPI.Session> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.body());
                    String result = response.body().result;
                    if(!TextUtils.isEmpty(result)){
                        checkSessionCallback(result);
                    }
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getApplicationContext(), msgText, Toast.LENGTH_SHORT).show();
                    checkSessionCallback(SessionAPI.RESULT_ERROR);
                }
            }

            @Override
            public void onFailure(Call<SessionAPI.Session> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                checkSessionCallback(SessionAPI.RESULT_ERROR);
            }
        });
    }

    @Override
    public void showIndicator() {
        // Show 하는 순간 여러번 showProgress가 호출되면 isAdded가 제대로 output을 내지 못함
        if(mIsShowIndicatorDialog == false){
            mIndicatorDialog.show(getFragmentManager(), "");
            mIsShowIndicatorDialog = true;
        }
    }

    @Override
    public void hideIndicator() {
        mIndicatorDialog.dismiss();
        mIsShowIndicatorDialog = false;
    }

}
