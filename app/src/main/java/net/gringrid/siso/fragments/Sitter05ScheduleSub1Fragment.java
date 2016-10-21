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
import net.gringrid.siso.views.SisoToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 기본정보 > 출퇴근방법 출퇴근/재택형 스케줄, 시급
 * A simple {@link Fragment} subclass.
 */
public class Sitter05ScheduleSub1Fragment extends Fragment implements SisoPicker.OnClickPlusMinusListener, View.OnClickListener {

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

    private TextView id_tv_mon;
    private TextView id_tv_tue;
    private TextView id_tv_wed;
    private TextView id_tv_thu;
    private TextView id_tv_fri;
    private TextView id_tv_sat;
    private TextView id_tv_sun;

    private TextView id_tv_time1;
    private TextView id_tv_time2;
    private TextView id_tv_time3;
    private TextView id_tv_time4;
    private TextView id_tv_time5;
    private TextView id_tv_time6;
    private TextView id_tv_time7;

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

        id_tv_mon = (TextView)getView().findViewById(R.id.id_tv_mon);
        id_tv_tue = (TextView)getView().findViewById(R.id.id_tv_tue);
        id_tv_wed = (TextView)getView().findViewById(R.id.id_tv_wed);
        id_tv_thu = (TextView)getView().findViewById(R.id.id_tv_thu);
        id_tv_fri = (TextView)getView().findViewById(R.id.id_tv_fri);
        id_tv_sat = (TextView)getView().findViewById(R.id.id_tv_sat);
        id_tv_sun = (TextView)getView().findViewById(R.id.id_tv_sun);
        id_tv_time1 = (TextView)getView().findViewById(R.id.id_tv_time1);
        id_tv_time2 = (TextView)getView().findViewById(R.id.id_tv_time2);
        id_tv_time3 = (TextView)getView().findViewById(R.id.id_tv_time3);
        id_tv_time4 = (TextView)getView().findViewById(R.id.id_tv_time4);
        id_tv_time5 = (TextView)getView().findViewById(R.id.id_tv_time5);
        id_tv_time6 = (TextView)getView().findViewById(R.id.id_tv_time6);
        id_tv_time7 = (TextView)getView().findViewById(R.id.id_tv_time7);

        id_tv_mon.setOnClickListener(this);
        id_tv_tue.setOnClickListener(this);
        id_tv_wed.setOnClickListener(this);
        id_tv_thu.setOnClickListener(this);
        id_tv_fri.setOnClickListener(this);
        id_tv_sat.setOnClickListener(this);
        id_tv_sun.setOnClickListener(this);
        id_tv_time1.setOnClickListener(this);
        id_tv_time2.setOnClickListener(this);
        id_tv_time3.setOnClickListener(this);
        id_tv_time4.setOnClickListener(this);
        id_tv_time5.setOnClickListener(this);
        id_tv_time6.setOnClickListener(this);
        id_tv_time7.setOnClickListener(this);

        setToggleChangedListener();
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
        int[] arrSalaryValues = getResources().getIntArray(R.array.picker_salary_value);
        int selectedValue = arrSalaryValues[id_pk_salary_hour.getCurrentIndex()];
        int selectedHour = getSelectedHour();
        int salaryWeek = selectedHour * selectedValue;
        float salaryDay = salaryWeek / 7;
        float salaryMonth = 21 * salaryDay;

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

