package net.gringrid.siso.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import net.gringrid.siso.models.Personal;

/**
 * Created by choijiho on 16. 8. 29..
 */
public class SharedData {
    public static final boolean DEBUG_MODE = true;
    public static final String PERSONAL = "personal_info";
    public static final String SITTER = "sitter_info";

    public static final String SESSION_KEY = "Session-Key";
    private static final String TAG = "jiho";

    private static SharedData instance;
    private static Context mContext;

    private SharedPreferences m_preference;
    private SharedPreferences.Editor m_editor;

    private SharedData(Context context) {
        mContext = context;
        if ( m_preference == null ){
            m_preference = mContext.getSharedPreferences("", Context.MODE_PRIVATE);
        }
        if ( m_editor == null ){
            m_editor = m_preference.edit();
        }
    }

    public static SharedData getInstance(Context context) {
        if ( instance == null ) {
            instance = new SharedData(context);
        }
        return instance;
    }

    public static String getSessionKey(){
       return getInstance(mContext).getGlobalDataString(SESSION_KEY);
    }

    public static Personal getPersonal(){
        return getInstance(mContext).getUserLoginData();
    }


    public void setUserLoginData(Personal personal){
        Gson gson = new Gson();
        String userJsonData = gson.toJson(personal);
        Log.d(TAG, "setUserLoginData: MEMBER : "+userJsonData);
        insertGlobalData(PERSONAL, userJsonData);
    }

    public Personal getUserLoginData(){
        Gson gson = new Gson();
        String userJsonData = getGlobalDataString(PERSONAL);
        Personal personal = null;
        if (!TextUtils.isEmpty(userJsonData)){
            personal = gson.fromJson(userJsonData, Personal.class);
        }
        return personal;
    }

    public void setGlobalData(String _key, String _val)
    {
        m_editor.putString(_key, _val);
    }

    public void setGlobalData(String _key, int _val)
    {
        m_editor.putInt(_key, _val);
    }

    public void setGlobalData(String _key, long _val)
    {
        m_editor.putLong(_key, _val);
    }

    public void setGlobalData(String _key, float _val)
    {
        m_editor.putFloat(_key, _val);
    }

    public void setGlobalData(String _key, boolean _val)
    {
        m_editor.putBoolean(_key, _val);
    }

    public void insertGlobalData(String _key, String _val)
    {
        m_editor.putString(_key, _val);
        m_editor.commit();
    }

    public void insertGlobalData(String _key, int _val)
    {
        m_editor.putInt(_key, _val);
        m_editor.commit();
    }

    public void insertGlobalData(String _key, long _val)
    {
        m_editor.putLong(_key, _val);
        m_editor.commit();
    }

    public void insertGlobalData(String _key, float _val)
    {
        m_editor.putFloat(_key, _val);
        m_editor.commit();
    }

    public void insertGlobalData(String _key, boolean _val)
    {
        m_editor.putBoolean(_key, _val);
        m_editor.commit();
    }

    public String getGlobalDataString(String _key){
        return m_preference.getString(_key, null);
    }

    public int getGlobalDataInt(String _key){
        return m_preference.getInt(_key, 0);
    }

    public int getGlobalDataInt(String _key, int _default){
        return m_preference.getInt(_key, _default);
    }

    public boolean getGlobalDataBoolean(String _key){
        return m_preference.getBoolean(_key, false);
    }

    public void commit(){
        m_editor.commit();
    }
}
