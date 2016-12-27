package net.gringrid.siso.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.gringrid.siso.R;
import net.gringrid.siso.models.User;
import net.gringrid.siso.views.SisoEditText;
import net.gringrid.siso.views.SisoToggleButton;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by choijiho on 16. 10. 19..
 */
public class SisoUtil {


    private static final String TAG = "jiho";

    public static int convertBirthToAge(String birth){
        int age = 0;

        if(!TextUtils.isEmpty(birth)){
            if(birth.length()==8){
                int year = Integer.parseInt(birth.substring(0,4));
                int month = Integer.parseInt(birth.substring(4,6));
                int day = Integer.parseInt(birth.substring(6,8));

                LocalDate birthDate = new LocalDate(year, month, day);
                LocalDate now = new LocalDate();
                age = Years.yearsBetween(birthDate, now).getYears();
            }else{
                age = User.NULL;
            }
        }else{
            age = User.NULL;
        }
        return age;
    }

    /**
     * User 객체를 상세화면에 보여질 Data 형태로변환한다.
     * @param context
     * @param user
     * @return
     */
    public static User convertDisplayValue(Context context, User user){
        User displayUser = null;

        displayUser = user;
        displayUser.sitterInfo.gender = getRadioValue(context, R.array.radio_gender, user.sitterInfo.gender);
        displayUser.sitterInfo.commute_type = getRadioValue(context, R.array.radio_commute, user.sitterInfo.commute_type);
        int distance_limit_idx = getIndexFromArray(context, R.array.picker_commute_distance_value, user.sitterInfo.distance_limit);
        displayUser.sitterInfo.distance_limit = getRadioValue(context, R.array.picker_commute_distance, String.valueOf(distance_limit_idx));
        displayUser.sitterInfo.work_exp = getRadioValue(context, R.array.picker_work_year_display, user.sitterInfo.work_exp);
        displayUser.sitterInfo.term_from = getDateString(user.sitterInfo.term_from, "-");
        displayUser.sitterInfo.term_to = getDateString(user.sitterInfo.term_to, "-");
        displayUser.sitterInfo.nat = getRadioValue(context, R.array.sitter_nat, user.sitterInfo.nat);
        displayUser.sitterInfo.religion = getRadioValue(context, R.array.sitter_rlg, user.sitterInfo.religion);
        displayUser.sitterInfo.skill = getMultiValue(context, R.array.multi_skill, user.sitterInfo.skill, ",");
        displayUser.sitterInfo.edu = getRadioValue(context, R.array.sitter_edu, user.sitterInfo.edu);
        if(!TextUtils.isEmpty(user.sitterInfo.edu)) {
            if (user.sitterInfo.edu.equals("4") || user.sitterInfo.edu.equals("5")) {
                if (!TextUtils.isEmpty(user.sitterInfo.school)) {
                    displayUser.sitterInfo.edu += " " + user.sitterInfo.school;
                }
                if (!TextUtils.isEmpty(user.sitterInfo.department)) {
                    displayUser.sitterInfo.edu += " " + user.sitterInfo.department;
                }
            }
        }

        if(!TextUtils.isEmpty(user.sitterInfo.salary)) {
            int salary = Integer.parseInt(user.sitterInfo.salary);
            displayUser.sitterInfo.salary = salary == 0 ? "협의" : String.format("%,d", salary);
        }

        return displayUser;
    }

    /**
     * User 객체를 상세화면에 보여질 Data 형태로변환한다.
     * @param context
     * @param user
     * @return
     */
    public static User convertParentDisplayValue(Context context, User user){
        User displayUser = null;

        displayUser = user;
        displayUser.parentInfo.commute_type = getRadioValue(context, R.array.radio_commute, user.parentInfo.commute_type);
        int distance_limit_idx = getIndexFromArray(context, R.array.picker_commute_distance_value, user.sitterInfo.distance_limit);
        displayUser.parentInfo.distance_limit = getRadioValue(context, R.array.picker_commute_distance, String.valueOf(distance_limit_idx));
        displayUser.parentInfo.term_from = getDateString(user.parentInfo.term_from, "-");
        displayUser.parentInfo.term_to = getDateString(user.parentInfo.term_to, "-");

//        displayUser.parentInfo.sitter_age = getRadioValue(context, R.array.picker_sitter_age, user.parentInfo.sitter_age);
        displayUser.parentInfo.work_exp = getRadioValue(context, R.array.picker_work_year, user.parentInfo.work_exp);
        displayUser.parentInfo.nat = getRadioValue(context, R.array.sitter_nat, user.parentInfo.nat);
        displayUser.parentInfo.religion = getRadioValue(context, R.array.sitter_rlg, user.parentInfo.religion);
        displayUser.parentInfo.edu = getRadioValue(context, R.array.sitter_edu, user.parentInfo.edu);
        displayUser.parentInfo.skill = getMultiValue(context, R.array.multi_skill, user.parentInfo.skill, ",");

        return displayUser;
    }

