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

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;


/**
 * 회원가입 > 시터/부모 선택
 */
public class Member01UserTypeFragment extends InputBaseFragment{

    private static final String TAG = "jiho";
    LinearLayout ll_parent;
    LinearLayout ll_sitter;

    public Member01UserTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member01_user_type, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ll_parent = (LinearLayout)view.findViewById(R.id.ll_parent);
        ll_sitter = (LinearLayout)view.findViewById(R.id.ll_sitter);

        ll_parent.setOnClickListener(this);
        ll_sitter.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.ll_parent:
                mUser.personalInfo.user_type = User.USER_TYPE_PARENT;
                break;
            case  R.id.ll_sitter:
                mUser.personalInfo.user_type = User.USER_TYPE_SITTER;
                break;
        }

        saveData();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void saveData() {
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
        moveNext();
    }

    @Override
    protected boolean isValidInput() {
        return false;
    }

    @Override
    protected void moveNext() {
        Member02NameBirthFragment fragment = new Member02NameBirthFragment();
        ((BaseActivity)getActivity()).setFragment(fragment, BaseActivity.TITLE_KEEP);
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
