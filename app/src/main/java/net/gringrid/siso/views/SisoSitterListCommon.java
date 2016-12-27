package net.gringrid.siso.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Child;
import net.gringrid.siso.models.SitterListItem;
import net.gringrid.siso.util.SisoUtil;


/**
 * 시터리스트 공통 부분
 */
public class SisoSitterListCommon extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "jiho";
    TextView id_tv_name;
    TextView id_tv_brief;
    TextView id_tv_addr1;
    TextView id_tv_distance;
    TextView id_tv_salary;
    TextView id_tv_testimonial_cnt;
    TextView id_tv_commute;
    ImageView id_iv_profile;
    ImageView id_iv_favorite;

    FrameLayout.LayoutParams mLp;

    public SisoSitterListCommon(Context context){
        super(context);
        initView();
    }

    public SisoSitterListCommon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public SisoSitterListCommon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        Log.d(TAG, "initView: initView ");
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.custom_sitter_list_common, this, false);
        int sidePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics());
        int mPhotoWidth = ((SisoUtil.getScreenWidth(getContext()) - sidePadding)/10) * 3;
        mLp = new FrameLayout.LayoutParams(mPhotoWidth,mPhotoWidth);

        addView(v);

        id_tv_name = (TextView)v.findViewById(R.id.id_tv_name);
        id_tv_brief = (TextView)v.findViewById(R.id.id_tv_brief);
        id_tv_addr1 = (TextView)v.findViewById(R.id.id_tv_addr1);
        id_tv_distance = (TextView)v.findViewById(R.id.id_tv_distance);
        id_tv_salary = (TextView)v.findViewById(R.id.id_tv_salary);
        id_tv_testimonial_cnt = (TextView)v.findViewById(R.id.id_tv_testimonial_cnt);
        id_tv_commute = (TextView)v.findViewById(R.id.id_tv_commute);
        id_iv_profile = (ImageView)v.findViewById(R.id.id_iv_profile);
        id_iv_favorite = (ImageView)v.findViewById(R.id.id_iv_favorite);
        id_iv_profile.setLayoutParams(mLp);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoSitterListCommon);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SisoSitterListCommon, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        typedArray.recycle();
    }

    public void setData(SitterListItem item) {
        id_tv_name.setText(item.name);
        id_tv_brief.setText(item.brief);
        id_tv_addr1.setText(item.addr);
        id_tv_distance.setText(item.distance);
        id_tv_salary.setText(item.salary);
        id_tv_testimonial_cnt.setText(item.testimonial_cnt);
        id_tv_commute.setText(item.commute);

        // 관심여부 아이콘
        if(TextUtils.isEmpty(item.favorite) || item.favorite.equals("N")){
            id_iv_favorite.setVisibility(View.GONE);
        }else if(item.favorite.equals("Y")){
            id_iv_favorite.setVisibility(View.VISIBLE);
        }
        Picasso.with(getContext()).load(item.img).into(id_iv_profile);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
        }
    }
}
