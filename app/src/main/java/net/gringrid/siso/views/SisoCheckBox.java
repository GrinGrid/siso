package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoCheckBox extends LinearLayout{

    LinearLayout id_ll_checkbox;
    ImageView   iv_checkbox;
    TextView    id_tv_title;
    TextView    id_tv_title_comment;
    boolean     mIsChecked;
    boolean     mIsAddClickListener;

    public SisoCheckBox(Context context){
        super(context);
        initView();
    }

    public SisoCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_checkbox, this, false);

        addView(v);

        id_ll_checkbox = (LinearLayout)findViewById(R.id.id_ll_checkbox);
        id_tv_title = (TextView)findViewById(R.id.id_tv_title);
        id_tv_title_comment = (TextView)findViewById(R.id.id_tv_title_comment);
        iv_checkbox = (ImageView) findViewById(R.id.iv_checkbox);

        if(mIsAddClickListener){
            id_ll_checkbox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleCheck();
                }
            });
        }
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoCheckBox);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoCheckBox, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        String title = typedArray.getString(R.styleable.SisoCheckBox_checkboxTitle);
        String titleComment = typedArray.getString(R.styleable.SisoCheckBox_checkboxTitleComment);
        boolean isChecked = typedArray.getBoolean(R.styleable.SisoCheckBox_isChecked, false);
        boolean isDisabled = typedArray.getBoolean(R.styleable.SisoCheckBox_isDisabled, false);
        mIsAddClickListener = typedArray.getBoolean(R.styleable.SisoCheckBox_isSetClickListener, true);

        if (isChecked){
            iv_checkbox.setImageResource(R.drawable.icon_checkbox_on);
        }

        if (isDisabled){
            iv_checkbox.setImageResource(R.drawable.icon_checkbox_disable);
        }

        id_tv_title.setText(title);
        id_tv_title_comment.setText(titleComment);
//        id_tv_label.setTextColor(labelColor);

        typedArray.recycle();
    }

    private void toggleCheck(){
        if (mIsChecked){
            iv_checkbox.setImageResource(R.drawable.icon_checkbox_off);
//            id_tv_title.setTextColor(ContextCompat.getColor(getContext(), R.color.color666666));
        }else{
            iv_checkbox.setImageResource(R.drawable.icon_checkbox_on);
//            id_tv_label.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        }

        mIsChecked = !mIsChecked;
    }

    public boolean isChecked(){
        return mIsChecked;
    }

    public void setCheck(boolean isCheck){
        mIsChecked = !isCheck;
        toggleCheck();
    }
}
