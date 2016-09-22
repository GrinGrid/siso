package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

import java.util.ArrayList;

/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoPicker extends LinearLayout implements View.OnClickListener{

    private static final String TAG = "jiho";
    ImageView   id_iv_minus;
    ImageView   id_iv_plus;
    TextView    id_tv_text;
    String[]    mDataList;
    int         mCurrentIdx;

    public SisoPicker(Context context){
        super(context);
        initView();
    }

    public SisoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_picker, this, false);

        addView(v);

        id_tv_text = (TextView)findViewById(R.id.id_tv_text);
        id_iv_minus = (ImageView) findViewById(R.id.id_iv_minus);
        id_iv_plus = (ImageView) findViewById(R.id.id_iv_plus);

        id_iv_minus.setOnClickListener(this);
        id_iv_plus.setOnClickListener(this);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoPicker);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoPicker, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int arrayResourceId = typedArray.getResourceId(R.styleable.SisoPicker_data_list, 0);
        mDataList = getResources().getStringArray(arrayResourceId);
        mCurrentIdx = typedArray.getInt(R.styleable.SisoPicker_default_idx, 0);
        setText();
        typedArray.recycle();
    }

    private void setText(){
        if(mDataList==null) return;
        id_tv_text.setText(mDataList[mCurrentIdx]);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: sisopicker");
        switch (v.getId()){
            case R.id.id_iv_minus:
                performMinus();
                break;

            case R.id.id_iv_plus:
                performPlus();
                break;
        }
    }

    private void performMinus(){
        if(mCurrentIdx > 0) {
            mCurrentIdx--;
        }
        setText();
    }

    private void performPlus(){
        if(mDataList==null) return;
        if(mCurrentIdx < mDataList.length-1) {
            mCurrentIdx++;
        }
        setText();
    }

    public int getCurrentIndex(){
       return mCurrentIdx;
    }

    public String getCurrentText(){
        return mDataList[mCurrentIdx];
    }
}
