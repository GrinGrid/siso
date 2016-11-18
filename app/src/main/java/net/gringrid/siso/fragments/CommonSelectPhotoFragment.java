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
import android.widget.ListView;


import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.GalleryAdapter;

import java.util.ArrayList;


/**
 * 구직 / 구인정보 입력 공통 > 사진선택
 */
public class CommonSelectPhotoFragment extends Fragment {

    public class PhotoData{
        public String thumbNail;
        public String imageId;
        public PhotoData(String thumbNail, String imageId){
            this.thumbNail = thumbNail;
            this.imageId = imageId;
        }
    }

    OnSelectCompleteListener mListener;

    public interface OnSelectCompleteListener{
        void onSelectComplete(String photoId);
    }

    private static final String TAG = "jiho";
    private ListView id_lv;
    GalleryAdapter mAdapter;

    public CommonSelectPhotoFragment() {
        // Required empty public constructor
    }

    public void setOnSelectCompleteListener(OnSelectCompleteListener listener){
        mListener = listener;
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
        final ArrayList<PhotoData> photoDatas = getThumbnailPhoto();
        mAdapter = new GalleryAdapter(getContext(), photoDatas, this);
        ((BaseActivity)getActivity()).setToolbarTitle("전체("+photoDatas.size()+")");
        id_lv.setAdapter(mAdapter);

        return view;
    }

    private ArrayList<PhotoData> getThumbnailPhoto(){
        ArrayList<PhotoData> result = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.IMAGE_ID
        };
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
        String imageId;
        String imageThumbnail;
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

    public void selectPhoto(String id){
        mListener.onSelectComplete(id);
        getActivity().onBackPressed();
    }

}
