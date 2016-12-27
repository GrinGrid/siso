package net.gringrid.siso.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gringrid.siso.R;
import net.gringrid.siso.models.SitterListItem;
import net.gringrid.siso.util.SisoUtil;

import java.util.List;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class ParentListFavoriteAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<SitterListItem> mList;
    int mPhotoWidth;
    FrameLayout.LayoutParams mLp;

    public ParentListFavoriteAdapter(Context mContext, List<SitterListItem> list) {
        this.mContext = mContext;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
        int sidePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics());
        mPhotoWidth = ((SisoUtil.getScreenWidth(mContext) - sidePadding)/10) * 3;
        Log.d(TAG, "SitterListSisoAdapter: photoWidth : "+mPhotoWidth);
        mLp = new FrameLayout.LayoutParams(mPhotoWidth,mPhotoWidth);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SitterListItem item;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.adapter_sitter_list_siso_row, null);
            holder = new ViewHolder();
            holder.id_tv_name = (TextView)convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_brief = (TextView)convertView.findViewById(R.id.id_tv_brief);
            holder.id_tv_addr1 = (TextView)convertView.findViewById(R.id.id_tv_addr1);
            holder.id_tv_distance = (TextView)convertView.findViewById(R.id.id_tv_distance);
            holder.id_tv_salary = (TextView)convertView.findViewById(R.id.id_tv_salary);
            holder.id_tv_testimonial_cnt = (TextView)convertView.findViewById(R.id.id_tv_testimonial_cnt);
            holder.id_tv_commute = (TextView)convertView.findViewById(R.id.id_tv_commute);
            holder.id_iv_profile = (ImageView)convertView.findViewById(R.id.id_iv_profile);
            holder.id_iv_favorite = (ImageView)convertView.findViewById(R.id.id_iv_favorite);
            holder.id_iv_profile.setLayoutParams(mLp);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        item = mList.get(position);
        holder.id_tv_addr1.setText(item.addr);
        holder.id_tv_name.setText(item.name+" 시터("+item.age+"세)");
        holder.id_tv_brief.setText(item.brief);
        holder.id_tv_distance.setText(item.distance);
        holder.id_tv_salary.setText(SisoUtil.getListSalary(mContext, item.salary));
        holder.id_tv_testimonial_cnt.setText("리뷰 "+item.testimonial_cnt+"건");
        holder.id_tv_commute.setText(SisoUtil.getRadioValue(mContext, R.array.radio_commute, item.commute));

        // 관심여부 아이콘
        if(TextUtils.isEmpty(item.favorite) || item.favorite.equals("N")){
            holder.id_iv_favorite.setVisibility(View.GONE);
        }else if(item.favorite.equals("Y")){
            holder.id_iv_favorite.setVisibility(View.VISIBLE);
        }
        Picasso.with(mContext).load(item.img).into(holder.id_iv_profile);

        return convertView;
    }

    class ViewHolder{
        TextView id_tv_name;
        TextView id_tv_brief;
        TextView id_tv_addr1;
        TextView id_tv_distance;
        TextView id_tv_salary;
        TextView id_tv_testimonial_cnt;
        TextView id_tv_commute;
        ImageView id_iv_profile;
        ImageView id_iv_favorite;

    }
}
