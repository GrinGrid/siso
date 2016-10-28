package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.views.SisoAddChild;
import net.gringrid.siso.views.SisoToggleButton;

import java.util.HashMap;



/**
 * 구인정보입력 > 아이정보
 * A simple {@link Fragment} subclass.
 */
public class Parent02CldrnFragment extends Fragment implements SisoToggleButton.OnToggleChangedListener, SisoAddChild.OnChildFormRemoveListener, View.OnClickListener {

    private static final String TAG = "jiho";
    private static final int BOY = 0;
    private static final int GIRL = 1;
    private static final int NEWBORN = 2;

    SisoToggleButton id_tg_btn_boy;
    SisoToggleButton id_tg_btn_girl;
    SisoToggleButton id_tg_btn_newborn;
    LinearLayout id_ll_child;
    LinearLayout id_ll_newborn;
    LinearLayout id_ll_child_title;
    LinearLayout id_ll_newborn_title;
    private TextView id_tv_next_btn;


    int mChildAddCount;

    HashMap<Integer, SisoAddChild> mHashMap = new HashMap<>();

    public Parent02CldrnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent02_cldrn, container, false);
    }

    @Override
    public void onResume() {
        id_tg_btn_boy = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_boy);
        id_tg_btn_girl = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_girl);
        id_tg_btn_newborn = (SisoToggleButton)getView().findViewById(R.id.id_tg_btn_newborn);
        id_ll_child = (LinearLayout)getView().findViewById(R.id.id_ll_child);
        id_ll_newborn = (LinearLayout)getView().findViewById(R.id.id_ll_newborn);
        id_ll_child_title = (LinearLayout)getView().findViewById(R.id.id_ll_child_title);
        id_ll_newborn_title = (LinearLayout)getView().findViewById(R.id.id_ll_newborn_title);

        id_tg_btn_boy.setOnToggleChangedListener(this);
        id_tg_btn_girl.setOnToggleChangedListener(this);
        id_tg_btn_newborn.setOnToggleChangedListener(this);

        id_tv_next_btn = (TextView)getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        super.onResume();
    }

    private void addChild(int child) {
        SisoAddChild sisoAddChild = new SisoAddChild(getContext());
        sisoAddChild.setOnChildFormRevoeListener(this);
        sisoAddChild.setTag(mChildAddCount);

        switch (child){
            case BOY:
                sisoAddChild.setIconResource(R.drawable.ic_boy_small);
                id_ll_child_title.setVisibility(View.VISIBLE);
                id_ll_child.addView(sisoAddChild);
                break;
            case GIRL:
                sisoAddChild.setIconResource(R.drawable.ic_girl_small);
                id_ll_child_title.setVisibility(View.VISIBLE);
                id_ll_child.addView(sisoAddChild);
                break;
            case NEWBORN:
                sisoAddChild.setIconResource(R.drawable.ic_newborn_small);
                id_ll_newborn_title.setVisibility(View.VISIBLE);
                id_ll_newborn.addView(sisoAddChild);
                break;
        }
        mHashMap.put(mChildAddCount++, sisoAddChild);
    }

    @Override
    public void onChanged(View view) {
        switch (view.getId()){
            case R.id.id_tg_btn_boy:
                addChild(BOY);
                break;
            case R.id.id_tg_btn_girl:
                addChild(GIRL);
                break;
            case R.id.id_tg_btn_newborn:
                addChild(NEWBORN);
                break;
        }
    }

    @Override
    public void onRemoved(int index) {
        if(id_ll_child.getChildCount() == 0){
            id_ll_child_title.setVisibility(View.GONE);
        }
        if(id_ll_newborn.getChildCount() == 0){
            id_ll_newborn_title.setVisibility(View.GONE);
        }
        mHashMap.remove(index);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                // TODO 입력항목 체크
//                saveData();
//                executeSave();
                Parent03AgeFragment fragment = new Parent03AgeFragment();
                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_basic_title);
                break;

        }
    }
}
