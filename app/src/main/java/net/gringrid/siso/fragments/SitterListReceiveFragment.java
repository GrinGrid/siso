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
import net.gringrid.siso.models.SitterListItem;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SitterAPI;
import net.gringrid.siso.util.SharedData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SitterListReceiveFragment extends Fragment implements AdapterView.OnItemClickListener {


    private static final String TAG = "jiho";
    private SitterListSisoAdapter mAdapter;
    private List<SitterListItem> mList;
    private ListView id_lv;
    private User mUser;

    public SitterListReceiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter_list_receive, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Sitterlistfavorite");
        mUser = SharedData.getInstance(getContext()).getUserData();
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume: favorite");
        mList = new ArrayList<>();
        for(int i=0;i<100;i++){
            SitterListItem item = new SitterListItem();
            item.name = "이순자 시터(23세)";
            item.brief = "전직 유치원 교사!  아이들과 잘 놀아요!아이들과 잘 놀아요!";
            item.addr = "경기도 > 읜왕시 > 내손도옹  0.4km";
            mList.add(item);
        }
        mAdapter = new SitterListSisoAdapter(getContext(), mList);
        id_lv = (ListView)getView().findViewById(R.id.id_lv);
        id_lv.setAdapter(mAdapter);
        id_lv.setOnItemClickListener(this);
        loadList();
        super.onResume();
    }

    private void loadList() {
        Log.d(TAG, "loadSitterList: "+mUser.personalInfo.email);
        SitterAPI client = ServiceGenerator.getInstance(getActivity()).createService(SitterAPI.class);
        Call<SitterList> call = client.getRcvListSiso(mUser.personalInfo.email);
        call.enqueue(new Callback<SitterList>() {
            @Override
            public void onResponse(Call<SitterList> call, Response<SitterList> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
                    }
                    mList = response.body().group_first;
                    mAdapter = new SitterListSisoAdapter(getContext(), mList);
                    id_lv = (ListView)getView().findViewById(R.id.id_lv);
                    id_lv.setAdapter(mAdapter);
                    id_lv.setOnItemClickListener(SitterListReceiveFragment.this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        SitterDetailFragment fragment = new SitterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(fragment.MODE, fragment.MODE_FROM_SITTER_LIST);
        Log.d(TAG, "onItemClick: trg_email : "+mList.get(position).email);
        bundle.putString(SharedData.EMAIL, mList.get(position).email);
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, Integer.MIN_VALUE);
    }
}
