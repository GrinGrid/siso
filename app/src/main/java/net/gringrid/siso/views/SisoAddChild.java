package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Child;


/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoAddChild extends LinearLayout implements View.OnClickListener {

    ImageView   id_iv_child_icon;
    EditText    id_et_name;
    EditText    id_et_birth;
    ImageView   id_iv_checkbox;
    TextView    id_tv_clear;
    LinearLayout id_ll_newborn_gender;

    SisoToggleButton id_tg_btn_boy;
    SisoToggleButton id_tg_btn_girl;
    SisoToggleButton id_tg_btn_newborn;

    private int[] mRadioGender = new int[]{R.id.id_tg_btn_boy, R.id.id_tg_btn_girl, R.id.id_tg_btn_newborn};

    boolean     mIsChecked;
    boolean     mIsExpect;

    OnChildFormRemoveListener mlistener;

    public interface OnChildFormRemoveListener{
        void onRemoved(int index);
    }

    public void setOnChildFormRevoeListener(OnChildFormRemoveListener listener){
        mlistener = listener;
    }

    public SisoAddChild(Context context){
        super(context);
        initView();
    }

    public SisoAddChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoAddChild(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_add_child_form, this, false);

        addView(v);

        id_iv_child_icon = (ImageView)findViewById(R.id.id_iv_child_icon);
        id_et_name = (EditText)findViewById(R.id.id_et_name);
        id_et_birth = (EditText)findViewById(R.id.id_et_birth);
        id_iv_checkbox = (ImageView)findViewById(R.id.id_iv_checkbox);
        id_tv_clear = (TextView)findViewById(R.id.id_tv_clear);
        id_ll_newborn_gender = (LinearLayout)findViewById(R.id.id_ll_newborn_gender);
        id_tg_btn_boy = (SisoToggleButton) findViewById(R.id.id_tg_btn_boy);
        id_tg_btn_girl = (SisoToggleButton) findViewById(R.id.id_tg_btn_girl);
        id_tg_btn_newborn = (SisoToggleButton) findViewById(R.id.id_tg_btn_newborn);

        id_iv_checkbox.setOnClickListener(this);
        id_tv_clear.setOnClickListener(this);
        id_tg_btn_boy.setOnClickListener(this);
        id_tg_btn_girl.setOnClickListener(this);
        id_tg_btn_newborn.setOnClickListener(this);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoAddChild);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoAddChild, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int childIconRscId = typedArray.getResourceId(R.styleable.SisoAddChild_child_icon, 0);
        id_iv_child_icon.setImageResource(childIconRscId);

        typedArray.recycle();
    }

    private void toggleCheck(){
        if (mIsChecked){
            id_iv_checkbox.setImageResource(R.drawable.icon_checkbox_off);
        }else{
            id_iv_checkbox.setImageResource(R.drawable.icon_checkbox_on);
        }
        mIsChecked = !mIsChecked;
    }

    public void setCheck(boolean isCheck){
        mIsChecked = !isCheck;
        toggleCheck();
    }

    public void setIconResource(int id){
        if(id == R.drawable.ic_newborn_small){
            id_ll_newborn_gender.setVisibility(View.VISIBLE);
            mIsExpect = true;
        }else{
            id_ll_newborn_gender.setVisibility(View.GONE);
            mIsExpect = false;
        }
        id_iv_child_icon.setImageResource(id);
    }

    private  int getRadioValue(){
        for(int i=0; i<mRadioGender.length; i++){
            if (((SisoToggleButton)findViewById(mRadioGender[i])).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    public Child getValue(){
        Child child = new Child();
        child.name = id_et_name.getText().toString();
        child.birth = Integer.parseInt(id_et_birth.getText().toString());
        child.isCare = mIsChecked?1:0;
        child.gender = getRadioValue();
        child.isExpect = mIsExpect?1:0;
        return child;
    }

    private void selectRadio(int selectItem) {
        for(int src:mRadioGender){
            if(src == selectItem){
                ((SisoToggleButton)findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)findViewById(src)).setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_tg_btn_boy:
            case R.id.id_tg_btn_girl:
            case R.id.id_tg_btn_newborn:
                selectRadio(v.getId());
                break;
            case R.id.id_iv_checkbox:
                toggleCheck();
                break;
            case R.id.id_tv_clear:
                ((ViewGroup)SisoAddChild.this.getParent()).removeView(SisoAddChild.this);
                if(mlistener!=null){
                    mlistener.onRemoved((Integer)SisoAddChild.this.getTag());
                }
                break;
        }
    }
}
