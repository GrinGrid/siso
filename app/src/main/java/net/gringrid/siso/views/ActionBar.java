package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
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
public class ActionBar extends LinearLayout {

    String TAG = "jiho";

    ImageView   id_iv_left_image;
    TextView    id_tv_text;
    ImageView   id_iv_right_image;
    int         mLeftButtonMode;
    final int   LEFT_BUTTON_MENU        = 0;
    final int   LEFT_BUTTON_PREVIOUS    = 1;

    public interface OnLeftButtonClicked{
       public void OnClicked();
    }

    public ActionBar(Context context){
        super(context);
        initView();
    }

    public ActionBar(Context context, AttributeSet attrs) {

        super(context, attrs);

        initView();
        getAttrs(attrs);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_actionbar, this, false);

        addView(v);

        id_iv_left_image = (ImageView)findViewById(R.id.id_iv_left_image);
        id_tv_text = (TextView)findViewById(R.id.id_tv_text);
        id_iv_right_image = (ImageView)findViewById(R.id.id_iv_right_image);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ActionBar);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ActionBar, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        int leftImageResID = typedArray.getResourceId(R.styleable.ActionBar_left_image, R.drawable.icon_navigation_menu);
        id_iv_left_image.setImageResource(leftImageResID);
        
        if(leftImageResID == R.drawable.icon_navigation_menu ){
            mLeftButtonMode = LEFT_BUTTON_MENU;
        }else if(leftImageResID == R.drawable.icon_navigation_previous){
            mLeftButtonMode = LEFT_BUTTON_PREVIOUS;
        }
        
        id_iv_left_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftButtonMode == LEFT_BUTTON_MENU){
                    menuClicked();
                }else if (mLeftButtonMode == LEFT_BUTTON_PREVIOUS){
                    previousClicked();
                }
            }
        });

        String textStr = typedArray.getString(R.styleable.ActionBar_text);
        id_tv_text.setText(textStr);

        int rightImageResID = typedArray.getResourceId(R.styleable.ActionBar_right_image, R.color.colorWhite);
        id_iv_right_image.setImageResource(rightImageResID);

        typedArray.recycle();
    }

    private void previousClicked() {

    }

    private void menuClicked() {

        Log.d(TAG, "menuClicked: Menu clicked");
    }

    void setLeftImage(int resID){
        id_iv_left_image.setImageResource(resID);
    }

    void setText(String str){
        id_tv_text.setText(str);
    }

    void setRightImage(int resID){
        id_iv_right_image.setImageResource(resID);
    }

    public void setTextAlign(int gravity){
        id_tv_text.setGravity(gravity);
    }

}
