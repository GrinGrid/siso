package net.gringrid.siso.fragments;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.util.SisoUtil;

import java.io.File;
import java.io.IOException;


/**
 * 사진 촬영/선택 후 편집하기 위한 화면
 */
public class CommonPhotoEditFragment extends Fragment implements CommonSelectPhotoFragment.OnSelectCompleteListener, View.OnClickListener {

    private static final String TAG = "jiho";
    private Uri mTakePhotoOutputUri;

    private LinearLayout id_ll_photo;
    private CropImageView id_civ;
    private TextView id_tv_next_btn;
    private TextView id_tv_crop;
    private Bitmap mBitmap;


    OnEditCompleteListener mListener;

    public interface OnEditCompleteListener{
        void onEditComplete(Bitmap photo);
    }

    public CommonPhotoEditFragment() {
        // Required empty public constructor
    }

    public void setOnEditCompleteListener(OnEditCompleteListener listener){
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
        int action = getArguments().getInt(CommonPhotoFragment.ACTION_PHOTO);
        performAction(action);
        return inflater.inflate(R.layout.fragment_common_photo_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_ll_photo = (LinearLayout)view.findViewById(R.id.id_ll_photo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SisoUtil.getScreenWidth(getContext()));
        id_ll_photo.setLayoutParams(lp);
        id_civ = (CropImageView) view.findViewById(R.id.id_civ);
        id_civ.setAspectRatio(1,1);
        id_civ.setFixedAspectRatio(true);
        id_civ.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);
        id_tv_crop = (TextView) view.findViewById(R.id.id_tv_crop);
        id_tv_crop.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    private void performAction(int action) {
        switch (action){
            case CommonPhotoFragment.ACT_TAKE_PHOTO:
                takePhoto();
                break;

            case CommonPhotoFragment.ACT_SELECT_PHOTO:
                selectPhoto();
                break;
        }
    }


    /**
     * 사진촬영
     */
    private void takePhoto() {
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+CommonPhotoFragment.PHOTO_DIRECTORY_NAME;

        File newDir = new File(dir);
        newDir.mkdirs();
        String tmpFileName = dir+CommonPhotoFragment.PROFILE_PHOTO_FILE_NAME;
        File mNewFile = new File(tmpFileName);
        try {
            mNewFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "takePicture: before : "+mNewFile.length());
        mTakePhotoOutputUri = Uri.fromFile(mNewFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoOutputUri);
        startActivityForResult(cameraIntent, CommonPhotoFragment.ACT_TAKE_PHOTO);
    }

    private void selectPhoto() {
        CommonSelectPhotoFragment fragment = new CommonSelectPhotoFragment();
        fragment.setOnSelectCompleteListener(this);
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: result code is not ok");
        }else{
            Log.d(TAG, "onActivityResult: result code RESULT_OK");
        }

        switch (requestCode) {
            case CommonPhotoFragment.ACT_TAKE_PHOTO:
                id_civ.setImageUriAsync(mTakePhotoOutputUri);
                break;
        }
    }

    @Override
    public void onSelectComplete(String photoId) {
        Uri imageUri=
                ContentUris
                        .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,Long.parseLong(photoId));
        id_civ.setImageUriAsync(imageUri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.id_tv_crop:
                performCrop();
                break;

            case R.id.id_tv_next_btn:
                mListener.onEditComplete(mBitmap);
                getActivity().onBackPressed();
                break;
        }
    }

    private void performCrop() {
        mBitmap = id_civ.getCroppedImage();
        id_civ.setImageBitmap(mBitmap);
    }
}