            case R.id.id_tv_mon:
                checkGroup(GROUP_COL, 0);
                break;
            case R.id.id_tv_tue:
                checkGroup(GROUP_COL, 1);
                break;
            case R.id.id_tv_wed:
                checkGroup(GROUP_COL, 2);
                break;
            case R.id.id_tv_thu:
                checkGroup(GROUP_COL, 3);
                break;
            case R.id.id_tv_fri:
                checkGroup(GROUP_COL, 4);
                break;
            case R.id.id_tv_sat:
                checkGroup(GROUP_COL, 5);
                break;
            case R.id.id_tv_sun:
                checkGroup(GROUP_COL, 6);
                break;
            case R.id.id_tv_time1:
                checkGroup(GROUP_ROW, 0);
                break;
            case R.id.id_tv_time2:
                checkGroup(GROUP_ROW, 1);
                break;
            case R.id.id_tv_time3:
                checkGroup(GROUP_ROW, 2);
                break;
            case R.id.id_tv_time4:
                checkGroup(GROUP_ROW, 3);
                break;
            case R.id.id_tv_time5:
                checkGroup(GROUP_ROW, 4);
                break;
            case R.id.id_tv_time6:
                checkGroup(GROUP_ROW, 5);
                break;
            case R.id.id_tv_time7:
                checkGroup(GROUP_ROW, 6);
                break;
        }
    }

    private void checkGroup(int groupType, int groupNum){
        String tg_id="";
        SisoToggleButton tmpTgBtn;
        boolean isCheckedGroup = isCheckedGroup(groupType, groupNum);
        for(int i=0; i<7; i++){
            if(groupType == GROUP_COL){
                tg_id = "id_tg_"+i+groupNum;
            }else if(groupType == GROUP_ROW){
                tg_id = "id_tg_"+groupNum+i;
            }
            tmpTgBtn = getToggleButtonByStr(tg_id);
            tmpTgBtn.setChecked(!isCheckedGroup);
        }
    }

    private boolean isCheckedGroup(int groupType, int groupNum){
        String tg_id="";
        SisoToggleButton tmpTgBtn;
        boolean isAllChecked=true;

        for(int i=0; i<7; i++){
            if(groupType == GROUP_COL){
                tg_id = "id_tg_"+i+groupNum;
            }else if(groupType == GROUP_ROW){
                tg_id = "id_tg_"+groupNum+i;
            }
            tmpTgBtn = getToggleButtonByStr(tg_id);
            if(!tmpTgBtn.isChecked()) return false;
        }
        return isAllChecked;
    }

    private void setToggleChangedListener(){
        String tg_id;
        SisoToggleButton tmpTgBtn;

        for(int i=0; i<GROUP_COL_LEN; i++){
            for(int j=0; j<GROUP_ROW_LEN; j++) {
                tg_id = "id_tg_"+i+j;
                tmpTgBtn = getToggleButtonByStr(tg_id);
                tmpTgBtn.setOnToggleChangedListener(new SisoToggleButton.OnToggleChangedListener() {
                    @Override
                    public void onChanged(View view) {
                        calculateSalary();
                    }
                });
            }
        }
    }

    private int getSelectedHour(){
        String tg_id;
        SisoToggleButton tmpTgBtn;

        int totalHour = 0;
        for(int i=0; i<GROUP_COL_LEN; i++){
            for(int j=0; j<GROUP_ROW_LEN; j++) {
                tg_id = "id_tg_"+i+j;
                tmpTgBtn = getToggleButtonByStr(tg_id);
                if(tmpTgBtn.isChecked()){
                    if(i==6){
                        totalHour += 6;
                    }else{
                        totalHour+=3;
                    }
                }
            }
        }
        return totalHour;
    }

    private SisoToggleButton getToggleButtonByStr(String id){
        String packageName = getActivity().getPackageName();
        int viewId = getResources().getIdentifier(id, "id", packageName);
        return (SisoToggleButton)getView().findViewById(viewId);
    }

    private void saveData() {

        String tg_id;
        SisoToggleButton tmpTgBtn;
        String result = "";

        for(int i=0; i<GROUP_COL_LEN; i++){
            for(int j=0; j<GROUP_ROW_LEN; j++) {
                tg_id = "id_tg_"+j+i;
                tmpTgBtn = getToggleButtonByStr(tg_id);
                result += tmpTgBtn.isChecked()?"1":"0";
            }
        }

        mUser.sitterInfo.mon = result.substring(7*0, 7*0+7);
        mUser.sitterInfo.tue = result.substring(7*1, 7*1+7);
        mUser.sitterInfo.wed = result.substring(7*2, 7*2+7);
        mUser.sitterInfo.thu = result.substring(7*3, 7*3+7);
        mUser.sitterInfo.fri = result.substring(7*4, 7*4+6);
        mUser.sitterInfo.sat = result.substring(7*5, 7*5+7);
        mUser.sitterInfo.sun = result.substring(7*6, 7*6+7);

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
}
