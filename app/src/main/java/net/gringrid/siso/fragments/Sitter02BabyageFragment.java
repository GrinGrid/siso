package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 희망 근무조건 > 희망 돌봄아이 연령
 */
public class Sitter02BabyageFragment extends InputBaseFragment implements InputBaseFragment.OnSaveCompleteListener {


    private static final String TAG = "jiho";
    User mUser;
    private TextView id_tv_next_btn;
    private int[] mAry = new int[]{
            R.id.id_tg_btn_age_0,
            R.id.id_tg_btn_age_1,
            R.id.id_tg_btn_age_2,
            R.id.id_tg_btn_age_3,
            R.id.id_tg_btn_age_4
    };

    public Sitter02BabyageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        Log.d(TAG, "onCreate: fragment1");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter02_babyage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;
                saveData();
                executeSave(this);
                break;
        }
    }

    @Override
    protected void loadData() {
        if(!TextUtils.isEmpty(mUser.sitterInfo.baby_age)) {
            SisoUtil.checkToggleButtonByString(mAry, mUser.sitterInfo.baby_age, getView());
        }
    }

    @Override
    protected boolean isValidInput() {
        boolean isChecked=false;
        for(int i=0; i<mAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mAry[i])).isChecked();
            if(isChecked){
                break;
            }
        }

        if(!isChecked){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_baby_age_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        boolean isChecked;
        String ageStr = "";
        for(int i=0; i<mAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mAry[i])).isChecked();
            if(isChecked){
                ageStr += i+User.DELIMITER;
            }
        }
        mUser.sitterInfo.baby_age = SisoUtil.deleteLastDelimiter(ageStr, User.DELIMITER);

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Sitter00IndexFragment fragment = new Sitter00IndexFragment();
        ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_basic_title);
    }

    @Override
    public void onSaveComplete() {
        moveNext();
    }
}
