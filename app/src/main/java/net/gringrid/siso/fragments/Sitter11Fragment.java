package net.gringrid.siso.fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Sitter;
import net.gringrid.siso.util.SharedData;

import java.io.File;
import java.io.IOException;

/**
 * 구직정보입력 > 프로필사진 등록 > 프로필 사진 등록
 * A simple {@link Fragment} subclass.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter11Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    private static final int ACT_TAKE_PIC = 1;
    private static final int PIC_PIC_CROP = 2;
    private static final int ACTION_REQUEST_GALLERY = 3;
    Sitter mSitter;
    private TextView id_tv_next_btn;

    private LinearLayout id_ll_radio1;
    private LinearLayout id_ll_radio2;
    private LinearLayout id_ll_radio3;
    private TextView id_tv_radio1;
    private TextView id_tv_radio2;
    private TextView id_tv_radio3;
    private ImageView id_iv_radio1;
    private ImageView id_iv_radio2;
    private ImageView id_iv_radio3;
    int mRadio[] = new int[]{
            R.id.id_ll_radio1,
            R.id.id_ll_radio2,
            R.id.id_ll_radio3
    };
    private File mNewFile;
    private ImageView id_iv_profile;
    private CropImageView id_civ;

    private Uri mOutputFileUri;
    private Uri mUri;

    public Sitter11Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSitter = SharedData.getInstance(getContext()).getSitterData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter11, container, false);
    }

    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_ll_radio1 = (LinearLayout) getView().findViewById(R.id.id_ll_radio1);
        id_ll_radio2 = (LinearLayout) getView().findViewById(R.id.id_ll_radio2);
        id_ll_radio3 = (LinearLayout) getView().findViewById(R.id.id_ll_radio3);
        id_tv_radio1 = (TextView) getView().findViewById(R.id.id_tv_radio1);
        id_tv_radio2 = (TextView) getView().findViewById(R.id.id_tv_radio2);
        id_tv_radio3 = (TextView) getView().findViewById(R.id.id_tv_radio3);
        id_iv_radio1 = (ImageView) getView().findViewById(R.id.id_iv_radio1);
        id_iv_radio2 = (ImageView) getView().findViewById(R.id.id_iv_radio2);
        id_iv_radio3 = (ImageView) getView().findViewById(R.id.id_iv_radio3);

        id_ll_radio1.setOnClickListener(this);
        id_ll_radio2.setOnClickListener(this);
        id_ll_radio3.setOnClickListener(this);
//        id_iv_profile = (ImageView)getView().findViewById(R.id.id_iv_profile);
        id_civ = (CropImageView) getView().findViewById(R.id.id_civ);
//        id_icv = (ImageCropView) getView().findViewById(R.id.id_icv);
//        id_icv.setGridInnerMode(ImageCropView.GRID_ON);
//        id_icv.setGridOuterMode(ImageCropView.GRID_ON);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_ll_radio1:
                selectRadio(1);
                takePicture();
                break;
            case R.id.id_ll_radio2:
                selectRadio(2);
                pickFromGallery();
                break;
            case R.id.id_ll_radio3:
                selectRadio(3);
                insertLater();
                break;
            case R.id.id_tv_next_btn:
                Log.d(TAG, "onClick: next btn");
                // TODO 입력값 체크
//                saveData();
                // TODO 사진전송 request
                Sitter11_sub1Fragment fragment = new Sitter11_sub1Fragment();
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_introduce_title);
                break;
        }
    }

    private void insertLater() {
        Log.d(TAG, "insertLater: ");
        id_civ.startCrop(
                mUri,
                new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {
                        id_civ.setImageBitmap(cropped);
                        Log.d(TAG, "onSuccess: cropcallback");
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "onerror cropcallback");
                    }
                },

                new SaveCallback() {

                    @Override
                    public void onSuccess(Uri outputUri) {
                        id_civ.setImageURI(outputUri);
                        Log.d(TAG, "onerror savecallback");
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "onerror savecallback");
                    }
                }
        );
    }


    private void saveData() {
        //TODO 저장
        Log.d(TAG, "onClick: mSitter : "+mSitter.toString());
        SharedData.getInstance(getContext()).setObjectData(SharedData.SITTER, mSitter);
    }

    private void takePicture(){
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/siso/";

        File newDir = new File(dir);
        newDir.mkdirs();
        String tmpFileName = dir+"siso_profile.jpg";
        mNewFile = new File(tmpFileName);
        try {
            mNewFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "takePicture: before : "+mNewFile.length());
        mOutputFileUri = Uri.fromFile(mNewFile);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(cameraIntent, ACT_TAKE_PIC);
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");

//        Intent chooser = Intent.createChooser(intent, "Choose a Picture");
        startActivityForResult(intent, ACTION_REQUEST_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: result code is not ok");
        }else{
            Log.d(TAG, "onActivityResult: result code RESULT_OK");
        }
        if(null == data){
            Log.d(TAG, "onActivityResult: data is null : "+data.toString());
        }else{
            Log.d(TAG, "onActivityResult: data is not null");
        }
        switch (requestCode){
            case ACTION_REQUEST_GALLERY:
                Log.d(TAG, "onActivityResult:ACTIONREQEUSTGALLERY ");
                mUri = data.getData();
                id_civ.startLoad(mUri, new LoadCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "startLoad onSuccess: ");

                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "startLoad onError: ");

                    }
                });


//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                Log.d(TAG, "onActivityResult: my picture path : "+picturePath);
//                id_iv_profile.setImageURI(uri);
//                String filePath = BitmapLoadUtils.getPathFromUri(getContext(), uri);
//                Log.d(TAG, "onActivityResult: filepath : "+filePath);
//                id_icv.setImageFilePath(picturePath.toString());


//                Uri filePathUri = Uri.parse(filePath);

//                id_icv.setImageFilePath(filePathUri.toString());
//                id_icv.setAspectRatio(1,1);
                break;

            case ACT_TAKE_PIC:
                if(resultCode == Activity.RESULT_OK && requestCode == ACT_TAKE_PIC){
                    Log.d(TAG, "onActivityResult: ok");
                    Log.d(TAG, "takePicture: before : "+mNewFile.length());

//                    Uri uri = data.getData();
//                    String filePath = BitmapLoadUtils.getPathFromUri(getActivity(), uri);
//                    Uri filePathUri = Uri.parse(filePath);


                    mOutputFileUri = data.getData();
//                    id_icv.setImageFilePath(mOutputFileUri.toString());
//                    id_icv.setAspectRatio(1,1);
                    try {
//                        (Bitmap)(data.getExtras().get("data"));
//                        performCrop();
//                        id_iv_profile.setImageURI(mOutputFileUri);

                    }catch (Exception e){
//get the returned data
//                        Bundle extras = data.getExtras();
//get the cropped bitmap
//                        Bitmap thePic = extras.getParcelable("data");
                        //retrieve a reference to the ImageView
//display the returned cropped image
//                        id_iv_profile.setImageBitmap(thePic);
                    }
                }
                break;

            case PIC_PIC_CROP:


                break;
        }
    }

    private void performCrop(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mOutputFileUri, "image/*");

        intent.putExtra("outputX", 90);
        intent.putExtra("outputY", 90);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PIC_PIC_CROP);

//        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//        cropIntent.setDataAndType(mOutputFileUri, "image/*");
//        cropIntent.putExtra("crop", "true");
//        cropIntent.putExtra("aspectX", 1);
//        cropIntent.putExtra("aspectY", 1);
//        cropIntent.putExtra("outputX", 90);
//        cropIntent.putExtra("outputY", 90);
//        cropIntent.putExtra("return-data", true);
//        startActivityForResult(cropIntent, PIC_PIC_CROP);
    }

    private void resetRadio(){
        id_tv_radio1.setTextColor(ContextCompat.getColor(getContext(), R.color.color787878));
        id_tv_radio2.setTextColor(ContextCompat.getColor(getContext(), R.color.color787878));
        id_tv_radio3.setTextColor(ContextCompat.getColor(getContext(), R.color.color787878));
        id_iv_radio1.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_radio_off));
        id_iv_radio2.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_radio_off));
        id_iv_radio3.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_radio_off));
    }

    private void selectRadio(int idx) {
        resetRadio();

        String packageName = getActivity().getPackageName();
        int textViewId = getResources().getIdentifier("id_tv_radio"+idx, "id", packageName);
        int imgViewId = getResources().getIdentifier("id_iv_radio"+idx, "id", packageName);
        TextView text = (TextView)getView().findViewById(textViewId);
        ImageView img = (ImageView) getView().findViewById(imgViewId);

        text.setTextColor(ContextCompat.getColor(getContext(), R.color.colorContentTextPink));
        img.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_radio_on));
    }

}
