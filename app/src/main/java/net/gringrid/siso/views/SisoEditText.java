package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoEditText extends LinearLayout{

    TextView    id_tv_label;
    EditText    id_et_input;
    TextView    id_tv_guide;

    public SisoEditText(Context context){
        super(context);
        initView();
    }

    public SisoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_edittext, this, false);

        addView(v);

        id_tv_label = (TextView)findViewById(R.id.id_tv_label);
        id_et_input = (EditText)findViewById(R.id.id_et_input);
        id_tv_guide = (TextView)findViewById(R.id.id_tv_guide);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoEditText);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoEditText, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        String labelStr = typedArray.getString(R.styleable.SisoEditText_label);
        String inputStr = typedArray.getString(R.styleable.SisoEditText_input);
        String guideStr = typedArray.getString(R.styleable.SisoEditText_guide);

        id_tv_label.setText(labelStr);
        id_et_input.setText(inputStr);
        id_tv_guide.setText(guideStr);
        typedArray.recycle();
    }

    void setLabel(String str){
        id_tv_label.setText(str);
    }
    void setInput(String str){
        id_et_input.setText(str);
    }
    void setGuide(String str){
        id_tv_guide.setText(str);
    }
}
