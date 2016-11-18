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
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoToggleButton;

import org.w3c.dom.Text;


/**
 * 구직정보입력 > 돌봄기본정보 > 시터경력, 근무가능기간
 */
public class Sitter01ExpPeriodFragment extends InputBaseFragment {

    private static final String TAG = "jiho";
    User mUser;

    private TextView id_tv_next_btn;
    int mRadioTerm[] = new int[]{R.id.id_tg_btn_now, R.id.id_tg_btn_day};
    private SisoToggleButton id_tg_btn_now;
    private SisoToggleButton id_tg_btn_day;
    private SisoPicker id_pk_experience;

    public Sitter01ExpPeriodFragment() {
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
        return inflater.inflate(R.layout.fragment_sitter01_exp_period, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_btn_now = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_now);
        id_tg_btn_day = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_day);
        id_tg_btn_now.setOnClickListener(this);
        id_tg_btn_day.setOnClickListener(this);
        id_pk_experience = (SisoPicker)view.findViewById(R.id.id_pk_experience);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tg_btn_now:
                SisoUtil.selectRadio(mRadioTerm, R.id.id_tg_btn_now, getView());
                break;
            case R.id.id_tg_btn_day:
                SisoUtil.selectRadio(mRadioTerm, R.id.id_tg_btn_day, getView());
                break;
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();

                moveNext();

                break;
        }
    }

    @Override
    protected void loadData() {
        if(mUser.sitterInfo==null) return;
        // 경력
        if(!TextUtils.isEmpty(mUser.sitterInfo.work_exp)){
            id_pk_experience.setIndex(Integer.parseInt(mUser.sitterInfo.work_exp));
        }
        // 근무기간
        if(!TextUtils.isEmpty(mUser.sitterInfo.term_from) &&
                !TextUtils.isEmpty(mUser.sitterInfo.term_to)){
            if(mUser.sitterInfo.term_from.equals(User.TERM_MIN) &&
                    mUser.sitterInfo.term_to.equals(User.TERM_MAX)){
                SisoUtil.selectRadio(mRadioTerm, R.id.id_tg_btn_now, getView());
            }else{
                SisoUtil.selectRadio(mRadioTerm, R.id.id_tg_btn_day, getView());
            }
        }
    }


    @Override
    protected boolean isValidInput() {
        // 근무가능기간 필수선택
        if(!SisoUtil.isRadioGroupSelected(mRadioTerm, getView())){
            Toast.makeText(getContext(), "근무가능기간을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        int work_exp = id_pk_experience.getCurrentIndex();
        mUser.sitterInfo.work_exp = String.valueOf(work_exp);
        int schedule = SisoUtil.getRadioValue(mRadioTerm, getView());
        if ( schedule == 0 ){
            mUser.sitterInfo.term_from = User.TERM_MIN;
            mUser.sitterInfo.term_to = User.TERM_MAX;
        }
        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);

    }

    @Override
    protected void moveNext() {
        int schedule = SisoUtil.getRadioValue(mRadioTerm, getView());
        // 즉시가능일경우 출퇴근 설정 화면
        if ( schedule == 0 ){
            CommonCommuteFragment fragment = new CommonCommuteFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage1);
            // 특정기간일경우 근무기간 설정 화면
        }else if (schedule == 1 ){
            CommonWorkPeriodFragment fragment = new CommonWorkPeriodFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage1);
        }
    }
}
