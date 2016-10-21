package net.gringrid.siso.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.network.restapi.AddrAPI;

import java.util.List;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class AddrAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    AddrAPI.AddrOutput mList;
    List<AddrAPI.Juso> mListJuso;

    public AddrAdapter(Context mContext, AddrAPI.AddrOutput list) {
        this.mContext = mContext;
        this.mList = list;
        mListJuso = mList.juso;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListJuso.size();
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
            convertView = mInflater.inflate(R.layout.adapter_addr_row, null);
            holder = new ViewHolder();
            holder.id_tv_num = (TextView)convertView.findViewById(R.id.id_tv_num);
            holder.id_tv_addr = (TextView)convertView.findViewById(R.id.id_tv_addr);
            holder.id_tv_addr_jibun = (TextView)convertView.findViewById(R.id.id_tv_addr_jibun);
            holder.id_tv_post_no = (TextView)convertView.findViewById(R.id.id_tv_post_no);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.id_tv_addr.setText(mListJuso.get(position).roadAddr);
        holder.id_tv_addr_jibun.setText(mListJuso.get(position).jibunAddr);
        holder.id_tv_post_no.setText(mListJuso.get(position).zipNo);
        if (position%2==0){
            convertView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorEBEBEB));
        }else{
            convertView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorWhite));
        }
        return convertView;
    }

    class ViewHolder{
        TextView id_tv_num;
        TextView id_tv_addr;
        TextView id_tv_addr_jibun;
        TextView id_tv_post_no;
    }
}
