package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoToggleButton extends LinearLayout{

    private static final String TAG = "jiho";
    LinearLayout id_ll_toggle;
    ImageView   id_iv_toggle_img;
    TextView    id_tv_label;
    Drawable    mSelectedImgRes;
    Drawable    mNormalImgRes;
    private boolean mIsChecked;
    private boolean mIsAddClickListener;

    OnToggleChangedListener mlistener;

    public interface OnToggleChangedListener{
        void onChanged(View view);
    }

    public void setOnToggleChangedListener(OnToggleChangedListener listener){
        mlistener = listener;
    }

    public SisoToggleButton(Context context){
        super(context);
        initView();
        registEvent();
    }

    public SisoToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
        registEvent();
    }

    public SisoToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
        registEvent();
    }

    private void registEvent() {
        Log.d(TAG, "registEvent: "+mIsAddClickListener);
        if(mIsAddClickListener){
            id_ll_toggle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleCheck();
                }
            });
        }
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_toggle_button, this, false);

        addView(v);

        id_ll_toggle = (LinearLayout) findViewById(R.id.id_ll_toggle);
        id_tv_label = (TextView)findViewById(R.id.id_tv_label);
        id_iv_toggle_img = (ImageView) findViewById(R.id.id_iv_toggle_img);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoToggleButton);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoToggleButton, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        Log.d(TAG, "setTypeArray: ");

        String labelStr = typedArray.getString(R.styleable.SisoToggleButton_toggleLabel);
        mNormalImgRes = typedArray.getDrawable(R.styleable.SisoToggleButton_toggle_normal);
        mSelectedImgRes= typedArray.getDrawable(R.styleable.SisoToggleButton_toggle_selected);
        mIsAddClickListener = typedArray.getBoolean(R.styleable.SisoToggleButton_isAddClickListener, true);

        boolean isChecked = typedArray.getBoolean(R.styleable.SisoToggleButton_isSelected, false);
        setChecked(isChecked);

        if(!TextUtils.isEmpty(labelStr)){
            id_tv_label.setVisibility(View.VISIBLE);
            id_tv_label.setText(labelStr);
        }

        typedArray.recycle();
    }

    public void setChecked(boolean isChecked) {
        mIsChecked = isChecked;
        toggleCheck();
    }

    private void toggleCheck(){
        if (mIsChecked){
            id_iv_toggle_img.setImageDrawable(mSelectedImgRes);
            id_tv_label.setTextColor(ContextCompat.getColor(getContext(), R.color.colorContentTextPink));
        }else{
            id_iv_toggle_img.setImageDrawable(mNormalImgRes);
            id_tv_label.setTextColor(ContextCompat.getColor(getContext(), R.color.color7A7A7A));
        }

        mIsChecked = !mIsChecked;
        if(mlistener!=null){
            mlistener.onChanged(SisoToggleButton.this);
        }
    }

    public boolean isChecked(){
        return !mIsChecked;
    }

    void setLabel(String str){
        id_tv_label.setText(str);
    }
}
