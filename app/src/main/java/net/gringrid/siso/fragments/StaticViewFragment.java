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
 * 기능이 없는 View만 출력하는 fragment
 */
public class StaticViewFragment extends Fragment {


    private static final String TAG = "jiho";
    private int mViewId;


//    public static StaticViewFragment newInstance(int page, int titleRscId){
//        StaticViewFragment fragment = new StaticViewFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putInt("someTitle", titleRscId);
//        fragment.setArguments(args);
//
//        return fragment;
//    }

    public StaticViewFragment() {

        // Required empty public constructor
    }

    public StaticViewFragment(int viewId){
        mViewId = viewId;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Sitterlistfavorite");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(mViewId, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

}
