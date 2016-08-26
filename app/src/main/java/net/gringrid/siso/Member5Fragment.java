package net.gringrid.siso;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Member;
import net.gringrid.siso.network.restapi.MemberAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Member5Fragment extends Fragment implements View.OnClickListener, Callback<Member>{

    private static final String TAG = "jiho";
    TextView id_tv_next_btn;

    public Member5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member5, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case  R.id.id_tv_next_btn:

                Log.d(TAG, "onClick: call");

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MemberAPI.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                MemberAPI memberAPI = retrofit.create(MemberAPI.class);
                Call<Member> call = memberAPI.getMember("kyaku@hotmail2.com");
                call.enqueue(this);
                //((BaseActivity)getActivity()).setFragment(fragment, "무료 회원가입", 0);
                break;

        }
    }

    @Override
    public void onResponse(Call<Member> call, Response<Member> response) {
        Log.d(TAG, "onResponse body : "+response.body().getAddr1());
        Log.d(TAG, "onResponse body : "+response.body().toString());
        Log.d(TAG, "onResponse body : "+response.body());
        Log.d(TAG, "onResponse message : "+response.message());
    }

    @Override
    public void onFailure(Call<Member> call, Throwable t) {
        
        Log.d(TAG, "onFailure: "+t.getMessage());

    }
}
