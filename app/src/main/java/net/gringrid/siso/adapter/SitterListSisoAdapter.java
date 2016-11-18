package net.gringrid.siso.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class SitterListSisoAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<SitterListItem> mList;

    public SitterListSisoAdapter(Context mContext, List<SitterListItem> list) {
        this.mContext = mContext;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
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

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.id_tv_addr1.setText(mList.get(position).addr);
        holder.id_tv_name.setText(mList.get(position).name+" 시터("+mList.get(position).age+"세)");
        holder.id_tv_brief.setText(mList.get(position).brief);
        holder.id_tv_distance.setText(mList.get(position).distance);
        holder.id_tv_salary.setText(SisoUtil.getListSalary(mContext, mList.get(position).salary));
        holder.id_tv_testimonial_cnt.setText("리뷰 "+mList.get(position).testimonialCnt+"건");
        holder.id_tv_commute.setText(SisoUtil.getRadioValue(mContext, R.array.radio_commute, mList.get(position).commute));
        Picasso.with(mContext).load(mList.get(position).img).into(holder.id_iv_profile);

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

    }
}
