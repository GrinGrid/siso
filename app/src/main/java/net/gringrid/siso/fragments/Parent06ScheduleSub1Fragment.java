package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gringrid.siso.R;

/**
 * 구인정보 > 출퇴근유형 시간제, 재택형
 * A simple {@link Fragment} subclass.
 */
public class Parent06ScheduleSub1Fragment extends Fragment {


    public Parent06ScheduleSub1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent06_schedule_sub1, container, false);
    }

}
