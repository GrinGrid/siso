package net.gringrid.siso;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


public class SisoDialog extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "jiho";

    TextView id_tv_title;
    TextView id_tv_content;
    FrameLayout id_fl_close;
    TextView id_tv_btn1;
    TextView id_tv_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_siso_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        id_tv_title = (TextView)findViewById(R.id.id_tv_title);
        id_tv_content = (TextView)findViewById(R.id.id_tv_content);
        id_fl_close = (FrameLayout) findViewById(R.id.id_fl_close);
        id_tv_btn1 = (TextView) findViewById(R.id.id_tv_btn1);
        id_tv_btn2 = (TextView) findViewById(R.id.id_tv_btn2);

        id_fl_close.setOnClickListener(this);
        id_tv_btn1.setOnClickListener(this);
        id_tv_btn2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
