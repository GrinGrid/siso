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

public class RootActivity extends AppCompatActivity {

    private static final String TAG = "jiho";
    Dialog loadingDialog = null;
    Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: rootActivity");
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
