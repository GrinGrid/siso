package net.gringrid.siso.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
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

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.adapter.GalleryAdapter;

import java.util.ArrayList;


/**
 * 갤러리에서 사진선택
 * A simple {@link Fragment} subclass.
 */
public class Sitter11SelectPhotoFragment extends Fragment {


    public class PhotoData{
        public Uri thumbNail;
        public String imageId;
        public PhotoData(Uri thumbNail, String imageId){
            this.thumbNail = thumbNail;
            this.imageId = imageId;
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
        return inflater.inflate(R.layout.fragment_sitter11_select_photo, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        id_lv = (ListView)getView().findViewById(R.id.id_lv);
        long start = System.currentTimeMillis();
        mAdapter = new GalleryAdapter(getContext(), getThumbnailPhoto());
        ((BaseActivity)getActivity()).setToolbarTitle("전체("+mAdapter.getCount()+")");
        Log.d(TAG, "onResume: getThumnailPhoto exe time : "+(System.currentTimeMillis() - start));
        id_lv.setAdapter(mAdapter);
        id_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onClick: mPhotoList.get(finalPosition).imageId : "+mPhotoList.get(finalPosition).imageId);
            }
        });
    }

    private ArrayList<PhotoData> getThumbnailPhoto(){
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

        imageCursor.moveToFirst();

        while (imageCursor.moveToNext()){
            imageThumbnail = imageCursor.getString(dataColumnIndex);
            imageId = imageCursor.getString(idColumnIndex);
            photoData = new PhotoData(Uri.parse(imageThumbnail), imageId);
            result.add(photoData);
        }
        imageCursor.close();
        Log.d(TAG, "getThumnailPhoto: result size : "+result.size());


        return result;
    }


    private ArrayList<PhotoData> getThumnailPhoto(){
        ArrayList<PhotoData> images = new ArrayList<>();
        ArrayList<PhotoData> result = new ArrayList<>();
        String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        ContentResolver resolver = getContext().getContentResolver();

        Cursor imageCursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        imageCursor.moveToFirst();
        String imageId;
        String thumbNail = null;
        String imagePath = null;
        int dataColumnIndex;
        int idColumnIndex;

        dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        idColumnIndex = imageCursor.getColumnIndex(projection[1]);
        PhotoData photoData;

        Cursor thumbnailCursor = null;
        String[] thumbnailProjection = { MediaStore.Images.Thumbnails.DATA };

        int thumbnailColumnIndex;



        while (imageCursor.moveToNext()){
            imagePath = imageCursor.getString(dataColumnIndex);
            imageId = imageCursor.getString(idColumnIndex);
//            Log.d(TAG, "getThumnailPhoto: imagepath : "+imagePath+", imageId : "+imageId);
            thumbnailCursor = resolver.query(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    projection,
                    MediaStore.Images.Thumbnails.IMAGE_ID + "=?",
                    new String[]{imageId},
                    null //MediaStore.Images.Thumbnails.MICRO_KIND
            );
            thumbnailColumnIndex = thumbnailCursor.getColumnIndex(thumbnailProjection[0]);
            if(thumbnailCursor==null){
                Log.d(TAG, "getThumnailPhoto: thumbnailCursor NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            }else if(thumbnailCursor.moveToFirst()){
                thumbNail = thumbnailCursor.getString(thumbnailColumnIndex);
            }else{
                MediaStore.Images.Thumbnails.getThumbnail(resolver, Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND, null);
                Log.d(TAG, "getThumnailPhoto: thumbnail EMPTYYYYYYYYYYYYYYYYYYYYYYYYYYYY ");
            }
            photoData = new PhotoData(Uri.parse(thumbNail), imagePath);
            result.add(photoData);
        }
        imageCursor.close();
        thumbnailCursor.close();
        Log.d(TAG, "getThumnailPhoto: result size : "+result.size());

        return result;
    }

}
