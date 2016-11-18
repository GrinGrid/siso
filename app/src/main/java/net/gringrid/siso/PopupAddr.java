package net.gringrid.siso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class PopupAddr extends Activity implements Callback<Personal>, View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "jiho";
    // 한 페이지에 보여질 항목 수
    private static final int COUNT_PER_PAGE = 10;
    // 버튼으로 표시할 페이지 수
    private static final int COUNT_DRAW_PAGE = 5;

    private static final String JUSO_CONFIRM_KEY = "U01TX0FVVEgyMDE2MTAxMDE0MjI1MTE1NjQx";
    private static final String JUSO_URL = "http://www.juso.go.kr/";
    private static final String DAUM_API_KEY = "20e6ba46a8a6e8c276c479edb01e473c";
    private static final String DAUM_API_URL = "https://apis.daum.net/local/geo/";
//    public static final String DAUM_ADDR_URL = "http://www.siso4u.net/addr.html";

    private EditText id_et_search;
    private TextView id_tv_search;
    private ArrayList<AddrAPI.AddrOutput> mAddrList;
    private ListView id_lv;
    private AddrAdapter mAdapter;
    private String mPostNo;
    private String mAddr;
    private String mLng;
    private String mLat;
    private LinearLayout id_ll_page;

    private int mCurrentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_addr);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        id_et_search = (EditText)findViewById(R.id.id_et_search);
        id_tv_search = (TextView)findViewById(R.id.id_tv_search);
        id_lv = (ListView)findViewById(R.id.id_lv);

        id_tv_search.setOnClickListener(this);
        id_lv.setOnItemClickListener(this);

        id_ll_page = (LinearLayout)findViewById(R.id.id_ll_page);
    }

    private void searchJuso() {
        AddrAPI.AddrInput input = new AddrAPI.AddrInput();
        input.confmKey = JUSO_CONFIRM_KEY;
        input.keyword = id_et_search.getText().toString();
        input.currentPage = mCurrentPage;
        input.countPerPage = COUNT_PER_PAGE;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JUSO_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        AddrAPI api = retrofit.create(AddrAPI.class);
        Call<AddrAPI.AddrOutput> call = api.getAddr(input.currentPage, input.countPerPage, input.keyword, input.confmKey);
        call.enqueue(new Callback<AddrAPI.AddrOutput>() {
            @Override
            public void onResponse(Call<AddrAPI.AddrOutput> call, Response<AddrAPI.AddrOutput> response) {
                drawPage(response.body().common);
                Log.d(TAG, "onResponse: ADDR onResponse : "+response.body().toString());
                AddrAPI.AddrOutput output = response.body();
                mAdapter = new AddrAdapter(PopupAddr.this, output);
                id_lv.setAdapter(mAdapter);
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
            case R.id.id_tv_search:
                mCurrentPage = 1;
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
                .baseUrl(DAUM_API_URL)
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

    private void drawPage(AddrAPI.Common common){
        int totalCount = Integer.parseInt(common.totalCount);
        // 총 페이지 구하기
        int totalPage = (int)Math.ceil(totalCount / (float)COUNT_PER_PAGE);
        int currentPageGroup = (mCurrentPage-1) / COUNT_DRAW_PAGE;
        int toPageNum = (currentPageGroup*COUNT_DRAW_PAGE)+COUNT_DRAW_PAGE < totalPage ? (currentPageGroup*COUNT_DRAW_PAGE)+COUNT_DRAW_PAGE : totalPage;
        id_ll_page.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20,0,20,0);

        Log.d(TAG, "totalPage : "+totalPage);
        Log.d(TAG, "currentPageGroup : "+currentPageGroup);
        Log.d(TAG, "toPageNum : "+toPageNum);
        Log.d(TAG, "currentPageGroup : "+currentPageGroup*COUNT_DRAW_PAGE);

        if(mCurrentPage > 1) {
            ImageView previousIv = new ImageView(this);
            previousIv.setLayoutParams(lp);
            previousIv.setImageResource(R.drawable.ic_zipcode_previous);
            id_ll_page.addView(previousIv);
            previousIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPage--;
                    searchJuso();
                }
            });
        }

        for(int i=currentPageGroup*COUNT_DRAW_PAGE; i<toPageNum; i++){
            final int fi = i+1;
            TextView pageItem = new TextView(this);
            pageItem.setLayoutParams(lp);
            pageItem.setGravity(Gravity.CENTER);
            pageItem.setText(String.valueOf(fi));
            if(fi==mCurrentPage){
                pageItem.setBackgroundResource(R.drawable.ic_zipcode_green);
            }else{
                pageItem.setBackgroundResource(R.drawable.ic_zipcode);
            }
            id_ll_page.addView(pageItem);
            pageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPage = fi;
                    searchJuso();
                }
            });
        }

        if(mCurrentPage < totalPage){
            ImageView nextIv = new ImageView(this);
            nextIv.setLayoutParams(lp);
            nextIv.setImageResource(R.drawable.ic_zipcode_next);
            id_ll_page.addView(nextIv);
            nextIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPage++;
                    searchJuso();
                }
            });

        }
    }
}
