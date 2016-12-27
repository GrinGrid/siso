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
            holder.id_tv_sender = (TextView)convertView.findViewById(R.id.id_tv_sender);
            holder.id_tv_msg = (TextView)convertView.findViewById(R.id.id_tv_msg);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        item = mList.get(position);
        holder.id_tv_req_date.setText(item.req_date);
        holder.id_tv_sender.setText(item.sender);
        holder.id_tv_msg.setText(item.msg);

        return convertView;
    }

    class ViewHolder{
        TextView id_tv_sender;
        TextView id_tv_req_date;
        TextView id_tv_msg;

    }
}
