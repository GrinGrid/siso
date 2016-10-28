package net.gringrid.siso.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.GalleryAdapter;
import net.gringrid.siso.adapter.GalleryBitmapAdapter;
import net.gringrid.siso.util.SharedData;

import java.util.ArrayList;


/**
 * 갤러리에서 사진선택
 * A simple {@link Fragment} subclass.
 */
public class Sitter11SelectPhotoFragment extends Fragment {



    public class PhotoData{
        public String thumbNail;
        public String imageId;
        public PhotoData(String thumbNail, String imageId){
            this.thumbNail = thumbNail;
            this.imageId = imageId;
        }
    }

    public class PhotoItem{
        public String thumbNail;
        public String imageId;
        public Bitmap bitmapThumbnail;
        public PhotoItem(String thumbNail, String imageId, Bitmap bitmapThumbnail){
            this.thumbNail = thumbNail;
            this.imageId = imageId;
            this.bitmapThumbnail = bitmapThumbnail;
        }
    }

    private static final String TAG = "jiho";
    private ListView id_lv;
    GalleryAdapter mAdapter;

    public Sitter11SelectPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sitter11_select_photo, container, false);
        id_lv = (ListView)view.findViewById(R.id.id_lv);


        long start = System.currentTimeMillis();
//        getBitmapThumbnail();

//        GalleryBitmapAdapter mAdapter = new GalleryBitmapAdapter(getContext(), thumbnails, this);
//        Log.d(TAG, "onResume: 전체 :"+photoDatas.size());
//        ((BaseActivity)getActivity()).setToolbarTitle("전체("+photoDatas.size()+")");
//        Log.d(TAG, "onResume: getThumnailPhoto exe time : "+(System.currentTimeMillis() - start));
//        id_lv.setAdapter(mAdapter);

//        getThumnailPhoto();
        final ArrayList<PhotoData> photoDatas = getThumbnailPhoto();
//        final ArrayList<PhotoData> photoDatas = getOriginalPhoto();
        Log.d(TAG, "onCreateView: photoDatas size : "+photoDatas.size());
        mAdapter = new GalleryAdapter(getContext(), photoDatas, this);
        Log.d(TAG, "onResume: 전체 :"+photoDatas.size());
        ((BaseActivity)getActivity()).setToolbarTitle("전체("+photoDatas.size()+")");
        Log.d(TAG, "onResume: getThumnailPhoto exe time : "+(System.currentTimeMillis() - start));
        id_lv.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }
    private int count;
    private Bitmap[] thumbnails;
    private String[] arrPath;

    private ArrayList<PhotoData> getOriginalPhoto(){
        // TODO 최신 data 가져오도록 수정
        ArrayList<PhotoData> result = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID
        };
//                MediaStore.Images.ImageColumns.DATE_TAKEN};
        ContentResolver resolver = getContext().getContentResolver();
        String orderBy = MediaStore.Images.Media._ID+ " DESC";
        Cursor imageCursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                orderBy
        );
        int dataColumnIndex;
        int idColumnIndex;
        String imageId = null;
        String imageThumbnail = null;
        PhotoData photoData;

        dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        idColumnIndex = imageCursor.getColumnIndex(projection[1]);

        imageCursor.moveToFirst();

        do {
            imageThumbnail = "file:///"+imageCursor.getString(dataColumnIndex);
            imageId = imageCursor.getString(idColumnIndex);
            photoData = new PhotoData(imageThumbnail, imageId);
            result.add(photoData);
            Log.d(TAG, "getThumbnailPhoto: imageThumbnail : "+imageThumbnail+", id : "+imageId);
        }while (imageCursor.moveToNext());

//        while (imageCursor.moveToNext()){
//            imageThumbnail = "file:///"+imageCursor.getString(dataColumnIndex);
//            imageId = imageCursor.getString(idColumnIndex);
//            photoData = new PhotoData(imageThumbnail, imageId);
//            result.add(photoData);
//            Log.d(TAG, "getThumbnailPhoto: imageThumbnail : "+imageThumbnail+", id : "+imageId);
//        }
        imageCursor.close();
        Log.d(TAG, "getThumnailPhoto: result size : "+result.size());


        return result;
    }

    private void getBitmapThumbnail(){
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID+" DESC";
        ContentResolver resolver = getContext().getContentResolver();
        Cursor imagecursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy
        );

        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);

            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail( resolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
//            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail( resolver, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
            arrPath[i] = imagecursor.getString(dataColumnIndex);
            Log.d(TAG, "getBitmapThumbnail: id : "+id+", arrPath : "+arrPath[i]);
        }
        Log.d(TAG, "getBitmapThumbnail: count : "+count);
        Log.d(TAG, "getBitmapThumbnail: thumbnails.length : "+thumbnails.length);
    }


    private ArrayList<PhotoData> getThumbnailPhoto(){
        // TODO 최신 data 가져오도록 수정
        ArrayList<PhotoData> result = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.IMAGE_ID
        };
//                MediaStore.Images.ImageColumns.DATE_TAKEN};
        ContentResolver resolver = getContext().getContentResolver();
        String orderBy = MediaStore.Images.Thumbnails.IMAGE_ID+ " DESC";
        Cursor imageCursor = resolver.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                orderBy
        );
        int dataColumnIndex;
        int idColumnIndex;
        String imageId = null;
        String imageThumbnail = null;
        PhotoData photoData;

        dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        idColumnIndex = imageCursor.getColumnIndex(projection[1]);

        if(imageCursor.moveToFirst()) {

            do {
                imageThumbnail = "file:///" + imageCursor.getString(dataColumnIndex);
                imageId = imageCursor.getString(idColumnIndex);
                photoData = new PhotoData(imageThumbnail, imageId);
                result.add(photoData);
            } while (imageCursor.moveToNext());
            imageCursor.close();
            Log.d(TAG, "getThumnailPhoto: result size : " + result.size());
        }

        return result;
    }

    public void selectPhotoBitmap(int tag) {

        Log.d(TAG, "selectPhotoBitmap: arrpath : "+arrPath[tag]);
        SharedData.getInstance(getContext()).insertGlobalData(SharedData.SELECTED_PHOTO_ID, arrPath[tag]);
        ((BaseActivity)getActivity()).onBackPressed();
    }

    public void selectPhoto(String id){
        Log.d(TAG, "selectPhoto: id : "+id);

//        Bundle bundle = new Bundle();
//        bundle.putString(SharedData.USER, SharedData.getInstance(getContext()).getGlobalDataString(SharedData.USER));
//
//        SitterDetailFragment fragment = new SitterDetailFragment();
//        fragment.setArguments(bundle);
//        ((BaseActivity) getActivity()).setFragment(fragment, Integer.MIN_VALUE);
        SharedData.getInstance(getContext()).insertGlobalData(SharedData.SELECTED_PHOTO_ID, id);
        ((BaseActivity)getActivity()).onBackPressed();




    }

}
