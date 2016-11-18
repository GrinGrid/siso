package net.gringrid.siso.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;


/**
 * 회원가입 > 성명, 생년월일
 */
public class Member02NameBirthFragment extends InputBaseFragment{

    private static final String TAG = "jiho";

    TextView id_tv_next_btn;

    SisoEditText id_et_name;
    SisoEditText id_et_birth;
    private User mUser;

    public Member02NameBirthFragment() {
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
        return inflater.inflate(R.layout.fragment_member02_name_birth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_name = (SisoEditText) view.findViewById(R.id.id_et_name);
        id_et_birth = (SisoEditText) view.findViewById(R.id.id_et_birth);
        setScrollControl(view);
        loadData();
    }

    /**
     * 소프트키보드에 따라 스크롤 컨트롤
     */
    private void setScrollControl(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                final ScrollView id_sv = (ScrollView)view.findViewById(R.id.id_sv);
                // softkey visible
                if ( heightDiff > 500 ){
                    id_sv.scrollTo(0, 500);
                    Log.d(TAG, "onGlobalLayout: softkey");
                }else{
                    id_sv.scrollTo(0, 0);
                }
            }
        });
    }


    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();

                moveNext();

                break;
        }
    }

    @Override
    protected void loadData() {
        if ( SharedData.DEBUG_MODE ) {
            id_et_name.setInput("최지호");
            id_et_birth.setInput("19801022");
        }

        if(!TextUtils.isEmpty(mUser.personalInfo.name)){
            id_et_name.setInput(mUser.personalInfo.name);
        }
        if(!TextUtils.isEmpty(mUser.personalInfo.birth_date)){
            id_et_birth.setInput(mUser.personalInfo.birth_date);
        }
    }

    @Override
    protected boolean isValidInput() {

        if (TextUtils.isEmpty(id_et_name.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_name_write);
            return false;
        }else if(TextUtils.isEmpty(id_et_birth.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_birth_write);
            return false;
        }

        return true;
    }

    @Override
    protected void saveData() {
        mUser.personalInfo.name = id_et_name.getText().toString();
        mUser.personalInfo.birth_date = id_et_birth.getText().toString();
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }


    @Override
    protected void moveNext() {
        Member03EmailFragment fragment = new Member03EmailFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, BaseActivity.TITLE_KEEP);

    }
}
