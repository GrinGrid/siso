package net.gringrid.siso;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import net.gringrid.siso.views.ActionBar;

public class MemberJoinTwoActivity extends Activity {

    ActionBar actionBar;
    TextView id_tv_next_btn;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_join_two);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPink));
        actionBar = (ActionBar)findViewById(R.id.id_action_bar);
        actionBar.setTextAlign(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        id_tv_next_btn = (TextView)findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberJoinTwoActivity.this, BaseActivity.class);
                startActivity(intent);
            }
        });

    }
}

