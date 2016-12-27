package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;


/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoDetailRow extends LinearLayout {

    TextView    id_tv_item_name;
    TextView    id_tv_item_value;

    public SisoDetailRow(Context context){
        super(context);
        initView();
    }

    public SisoDetailRow(Context context, int name, String value){
        super(context);
        initView();
        if(!TextUtils.isEmpty(value)){
            setName(name);
            setValue(value);
        }
    }

    public SisoDetailRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoDetailRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_detail_row, this, false);

        addView(v);

        id_tv_item_name = (TextView) findViewById(R.id.id_tv_item_name);
        id_tv_item_value = (TextView)findViewById(R.id.id_tv_item_value);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoDetailRow);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoDetailRow, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int itemName = typedArray.getResourceId(R.styleable.SisoDetailRow_item_name, 0);
        int itemValue = typedArray.getResourceId(R.styleable.SisoDetailRow_item_value, 0);

        setName(itemName);
        setValue(itemValue);

        typedArray.recycle();
    }

    public void setName(int id){
        id_tv_item_name.setText(id);
    }

    public void setValue(int id){
        id_tv_item_value.setText(id);
    }

    public void setValue(String str){
        id_tv_item_value.setText(str);
    }
}
