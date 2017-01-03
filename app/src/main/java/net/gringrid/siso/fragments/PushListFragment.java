package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.PushListAdapter;
import net.gringrid.siso.models.PushListItem;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.PushAPI;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.util.SharedData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Push list
 */
public class PushListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "jiho";
    private PushListAdapter mAdapter;
    private List<PushListItem> mList;
    private ListView id_lv;
    private User mUser;

    public PushListFragment() {
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
        return inflater.inflate(R.layout.fragment_push_list, container, false);
    }

    @Override
    public void onResume() {
        for(int i=0;i<100;i++){
            PushListItem item = new PushListItem();
            item.sender = "이순자 시터(23세)";
            item.msg = "전직 유치원 교사!  아이들과 잘 놀아요!아이들과 잘 놀아요!";
            item.req_date= "경기도 > 읜왕시 > 내손도옹  0.4km";
            item.type = Integer.toString(PushListItem.PUSH_TYPE_CONTACT_REQUEST);
            if(i%5==0){
                item.is_read = "Y";
            }
            mList.add(item);
        }
        mAdapter = new PushListAdapter(getContext(), mList);
        id_lv = (ListView)getView().findViewById(R.id.id_lv);
        id_lv.setAdapter(mAdapter);
        id_lv.setOnItemClickListener(this);
        loadList();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // push type 에 따라 이동
        PushListItem item = (PushListItem)parent.getItemAtPosition(position);

        if(item == null || TextUtils.isEmpty(item.type)) return;

        int pushType = Integer.parseInt(item.type);

        switch (pushType){
            case PushListItem.PUSH_TYPE_CONTACT_REQUEST:
                moveSenderDetail(item.sender);
                break;

            case PushListItem.PUSH_TYPE_CONTACT_ACCEPT:
                moveSenderDetail(item.sender);
                break;

            case PushListItem.PUSH_TYPE_CONTACT_REJECT:
                moveSenderDetail(item.sender);
                break;

            case PushListItem.PUSH_TYPE_APROVAL_ACCEPT:
                moveAprovalAccept();
                break;

            case PushListItem.PUSH_TYPE_APROVAL_REJECT:
                moveApprovalReject();
                break;
        }
    }

    private void moveSenderDetail(String email) {

        SitterDetailFragment fragment = new SitterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(fragment.MODE, fragment.MODE_FROM_SITTER_LIST);
        bundle.putString(SharedData.EMAIL, email);
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, Integer.MIN_VALUE);
    }

    private void moveAprovalAccept() {

    }

    private void moveApprovalReject() {

    }


    private void loadList() {
        Log.d(TAG, "loadList<PushListItem>: "+mUser.personalInfo.email);
        PushAPI client = ServiceGenerator.getInstance(getActivity()).createService(PushAPI.class);
        Call<List<PushListItem>> call = client.getList(mUser.personalInfo.email);
        call.enqueue(new Callback<List<PushListItem>>() {
            @Override
            public void onResponse(Call<List<PushListItem>> call, Response<List<PushListItem>> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
                    }
                    mAdapter = new PushListAdapter(getContext(), response.body());
                    id_lv = (ListView)getView().findViewById(R.id.id_lv);
                    id_lv.setAdapter(mAdapter);
                    id_lv.setOnItemClickListener(PushListFragment.this);

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PushListItem>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
