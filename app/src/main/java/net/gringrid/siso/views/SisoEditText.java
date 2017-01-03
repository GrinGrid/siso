package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

import static net.gringrid.siso.R.styleable.SisoEditText_isReadOnly;

/**
 * SISO Custom Edit Box
 */
public class SisoEditText extends LinearLayout{

    private static final String TAG = "jiho";
    FrameLayout id_fl_edit_text;
    TextView    id_tv_label;
    EditText    id_et_input;
    TextView    id_tv_guide;
    TextView    id_tv_clear;

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

        id_fl_edit_text = (FrameLayout)findViewById(R.id.id_fl_edit_text);
        id_tv_label = (TextView)findViewById(R.id.id_tv_label);
        id_et_input = (EditText)findViewById(R.id.id_et_input);
        id_tv_guide = (TextView)findViewById(R.id.id_tv_guide);
        id_tv_clear = (TextView)findViewById(R.id.id_tv_clear);
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
        String inputType = typedArray.getString(R.styleable.SisoEditText_input_type);
        boolean isReadOnly = typedArray.getBoolean(R.styleable.SisoEditText_isReadOnly, false);

        if(isReadOnly){
            id_et_input.setEnabled(false);
            id_tv_clear.setVisibility(GONE);
        }

        int maxlength = typedArray.getInteger(R.styleable.SisoEditText_maxlength, 0);
        if(maxlength != 0){
            InputFilter[] inputFilters = new InputFilter[1];
            inputFilters[0] = new InputFilter.LengthFilter(maxlength);
            id_et_input.setFilters(inputFilters);
        }
        boolean isHiddenLabel = typedArray.getBoolean(R.styleable.SisoEditText_isHiddenLabel, false);

        id_tv_label.setText(labelStr);
        id_et_input.setHint(inputStr);
        id_tv_guide.setText(guideStr);

        if (!TextUtils.isEmpty(inputType)){
            if ( inputType.equals("number") ){
                id_et_input.setInputType(InputType.TYPE_CLASS_NUMBER);
            }else if ( inputType.equals("phone") ){
                id_et_input.setInputType(InputType.TYPE_CLASS_PHONE);
            }else if ( inputType.equals("email") ){
                id_et_input.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        }

        if(isHiddenLabel){
            id_tv_label.setVisibility(GONE);
        }

        id_et_input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    id_fl_edit_text.setBackgroundResource(R.drawable.edit_text_focus);
                }else{
                    id_fl_edit_text.setBackgroundResource(R.drawable.edit_text_normal);
                }
            }
        });
        id_tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                id_et_input.setText("");
            }
        });
        typedArray.recycle();
    }

    public Editable getText(){
        return id_et_input.getText();
    }
    public void setLabel(String str){ id_tv_label.setText(str); }
    public void setLabel(int strId){ id_tv_label.setText(strId); }
    public void setInput(String str){ id_et_input.setText(str); }
    public void setInput(int strId){ id_et_input.setText(strId); }
    public void setHint(int strId){ id_et_input.setHint(strId); }

    void setGuide(String str){
        id_tv_guide.setText(str);
    }
}
