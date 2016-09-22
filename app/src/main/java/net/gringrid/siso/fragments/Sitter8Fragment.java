package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import net.gringrid.siso.R;
import net.gringrid.siso.views.SisoEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter8Fragment extends Fragment implements View.OnClickListener {

    private ToggleButton id_tg_nat_kor;
    private ToggleButton id_tg_nat_chi_kor;
    private ToggleButton id_tg_nat_write;
    private ToggleButton id_tg_rlg_0;
    private ToggleButton id_tg_rlg_1;
    private ToggleButton id_tg_rlg_2;
    private ToggleButton id_tg_rlg_3;
    private ToggleButton id_tg_rlg_4;
    private SisoEditText id_et_nat;
    private SisoEditText id_et_rlg;

    int mRadioNat[] = new int[]{
            R.id.id_tg_nat_kor,
            R.id.id_tg_nat_chi_kor,
            R.id.id_tg_nat_write
    };

    int mRadioRlg[] = new int[]{
            R.id.id_tg_rlg_0,
            R.id.id_tg_rlg_1,
            R.id.id_tg_rlg_2,
            R.id.id_tg_rlg_3,
            R.id.id_tg_rlg_4
    };

    public Sitter8Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter8, container, false);
    }

    @Override
    public void onResume() {
        id_tg_nat_kor = (ToggleButton)getView().findViewById(R.id.id_tg_nat_kor);
        id_tg_nat_chi_kor = (ToggleButton)getView().findViewById(R.id.id_tg_nat_chi_kor);
        id_tg_nat_write = (ToggleButton)getView().findViewById(R.id.id_tg_nat_write);
        id_tg_nat_kor.setOnClickListener(this);
        id_tg_nat_chi_kor.setOnClickListener(this);
        id_tg_nat_write.setOnClickListener(this);

        id_tg_rlg_0 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_0);
        id_tg_rlg_1 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_1);
        id_tg_rlg_2 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_2);
        id_tg_rlg_3 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_3);
        id_tg_rlg_4 = (ToggleButton)getView().findViewById(R.id.id_tg_rlg_4);
        id_tg_rlg_0.setOnClickListener(this);
        id_tg_rlg_1.setOnClickListener(this);
        id_tg_rlg_2.setOnClickListener(this);
        id_tg_rlg_3.setOnClickListener(this);
        id_tg_rlg_4.setOnClickListener(this);

        id_et_nat = (SisoEditText) getView().findViewById(R.id.id_et_nat);
        id_et_rlg = (SisoEditText) getView().findViewById(R.id.id_et_rlg);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
//            case R.id.id_tv_next_btn:
//                Sitter3Fragment fragment = new Sitter3Fragment();
//                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
//                break;

            // 국적 Radio 선택
            case R.id.id_tg_nat_kor:
            case R.id.id_tg_nat_chi_kor:
                selectRadio(mRadioNat, viewId);
                id_et_nat.setVisibility(View.GONE);
                break;
            case R.id.id_tg_nat_write:
                selectRadio(mRadioNat, R.id.id_tg_nat_write);
                id_et_nat.setVisibility(View.VISIBLE);
                break;

            // 종교 Radio 선택
            case R.id.id_tg_rlg_0:
            case R.id.id_tg_rlg_1:
            case R.id.id_tg_rlg_2:
            case R.id.id_tg_rlg_3:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.GONE);
                break;
            case R.id.id_tg_rlg_4:
                selectRadio(mRadioRlg, viewId);
                id_et_rlg.setVisibility(View.VISIBLE);
                break;
        }
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
}
