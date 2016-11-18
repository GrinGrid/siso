package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.gringrid.siso.R;


/**
 * 요일, 시간별 스케줄을 선택할 수 있는 view
 */
public class SisoTimeTable extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "jiho";
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
    private final boolean IS_TIME_GROUP_NOT_SELECT_WEEKEND = true;

    int mViewItems[] = {
            R.id.id_ll_mon,
            R.id.id_ll_mon,
            R.id.id_ll_tue,
            R.id.id_ll_wed,
            R.id.id_ll_thu,
            R.id.id_ll_fri,
            R.id.id_ll_sat,
            R.id.id_ll_sun,
            R.id.id_ll_time1,
            R.id.id_ll_time2,
            R.id.id_ll_time3,
            R.id.id_ll_time4,
            R.id.id_ll_time5,
            R.id.id_ll_time6,
            R.id.id_ll_time7
    };

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
        boolean readonly = typedArray.getBoolean(R.styleable.SisoTimeTable_readonly, false);
        if(!readonly){
            setViewItemsClickListener();
            setTimeChangedListener();
        }
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

    private void setViewItemsClickListener(){
        for(int viewId:mViewItems){
            findViewById(viewId).setOnClickListener(this);
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
                if(tmpTgBtn!=null) {
                    tmpTgBtn.setOnToggleChangedListener(new SisoToggleButton.OnToggleChangedListener() {
                        @Override
                        public void onChanged(View view) {
                            mlistener.onTimeChanged();
                        }
                    });
                }
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

        int loopLen = 7;
        // 시간 선택시 주말(토요일,일요일)은 선택되지 않도록
        if(groupType == GROUP_ROW && IS_TIME_GROUP_NOT_SELECT_WEEKEND){
            loopLen = 5;
        }

        for(int i=0; i<loopLen; i++){
            if(groupType == GROUP_COL){
                tg_id = "id_tg_"+i+groupNum;
            }else if(groupType == GROUP_ROW){
                tg_id = "id_tg_"+groupNum+i;
            }
            tmpTgBtn = getToggleButtonByStr(tg_id);
            tmpTgBtn.setChecked(!isAllCheckedGroup);
        }
    }

    /**
     * Row, Col 그룹이 전부 체크 되었는지 확인
     * @param groupType
     * @param groupNum
     * @return
     */
    private boolean isAllCheckedGroup(int groupType, int groupNum){
        String tg_id="";
        SisoToggleButton tmpTgBtn;
        boolean isAllChecked=true;

        int loopLen = 7;
        if(groupType == GROUP_ROW && IS_TIME_GROUP_NOT_SELECT_WEEKEND){
            loopLen = 5;
        }

        for(int i=0; i<loopLen; i++){
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

    /**
     * 선택된 전체 시간의 합을 얻는다
     * @return 선택된 전체 시간의 합
     */
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

    public void setBitString(int week, String bitString) {
        String tg_id;
        SisoToggleButton tmpTgBtn;
        byte b = Byte.parseByte(bitString, 2);

        for(int i=0; i<GROUP_ROW_LEN; i++){
            tg_id = "id_tg_"+i+week;
            tmpTgBtn = getToggleButtonByStr(tg_id);
            if((b & 1<<i) > 0){
                tmpTgBtn.setChecked(true);
            }else{
                tmpTgBtn.setChecked(false);
            }
        }
    }
    // TODO 세팅

}
