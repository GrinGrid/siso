package net.gringrid.siso.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.models.SitterList;
import net.gringrid.siso.models.Testimonial;

import java.util.List;

/**
 * scrollview 에 넣어야 해서 높이 조정을 해줘야 함. 결국 getview를 갯수만큼 보여줘야 하기에
 * 그냥 갯수만큼 만들어서 사용함
 */
@Deprecated
public class TestimonialAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<Testimonial> mList;

    public TestimonialAdapter(Context mContext, List<Testimonial> list) {
        this.mContext = mContext;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: "+mList.size());
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
            convertView = mInflater.inflate(R.layout.adapter_testimonial_list_row, null);
            holder = new ViewHolder();
            holder.id_tv_name = (TextView)convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_conent = (TextView)convertView.findViewById(R.id.id_tv_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.id_tv_conent.setText(mList.get(position).content);

//        holder.id_tv_addr.setText(mListJuso.get(position).roadAddr);
        return convertView;
    }

    class ViewHolder{
        TextView id_tv_name;
        TextView id_tv_conent;
    }
}
