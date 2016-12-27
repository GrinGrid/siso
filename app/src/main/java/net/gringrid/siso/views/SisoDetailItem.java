package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Child;

import java.util.ArrayList;
import java.util.List;


/**
 * 시소 상세정보에서 표시될 여러개의 아이콘을 담을 한 줄 row
 * Created by choijiho on 16. 7. 13..
 */
public class SisoDetailItem extends LinearLayout {

    private final int ITEM_MAX_NUM = 5;
    private TextView    id_tv_item_title;
    private LinearLayout id_ll_item_list;
    private Context mContext;
    private int mItemWidth;

    public SisoDetailItem(Context context){
        super(context);
        mContext = context;
        initView();
    }

    public SisoDetailItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SisoDetailItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_detail_item, this, false);
        addView(v);

        id_ll_item_list = (LinearLayout) findViewById(R.id.id_ll_item_list);
        id_tv_item_title = (TextView)findViewById(R.id.id_tv_item_title);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        mItemWidth = screenWidth / ITEM_MAX_NUM;
    }


    public void setData(int titleRsc, List<DetailItem> list){

        LinearLayout item_layout;
        ImageView item_image;
        TextView item_text;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mItemWidth, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams itemLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        id_tv_item_title.setText(titleRsc);

        if(list.size() < ITEM_MAX_NUM) {
            int margin = ((mItemWidth/list.size()) * (ITEM_MAX_NUM - list.size()))/2;
            lp.setMargins(margin, 0, margin, 0);
        }

        for(DetailItem item : list){
            item_layout = new LinearLayout(mContext);
            item_image = new ImageView(mContext);
            item_text = new TextView(mContext);

            item_layout.setLayoutParams(lp);
            item_layout.setOrientation(VERTICAL);
            item_layout.setGravity(Gravity.CENTER);
            item_image.setLayoutParams(itemLp);
            item_text.setLayoutParams(itemLp);
            if(Build.VERSION.SDK_INT<23){
                item_text.setTextAppearance(mContext,R.style.DetailItemText);
            }else{
                item_text.setTextAppearance(R.style.DetailItemText);
            }
            item_text.setGravity(Gravity.CENTER);


            item_image.setImageResource(item.itemImgRsc);

            if(!TextUtils.isEmpty(item.itemTxt)){
                item_text.setText(item.itemTxt);
            }else{
                item_text.setText(item.itemTxtRsc);
            }

            item_layout.addView(item_image);
            item_layout.addView(item_text);

            id_ll_item_list.addView(item_layout);
        }
    }

    public static class DetailItem{
        public int itemImgRsc;
        public int itemTxtRsc;
        public String itemTxt;

        public DetailItem(int imgRsc, int txtRsc){
            this.itemImgRsc = imgRsc;
            this.itemTxtRsc = txtRsc;
        }
        public DetailItem(int imgRsc, String txt){
            this.itemImgRsc = imgRsc;
            this.itemTxt = txt;
        }
    }



}
