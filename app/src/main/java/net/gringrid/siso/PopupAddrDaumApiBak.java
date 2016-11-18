package net.gringrid.siso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Deprecated
public class PopupAddrDaumApiBak extends Activity implements Callback<Personal> {

    private static final String TAG = "jiho";
    private static final String DAUM_API_KEY = "20e6ba46a8a6e8c276c479edb01e473c";
    public static final String DAUM_ADDR_URL = "http://www.siso4u.net/addr.html";
    private String mPostNo;
    private String mAddr;
    private String mLng;
    private String mLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_addr_daum_api_bak);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initializeView();
    }


    @JavascriptInterface
    public void setAddr(String post_no, String addr){
//        Log.d(TAG, "setAddr: post_no : "+post_no);
//        Log.d(TAG, "setAddr: addr1 : "+addr);
//
//        mPostNo = post_no;
//        mAddr = addr;
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Personal.class, new MyDeserializer())
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(DAUM_API_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        MemberAPI memberAPI = retrofit.create(MemberAPI.class);
//        Call<Personal> call = memberAPI.getGPS(DAUM_API_KEY, "json", addr);
//        Log.d(TAG, "setAddr: "+call.toString());
//        call.enqueue(this);
    }

    @JavascriptInterface
    private void initializeView() {
        WebView id_wv = (WebView)findViewById(R.id.id_wv);

        if ( id_wv!= null ){
            id_wv.getSettings().setJavaScriptEnabled(true);
            id_wv.addJavascriptInterface(this, "AndroidFunction");
            id_wv.loadUrl(DAUM_ADDR_URL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onResponse(Call<Personal> call, Response<Personal> response) {
        mLat = response.body().lat;
        mLng = response.body().lng;
        Log.d(TAG, "onResponse body : "+response.body().toString());
        Log.d(TAG, "onResponse body : "+response.body().lat);
        Log.d(TAG, "onResponse body : "+response.body().lng);
        Log.d(TAG, "onResponse message : "+response.message());
        Intent intent = getIntent();
        intent.putExtra(User.DATA_POST_NO, mPostNo);
        intent.putExtra(User.DATA_ADDR, mAddr);
        intent.putExtra(User.DATA_LATITUDE, mLat);
        intent.putExtra(User.DATA_LONGITUDE, mLng);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFailure(Call<Personal> call, Throwable t) {
        Log.d(TAG, "onFailure: "+t.getMessage());
    }

    class MyDeserializer implements JsonDeserializer<Personal>{

        @Override
        public Personal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement channel = json.getAsJsonObject().get("channel");
            JsonElement item = channel.getAsJsonObject().get("item");
            JsonArray itemArry = item.getAsJsonArray();
            Log.d(TAG, "deserialize: itemArry length : "+itemArry.size());
            return new Gson().fromJson(itemArry.get(0), Personal.class);
        }
    }
}
