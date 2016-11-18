package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoModifyItemRow;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberModifyFragment extends InputBaseFragment{

    private static final String NULL = "NULL";
    private SisoModifyItemRow id_smir_name;
    private User mUser;
    private String mUserType;

    private int[] mModifyItems = {
            R.id.id_smir_name,
            R.id.id_smir_user_type,
            R.id.id_smir_email,
            R.id.id_smir_birth,
            R.id.id_smir_phone,
            R.id.id_smir_passwd,
            R.id.id_smir_addr,
            R.id.id_smir_work_exp,
            R.id.id_smir_work_period,
            R.id.id_smir_commute_type,
            R.id.id_smir_commute_distance,
            R.id.id_smir_schedule,
            R.id.id_smir_salary,
            R.id.id_smir_work_env,
            R.id.id_smir_baby_gender,
            R.id.id_smir_baby_age,
            R.id.id_smir_gender,
            R.id.id_smir_children_num,
            R.id.id_smir_skill,
            R.id.id_smir_nationality,
            R.id.id_smir_religion,
            R.id.id_smir_education,
            R.id.id_smir_license,
            R.id.id_smir_brief,
            R.id.id_smir_introduction,
            R.id.id_smir_profile_photo
    };

    private boolean[] mIsModifyEnable = {
            false,
            false,
            false,
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true
    };

    public MemberModifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mUser = SharedData.getInstance(getContext()).getUserData();
        mUserType = mUser.personalInfo.user_type;
        return inflater.inflate(R.layout.fragment_member_modify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_smir_name = (SisoModifyItemRow)view.findViewById(R.id.id_smir_name);
        id_smir_name.setContent("name content");
        SisoUtil.setArrayClickListener(mModifyItems, view, this);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setModifyData(String[] values){
        for(int i=0; i<mModifyItems.length; i++){
            SisoModifyItemRow item = (SisoModifyItemRow)getView().findViewById(mModifyItems[i]);
            item.setContent(values[i]);
        }
    }

    @Override
    protected void loadData() {
        String[] modifyItemsValue = {
                mUser.personalInfo.name,
                mUser.personalInfo.user_type,
                mUser.personalInfo.email,
                mUser.personalInfo.birth_date,
                mUser.personalInfo.phone,
                mUser.personalInfo.passwd,
                mUser.personalInfo.addr1,
                mUser.sitterInfo.work_exp,
                mUser.sitterInfo.term_from+"~"+mUser.sitterInfo.term_to,
                mUser.sitterInfo.commute_type,
                mUser.sitterInfo.distance_limit,
                NULL,
                mUser.sitterInfo.salary,
                NULL,
                NULL,
                NULL,
                mUser.sitterInfo.gender,
                mUser.sitterInfo.baby_boy,
                mUser.sitterInfo.skill,
                mUser.sitterInfo.nat,
                mUser.sitterInfo.religion,
                mUser.sitterInfo.edu,
                mUser.sitterInfo.license,
                mUser.sitterInfo.brief,
                mUser.sitterInfo.introduction,
                NULL
        };
        setModifyData(modifyItemsValue);
    }

    @Override
    protected boolean isValidInput() {
        return false;
    }

    @Override
    protected void saveData() {

    }

    @Override
    protected void moveNext() {

    }



    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.id_smir_name:
            case R.id.id_smir_birth:
                fragment = new Member02NameBirthFragment();
                break;
            case R.id.id_smir_passwd:
                fragment = new Member03EmailFragment();
                break;
            case R.id.id_smir_phone:
                fragment = new Member04PhoneFragment();
                break;
            case R.id.id_smir_addr:
                fragment = new Member05AddrFragment();
                break;
            case R.id.id_smir_work_exp:
            case R.id.id_smir_work_period:
                fragment = new Sitter01ExpPeriodFragment();
                break;
            case R.id.id_smir_commute_type:
            case R.id.id_smir_commute_distance:
                fragment = new CommonCommuteFragment();
                break;
            case R.id.id_smir_schedule:
            case R.id.id_smir_salary:
                fragment = new CommonScheduleHourlyFragment();
                if(mUserType.equals(User.USER_TYPE_SITTER)){
                   if(mUser.sitterInfo.commute_type.equals(User.COMMUTE_TYPE_REGIDENT)){
                       fragment = new CommonScheduleSalaryFragment();
                   }
                }else if(mUserType.equals(User.USER_TYPE_PARENT)){
                    if(mUser.parentInfo.commute_type.equals(User.COMMUTE_TYPE_REGIDENT)){
                        fragment = new CommonScheduleSalaryFragment();
                    }
                }
                break;
            case R.id.id_smir_work_env:
                if(mUserType.equals(User.USER_TYPE_SITTER)){
                    fragment = new Sitter02EnvBabygenderFragment();
                }else if(mUserType.equals(User.USER_TYPE_SITTER)){
                    fragment = new Parent02EnvRlgFragment();
                }
                break;

            case R.id.id_smir_baby_gender:
                fragment = new Sitter02EnvBabygenderFragment();
                break;

            case R.id.id_smir_baby_age:
                fragment = new Sitter02BabyageFragment();
                break;
        }
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.member_title);

    }
}
