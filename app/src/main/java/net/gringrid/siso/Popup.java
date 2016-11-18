package net.gringrid.siso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.MemberAPI;
import net.gringrid.siso.util.SharedData;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Setting : content, button
 *      Content : layout inflate
 *      Button : color, Text, listener, tag, order
 */
public class Popup extends Activity {

    private static final String TAG = "jiho";

    protected static final String BTN_STYLE_WHITE = "WHITE";
    protected static final String BTN_STYLE_GREEN = "GREEN";
    protected static final String BTN_ORDER_FIRST = "FIRST";
    protected static final String BTN_ORDER_SECOND = "SECOND";

    private LinearLayout id_ll_content;
    protected TextView id_tv_title;
    private TextView id_tv_btn_first;
    private TextView id_tv_btn_second;

    protected User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initializeData();
        initializeView();
    }

    protected void initializeData() {
        mUser = SharedData.getInstance(this).getUserData();
    }

    protected void initializeView() {
        id_tv_title = (TextView) findViewById(R.id.id_tv_title);
        id_ll_content = (LinearLayout)findViewById(R.id.id_ll_content);
        id_tv_btn_first = (TextView) findViewById(R.id.id_tv_btn_first);
        id_tv_btn_second = (TextView) findViewById(R.id.id_tv_btn_second);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setPopupContent(View view){
       id_ll_content.addView(view);
    }

    protected void setButton(String order, String style, int strId, View.OnClickListener listener){
        TextView tmpBtn = null;
        if(order.equals(BTN_ORDER_FIRST)){
            tmpBtn = id_tv_btn_first;
        }else if(order.equals(BTN_ORDER_SECOND)){
            tmpBtn = id_tv_btn_second;
        }
        tmpBtn.setVisibility(View.VISIBLE);

        if(style.equals(BTN_STYLE_WHITE)){
        }else if(style.equals(BTN_STYLE_GREEN)){
        }

        tmpBtn.setText(strId);
        tmpBtn.setOnClickListener(listener);
    }


//    *      Button : color, Text, listener, tag, order


}
