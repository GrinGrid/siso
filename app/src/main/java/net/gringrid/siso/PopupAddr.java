package net.gringrid.siso;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.gringrid.siso.adapter.AddrAdapter;
import net.gringrid.siso.network.restapi.AddrAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class PopupAddr extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "jiho";
    private EditText id_et_search;
    private Button id_bt_search;
    private ArrayList<AddrAPI.AddrOutput> mAddrList;
    private ListView id_lv;
    private AddrAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_addr);

        id_et_search = (EditText)findViewById(R.id.id_et_search);
        id_bt_search = (Button)findViewById(R.id.id_bt_search);
        id_lv = (ListView)findViewById(R.id.id_lv);

        id_bt_search.setOnClickListener(this);
    }

    private void searchJuso() {
        AddrAPI.AddrInput input = new AddrAPI.AddrInput();
        input.confmKey = "U01TX0FVVEgyMDE2MTAxMDE0MjI1MTE1NjQx";
        input.keyword = id_et_search.getText().toString();
        input.currentPage = 1;
        input.countPerPage = 10;
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
                break;
        }
    }
}
