package net.gringrid.siso;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import net.gringrid.siso.views.ActionBar;

public class MainActivity extends Activity {

    ActionBar actionBar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPink));
        actionBar = (ActionBar)findViewById(R.id.id_action_bar);
        actionBar.setTextAlign(Gravity.LEFT|Gravity.CENTER_VERTICAL);

    }
}
