package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gringrid.siso.R;

/**
 * 구인정보입력 > 시터연령, 시터필요기간
 */
public class Parent03AgeFragment extends Fragment {


    public Parent03AgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent03_age, container, false);
    }

}
