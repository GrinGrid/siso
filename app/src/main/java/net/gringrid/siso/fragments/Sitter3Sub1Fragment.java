package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;




/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter3Sub1Fragment extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {


    private static final String TAG = "jiho";
    User mUser;

    private TextView id_tv_next_btn;
    private CalendarView id_cv;
    private ToggleButton id_tg_from;
    private ToggleButton id_tg_to;

    public Sitter3Sub1Fragment() {
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
        return inflater.inflate(R.layout.fragment_sitter3_sub1, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tg_from = (ToggleButton) getView().findViewById(R.id.id_tg_from);
        id_tg_to = (ToggleButton) getView().findViewById(R.id.id_tg_to);
        id_tg_from.setOnClickListener(this);
        id_tg_to.setOnClickListener(this);
        id_cv = (CalendarView) getView().findViewById(R.id.id_cv);
        id_cv.setOnDateChangeListener(this);
        id_cv.setVisibility(View.GONE);

//        loadData();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch (v.getId()){
            case R.id.id_tg_from:
            case R.id.id_tg_to:
                id_cv.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        String yearStr = String.format("%d",year);
        String monthStr = String.format("%02d",month);
        String dayStr = String.format("%02d",dayOfMonth);
        String dateStr = yearStr+"-"+monthStr+"-"+dayStr;

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
}
