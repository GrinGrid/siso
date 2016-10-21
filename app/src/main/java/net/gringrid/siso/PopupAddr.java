package net.gringrid.siso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.gringrid.siso.adapter.AddrAdapter;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.AddrAPI;
import net.gringrid.siso.network.restapi.MemberAPI;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class PopupAddr extends AppCompatActivity implements Callback<Personal>, View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "jiho";
    private static final String DAUM_API_KEY = "20e6ba46a8a6e8c276c479edb01e473c";
    public static final String DAUM_ADDR_URL = "http://www.siso4u.net/addr.html";

    private EditText id_et_search;
    private Button id_bt_search;
    private ArrayList<AddrAPI.AddrOutput> mAddrList;
    private ListView id_lv;
    private AddrAdapter mAdapter;
    private String mPostNo;
    private String mAddr;
    private String mLng;
    private String mLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_addr);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        id_et_search = (EditText)findViewById(R.id.id_et_search);
        id_bt_search = (Button)findViewById(R.id.id_bt_search);
        id_lv = (ListView)findViewById(R.id.id_lv);

        id_bt_search.setOnClickListener(this);
        id_lv.setOnItemClickListener(this);
    }

    private void searchJuso() {
        AddrAPI.AddrInput input = new AddrAPI.AddrInput();
        input.confmKey = "U01TX0FVVEgyMDE2MTAxMDE0MjI1MTE1NjQx";
        input.keyword = id_et_search.getText().toString();
        input.currentPage = 1;
        input.countPerPage = 100;
        String url = "http://www.juso.go.kr/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        AddrAPI api = retrofit.create(AddrAPI.class);
        Call<AddrAPI.AddrOutput> call = api.getAddr(input.currentPage, input.countPerPage, input.keyword, input.confmKey);
//        Call<AddrAPI.AddrOutput> call = api.getAddr(input);
        call.enqueue(new Callback<AddrAPI.AddrOutput>() {
            @Override
            public void onResponse(Call<AddrAPI.AddrOutput> call, Response<AddrAPI.AddrOutput> response) {
                Log.d(TAG, "onResponse: ADDR onResponse : "+response.body().toString());
                AddrAPI.AddrOutput output = response.body();
                mAdapter = new AddrAdapter(PopupAddr.this, output);
                id_lv.setAdapter(mAdapter);
//                Log.d(TAG, "onResponse: size : "+output.juso.size());
            }

            @Override
            public void onFailure(Call<AddrAPI.AddrOutput> call, Throwable t) {
                Log.d(TAG, "onFailure: ADDR onResponse : :"+t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_bt_search:
                searchJuso();
                hideSoftKeyboard();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mAddr = ((TextView)view.findViewById(R.id.id_tv_addr)).getText().toString();
        mPostNo = ((TextView)view.findViewById(R.id.id_tv_post_no)).getText().toString();


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Personal.class, new MyDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MemberAPI.DAUM_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MemberAPI memberAPI = retrofit.create(MemberAPI.class);
        Call<Personal> call = memberAPI.getGPS(DAUM_API_KEY, "json", mAddr);
        Log.d(TAG, "setAddr: "+call.toString());
        call.enqueue(this);
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

    class MyDeserializer implements JsonDeserializer<Personal> {

        @Override
        public Personal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement channel = json.getAsJsonObject().get("channel");
            JsonElement item = channel.getAsJsonObject().get("item");
            JsonArray itemArry = item.getAsJsonArray();
            Log.d(TAG, "deserialize: itemArry length : "+itemArry.size());
            return new Gson().fromJson(itemArry.get(0), Personal.class);
        }
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if ( view != null ){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
