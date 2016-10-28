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
public class Member01UserTypeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    LinearLayout ll_parent;
    LinearLayout ll_sitter;

    User mUser;

    public Member01UserTypeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_member1, container, false);
        ll_parent = (LinearLayout)view.findViewById(R.id.ll_parent);
        ll_sitter = (LinearLayout)view.findViewById(R.id.ll_sitter);

        ll_parent.setOnClickListener(this);
        ll_sitter.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        Member02NameBirthFragment fragment = new Member02NameBirthFragment();

        switch (v.getId()){
            case  R.id.ll_parent:
                ((BaseActivity)getActivity()).setFragment(fragment, R.string.member_title);
                mUser.personalInfo.user_type = User.USER_TYPE_PARENT;
                break;
            case  R.id.ll_sitter:
                ((BaseActivity)getActivity()).setFragment(fragment, R.string.member_title);
                mUser.personalInfo.user_type = User.USER_TYPE_SITTER;
                break;
        }
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

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
