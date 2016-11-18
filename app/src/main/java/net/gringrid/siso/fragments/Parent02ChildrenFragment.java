package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Child;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoAddChild;
import net.gringrid.siso.views.SisoToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 구인정보입력 > 아이정보
 */
public class Parent02ChildrenFragment extends InputBaseFragment implements SisoToggleButton.OnToggleChangedListener,
        SisoAddChild.OnChildFormRemoveListener{

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

    public Parent02ChildrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent02_children, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tg_btn_boy = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_boy);
        id_tg_btn_girl = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_girl);
        id_tg_btn_newborn = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_newborn);
        id_ll_child = (LinearLayout)view.findViewById(R.id.id_ll_child);
        id_ll_newborn = (LinearLayout)view.findViewById(R.id.id_ll_newborn);
        id_ll_child_title = (LinearLayout)view.findViewById(R.id.id_ll_child_title);
        id_ll_newborn_title = (LinearLayout)view.findViewById(R.id.id_ll_newborn_title);

        id_tg_btn_boy.setOnToggleChangedListener(this);
        id_tg_btn_girl.setOnToggleChangedListener(this);
        id_tg_btn_newborn.setOnToggleChangedListener(this);

        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void addChildWithData(int childType, Child data){
        Log.d(TAG, "addChildWithData: "+childType);
        SisoAddChild sisoAddChild = new SisoAddChild(getContext(), data);
        sisoAddChild.setOnChildFormRevoeListener(this);
        sisoAddChild.setTag(mChildAddCount);

        switch (childType){
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
        Log.d(TAG, "addChildWithData: mChildAddCount : "+mChildAddCount);
        mHashMap.put(mChildAddCount++, sisoAddChild);
    }

    private void addChild(int childType) {
        addChildWithData(childType, null);
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
        Log.d(TAG, "onRemoved: mHashMap size : "+mHashMap.size());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;
                saveData();
                moveNext();
                break;
        }
    }

    @Override
    protected void loadData() {
        if(mUser.parentInfo.children_info != null && mUser.parentInfo.children_info.size() > 0){
            for(Child child : mUser.parentInfo.children_info){
                addChildWithData(Integer.parseInt(child.gender), child);
            }
        }
    }

    @Override
    protected boolean isValidInput() {
        if(mHashMap == null || mHashMap.size()==0){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_children_write);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        List<Child> listChild = new ArrayList<>();
        Child child;
        for(Map.Entry<Integer, SisoAddChild> entry : mHashMap.entrySet()){
            child = entry.getValue().getData();
            listChild.add(child);
        }
        mUser.parentInfo.children_info = listChild;
        Log.d(TAG, "saveData: mUser : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Parent02EnvRlgFragment fragment = new Parent02EnvRlgFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
    }
}
