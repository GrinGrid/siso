package net.gringrid.siso.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_work_env;
    SisoCheckBox id_cb_introduce;
    SisoCheckBox id_cb_photo;

    Sitter mSitter;
    TextView id_tv_next_btn;

    public Sitter1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSitter = SharedData.getInstance(getContext()).getSitterData();
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
        id_cb_photo = (SisoCheckBox)getView().findViewById(R.id.id_cb_photo);

        id_cb_basic.setOnClickListener(this);
        id_cb_work_env.setOnClickListener(this);
        id_cb_introduce.setOnClickListener(this);
        id_cb_photo.setOnClickListener(this);

        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        loadData();

        super.onResume();
    }

    private void loadData() {
        Log.d(TAG, "loadData: isBasicInfoInsert : "+isBasicInfoInsert());
        id_cb_basic.setCheck(isBasicInfoInsert());
        id_cb_work_env.setCheck(isWishInfoInsert());
        id_cb_introduce.setCheck(isIntroduceInfoInsert());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                //((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;

            case R.id.id_cb_basic:
                Log.d(TAG, "onClick: id_cb_basic");
                Sitter2Fragment sitter2Fragment = new Sitter2Fragment();
                ((BaseActivity) getActivity()).setFragment(sitter2Fragment, R.string.sitter_basic_title);
                break;

            case R.id.id_cb_work_env:
                Log.d(TAG, "onClick: id_work_env");
                Sitter6Fragment sitter6Fragment = new Sitter6Fragment();
                ((BaseActivity) getActivity()).setFragment(sitter6Fragment, R.string.sitter_wish_title);
                break;

            case R.id.id_cb_introduce:
                Sitter9Fragment sitter9Fragment = new Sitter9Fragment();
                ((BaseActivity) getActivity()).setFragment(sitter9Fragment, R.string.sitter_introduce_title);
                break;

            case R.id.id_cb_photo:
                Sitter11Fragment sitter11Fragment = new Sitter11Fragment();
                ((BaseActivity) getActivity()).setFragment(sitter11Fragment, R.string.sitter_photo_title);
                break;
        }
    }

    private boolean isBasicInfoInsert(){
        // *성별
        // 자녀정보
        // 시터경력
        // *근무기간
        // 아이돌봄특기
        // *출퇴근방법
        // 출퇴근 가능거리
        // *근무가능시간
        // 희망시급
        // 희망월급

        if(mSitter.gender == Integer.MAX_VALUE) return false;
        if(mSitter.commute_type == Integer.MAX_VALUE) return false;
        if(TextUtils.isEmpty(mSitter.term_from)) return false;
        if(TextUtils.isEmpty(mSitter.term_to)) return false;
        if(TextUtils.isEmpty(mSitter.mon)) return false;
        if(TextUtils.isEmpty(mSitter.tue)) return false;
        if(TextUtils.isEmpty(mSitter.wed)) return false;
        if(TextUtils.isEmpty(mSitter.thu)) return false;
        if(TextUtils.isEmpty(mSitter.fri)) return false;
        if(TextUtils.isEmpty(mSitter.sat)) return false;
        if(TextUtils.isEmpty(mSitter.sun)) return false;
        return true;
    }

    private boolean isWishInfoInsert(){
        // 근무조건 - 애완견
        // 근무조건 - CCTV
        // 근무조건 - 다른어른
        // 아이성별
        // 아이연령대
        // *국적
        // *종교
        if(TextUtils.isEmpty(mSitter.nat)) return false;
        if(TextUtils.isEmpty(mSitter.religion)) return false;
        return true;
    }

    private boolean isIntroduceInfoInsert(){
        // 자기소개 한줄
        // 자기소개
        if(TextUtils.isEmpty(mSitter.brief)) return false;
        if(TextUtils.isEmpty(mSitter.introduction)) return false;
        return true;
    }

    private boolean isPhotoInfoInsert(){
        // 사진
        if(TextUtils.isEmpty(mSitter.sun)) return false;
        return true;
    }
}
