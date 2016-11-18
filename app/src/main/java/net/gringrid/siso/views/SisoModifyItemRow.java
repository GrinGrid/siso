package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * 수정항목에 대한 하나의 row
 */
public class SisoModifyItemRow extends LinearLayout{

    LinearLayout id_ll_row;
    TextView    id_tv_title;
    TextView    id_tv_content;
    LinearLayout id_ll_arrow;

    public SisoModifyItemRow(Context context){
        super(context);
        initView();
    }

    public SisoModifyItemRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoModifyItemRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_modify_item_row, this, false);

        addView(v);

        id_ll_row = (LinearLayout)findViewById(R.id.id_ll_row);
        id_tv_title = (TextView)findViewById(R.id.id_tv_title);
        id_tv_content = (TextView)findViewById(R.id.id_tv_content);
        id_ll_arrow = (LinearLayout)findViewById(R.id.id_ll_arrow);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoModifyItemRow);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoModifyItemRow, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        String title = typedArray.getString(R.styleable.SisoModifyItemRow_modifyItemTitle);
        String content = typedArray.getString(R.styleable.SisoModifyItemRow_modifyItemContent);
        boolean isVisibleArrow = typedArray.getBoolean(R.styleable.SisoModifyItemRow_isVisibleArrow, false);

        if (isVisibleArrow){
            id_ll_arrow.setVisibility(VISIBLE);
        }else{
            id_ll_arrow.setVisibility(INVISIBLE);
        }

        id_tv_title.setText(title);
        id_tv_content.setText(content);

        typedArray.recycle();
    }

    public void setContent(String content){
        id_tv_content.setText(content);
    }
}
