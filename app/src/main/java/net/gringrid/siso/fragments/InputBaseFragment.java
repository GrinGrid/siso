package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 입력받는 Fragment 화면의 공통 Class
 */
public abstract class InputBaseFragment extends Fragment implements View.OnClickListener {

    protected static final String TAG = "jiho";


    protected interface OnSaveCompleteListener{
        public void onSaveComplete();
    }

    protected User mUser;
    public InputBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        super.onCreate(savedInstanceState);
    }

    protected abstract void loadData();
    protected abstract boolean isValidInput();
    protected abstract void saveData();
    protected abstract void moveNext();

    @Override
    public void onClick(View v) {

    }

    protected  void executeSave(final OnSaveCompleteListener saveCompleteListener) {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<User> call = client.modify(mUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        saveCompleteListener.onSaveComplete();
                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
                    }

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
