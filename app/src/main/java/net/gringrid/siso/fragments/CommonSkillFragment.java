package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoToggleButton;

/**
 * 구직 / 구인정보 입력 공통 > 특기설정
 */
public class CommonSkillFragment extends InputBaseFragment {


    private int[] mSkillAry = new int[]{
            R.id.id_tg_btn_care,
            R.id.id_tg_btn_baby,
            R.id.id_tg_btn_outdoor,
            R.id.id_tg_btn_house,
            R.id.id_tg_btn_homework,
            R.id.id_tg_btn_commute,
            R.id.id_tg_btn_cook,
            R.id.id_tg_btn_foreign_language,
            R.id.id_tg_btn_music_physical
    };

    private TextView id_tv_next_btn;
    private String mUserType;

    public CommonSkillFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_skill, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView)view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        setOnclickListener();

        initViewString(view);

        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViewString(View view) {

        TextView id_tv_skill = (TextView)view.findViewById(R.id.id_tv_skill);
        TextView id_tv_skill_comment = (TextView)view.findViewById(R.id.id_tv_skill_comment);
        SisoToggleButton id_tg_btn_care = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_care);
        SisoToggleButton id_tg_btn_baby = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_baby);
        SisoToggleButton id_tg_btn_outdoor = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_outdoor);
        SisoToggleButton id_tg_btn_house = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_house);
        SisoToggleButton id_tg_btn_homework = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_homework);
        SisoToggleButton id_tg_btn_commute = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_commute);
        SisoToggleButton id_tg_btn_cook = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_cook);
        SisoToggleButton id_tg_btn_foreign_language = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_foreign_language);
        SisoToggleButton id_tg_btn_music_physical = (SisoToggleButton) view.findViewById(R.id.id_tg_btn_music_physical);

        if(mUserType.equals(User.USER_TYPE_SITTER)){
            id_tv_skill.setText(R.string.common_sitter_skill);
            id_tv_skill_comment.setText(R.string.common_sitter_skill_comment);
            id_tg_btn_care.setLabel(R.string.common_sitter_tg_skill_care);
            id_tg_btn_baby.setLabel(R.string.common_sitter_tg_skill_baby);
            id_tg_btn_outdoor.setLabel(R.string.common_sitter_tg_skill_outdoor);
            id_tg_btn_house.setLabel(R.string.common_sitter_tg_skill_housekeeping);
            id_tg_btn_homework.setLabel(R.string.common_sitter_tg_skill_homework);
            id_tg_btn_commute.setLabel(R.string.common_sitter_tg_skill_commute);
            id_tg_btn_cook.setLabel(R.string.common_sitter_tg_skill_cook);
            id_tg_btn_foreign_language.setLabel(R.string.common_sitter_tg_skill_foreign_language);
            id_tg_btn_music_physical.setLabel(R.string.common_sitter_tg_skill_music_physical);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            id_tv_skill.setText(R.string.common_parent_skill);
            id_tv_skill_comment.setText(R.string.common_parent_skill_comment);
            id_tg_btn_care.setLabel(R.string.common_parent_tg_skill_care);
            id_tg_btn_baby.setLabel(R.string.common_parent_tg_skill_baby);
            id_tg_btn_outdoor.setLabel(R.string.common_parent_tg_skill_outdoor);
            id_tg_btn_house.setLabel(R.string.common_parent_tg_skill_housekeeping);
            id_tg_btn_homework.setLabel(R.string.common_parent_tg_skill_homework);
            id_tg_btn_commute.setLabel(R.string.common_parent_tg_skill_commute);
            id_tg_btn_cook.setLabel(R.string.common_parent_tg_skill_cook);
            id_tg_btn_foreign_language.setLabel(R.string.common_parent_tg_skill_foreign_language);
            id_tg_btn_music_physical.setLabel(R.string.common_parent_tg_skill_music_physical);
        }
    }

    private void setOnclickListener() {
        SisoUtil.setArrayClickListener(mSkillAry, getView(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tg_btn_care:
            case R.id.id_tg_btn_baby:
            case R.id.id_tg_btn_outdoor:
            case R.id.id_tg_btn_house:
            case R.id.id_tg_btn_homework:
            case R.id.id_tg_btn_commute:
            case R.id.id_tg_btn_cook:
            case R.id.id_tg_btn_foreign_language:
            case R.id.id_tg_btn_music_physical:
                checkSelectCount(v.getId());
                break;
            case R.id.id_tv_next_btn:

                if(!isValidInput()) return;

                saveData();

                moveNext();
                break;
        }
    }

    /**
     * Skill Toggle Button 선택시마다 체크
     * @param viewId
     */
    private void checkSelectCount(int viewId) {
        boolean isChecked = ((SisoToggleButton)getView().findViewById(viewId)).isChecked();
        if(isChecked){
            ((SisoToggleButton)getView().findViewById(viewId)).setChecked(!isChecked);
        }else{
            boolean targetIsChecked;
            int checkCount=0;
            for(int i=0; i<mSkillAry.length; i++){
                targetIsChecked = ((SisoToggleButton)getView().findViewById(mSkillAry[i])).isChecked();
                if(targetIsChecked){
                    checkCount++;
                }
            }
            if(checkCount>=5){
                SisoUtil.showErrorMsg(getContext(), R.string.invalid_skill_max_select);
            }else{
                ((SisoToggleButton)getView().findViewById(viewId)).setChecked(true);
            }
        }
    }

    @Override
    protected void loadData() {
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            if(!TextUtils.isEmpty(mUser.sitterInfo.skill)) {
                String convertStr = SisoUtil.convertMultiStringToIndexString(getContext(), mUser.sitterInfo.skill, R.array.multi_skill);
                SisoUtil.checkToggleButtonByString(mSkillAry, convertStr, getView());
            }
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            if(!TextUtils.isEmpty(mUser.parentInfo.skill)) {
                String convertStr = SisoUtil.convertMultiStringToIndexString(getContext(), mUser.parentInfo.skill, R.array.multi_skill);
                SisoUtil.checkToggleButtonByString(mSkillAry, convertStr, getView());
            }
        }
    }

    @Override
    protected boolean isValidInput() {

        boolean isChecked;
        int checkCount=0;
        for(int i=0; i<mSkillAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mSkillAry[i])).isChecked();
            if(isChecked){
                checkCount++;
            }
        }
        if(checkCount == 0){
            SisoUtil.showErrorMsg(getContext(),R.string.invalid_skill_min_select);
            return false;
        }
        if(checkCount > 5){
            SisoUtil.showErrorMsg(getContext(),R.string.invalid_skill_max_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        String skillStr = "";
        String[] valueAry = getContext().getResources().getStringArray(R.array.multi_skill);

        boolean isChecked;
        for(int i=0; i<mSkillAry.length; i++){
            isChecked = ((SisoToggleButton)getView().findViewById(mSkillAry[i])).isChecked();
            if(isChecked){
                skillStr += valueAry[i]+User.DELIMITER;
            }
        }
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            mUser.sitterInfo.skill = SisoUtil.deleteLastDelimiter(skillStr, User.DELIMITER);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.skill = SisoUtil.deleteLastDelimiter(skillStr, User.DELIMITER);
        }
        Log.d(TAG, "onClick: mSitter : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        if(mUserType.equals(User.USER_TYPE_SITTER)) {
            Sitter03NatRlgFragment fragment = new Sitter03NatRlgFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            Parent03NatEduFragment fragment = new Parent03NatEduFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_basic_title);
        }

    }
}
