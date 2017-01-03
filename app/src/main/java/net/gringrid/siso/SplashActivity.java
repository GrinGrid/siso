package net.gringrid.siso;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import net.gringrid.siso.util.SharedData;


public class SplashActivity extends RootActivity{
    protected static final String TAG = "jiho";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        loadingDialog = new Dialog(SplashActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void moveNext() {
        Log.d(TAG, "splash moveNext: ");
        super.moveNext();
        boolean isReadGuide = SharedData.getInstance(this).getGlobalDataBoolean(SharedData.IS_READ_GUIDE);
        Intent intent;
        if(isReadGuide){
            intent = new Intent(SplashActivity.this, BaseActivity.class);
            intent.putExtra("menu", BaseActivity.MENU_SIGN_UP);
        }else{
            intent = new Intent(SplashActivity.this, SisoGuideActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
