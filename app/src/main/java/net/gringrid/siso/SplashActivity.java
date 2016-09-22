package net.gringrid.siso;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class SplashActivity extends RootActivity{

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPink));
        setContentView(R.layout.activity_splash);
        ImageView iv_logo = (ImageView)findViewById(R.id.iv_logo);
        iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, BaseActivity.class);
                intent.putExtra("menu", BaseActivity.MENU_SIGN_UP);
                startActivity(intent);
            }
        });
    }
}
