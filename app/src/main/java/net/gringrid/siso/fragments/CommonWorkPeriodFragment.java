package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Parent;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;

import org.w3c.dom.Text;


/**
 * 구직 / 구인정보 입력 공통 > 근무기간 설정
 */
public class CommonWorkPeriodFragment extends InputBaseFragment implements CalendarView.OnDateChangeListener {

    private static final String TAG = "jiho";
    private String mUserType;

    private TextView id_tv_next_btn;
    private CalendarView id_cv;
    private FrameLayout id_fl_cv;
    private TextView id_tv_start_0;
    private TextView id_tv_start_1;
    private TextView id_tv_end_0;
    private TextView id_tv_end_1;
    private TextView id_tv_start;
    private TextView id_tv_end;

    private String mStartDate = User.TERM_MIN;
    private String mEndDate = User.TERM_MAX;

    int mLastClickedBtn;

    public CommonWorkPeriodFragment() {
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
        return inflater.inflate(R.layout.fragment_period_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tv_start = (TextView) view.findViewById(R.id.id_tv_start);
        id_tv_end = (TextView) view.findViewById(R.id.id_tv_end);
        id_tv_start_0 = (TextView) view.findViewById(R.id.id_tv_start_0);
        id_tv_start_1 = (TextView) view.findViewById(R.id.id_tv_start_1);
        id_tv_end_0 = (TextView) view.findViewById(R.id.id_tv_end_0);
        id_tv_end_1 = (TextView) view.findViewById(R.id.id_tv_end_1);

        id_tv_start_0.setOnClickListener(this);
        id_tv_start_1.setOnClickListener(this);
        id_tv_end_0.setOnClickListener(this);
        id_tv_end_1.setOnClickListener(this);
        id_fl_cv = (FrameLayout)view.findViewById(R.id.id_fl_cv);
        id_cv = (CalendarView) view.findViewById(R.id.id_cv);
        id_cv.setOnDateChangeListener(this);

        loadData();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        int viewId = v.getId();
        switch (v.getId()){
            case R.id.id_tv_start_0:

            case R.id.id_tv_start_1:
                setStartBtn(v.getId());
                setCalendarVisible(viewId);
                setStartText(viewId, "");
                mLastClickedBtn=viewId;
                break;

            case R.id.id_tv_end_0:
            case R.id.id_tv_end_1:
                setEndBtn(v.getId());
                setCalendarVisible(viewId);
                setEndText(viewId, "");
                mLastClickedBtn=viewId;
                break;

            case R.id.id_tv_next_btn:
                if(!isValidInput()) return;

                saveData();

                moveNext();
                break;
        }
    }

    private void setStartBtn(int id) {

        if(id == R.id.id_tv_start_0){
            id_tv_start_0.setBackgroundResource(R.drawable.ic_calendar_selected);
            id_tv_start_0.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            id_tv_start_1.setBackgroundResource(R.drawable.ic_calendar_unselect);
            id_tv_start_1.setTextColor(ContextCompat.getColor(getContext(), R.color.sisoPrimaryText));

        }else if(id == R.id.id_tv_start_1){
            id_tv_start_0.setBackgroundResource(R.drawable.ic_calendar_unselect);
            id_tv_start_0.setTextColor(ContextCompat.getColor(getContext(), R.color.sisoPrimaryText));
            id_tv_start_1.setBackgroundResource(R.drawable.ic_calendar_select);
            id_tv_start_1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            if(!mEndDate.equals(User.TERM_MAX)){
                id_tv_end_1.setBackgroundResource(R.drawable.ic_calendar_selected);
            }
        }
    }

    private void setEndBtn(int id) {

        if(id == R.id.id_tv_end_0){
            id_tv_end_0.setBackgroundResource(R.drawable.ic_calendar_selected);
            id_tv_end_0.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            id_tv_end_1.setBackgroundResource(R.drawable.ic_calendar_unselect);
            id_tv_end_1.setTextColor(ContextCompat.getColor(getContext(), R.color.sisoPrimaryText));

        }else if(id == R.id.id_tv_end_1){
            id_tv_end_0.setBackgroundResource(R.drawable.ic_calendar_unselect);
            id_tv_end_0.setTextColor(ContextCompat.getColor(getContext(), R.color.sisoPrimaryText));
            id_tv_end_1.setBackgroundResource(R.drawable.ic_calendar_select);
            id_tv_end_1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            if(!mStartDate.equals(User.TERM_MIN)){
                id_tv_start_1.setBackgroundResource(R.drawable.ic_calendar_selected);
            }
        }
    }

    @Override
    protected void moveNext() {
        // 구인/구직자 모두 출퇴근유형, 거리 입력 화면으로 이동
        CommonCommuteFragment fragment = new CommonCommuteFragment();
        int titleId = 0;
        if(mUserType.equals(User.USER_TYPE_SITTER)) {
            titleId = R.string.sitter_basic_title;

        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            titleId = R.string.parent_index_title;
        }
        ((BaseActivity) getActivity()).setFragment(fragment, titleId);
    }

    @Override
    protected void loadData() {
        String term_from="";
        String term_to="";

        if(mUserType.equals(User.USER_TYPE_SITTER)) {
            term_from = mUser.sitterInfo.term_from;
            term_to = mUser.sitterInfo.term_to;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            term_from = mUser.parentInfo.term_from;
            term_to = mUser.parentInfo.term_to;
        }

        if(!TextUtils.isEmpty(term_from)){
            if(term_from.equals(User.TERM_MIN)){
                setStartBtn(R.id.id_tv_start_0);
                setStartText(R.id.id_tv_start_0, "");
            }else{
                setStartBtn(R.id.id_tv_start_1);
                setStartText(R.id.id_tv_start_1, term_from);
            }
        }

        if(!TextUtils.isEmpty(term_to)){
            if(term_to.equals(User.TERM_MAX)){
                setStartBtn(R.id.id_tv_end_0);
                setStartText(R.id.id_tv_end_0, "");
            }else{
                setStartBtn(R.id.id_tv_end_1);
                setEndText(R.id.id_tv_end_1, term_to);
            }
        }
    }

    @Override
    protected boolean isValidInput() {
//        if(!SisoUtil.isRadioGroupSelected(mRadioStart, getView())){
//            SisoUtil.showErrorMsg(getContext(), R.string.invalid_period_start_select);
//            return false;
//        }
//        if(!SisoUtil.isRadioGroupSelected(mRadioEnd, getView())){
//            SisoUtil.showErrorMsg(getContext(), R.string.invalid_period_end_select);
//            return false;
//        }
        if(TextUtils.isEmpty(mStartDate)){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_period_start_write);
            return false;
        }
        if(TextUtils.isEmpty(mEndDate)){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_period_end_write);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        if(mUserType.equals(User.USER_TYPE_SITTER)) {
            mUser.sitterInfo.term_from = mStartDate;
            mUser.sitterInfo.term_to = mEndDate;
        }else if(mUserType.equals(User.USER_TYPE_PARENT)){
            mUser.parentInfo.term_from = mStartDate;
            mUser.parentInfo.term_to = mEndDate;
        }
        Log.d(TAG, "saveData : "+mUser.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void setStartText(int viewId, String date) {
        id_tv_start.setGravity(Gravity.LEFT);
        if(viewId==R.id.id_tv_start_0){
            mStartDate = User.TERM_MIN;
            id_tv_start.setText("시작일 : 상관없음");
        }else if(viewId == R.id.id_tv_start_1){
            mStartDate = date;
            id_tv_start.setText("시작일 : "+SisoUtil.getDateString(date, "-"));
        }
    }

    private void setEndText(int viewId, String date) {
        id_tv_end.setGravity(Gravity.LEFT);
        if(viewId==R.id.id_tv_end_0){
            mEndDate = User.TERM_MAX;
            id_tv_end.setText("종료일 : 상관없음");
        }else{
            mEndDate = date;
            id_tv_end.setText("종료일 : "+SisoUtil.getDateString(date, "-"));
        }
    }


    /**
     * 시작일이나 종료일 지정을 선택 한 경우 달력 보이도록 설정
     * @param viewId
     */
    private void setCalendarVisible(int viewId) {
        if(viewId==R.id.id_tv_start_1 || viewId==R.id.id_tv_end_1){
            id_fl_cv.setVisibility(View.VISIBLE);
        }else{
            id_fl_cv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

        String yearStr = String.format("%d",year);
        String monthStr = String.format("%02d",month+1);
        String dayStr = String.format("%02d",dayOfMonth);
        String dateStr = yearStr+monthStr+dayStr;

        if(mLastClickedBtn == R.id.id_tv_start_1){
            setStartText(R.id.id_tv_start_1, dateStr);
        }
        if(mLastClickedBtn == R.id.id_tv_end_1){
            setEndText(R.id.id_tv_end_1, dateStr);
        }
    }


}
