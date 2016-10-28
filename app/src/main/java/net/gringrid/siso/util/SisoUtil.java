package net.gringrid.siso.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import net.gringrid.siso.R;
import net.gringrid.siso.models.User;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Interceptor;

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

    public static User convertDisplayValue(Context context, User user){

        User displayUser = null;

        displayUser = user;
        displayUser.sitterInfo.gender = getRadioValue(context, R.array.radio_gender, user.sitterInfo.gender);
        displayUser.sitterInfo.commute_type = getRadioValue(context, R.array.radio_commute, user.sitterInfo.commute_type);
        displayUser.sitterInfo.work_year = getRadioValue(context, R.array.picker_work_year_display, user.sitterInfo.work_year);
        displayUser.sitterInfo.term_from = getDateString(user.sitterInfo.term_from, "-");
        displayUser.sitterInfo.term_to = getDateString(user.sitterInfo.term_to, "-");
        displayUser.sitterInfo.skill = getMultiValue(context, R.array.multi_skill, user.sitterInfo.skill, ",");
        int salary = Integer.parseInt(user.sitterInfo.salary);
        displayUser.sitterInfo.salary = salary==0?"협의":String.format("%,d", salary);

        return displayUser;
    }

    /**
     * DB에 입력된 index로 화면에 표시 할 값을 가져온다.
     * @param context
     * @param ary : 화면에 표시 할 String 이 담긴 배열
     * @param idx : DB에 입력된 index
     * @return 화면에 표시 될 값
     */
    private static String getRadioValue(Context context, int ary, String idx){
        String[] valueAry = context.getResources().getStringArray(ary);
        String value = valueAry[Integer.parseInt(idx)];
        return value;
    }

    /**
     * 구분자를 추가한 날짜 포멧을 얻는다
     * @param orgDateStr 8자리 yyyyMMdd
     * @param sep 구분자
     * @return 년, 월, 일  사이에 구분자를 추가한 문자열
     */
    private static String getDateString(String orgDateStr, String sep){
        String newDateStr;

        if(orgDateStr.trim().length() != 8){
           return orgDateStr;
        }
        Log.d(TAG, "getDateString: org : "+orgDateStr);

        SimpleDateFormat orgSDF = new SimpleDateFormat("yyyyyMMdd");
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

    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
