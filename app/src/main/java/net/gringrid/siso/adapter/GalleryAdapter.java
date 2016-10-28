package net.gringrid.siso.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.gringrid.siso.R;
import net.gringrid.siso.fragments.Sitter11SelectPhotoFragment;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SisoUtil;

import java.util.ArrayList;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class GalleryAdapter extends BaseAdapter implements View.OnClickListener {


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;
    private int mImageWidth;
    LinearLayout.LayoutParams mLp;
    Context mContext;
    Sitter11SelectPhotoFragment mFragment;

    ArrayList<Sitter11SelectPhotoFragment.PhotoData> mPhotoList;
    int mTotalSize;

    ImageLoader mImageLoader;

    public GalleryAdapter(Context mContext, ArrayList<Sitter11SelectPhotoFragment.PhotoData> list, Fragment fragment) {
        this.mContext = mContext;
        this.mPhotoList = list;
        this.mTotalSize = mPhotoList.size();
        this.mFragment = (Sitter11SelectPhotoFragment)fragment;

        mInflater = LayoutInflater.from(mContext);

        int screenWidth = SisoUtil.getScreenWidth(mContext);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics());
        mImageWidth = (screenWidth - margin*8) / 3;
        mLp = new LinearLayout.LayoutParams(mImageWidth, mImageWidth);
        mLp.setMargins(margin,0,margin,0);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
//        Log.d(TAG, "getCount: "+(int)Math.ceil(mPhotoList.size()/3.0));
        return (int)Math.ceil(mPhotoList.size()/3.0);
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

            holder.id_iv_0.setOnClickListener(this);
            holder.id_iv_1.setOnClickListener(this);
            holder.id_iv_2.setOnClickListener(this);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        int imgIndex = position * 3;



        mImageLoader.displayImage(mPhotoList.get(imgIndex).thumbNail, holder.id_iv_0);
//        holder.id_iv_0.setImageURI(mPhotoList.get(imgIndex).thumbNail);
        holder.id_iv_0.setTag(imgIndex++);

        if(imgIndex < mTotalSize) {
            mImageLoader.displayImage(mPhotoList.get(imgIndex).thumbNail, holder.id_iv_1);
//            holder.id_iv_1.setImageURI(mPhotoList.get(imgIndex).thumbNail);
            holder.id_iv_1.setTag(imgIndex++);
        }else {
            holder.id_iv_1.setImageURI(null);
        }

        if(imgIndex < mTotalSize){
            mImageLoader.displayImage(mPhotoList.get(imgIndex).thumbNail, holder.id_iv_2);
//            holder.id_iv_2.setImageURI(mPhotoList.get(imgIndex).thumbNail);
            holder.id_iv_2.setTag(imgIndex);
        } else{
            holder.id_iv_2.setImageURI(null);
        }

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
    }

    @Override
    public void onClick(View v) {

        mFragment.selectPhoto(mPhotoList.get((int)v.getTag()).imageId);
//        Log.d(TAG, "onClick: tag "+mPhotoList.get((int)v.getTag()).imageId);
    }

    class ViewHolder{
        ImageView id_iv_0;
        ImageView id_iv_1;
        ImageView id_iv_2;
    }
}
