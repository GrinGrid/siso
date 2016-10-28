package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.SitterListSisoAdapter;
import net.gringrid.siso.models.SitterList;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.network.restapi.SitterAPI;
import net.gringrid.siso.util.SharedData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 시터리스트 > 시소추천
 */
public class SitterListSIsoFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "jiho";
    private SitterListSisoAdapter mAdapter;
    private List<SitterList> mList;
    private ListView id_lv;
    private User mUser;

    public static SitterListSIsoFragment newInstance(int page, int titleRscId){
        SitterListSIsoFragment sitterListSIsoFragment = new SitterListSIsoFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putInt("someTitle", titleRscId);
        sitterListSIsoFragment.setArguments(args);
        return sitterListSIsoFragment;
    }

    public SitterListSIsoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mList = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_sitter_list_siso, container, false);
    }

    @Override
    public void onResume() {
        for(int i=0;i<100;i++){
            SitterList item = new SitterList();
            item.name = "이순자 시터(23세)";
            item.brief = "전직 유치원 교사!  아이들과 잘 놀아요!아이들과 잘 놀아요!";
            item.addr1 = "경기도 > 읜왕시 > 내손도옹  0.4km";
            mList.add(item);
        }
        mAdapter = new SitterListSisoAdapter(getContext(), mList);
        id_lv = (ListView)getView().findViewById(R.id.id_lv);
        id_lv.setAdapter(mAdapter);
        id_lv.setOnItemClickListener(this);
        loadList();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SitterDetailFragment fragment = new SitterDetailFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, Integer.MIN_VALUE);
    }

    private void loadList() {
        SitterAPI client = ServiceGenerator.getInstance(getActivity()).createService(SitterAPI.class);
        Call<SitterList> call = client.getListSiso(mUser.personalInfo.email);
        call.enqueue(new Callback<SitterList>() {
            @Override
            public void onResponse(Call<SitterList> call, Response<SitterList> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
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
            public void onFailure(Call<SitterList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
