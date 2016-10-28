package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;

/**
 * 회원가입 > 부모 > 직업
 */
public class Member04JobFragment extends Fragment implements View.OnClickListener {

    User mUser;
    private TextView id_tv_next_btn;
    private ToggleButton id_tg_job_0;
    private ToggleButton id_tg_job_1;
    private ToggleButton id_tg_job_2;
    private ToggleButton id_tg_job_3;
    private ToggleButton id_tg_job_4;
    private SisoEditText id_et_job;

    int mRadioJob[] = new int[]{
            R.id.id_tg_job_0,
            R.id.id_tg_job_1,
            R.id.id_tg_job_2,
            R.id.id_tg_job_3,
            R.id.id_tg_job_4
    };

    public Member04JobFragment() {
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
        View view = inflater.inflate(R.layout.fragment_member04_job, container, false);

        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_job_0 = (ToggleButton)view.findViewById(R.id.id_tg_job_0);
        id_tg_job_1 = (ToggleButton)view.findViewById(R.id.id_tg_job_1);
        id_tg_job_2 = (ToggleButton)view.findViewById(R.id.id_tg_job_2);
        id_tg_job_3 = (ToggleButton)view.findViewById(R.id.id_tg_job_3);
        id_tg_job_4 = (ToggleButton)view.findViewById(R.id.id_tg_job_4);

        id_tg_job_0.setOnClickListener(this);
        id_tg_job_1.setOnClickListener(this);
        id_tg_job_2.setOnClickListener(this);
        id_tg_job_3.setOnClickListener(this);
        id_tg_job_4.setOnClickListener(this);

        id_et_job = (SisoEditText) view.findViewById(R.id.id_et_job);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.id_tv_next_btn:
                // TODO 입력값 체크
                saveData();
                Member05AddrFragment fragment = new Member05AddrFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_title);
                break;

            // 직업 Radio 선택
            case R.id.id_tg_job_1:
                selectRadio(mRadioJob, viewId);
                id_et_job.setVisibility(View.GONE);
                break;
            case R.id.id_tg_job_2:
            case R.id.id_tg_job_3:
            case R.id.id_tg_job_0:
            case R.id.id_tg_job_4:
                selectRadio(mRadioJob, viewId);
                id_et_job.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void saveData() {
        int jobRadio = getRadioValue(mRadioJob);
        mUser.personalInfo.job = String.valueOf(jobRadio);

        if(jobRadio!=1){
            mUser.personalInfo.job_detail = id_et_job.getText().toString();
        }

        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void selectRadio(int[] radioList, int selectItem) {
        for(int src:radioList){
            if(src == selectItem){
                ((ToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((ToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if( ((ToggleButton)getView().findViewById(radioList[i])).isChecked() ){
                return i;
            }
        }
        return 0;
    }
}
