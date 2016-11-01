package net.gringrid.siso;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class RootActivity extends AppCompatActivity {

    private static final String TAG = "jiho";
    Dialog loadingDialog = null;
    Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG, "================================");
        Log.d(TAG, "RootActivity onCreate");
        Log.d(TAG, "================================");
        // TODO FCM TEST

        Log.d(TAG, "RootActivity onCreate: FireBase");
        Log.d(TAG, "RootActivity onCreate: FireBase");
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: RootActivity token : "+token);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "================================");
        Log.d(TAG, "RootActivity onResume");
        Log.d(TAG, "================================");
        super.onResume();
    }



    public void showProgress(){
        if (mHandler==null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loadingDialog = new Dialog(RootActivity.this);
                loadingDialog.setTitle("Loading data..");
                loadingDialog.setContentView(R.layout.loading);
                loadingDialog.show();
            }
        });
    }


    public void hideProgress(){
        mHandler.post(new Runnable() {
        @Override
            public void run() {
                loadingDialog.dismiss();
            }
        });
    }
//    public void showProgress(){
//        if (mHandler==null){
//            mHandler = new Handler(Looper.getMainLooper());
//        }
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog = new Dialog(RootActivity.this);
//                loadingDialog.setTitle("Loading data..");
//                loadingDialog.setContentView(R.layout.loading);
//                loadingDialog.show();
//
//            }
//        });
//    }
//
//    public void hideProgress(){
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog.dismiss();
//            }
//        });
//    }


}
