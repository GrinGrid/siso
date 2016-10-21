package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

/**
 * 구직정보입력 > 자기소개
 * A simple {@link Fragment} subclass.
 */
public class Sitter09IntroductionFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private SisoEditText id_et_brief;
    private SisoEditText id_et_introduce;

    public Sitter09IntroductionFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter9, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_brief = (SisoEditText) getView().findViewById(R.id.id_et_brief);
        id_et_introduce = (SisoEditText) getView().findViewById(R.id.id_et_introduce);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:
                // TODO 입력값 체크
                saveData();
                Sitter10EduLicFragment fragment = new Sitter10EduLicFragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_introduce_title);
                break;

        }
    }

    private void saveData() {
        mUser.sitterInfo.brief = id_et_brief.getText().toString();
        mUser.sitterInfo.introduction = id_et_introduce.getText().toString();

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

}
