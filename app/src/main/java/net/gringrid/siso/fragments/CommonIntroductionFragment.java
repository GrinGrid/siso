package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoEditText;

/**
 * 구직 / 구인정보 입력 공통 > 자기소개
 */
public class CommonIntroductionFragment extends InputBaseFragment implements
        InputBaseFragment.OnSaveCompleteListener {

    private TextView id_tv_next_btn;
    private SisoEditText id_et_brief;
    private SisoEditText id_et_introduce;
    private String mUserType;

    public CommonIntroductionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_introduce, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_brief = (SisoEditText)view.findViewById(R.id.id_et_brief);
        id_et_introduce = (SisoEditText)view.findViewById(R.id.id_et_introduce);

        initViewString(view);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViewString(View view) {
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            id_et_brief.setLabel(R.string.common_sitter_brief);
            id_et_brief.setHint(R.string.common_sitter_brief_hint);
            id_et_introduce.setLabel(R.string.common_sitter_introduce);
            id_et_introduce.setHint(R.string.common_sitter_introduce_hint);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)) {
            id_et_brief.setLabel(R.string.common_sitter_brief);
            id_et_brief.setHint(R.string.common_sitter_brief_hint);
            id_et_introduce.setLabel(R.string.common_sitter_introduce);
            id_et_introduce.setHint(R.string.common_sitter_introduce_hint);
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();

                executeSave(this);

                break;
        }
    }

    @Override
    protected void loadData() {
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            if(!TextUtils.isEmpty(mUser.sitterInfo.brief)){
                id_et_brief.setInput(mUser.sitterInfo.brief);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.introduction)){
                id_et_introduce.setInput(mUser.sitterInfo.introduction);
            }
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            if(!TextUtils.isEmpty(mUser.parentInfo.brief)){
                id_et_brief.setInput(mUser.parentInfo.brief);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.introduction)){
                id_et_introduce.setInput(mUser.parentInfo.introduction);
            }
        }
    }

    @Override
    protected boolean isValidInput() {
        if(TextUtils.isEmpty(id_et_brief.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_brief_write);
            return false;
        }
        if(TextUtils.isEmpty(id_et_introduce.getText())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_introduce_write);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            mUser.sitterInfo.brief = id_et_brief.getText().toString();
            mUser.sitterInfo.introduction = id_et_introduce.getText().toString();
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.brief = id_et_brief.getText().toString();
            mUser.parentInfo.introduction = id_et_introduce.getText().toString();
        }

        Log.d(TAG, "onClick: mUser : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            Sitter00IndexFragment fragment = new Sitter00IndexFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            Parent00IndexFragment fragment = new Parent00IndexFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
        }
    }

    @Override
    public void onSaveComplete() {
        moveNext();
    }
}
