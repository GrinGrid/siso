package net.gringrid.siso.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.gringrid.siso.fragments.Sitter11_sub1Fragment;
import java.util.ArrayList;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class GalleryAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private int mImageWidth;
    Context mContext;
    ArrayList<Sitter11_sub1Fragment.PhotoData> mPhotoList;

    public GalleryAdapter(Context mContext, ArrayList<Sitter11_sub1Fragment.PhotoData> list) {
        this.mContext = mContext;
        this.mPhotoList = list;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.d(TAG, "GalleryAdapter: screenwidth : "+screenWidth);
        mImageWidth = screenWidth / 4;
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
            Log.d(TAG, "getView: convertView is null");
            holder = new ViewHolder();
            convertView = new ImageView(mContext);


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mImageWidth, mImageWidth);
            lp.setMargins(0,0,0,0);
            holder.imageView = (ImageView)convertView;
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageView.setAdjustViewBounds(false);
//            holder.imageView.setLayoutParams(new GridView.LayoutParams(mImageWidth,mImageWidth));
            holder.imageView.setLayoutParams(lp);
//            holder.imageView.setPadding(2,2,2,2);
            holder.imageView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final int finalPosition = position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: position "+finalPosition);
                Log.d(TAG, "onClick: mPhotoList.get(finalPosition).imageId : "+mPhotoList.get(finalPosition).imageId);
            }
        });
        holder.imageView.setImageURI(mPhotoList.get(position).thumbNail);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
    }
}