    /**
     * DB에 입력된 index로 화면에 표시 할 값을 가져온다.
     * @param context
     * @param ary : 화면에 표시 할 String 이 담긴 배열
     * @param idx : DB에 입력된 index
     * @return 화면에 표시 될 값
     */
    public static String getRadioValue(Context context, int ary, String idx){
        // TODO idx의 숫자여부를 판단하여 숫자가 아니면 return
        if(TextUtils.isEmpty(idx)) return idx;
        String[] valueAry = context.getResources().getStringArray(ary);
        String value = valueAry[Integer.parseInt(idx)];
        value = value.replace("\n", "");
        return value;
    }

    /**
     * 구분자를 추가한 날짜 포멧을 얻는다
     * @param orgDateStr 8자리 yyyyMMdd
     * @param sep 구분자
     * @return 년, 월, 일  사이에 구분자를 추가한 문자열
     */
    public static String getDateString(String orgDateStr, String sep){
        String newDateStr;

        if(TextUtils.isEmpty(orgDateStr) || orgDateStr.trim().length() != 8){
           return orgDateStr;
        }

        SimpleDateFormat orgSDF = new SimpleDateFormat("yyyyMMdd");
        Date orgDate;
        try {
            orgDate = orgSDF.parse(orgDateStr);
            String newFormat = "yyyy"+sep+"MM"+sep+"dd";
            SimpleDateFormat newSDF = new SimpleDateFormat(newFormat);
            newDateStr = newSDF.format(orgDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return orgDateStr;
        }
        return newDateStr;
    }

    private static String getMultiValue(Context context, int ary, String strValue, String dlmt){
        if(TextUtils.isEmpty(strValue)) return strValue;

        String[] totalList = context.getResources().getStringArray(ary);
        String[] checkedList = strValue.split(dlmt);
        String result = "";

        for(int i=0; i<totalList.length; i++){
            for(int j=0; j<checkedList.length; j++){
                if(totalList[i].equals(checkedList[j])){
                    result+=i+dlmt;
                    break;
                }
            }
        }
        return result;
    }

    public static String getListSalary(Context context, String salary){
        if(TextUtils.isEmpty(salary)) return salary;
        int intValue = Integer.parseInt(salary);

        String[] strList;// = context.getResources().getStringArray();
        String[] valList;
        String unit;
        if(intValue > 100000){
            strList = context.getResources().getStringArray(R.array.picker_salary_month);
            valList = context.getResources().getStringArray(R.array.picker_salary_month_value);
            unit = "월급 ";
        }else{
            strList = context.getResources().getStringArray(R.array.picker_salary);
            valList = context.getResources().getStringArray(R.array.picker_salary_value);
            unit = "시급 ";
        }

        for(int i=0;i<valList.length;i++){
            if(valList[i].equals(salary)){
                return unit + strList[i];
            }
        }
        return salary;
    }

    /**
     * 휴대폰의 넓이값을 구한다
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    /**
     * 라디오 그룹이 하나라도 선택되었는지 여부를 확인한다
     * @param radioList
     * @param view
     * @return
     */
    public static boolean isRadioGroupSelected(int[] radioList, View view){
        View viewType = view.findViewById(radioList[0]);

        for(int i=0; i<radioList.length; i++){
            if(viewType instanceof SisoToggleButton){
                if (((SisoToggleButton)view.findViewById(radioList[i])).isChecked()) {
                    return true;
                }
            }else if(viewType instanceof ToggleButton){
                if (((ToggleButton)view.findViewById(radioList[i])).isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 라디오 그룹에서 선택된 index 를 구한다
     * @param radioList
     * @param view
     * @return
     */
    public static int getRadioValue(int[] radioList, View view){
        View viewType = view.findViewById(radioList[0]);
        for(int i=0; i<radioList.length; i++){
            if(viewType instanceof SisoToggleButton){
                if (((SisoToggleButton)view.findViewById(radioList[i])).isChecked()) {
                    return i;
                }
            }else if(viewType instanceof ToggleButton){
                if (((ToggleButton)view.findViewById(radioList[i])).isChecked()) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Array resource로 부터 해당하는 값의 index 를 얻는다
     * @param aryId
     * @param value
     * @return
     */
    public static int getIndexFromArray(Context context, int aryId, String value){
        String[] totalList = context.getResources().getStringArray(aryId);

        for(int i=0; i<totalList.length; i++){
            if(totalList[i].equals(value)) {
                return i;
            }
        }
        // TODO 해당하는 index가 없을경우?
        return 0;
    }
    /**
     * Array resource로 부터 해당하는 값의 index 를 얻는다
     * @param aryId
     * @param value
     * @return
     */
    public static String getValueFromArray(Context context, int aryId, String value){
        String[] totalList = context.getResources().getStringArray(aryId);

        for(int i=0; i<totalList.length; i++){
            if(totalList[i].equals(value)) {
                return totalList[i];
            }
        }
        return value;
    }

    /**
     * 라디오 그룹에서 특정 항목을 선택한다
     * @param radioList
     * @param selectItem
     * @param view
     */
    public static void selectRadio(int[] radioList, int selectItem, View view) {

        boolean isCheckItem;
        View viewType = view.findViewById(selectItem);

        for(int src:radioList){
            if(src == selectItem){
                isCheckItem = true;
            }else{
                isCheckItem = false;
            }

            if(viewType instanceof SisoToggleButton){
                ((SisoToggleButton)view.findViewById(src)).setChecked(isCheckItem);
            }else if(viewType instanceof ToggleButton){
                ((ToggleButton)view.findViewById(src)).setChecked(isCheckItem);
            }
        }
    }

    public static void loadRadioDataWithWriteContent(int[] radioList, String value, int writeIdx, int writeTextViewId, View view){
        if(TextUtils.isEmpty(value)) return;
        int idx;
        try{
            idx = Integer.parseInt(value);
            Log.d(TAG, "loadRadioDataWithWriteContent: idx : "+idx);
            selectRadio(radioList, radioList[idx], view);
        }catch (NumberFormatException e){
            selectRadio(radioList, radioList[writeIdx], view);
            (view.findViewById(writeTextViewId)).setVisibility(View.VISIBLE);
            ((SisoEditText)view.findViewById(writeTextViewId)).setInput(value);
        }

    }

    /**
     * 에러메시지를 보여준다
     * @param context
     * @param msgId
     */
    public static void showErrorMsg(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_LONG).show();
    }

    /**
     * 메시지를 보여준다
     * @param context
     * @param msg
     */
    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * array그룹에 OnClickListener를 세팅한다.
     * @param radioList
     * @param view
     * @param listener
     */
    public static void setArrayClickListener(int[] radioList, View view, View.OnClickListener listener) {
        for(int radioId:radioList){
            view.findViewById(radioId).setOnClickListener(listener);
        }
    }

    /**
     * Delimiter로 구분된 String의 마지막 Delimiter를 삭제한다.
     * @param src
     * @param delimiter
     * @return
     */
    public static String deleteLastDelimiter(String src, String delimiter){
        if(TextUtils.isEmpty(src)) return src;
        int srcLen = src.trim().length();
        int delimiterLen = delimiter.length();
        return src.substring(0, srcLen-delimiterLen);
    }

    /**
     * Delimiter로 구분된 String값을 다시 index로 구분하여 변환한다
     * @param context
     * @param multiStr
     * @param arrayId
     * @return
     */
    public static String convertMultiStringToIndexString(Context context, String multiStr, int arrayId){
        String[] valueAry = context.getResources().getStringArray(arrayId);
        String[] itemAry = multiStr.split(User.DELIMITER);
        String idxStr="";
        for(int i=0; i<itemAry.length; i++){
            for(int j=0; j<valueAry.length; j++){
                if(itemAry[i].equals(valueAry[j])){
                    idxStr += j+User.DELIMITER;
                }
            }
        }
        return deleteLastDelimiter(idxStr, User.DELIMITER);
    }


    /**
     * Delimiter로 구분된 String 값으로 부터 Toggle 버튼을 체크한다
     * @param toggleList
     * @param data
     * @param view
     */
    public static void checkToggleButtonByString(int[] toggleList, String data, View view){
        View viewType = view.findViewById(toggleList[0]);
        String[] ages = data.split(User.DELIMITER);
        for(String ageIdx : ages){
            if(viewType instanceof SisoToggleButton){
                ((SisoToggleButton)view.findViewById(toggleList[Integer.parseInt(ageIdx)])).setChecked(true);
            }else if(viewType instanceof ToggleButton){
                ((ToggleButton)view.findViewById(toggleList[Integer.parseInt(ageIdx)])).setChecked(true);
            }
        }
    }

    public static int findIndexFromArrayString(Context context, int aryId, String findStr){
        String[] arrSalaryValues = context.getResources().getStringArray(aryId);
        int result = 0;
        for(int i=0; i<arrSalaryValues.length; i++){
            if(arrSalaryValues[i].equals(findStr)){
                result = i;
                break;
            }
        }
        return result;
    }
}
