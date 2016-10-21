package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoCheckBox;


/**
 * 구직정보 등록 > 인덱스
 * A simple {@link Fragment} subclass.
 */
public class Sitter01IndexFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    SisoCheckBox id_cb_basic;
    SisoCheckBox id_cb_work_env;
    SisoCheckBox id_cb_introduce;
    SisoCheckBox id_cb_photo;

    User mUser;
    TextView id_tv_next_btn;

    public Sitter01IndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUser = SharedData.getInstance(getContext()).getUserData();
        Log.d(TAG, "onCreate: fragment1 mUser.sitterInfo : "+mUser.toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sitter1, container, false);
        Log.d(TAG, "onCreateView: fragment1");
        // Inflate the layout for this fragment
        id_cb_basic = (SisoCheckBox)view.findViewById(R.id.id_cb_basic);
        id_cb_work_env = (SisoCheckBox)view.findViewById(R.id.id_cb_work_env);
        id_cb_introduce = (SisoCheckBox)view.findViewById(R.id.id_cb_introduce);
        id_cb_photo = (SisoCheckBox)view.findViewById(R.id.id_cb_photo);

        id_cb_basic.setOnClickListener(this);
        id_cb_work_env.setOnClickListener(this);
        id_cb_introduce.setOnClickListener(this);
        id_cb_photo.setOnClickListener(this);

        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        if (mUser==null) return;
        id_cb_basic.setCheck(isBasicInfoInsert());
        id_cb_work_env.setCheck(isWishInfoInsert());
        id_cb_introduce.setCheck(isIntroduceInfoInsert());
        id_cb_photo.setCheck(isPhotoInfoInsert());

        if(id_cb_basic.isChecked() &&
                id_cb_work_env.isChecked() &&
                id_cb_introduce.isChecked() &&
                id_cb_photo.isChecked()){
           id_tv_next_btn.setVisibility(View.VISIBLE);
        }else{
            id_tv_next_btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tv_next_btn:

                Bundle bundle = new Bundle();
                bundle.putString(SharedData.USER, SharedData.getInstance(getContext()).getGlobalDataString(SharedData.USER));

                SitterDetailFragment fragment = new SitterDetailFragment();
                fragment.setArguments(bundle);
                ((BaseActivity) getActivity()).setFragment(fragment, Integer.MIN_VALUE);
                //((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
                break;

            case R.id.id_cb_basic:
                Log.d(TAG, "onClick: id_cb_basic");
                Sitter02GenderFragment sitter02GenderFragment = new Sitter02GenderFragment();
                ((BaseActivity) getActivity()).setFragment(sitter02GenderFragment, R.string.sitter_basic_title);
                break;

            case R.id.id_cb_work_env:
                Log.d(TAG, "onClick: id_work_env");
                Sitter06EnvFragment sitter06EnvFragment = new Sitter06EnvFragment();
                ((BaseActivity) getActivity()).setFragment(sitter06EnvFragment, R.string.sitter_wish_title);
                break;

            case R.id.id_cb_introduce:
                Sitter09IntroductionFragment sitter09IntroductionFragment = new Sitter09IntroductionFragment();
                ((BaseActivity) getActivity()).setFragment(sitter09IntroductionFragment, R.string.sitter_introduce_title);
                break;

            case R.id.id_cb_photo:
                Sitter11PhotoFragment sitter11PhotoFragment = new Sitter11PhotoFragment();
                ((BaseActivity) getActivity()).setFragment(sitter11PhotoFragment, R.string.sitter_photo_title);
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

        if(TextUtils.isEmpty(mUser.sitterInfo.gender)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.commute_type)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.term_from)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.term_to)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.mon)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.tue)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.wed)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.thu)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.fri)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.sat)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.sun)) return false;
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
        if(TextUtils.isEmpty(mUser.sitterInfo.nat)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.religion)) return false;
        return true;
    }

    private boolean isIntroduceInfoInsert(){
        // 자기소개 한줄
        // 자기소개
        if(TextUtils.isEmpty(mUser.sitterInfo.brief)) return false;
        if(TextUtils.isEmpty(mUser.sitterInfo.introduction)) return false;
        return true;
    }

    private boolean isPhotoInfoInsert(){
        // 사진
        if(TextUtils.isEmpty(mUser.imageInfo.prf_img_url)) return false;
        return true;
    }
}
