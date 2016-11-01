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
import net.gringrid.siso.views.SisoToggleButton;


/**
 * 구인정보 입력 > 근무기간 설정
 */
public class Parent05PeriodFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    User mUser;

    private TextView id_tv_next_btn;
    int mRadioTerm[] = new int[]{R.id.id_tg_btn_now, R.id.id_tg_btn_day};
    private SisoToggleButton id_tg_btn_now;
    private SisoToggleButton id_tg_btn_day;

    public Parent05PeriodFragment() {
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
        return inflater.inflate(R.layout.fragment_parent05_period, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_btn_now = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_now);
        id_tg_btn_day = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_day);
        id_tg_btn_now.setOnClickListener(this);
        id_tg_btn_day.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();

                // TODO 근무기간이 즉시 가능일때는 Sitter4
                // TODO 근무기간이 특정기간일때는 Sitter3-1
                int schedule = getRadioValue(mRadioTerm);
                // 즉시가능일경우
                if ( schedule == 0 ){
                    Parent06CommuteFragment fragment = new Parent06CommuteFragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                // 특정기간일경우
                }else if (schedule == 1 ){
                    Bundle bundle = new Bundle();
                    bundle.putString(User.USER_TYPE, User.USER_TYPE_PARENT);
                    PeriodSelectFragment fragment = new PeriodSelectFragment();
                    fragment.setArguments(bundle);
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                }
                break;
            case R.id.id_tg_btn_now:
                selectRadio(R.id.id_tg_btn_now);
                break;
            case R.id.id_tg_btn_day:
                selectRadio(R.id.id_tg_btn_day);
                break;
        }

    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if (((SisoToggleButton) getView().findViewById(radioList[i])).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private void selectRadio(int selectItem) {
        for(int src:mRadioTerm){
            if(src == selectItem){
                ((SisoToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }

    private void saveData() {
        int schedule = getRadioValue(mRadioTerm);
        if ( schedule == 0 ){
            mUser.sitterInfo.term_from = "17000101";
            mUser.sitterInfo.term_to = "30000101";
        }
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }
}