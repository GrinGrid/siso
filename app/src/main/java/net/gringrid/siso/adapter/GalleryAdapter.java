package net.gringrid.siso.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.gringrid.siso.R;
import net.gringrid.siso.fragments.Sitter11SelectPhotoFragment;
import java.util.ArrayList;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class GalleryAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;
    private int mImageWidth;
    LinearLayout.LayoutParams mLp;
    Context mContext;
    ArrayList<Sitter11SelectPhotoFragment.PhotoData> mPhotoList;

    public GalleryAdapter(Context mContext, ArrayList<Sitter11SelectPhotoFragment.PhotoData> list) {
        this.mContext = mContext;
        this.mPhotoList = list;

        mInflater = LayoutInflater.from(mContext);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.d(TAG, "GalleryAdapter: screenwidth : "+screenWidth);
        mImageWidth = screenWidth / 3;
        mLp = new LinearLayout.LayoutParams(mImageWidth, mImageWidth);
        Log.d(TAG, "GalleryAdapter: mImageWidth : "+mImageWidth);
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
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
            convertView = mInflater.inflate(R.layout.adapter_select_photo_row, null);
            holder = new ViewHolder();
            holder.id_iv_0 = (ImageView)convertView.findViewById(R.id.id_iv_0);
            holder.id_iv_1 = (ImageView)convertView.findViewById(R.id.id_iv_1);
            holder.id_iv_2 = (ImageView)convertView.findViewById(R.id.id_iv_2);

            holder.id_iv_0.setLayoutParams(mLp);
            holder.id_iv_1.setLayoutParams(mLp);
            holder.id_iv_2.setLayoutParams(mLp);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.id_iv_0.setImageURI(mPhotoList.get(position++).thumbNail);
        holder.id_iv_1.setImageURI(mPhotoList.get(position++).thumbNail);
        holder.id_iv_2.setImageURI(mPhotoList.get(position).thumbNail);

//        final int finalPosition = position;
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: position "+finalPosition);
//                Log.d(TAG, "onClick: mPhotoList.get(finalPosition).imageId : "+mPhotoList.get(finalPosition).imageId);
//            }
//        });
//        holder.imageView.setImageURI(mPhotoList.get(position).thumbNail);

        return convertView;
//        ViewHolder holder;
//
//        if(convertView == null){
//            Log.d(TAG, "getView: convertView is null");
//            holder = new ViewHolder();
//            convertView = new ImageView(mContext);
//
//
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mImageWidth, mImageWidth);
//            lp.setMargins(0,0,0,0);
//            holder.imageView = (ImageView)convertView;
//            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            holder.imageView.setAdjustViewBounds(false);
////            holder.imageView.setLayoutParams(new GridView.LayoutParams(mImageWidth,mImageWidth));
//            holder.imageView.setLayoutParams(lp);
////            holder.imageView.setPadding(2,2,2,2);
//            holder.imageView.setTag(holder);
//        }else{
//            holder = (ViewHolder)convertView.getTag();
//        }
//
//        final int finalPosition = position;
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: position "+finalPosition);
//                Log.d(TAG, "onClick: mPhotoList.get(finalPosition).imageId : "+mPhotoList.get(finalPosition).imageId);
//            }
//        });
//        holder.imageView.setImageURI(mPhotoList.get(position).thumbNail);
    }

    class ViewHolder{
        ImageView id_iv_0;
        ImageView id_iv_1;
        ImageView id_iv_2;
    }
}
