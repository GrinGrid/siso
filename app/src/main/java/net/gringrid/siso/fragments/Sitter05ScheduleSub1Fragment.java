package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import net.gringrid.siso.views.SisoPicker;
import net.gringrid.siso.views.SisoTimeTable;
import net.gringrid.siso.views.SisoToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 기본정보 > 출퇴근방법 출퇴근/재택형 스케줄, 시급
 * A simple {@link Fragment} subclass.
 */
public class Sitter05ScheduleSub1Fragment extends Fragment implements SisoPicker.OnClickPlusMinusListener, View.OnClickListener, SisoTimeTable.OnTimeChangedListener {

    private static final String TAG = "jiho";
    private static final int GROUP_ROW = 0;
    private static final int GROUP_COL = 1;
    private static final int GROUP_ROW_LEN = 7;
    private static final int GROUP_COL_LEN = 7;
    User mUser;
    private SisoPicker id_pk_salary_hour;
    private TextView id_tv_salary_hour;
    private TextView id_tv_salary_week;
    private TextView id_tv_salary_week2;
    private TextView id_tv_salary_day;
    private TextView id_tv_salary_day2;
    private TextView id_tv_salary_month;
    private TextView id_tv_next_btn;
    private TextView id_tv_selected_hour;

    private SisoTimeTable id_stt;
//
//    private TextView id_tv_mon;
//    private TextView id_tv_tue;
//    private TextView id_tv_wed;
//    private TextView id_tv_thu;
//    private TextView id_tv_fri;
//    private TextView id_tv_sat;
//    private TextView id_tv_sun;
//
//    private TextView id_tv_time1;
//    private TextView id_tv_time2;
//    private TextView id_tv_time3;
//    private TextView id_tv_time4;
//    private TextView id_tv_time5;
//    private TextView id_tv_time6;
//    private TextView id_tv_time7;

    public Sitter05ScheduleSub1Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        Log.d(TAG, "onCreate: SITTER5_sub1 : "+mUser.toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter5_sub1, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView)getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_pk_salary_hour = (SisoPicker)getView().findViewById(R.id.id_pk_salary_hour);
        id_pk_salary_hour.setOnClickPlusMinusListener(this);


        id_tv_selected_hour = (TextView)getView().findViewById(R.id.id_tv_selected_hour);
        id_tv_salary_hour = (TextView)getView().findViewById(R.id.id_tv_salary_hour);
        id_tv_salary_day = (TextView)getView().findViewById(R.id.id_tv_salary_day);
        id_tv_salary_day2 = (TextView)getView().findViewById(R.id.id_tv_salary_day2);
        id_tv_salary_week = (TextView)getView().findViewById(R.id.id_tv_salary_week);
        id_tv_salary_week2 = (TextView)getView().findViewById(R.id.id_tv_salary_week2);
        id_tv_salary_month = (TextView)getView().findViewById(R.id.id_tv_salary_month);

        id_stt = (SisoTimeTable)getView().findViewById(R.id.id_stt);
        id_stt.setOnTimeChangedListener(this);

        super.onResume();
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
        float salaryDay = salaryWeek / 7;
        float salaryMonth = 21 * salaryDay;


        Log.d(TAG, "calculateSalary: salaryCurrentIndex : "+id_pk_salary_hour.getCurrentIndex());
        Log.d(TAG, "calculateSalary: salaryDay : "+salaryDay);
        Log.d(TAG, "calculateSalary: salaryHour : "+selectedHour);
        Log.d(TAG, "calculateSalary: salaryvalue : "+selectedValue);
        // TODO 협의 예외처리
        id_tv_selected_hour.setText(String.valueOf(selectedHour));
        id_tv_salary_hour.setText(id_pk_salary_hour.getCurrentText());
        id_tv_salary_day.setText(String.format("%,.0f",salaryDay)+"원");
        id_tv_salary_day2.setText(String.format("%,.0f",salaryDay)+"원");
        id_tv_salary_week.setText(String.format("%,d",salaryWeek)+"원");
        id_tv_salary_week2.setText(String.format("%,d",salaryWeek)+"원");
        id_tv_salary_month.setText(String.format("%,.0f",salaryMonth)+"원");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
                saveData();
                executeSave();
                Sitter01IndexFragment fragment = new Sitter01IndexFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_basic_title);
                break;

        }
    }


    private void saveData() {
        mUser.sitterInfo.mon = id_stt.getBitString(SisoTimeTable.MON);
        mUser.sitterInfo.tue = id_stt.getBitString(SisoTimeTable.TUE);
        mUser.sitterInfo.wed = id_stt.getBitString(SisoTimeTable.WED);
        mUser.sitterInfo.thu = id_stt.getBitString(SisoTimeTable.THU);
        mUser.sitterInfo.fri = id_stt.getBitString(SisoTimeTable.FRI);
        mUser.sitterInfo.sat = id_stt.getBitString(SisoTimeTable.SAT);
        mUser.sitterInfo.sun = id_stt.getBitString(SisoTimeTable.SUN);

        String[] arrSalaryValues = getResources().getStringArray(R.array.picker_salary_value);
        String selectedValue = arrSalaryValues[id_pk_salary_hour.getCurrentIndex()];

        Log.d(TAG, "saveData: index : "+id_pk_salary_hour.getCurrentIndex());
        Log.d(TAG, "saveData: SALARAY : "+selectedValue);
        mUser.sitterInfo.salary = selectedValue;

        Log.d(TAG, "onClick: mUser.sitterInfo : "+mUser.sitterInfo.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void executeSave() {
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        Call<User> call = client.modify(mUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: success body : "+response.body().toString());
                    }

                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(getContext(), "["+msgCode+"] "+msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }


    /**
     * 시간표 시간 선택이 변경될 때 호출된다
     */
    @Override
    public void onTimeChanged() {
        calculateSalary();
    }

}
