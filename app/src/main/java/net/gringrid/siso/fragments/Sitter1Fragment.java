package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.util.SharedData;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter1Fragment extends Fragment implements View.OnClickListener {


    TextView id_tv_next_btn;

    public Sitter1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter1, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Sitter2Fragment fragment = new Sitter2Fragment();

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;

        }

    }
}
