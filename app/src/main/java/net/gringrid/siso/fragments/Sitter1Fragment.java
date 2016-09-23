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
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_work_env;
    SisoCheckBox id_cb_introduce;

    TextView id_tv_next_btn;

    public Sitter1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: fragment1");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: fragment1");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter1, container, false);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: fragment1");

        id_cb_basic = (SisoCheckBox)getView().findViewById(R.id.id_cb_basic);
        id_cb_work_env = (SisoCheckBox)getView().findViewById(R.id.id_cb_work_env);
        id_cb_introduce = (SisoCheckBox)getView().findViewById(R.id.id_cb_introduce);

        id_cb_basic.setOnClickListener(this);
        id_cb_work_env.setOnClickListener(this);
        id_cb_introduce.setOnClickListener(this);

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

            case R.id.id_cb_basic:
                Log.d(TAG, "onClick: id_cb_basic");
                break;

            case R.id.id_cb_work_env:
                Log.d(TAG, "onClick: id_cb_env");
                break;

            case R.id.id_cb_introduce:
                Log.d(TAG, "onClick: id_cb_env");
                break;
        }

    }
}
