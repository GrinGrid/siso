package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoTimeTable;

/**
 * 구직 / 구인정보 입력 공통 > 스케줄/시급 설정
 */
public class CommonScheduleHourlyFragment extends InputBaseFragment implements SisoPicker.OnClickPlusMinusListener, SisoTimeTable.OnTimeChangedListener, InputBaseFragment.OnSaveCompleteListener {

    private SisoPicker id_pk_salary_hour;
    private TextView id_tv_schedule;
    private TextView id_tv_salary;
    private TextView id_tv_salary_comment;
    private TextView id_tv_salary_hour;
    private TextView id_tv_salary_week;
    private TextView id_tv_salary_week2;
    private TextView id_tv_salary_day;
    private TextView id_tv_salary_day2;
    private TextView id_tv_salary_month;
    private TextView id_tv_next_btn;
    private TextView id_tv_selected_hour;
    private LinearLayout id_ll_calculator;

    private SisoTimeTable id_stt;
    private String mUserType;

    public CommonScheduleHourlyFragment() {
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
        return inflater.inflate(R.layout.fragment_common_schedume_hourly, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tv_schedule = (TextView)view.findViewById(R.id.id_tv_schedule);
        id_tv_salary = (TextView)view.findViewById(R.id.id_tv_salary);
        id_tv_salary_comment = (TextView)view.findViewById(R.id.id_tv_salary_comment);
        id_pk_salary_hour = (SisoPicker)view.findViewById(R.id.id_pk_salary_hour);
        id_pk_salary_hour.setOnClickPlusMinusListener(this);

        id_tv_selected_hour = (TextView)view.findViewById(R.id.id_tv_selected_hour);
        id_tv_salary_hour = (TextView)view.findViewById(R.id.id_tv_salary_hour);
        id_tv_salary_day = (TextView)view.findViewById(R.id.id_tv_salary_day);
        id_tv_salary_day2 = (TextView)view.findViewById(R.id.id_tv_salary_day2);
        id_tv_salary_week = (TextView)view.findViewById(R.id.id_tv_salary_week);
        id_tv_salary_week2 = (TextView)view.findViewById(R.id.id_tv_salary_week2);
        id_tv_salary_month = (TextView)view.findViewById(R.id.id_tv_salary_month);

        id_ll_calculator = (LinearLayout)view.findViewById(R.id.id_ll_calculator);

        id_stt = (SisoTimeTable)view.findViewById(R.id.id_stt);
        id_stt.setOnTimeChangedListener(this);

        // 사용자 유형에 따라 화면 텍스트 변경
        if(mUserType.equals(User.USER_TYPE_PARENT)){
            id_tv_schedule.setText(getString(R.string.parent06_schedule));
            id_tv_salary.setText(getString(R.string.parent06_salary));
            id_tv_salary_comment.setText(getString(R.string.parent06_salary_comment));
        }else if(mUserType.equals(User.USER_TYPE_SITTER)){
            id_tv_schedule.setText(getString(R.string.sitter5_sub1_schedule));
            id_tv_salary.setText(getString(R.string.sitter5_sub1_salary));
            id_tv_salary_comment.setText(getString(R.string.sitter5_sub1_salary_comment));
        }

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClickPlusMinus(View view, int CLICK_ACTION) {
        switch (view.getId()){
            case R.id.id_pk_salary_hour:
                calculateSalary();
                break;
        }
    }

    private void calculateSalary(){
        String[] arrSalaryValues = getResources().getStringArray(R.array.picker_salary_value);
        String selectedValue = arrSalaryValues[id_pk_salary_hour.getCurrentIndex()];
        int selectedHour = id_stt.getSelectedHour();
        int salaryWeek = selectedHour * Integer.valueOf(selectedValue);
        float salaryDay = salaryWeek / 5;
        float salaryMonth = 21 * salaryDay;

        // 협의 예외처리
        if(id_pk_salary_hour.getCurrentIndex() == 0){
            id_ll_calculator.setVisibility(View.GONE);
        }else{
            id_ll_calculator.setVisibility(View.VISIBLE);
            id_tv_selected_hour.setText(String.valueOf(selectedHour));
            id_tv_salary_hour.setText(id_pk_salary_hour.getCurrentText());
            id_tv_salary_day.setText(String.format("%,.0f",salaryDay)+"원");
            id_tv_salary_day2.setText(String.format("%,.0f",salaryDay)+"원");
            id_tv_salary_week.setText(String.format("%,d",salaryWeek)+"원");
            id_tv_salary_week2.setText(String.format("%,d",salaryWeek)+"원");
            id_tv_salary_month.setText(String.format("%,.0f",salaryMonth)+"원");
        }
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
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            if(!TextUtils.isEmpty(mUser.sitterInfo.mon)){
                id_stt.setBitString(SisoTimeTable.MON, mUser.sitterInfo.mon);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.tue)){
                id_stt.setBitString(SisoTimeTable.TUE, mUser.sitterInfo.tue);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.wed)){
                id_stt.setBitString(SisoTimeTable.WED, mUser.sitterInfo.wed);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.thu)){
                id_stt.setBitString(SisoTimeTable.THU, mUser.sitterInfo.thu);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.fri)){
                id_stt.setBitString(SisoTimeTable.FRI, mUser.sitterInfo.fri);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.sat)){
                id_stt.setBitString(SisoTimeTable.SAT, mUser.sitterInfo.sat);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.sun)){
                id_stt.setBitString(SisoTimeTable.SUN, mUser.sitterInfo.sun);
            }
            if(!TextUtils.isEmpty(mUser.sitterInfo.salary)){
                int idx = SisoUtil.findIndexFromArrayString(getContext(),
                        R.array.picker_salary_value, mUser.sitterInfo.salary);
                id_pk_salary_hour.setIndex(idx);
            }
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            if(!TextUtils.isEmpty(mUser.parentInfo.mon)){
                id_stt.setBitString(SisoTimeTable.MON, mUser.parentInfo.mon);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.tue)){
                id_stt.setBitString(SisoTimeTable.TUE, mUser.parentInfo.tue);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.wed)){
                id_stt.setBitString(SisoTimeTable.WED, mUser.parentInfo.wed);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.thu)){
                id_stt.setBitString(SisoTimeTable.THU, mUser.parentInfo.thu);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.fri)){
                id_stt.setBitString(SisoTimeTable.FRI, mUser.parentInfo.fri);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.sat)){
                id_stt.setBitString(SisoTimeTable.SAT, mUser.parentInfo.sat);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.sun)){
                id_stt.setBitString(SisoTimeTable.SUN, mUser.parentInfo.sun);
            }
            if(!TextUtils.isEmpty(mUser.parentInfo.salary)){
                int idx = SisoUtil.findIndexFromArrayString(getContext(),
                        R.array.picker_salary_value, mUser.parentInfo.salary);
                id_pk_salary_hour.setIndex(idx);
            }
        }
    }

    @Override
    protected boolean isValidInput() {
        // 스케줄을 선택여부 체크
        if(id_stt.getSelectedHour() == 0){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_schedule_write);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        String[] arrSalaryValues = getResources().getStringArray(R.array.picker_salary_value);
        String selectedValue = arrSalaryValues[id_pk_salary_hour.getCurrentIndex()];

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            mUser.sitterInfo.mon = id_stt.getBitString(SisoTimeTable.MON);
            mUser.sitterInfo.tue = id_stt.getBitString(SisoTimeTable.TUE);
            mUser.sitterInfo.wed = id_stt.getBitString(SisoTimeTable.WED);
            mUser.sitterInfo.thu = id_stt.getBitString(SisoTimeTable.THU);
            mUser.sitterInfo.fri = id_stt.getBitString(SisoTimeTable.FRI);
            mUser.sitterInfo.sat = id_stt.getBitString(SisoTimeTable.SAT);
            mUser.sitterInfo.sun = id_stt.getBitString(SisoTimeTable.SUN);
            mUser.sitterInfo.salary = selectedValue;
        } else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.mon = id_stt.getBitString(SisoTimeTable.MON);
            mUser.parentInfo.tue = id_stt.getBitString(SisoTimeTable.TUE);
            mUser.parentInfo.wed = id_stt.getBitString(SisoTimeTable.WED);
            mUser.parentInfo.thu = id_stt.getBitString(SisoTimeTable.THU);
            mUser.parentInfo.fri = id_stt.getBitString(SisoTimeTable.FRI);
            mUser.parentInfo.sat = id_stt.getBitString(SisoTimeTable.SAT);
            mUser.parentInfo.sun = id_stt.getBitString(SisoTimeTable.SUN);
            mUser.parentInfo.salary = selectedValue;
        }
        Log.d(TAG, "onClick: mUser : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        int titleId = 0;
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            titleId = R.string.sitter_title;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            titleId = R.string.parent_title;
        }
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            Sitter00IndexFragment fragment = new Sitter00IndexFragment();
            ((BaseActivity) getActivity()).setCleanUpFragment(fragment, titleId);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            Parent00IndexFragment fragment = new Parent00IndexFragment();
            ((BaseActivity) getActivity()).setCleanUpFragment(fragment, titleId);
        }
    }

    @Override
    public void onSaveComplete() {
        moveNext();
    }

    /**
     * 시간표 시간 선택이 변경될 때 호출된다
     */
    @Override
    public void onTimeChanged() {
        calculateSalary();
    }
}
