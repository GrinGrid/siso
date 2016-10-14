package net.gringrid.siso.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.models.SitterList;
import net.gringrid.siso.network.restapi.AddrAPI;

import java.util.List;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class SitterListSisoAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<SitterList> mList;

    public SitterListSisoAdapter(Context mContext, List<SitterList> list) {
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

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

//        holder.id_tv_addr.setText(mListJuso.get(position).roadAddr);
        return convertView;
    }

    class ViewHolder{
        TextView id_tv_name;
        TextView id_tv_brief;
        TextView id_tv_addr1;
    }
}
