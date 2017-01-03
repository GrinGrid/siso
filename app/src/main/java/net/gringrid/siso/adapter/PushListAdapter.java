package net.gringrid.siso.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gringrid.siso.R;
import net.gringrid.siso.models.PushListItem;
import net.gringrid.siso.models.SitterListItem;
import net.gringrid.siso.util.SisoUtil;

import java.util.List;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class PushListAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    Context mContext;
    List<PushListItem> mList;

    public PushListAdapter(Context mContext, List<PushListItem> list) {
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
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        PushListItem item;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.adapter_push_list_row, null);
            holder = new ViewHolder();
            holder.id_tv_req_date = (TextView)convertView.findViewById(R.id.id_tv_req_date);
//            holder.id_tv_sender = (TextView)convertView.findViewById(R.id.id_tv_sender);
            holder.id_tv_msg = (TextView)convertView.findViewById(R.id.id_tv_msg);
            holder.id_ll_container = (LinearLayout)convertView.findViewById(R.id.id_ll_container);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        item = mList.get(position);

        if(!TextUtils.isEmpty(item.is_read) && item.is_read.equals("Y")){
            holder.id_ll_container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sisoPushNewRow));
        }else{
            holder.id_ll_container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        }

        holder.id_tv_req_date.setText(item.req_date);
//        holder.id_iv_photo.setText(item.sender);
        holder.id_tv_msg.setText(item.msg);

        return convertView;
    }

    class ViewHolder{
        LinearLayout id_ll_container;
        ImageView id_iv_photo;
        TextView id_tv_req_date;
        TextView id_tv_msg;

    }
}
