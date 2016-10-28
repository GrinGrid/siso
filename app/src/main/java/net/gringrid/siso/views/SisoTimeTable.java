package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.gringrid.siso.R;


/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoTimeTable extends LinearLayout implements View.OnClickListener {

    public static final int MON = 0;
    public static final int TUE = 1;
    public static final int WED = 2;
    public static final int THU = 3;
    public static final int FRI = 4;
    public static final int SAT = 5;
    public static final int SUN = 6;

    private Context mContext;
    private static final int GROUP_ROW = 0;
    private static final int GROUP_COL = 1;
    private static final int GROUP_ROW_LEN = 7;
    private static final int GROUP_COL_LEN = 7;

    private LinearLayout id_ll_mon;
    private LinearLayout id_ll_tue;
    private LinearLayout id_ll_wed;
    private LinearLayout id_ll_thu;
    private LinearLayout id_ll_fri;
    private LinearLayout id_ll_sat;
    private LinearLayout id_ll_sun;

    private LinearLayout id_ll_time1;
    private LinearLayout id_ll_time2;
    private LinearLayout id_ll_time3;
    private LinearLayout id_ll_time4;
    private LinearLayout id_ll_time5;
    private LinearLayout id_ll_time6;
    private LinearLayout id_ll_time7;

    OnTimeChangedListener mlistener;


    public interface OnTimeChangedListener{
        void onTimeChanged();
    }

    public void setOnTimeChangedListener(OnTimeChangedListener listener){
        mlistener = listener;
    }

    public SisoTimeTable(Context context){
        super(context);
        mContext = context;
        initView();
    }

    public SisoTimeTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        getAttrs(attrs);
    }

    public SisoTimeTable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mContext = context;
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_timetable, this, false);

        addView(v);

        id_ll_mon = (LinearLayout)findViewById(R.id.id_ll_mon);
        id_ll_tue = (LinearLayout)findViewById(R.id.id_ll_tue);
        id_ll_wed = (LinearLayout)findViewById(R.id.id_ll_wed);
        id_ll_thu = (LinearLayout)findViewById(R.id.id_ll_thu);
        id_ll_fri = (LinearLayout)findViewById(R.id.id_ll_fri);
        id_ll_sat = (LinearLayout)findViewById(R.id.id_ll_sat);
        id_ll_sun = (LinearLayout)findViewById(R.id.id_ll_sun);

        id_ll_time1 = (LinearLayout)findViewById(R.id.id_ll_time1);
        id_ll_time2 = (LinearLayout)findViewById(R.id.id_ll_time2);
        id_ll_time3 = (LinearLayout)findViewById(R.id.id_ll_time3);
        id_ll_time4 = (LinearLayout)findViewById(R.id.id_ll_time4);
        id_ll_time5 = (LinearLayout)findViewById(R.id.id_ll_time5);
        id_ll_time6 = (LinearLayout)findViewById(R.id.id_ll_time6);
        id_ll_time7 = (LinearLayout)findViewById(R.id.id_ll_time7);

        id_ll_mon.setOnClickListener(this);
        id_ll_tue.setOnClickListener(this);
        id_ll_wed.setOnClickListener(this);
        id_ll_thu.setOnClickListener(this);
        id_ll_fri.setOnClickListener(this);
        id_ll_sat.setOnClickListener(this);
        id_ll_sun.setOnClickListener(this);

        id_ll_time1.setOnClickListener(this);
        id_ll_time2.setOnClickListener(this);
        id_ll_time3.setOnClickListener(this);
        id_ll_time4.setOnClickListener(this);
        id_ll_time5.setOnClickListener(this);
        id_ll_time6.setOnClickListener(this);
        id_ll_time7.setOnClickListener(this);

        setTimeChangedListener();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoTimeTable);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoTimeTable, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        typedArray.recycle();
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.id_ll_mon:
                checkGroup(GROUP_COL, 0);
                break;
            case R.id.id_ll_tue:
                checkGroup(GROUP_COL, 1);
                break;
            case R.id.id_ll_wed:
                checkGroup(GROUP_COL, 2);
                break;
            case R.id.id_ll_thu:
                checkGroup(GROUP_COL, 3);
                break;
            case R.id.id_ll_fri:
                checkGroup(GROUP_COL, 4);
                break;
            case R.id.id_ll_sat:
                checkGroup(GROUP_COL, 5);
                break;
            case R.id.id_ll_sun:
                checkGroup(GROUP_COL, 6);
                break;
            case R.id.id_ll_time1:
                checkGroup(GROUP_ROW, 0);
                break;
            case R.id.id_ll_time2:
                checkGroup(GROUP_ROW, 1);
                break;
            case R.id.id_ll_time3:
                checkGroup(GROUP_ROW, 2);
                break;
            case R.id.id_ll_time4:
                checkGroup(GROUP_ROW, 3);
                break;
            case R.id.id_ll_time5:
                checkGroup(GROUP_ROW, 4);
                break;
            case R.id.id_ll_time6:
                checkGroup(GROUP_ROW, 5);
                break;
            case R.id.id_ll_time7:
                checkGroup(GROUP_ROW, 6);
                break;
        }
    }

    /**
     * 각 셀에 대한 선택이 변경될때마다 알려준다
     */
    private void setTimeChangedListener(){
        String tg_id;
        SisoToggleButton tmpTgBtn;

        for(int col=0; col<GROUP_COL_LEN; col++){
            for(int row=0; row<GROUP_ROW_LEN; row++) {
                tg_id = "id_tg_"+col+row;
                tmpTgBtn = getToggleButtonByStr(tg_id);
                tmpTgBtn.setOnToggleChangedListener(new SisoToggleButton.OnToggleChangedListener() {
                    @Override
                    public void onChanged(View view) {
                        mlistener.onTimeChanged();
                    }
                });
            }
        }
    }

    /**
     * Row, Col 그룹 단위로 선택한다.
     * @param groupType Row, Col 구분
     * @param groupNum 그룹의 번호
     */
    private void checkGroup(int groupType, int groupNum){
        String tg_id="";
        SisoToggleButton tmpTgBtn;
        boolean isAllCheckedGroup = isAllCheckedGroup(groupType, groupNum);
        for(int i=0; i<7; i++){
            if(groupType == GROUP_COL){
                tg_id = "id_tg_"+i+groupNum;
            }else if(groupType == GROUP_ROW){
                tg_id = "id_tg_"+groupNum+i;
            }
            tmpTgBtn = getToggleButtonByStr(tg_id);
            tmpTgBtn.setChecked(!isAllCheckedGroup);
        }
    }

    private boolean isAllCheckedGroup(int groupType, int groupNum){
        String tg_id="";
        SisoToggleButton tmpTgBtn;
        boolean isAllChecked=true;

        for(int i=0; i<7; i++){
            if(groupType == GROUP_COL){
                tg_id = "id_tg_"+i+groupNum;
            }else if(groupType == GROUP_ROW){
                tg_id = "id_tg_"+groupNum+i;
            }
            tmpTgBtn = getToggleButtonByStr(tg_id);
            if(!tmpTgBtn.isChecked()) return false;
        }
        return isAllChecked;
    }

    private SisoToggleButton getToggleButtonByStr(String id){
        String packageName = mContext.getPackageName();
        int viewId = getResources().getIdentifier(id, "id", packageName);
        return (SisoToggleButton)findViewById(viewId);
    }

    public int getSelectedHour(){
        String tg_id;
        SisoToggleButton tmpTgBtn;

        int totalHour = 0;
        for(int i=0; i<GROUP_COL_LEN; i++){
            for(int j=0; j<GROUP_ROW_LEN; j++) {
                tg_id = "id_tg_"+i+j;
                tmpTgBtn = getToggleButtonByStr(tg_id);
                if(tmpTgBtn.isChecked()){
                    if(i==6){
                        totalHour += 6;
                    }else{
                        totalHour+=3;
                    }
                }
            }
        }
        return totalHour;
    }

    public String getBitString(int week) {
        String tg_id;
        SisoToggleButton tmpTgBtn;
        String result = "";

        for(int i=0; i<GROUP_ROW_LEN; i++){
            tg_id = "id_tg_"+i+week;
            tmpTgBtn = getToggleButtonByStr(tg_id);
            result += tmpTgBtn.isChecked()?"1":"0";
        }
        return result;
    }

    // TODO readonly 구현
    // TODO 세팅

}
