package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindPasswordFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    private TextView id_tv_find_btn;

    public FindPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_password, container, false);
    }

    @Override
    public void onResume() {
        id_tv_find_btn = (TextView)getView().findViewById(R.id.id_tv_find_btn);
        id_tv_find_btn.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_find_btn:
                sendFindMail();
                break;
        }

    }

    private void sendFindMail() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<ResponseBody> call = client.findPassword("nisclan@hotmail.com");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body());
                        Log.d(TAG, "onResponse: success body : "+response.message());
                    }

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
