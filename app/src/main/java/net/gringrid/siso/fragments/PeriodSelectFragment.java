package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;




/**
 * A simple {@link Fragment} subclass.
 */
public class PeriodSelectFragment extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {


    private static final String TAG = "jiho";
    User mUser;
    private String mUserType;

    private TextView id_tv_next_btn;
    private CalendarView id_cv;
    private FrameLayout id_fl_cv;
    private ToggleButton id_tg_start_0;
    private ToggleButton id_tg_start_1;
    private ToggleButton id_tg_end_0;
    private ToggleButton id_tg_end_1;
    private TextView id_tv_start;
    private TextView id_tv_end;

    int mRadioStart[] = new int[]{
            R.id.id_tg_start_0,
            R.id.id_tg_start_1
    };
    int mRadioEnd[] = new int[]{
            R.id.id_tg_end_0,
            R.id.id_tg_end_1
    };

    int mLastClickedBtn;

    public PeriodSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        if(getArguments()!=null) {
            mUserType = getArguments().getString(User.USER_TYPE);
            Log.d(TAG, "PeriodSelectFragment.onCreateView: userType = "+mUserType);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter3_sub1, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tv_start = (TextView) getView().findViewById(R.id.id_tv_start);
        id_tv_end = (TextView) getView().findViewById(R.id.id_tv_end);
        id_tg_start_0 = (ToggleButton) getView().findViewById(R.id.id_tg_start_0);
        id_tg_start_1 = (ToggleButton) getView().findViewById(R.id.id_tg_start_1);
        id_tg_end_0 = (ToggleButton) getView().findViewById(R.id.id_tg_end_0);
        id_tg_end_1 = (ToggleButton) getView().findViewById(R.id.id_tg_end_1);

        id_tg_start_0.setOnClickListener(this);
        id_tg_start_1.setOnClickListener(this);
        id_tg_end_0.setOnClickListener(this);
        id_tg_end_1.setOnClickListener(this);
        id_fl_cv = (FrameLayout)getView().findViewById(R.id.id_fl_cv);
        id_cv = (CalendarView) getView().findViewById(R.id.id_cv);
        id_cv.setOnDateChangeListener(this);

//        loadData();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        int viewId = v.getId();
        switch (v.getId()){
            case R.id.id_tg_start_0:
            case R.id.id_tg_start_1:
                selectRadio(mRadioStart, viewId);
                setCalendarVisible(viewId);
                setStartText(viewId, "");
                mLastClickedBtn=viewId;
                break;

            case R.id.id_tg_end_0:
            case R.id.id_tg_end_1:
                selectRadio(mRadioEnd, viewId);
                setCalendarVisible(viewId);
                setEndText(viewId, "");
                mLastClickedBtn=viewId;
                break;

            case R.id.id_tv_next_btn:
                saveData();
                if(mUserType.equals(User.USER_TYPE_PARENT)){
                    Parent06CommuteFragment fragment = new Parent06CommuteFragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);

                }else if(mUserType.equals(User.USER_TYPE_SITTER)){
                    Sitter04SkillFragment fragment = new Sitter04SkillFragment();
                    ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);

                }
                break;
        }
    }

    private void saveData() {
    }

    private void setEndText(int viewId, String date) {
        id_tv_end.setGravity(Gravity.LEFT);
        if(viewId==R.id.id_tg_end_0){
            id_tv_end.setText("종료일 : 상관없음");
        }else{
            id_tv_end.setText("종료일 : "+date);
        }
    }

    private void setStartText(int viewId, String date) {
        id_tv_start.setGravity(Gravity.LEFT);
        if(viewId==R.id.id_tg_start_0){
            Log.d(TAG, "setStartText: id_tg_start_0");
            id_tv_start.setText("시작일 : 상관없음");
        }else if(viewId == R.id.id_tg_start_1){
            Log.d(TAG, "setStartText: id_tg_start_1");
            id_tv_start.setText("시작일 : "+date);
        }
    }

    /**
     * 시작일이나 종료일 지정을 선택 한 경우 달력 보이도록 설정
     * @param viewId
     */
    private void setCalendarVisible(int viewId) {
        if(viewId==R.id.id_tg_start_1 || viewId==R.id.id_tg_end_1){
            id_fl_cv.setVisibility(View.VISIBLE);
        }else{
            id_fl_cv.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        String yearStr = String.format("%d",year);
        String monthStr = String.format("%02d",month+1);
        String dayStr = String.format("%02d",dayOfMonth);
        String dateStr = yearStr+"-"+monthStr+"-"+dayStr;
        if(mLastClickedBtn == R.id.id_tg_start_1){
            setStartText(R.id.id_tg_start_1, dateStr);
        }
        if(mLastClickedBtn == R.id.id_tg_end_1){
            setEndText(R.id.id_tg_end_1, dateStr);
        }
//        if(id_tg_from.isChecked()){
//            id_tg_from.setText("시작일\n"+dateStr);
//            id_tg_from.setTextOn("시작일\n"+dateStr);
//            id_tg_from.setTextOff("시작일\n"+dateStr);
//        }else if(id_tg_to.isChecked()){
//            id_tg_to.setText("종료일\n"+dateStr);
//            id_tg_to.setTextOn("종료일\n"+dateStr);
//            id_tg_to.setTextOff("종료일\n"+dateStr);
//        }

        Log.d(TAG, "onSelectedDayChange: onSelectedDayChange +");
        Log.d(TAG, "onSelectedDayChange: year : "+year);
        Log.d(TAG, "onSelectedDayChange: month : "+month);
        Log.d(TAG, "onSelectedDayChange: day : "+dayOfMonth);

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
