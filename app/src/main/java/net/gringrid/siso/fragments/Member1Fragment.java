package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;


/**
 * A simple {@link Fragment} subclass.
 */
public class Member1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    LinearLayout ll_parent;
    LinearLayout ll_sitter;
    Personal mPersonal;
    Gson mGson;



    public Member1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        String memberStr = SharedData.getInstance(getContext()).getGlobalDataString(SharedData.PERSONAL);
        mGson = new Gson();
        if ( memberStr != null ){
            mPersonal = mGson.fromJson(memberStr, Personal.class);
            Log.d(TAG, "onCreate: Fragment1 member str is null");
        }else{
            mPersonal = new Personal();
            Log.d(TAG, "onCreate: Fragment1 member str is not null");
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member1, container, false);
    }

    @Override
    public void onResume() {
        ll_parent = (LinearLayout) getView().findViewById(R.id.ll_parent);
        ll_sitter = (LinearLayout) getView().findViewById(R.id.ll_sitter);

        ll_parent.setOnClickListener(this);
        ll_sitter.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        Member2Fragment fragment = new Member2Fragment();

        switch (v.getId()){
            case  R.id.ll_parent:
                ((BaseActivity)getActivity()).setFragment(fragment, R.string.member_title);
                mPersonal.userType = User.USER_TYPE_PARENT;
                break;
            case  R.id.ll_sitter:
                ((BaseActivity)getActivity()).setFragment(fragment, R.string.member_title);
                mPersonal.userType = User.USER_TYPE_SITTER;
                break;
        }
        SharedData.getInstance(getContext()).insertGlobalData(SharedData.PERSONAL, mGson.toJson(mPersonal));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "fragments onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);
    }

}
