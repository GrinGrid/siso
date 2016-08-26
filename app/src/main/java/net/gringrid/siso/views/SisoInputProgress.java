package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * Created by choijiho on 16. 7. 13..
 */
public class SisoInputProgress extends LinearLayout{

    LinearLayout ll_container;
    final int mImageRightPadding = 3; // 3dp

    public SisoInputProgress(Context context){
        super(context);
        initView();
    }

    public SisoInputProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoInputProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_input_progress, this, false);

        addView(v);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoInputProgress);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoInputProgress, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int total_stage = typedArray.getInt(R.styleable.SisoInputProgress_total_stage, 1);
        int current_stage = typedArray.getInt(R.styleable.SisoInputProgress_current_stage, 1);
        float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (mImageRightPadding * scale + 0.5f);

        for ( int i=0; i<total_stage; i++ ){
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            imageView.setPadding(0,0,padding_in_px,0);

            imageView.setLayoutParams(lp);
            if (i==current_stage){
                imageView.setImageResource(R.drawable.icon_process_on);
            }else{
                imageView.setImageResource(R.drawable.icon_process_off);
            }
            ll_container.addView(imageView);
        }

        typedArray.recycle();
    }
}
