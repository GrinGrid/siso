package net.gringrid.siso.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImageView;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Image;
import net.gringrid.siso.models.User;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.network.restapi.SisoClient;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoToggleButton;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직정보입력 > 프로필사진 등록 > 프로필 사진 등록
 * A simple {@link Fragment} subclass.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Sitter11PhotoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";
    private static final int ACT_TAKE_PIC = 1;
    private static final int PIC_PIC_CROP = 2;
    private static final int ACTION_REQUEST_GALLERY = 3;
    User mUser;
    private TextView id_tv_next_btn;

//    private LinearLayout id_ll_radio1;
//    private LinearLayout id_ll_radio2;
//    private LinearLayout id_ll_radio3;
//    private TextView id_tv_radio1;
//    private TextView id_tv_radio2;
//    private TextView id_tv_radio3;
//    private ImageView id_iv_radio1;
//    private ImageView id_iv_radio2;
//    private ImageView id_iv_radio3;

    int mRadio[] = new int[]{
            R.id.id_tg_btn_take_photo,
            R.id.id_tg_btn_select_photo,
            R.id.id_tg_btn_later_upload
    };

    private File mNewFile;
    private ImageView id_iv_profile;
//    private CropImageView id_civ;
    private CropImageView id_civ;
    private LinearLayout id_ll_photo;

    private Uri mOutputFileUri;
    private Uri mUri;

    private SisoToggleButton id_tg_btn_take_photo;
    private SisoToggleButton id_tg_btn_select_photo;
    private SisoToggleButton id_tg_btn_laterupload_photo;

    public Sitter11PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        mUser = SharedData.getInstance(getContext()).getUserData();
        SharedData.getInstance(getContext()).insertGlobalData(SharedData.SELECTED_PHOTO_ID, null);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sitter11, container, false);


        id_tv_next_btn = (TextView) view.findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_tg_btn_take_photo = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_take_photo);
        id_tg_btn_select_photo = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_select_photo);
        id_tg_btn_laterupload_photo = (SisoToggleButton)view.findViewById(R.id.id_tg_btn_later_upload);

        id_tg_btn_take_photo.setOnClickListener(this);
        id_tg_btn_select_photo.setOnClickListener(this);
        id_tg_btn_laterupload_photo.setOnClickListener(this);

        id_ll_photo = (LinearLayout)view.findViewById(R.id.id_ll_photo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SisoUtil.getScreenWidth(getContext()));
        id_ll_photo.setLayoutParams(lp);
//        id_iv_profile = (ImageView)getView().findViewById(R.id.id_iv_profile);
        id_civ = (CropImageView) view.findViewById(R.id.id_civ);
        id_civ.setAspectRatio(1,1);
        id_civ.setFixedAspectRatio(true);
        id_civ.setScaleType(CropImageView.ScaleType.FIT_CENTER);
//        id_icv = (ImageCropView) getView().findViewById(R.id.id_icv);
//        id_icv.setGridInnerMode(ImageCropView.GRID_ON);
//        id_icv.setGridOuterMode(ImageCropView.GRID_ON);
        if(mUser.imageInfo!=null) {
            Log.d(TAG, "onResume: profile image url : "+mUser.imageInfo.prf_img_url);
//            Picasso.with(getContext()).load(mUser.imageInfo.prf_img_url).networkPolicy(NetworkPolicy.NO_CACHE).into(id_civ);
        }else{
            Log.d(TAG, "onResume: profile image url  is null : ");
        }

//        id_civ.setImageUriAsync(Uri.parse("content://media/external/images/media/87795"));

        // TODO Thumbnail 없는 사진을 background에서 생성하자
        ThumbnailFactory factory = new ThumbnailFactory();
        factory.execute("");
        return view;
    }


    @Override
    public void onResume() {
        Log.d(TAG, "Sitter11PhotoFragment onResume: ");

        String selectedPhotoId = SharedData.getInstance(getContext()).getGlobalDataString(SharedData.SELECTED_PHOTO_ID);
        if(!TextUtils.isEmpty(selectedPhotoId)){
            Log.d(TAG, "onResume: selected photo id : "+selectedPhotoId);
            setSelectedPhoto(selectedPhotoId);
        }

        super.onResume();
    }


//    private void setCropPhoto(){
//        final Uri saveUri = null;
//        id_civ.startCrop(
//                saveUri,
//                new CropCallback() {
//                    @Override
//                    public void onSuccess(Bitmap cropped) {
//                        Log.d(TAG, "CropCallback onSuccess: ");
//                        id_civ.setImageBitmap(cropped);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                },
//                new SaveCallback() {
//                    @Override
//                    public void onSuccess(Uri outputUri) {
//                        Log.d(TAG, "SaveCallback onSuccess: ");
//                        id_civ.setImageURI(saveUri);
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                }
//        );
//    }

    private void setSelectedPhoto(String selectedPhotoId) {
        Uri imageUri=
                ContentUris
                        .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,Long.parseLong(selectedPhotoId));
//                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));

        Log.d(TAG, "setSelectedPhoto: imageUri : "+imageUri);
        id_civ.setImageUriAsync(imageUri);

//        id_civ.setCropMode(CropImageView.CropMode.SQUARE);
//        id_civ.startLoad(imageUri,
//                new LoadCallback(){
//
//                    @Override
//                    public void onSuccess() {
//                        Log.d(TAG, "onSuccess: height : "+id_civ.getHeight());
//                        Log.d(TAG, "onSuccess: width : "+id_civ.getWidth());
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//        id_civ.setImageURI(imageUri);
//        Bitmap new_image = BitmapFactory.decodeFile(imageUri.toString());
//        id_civ.setImageBitmap(new_image);
//        SharedData.getInstance(getContext()).insertGlobalData(SharedData.SELECTED_PHOTO_ID, null);



//        String[] projection = {
//                MediaStore.Images.Media.DATA,
//                MediaStore.Images.Media._ID
//        };
//
//        ContentResolver resolver = getContext().getContentResolver();
//        Cursor imageCursor = resolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                MediaStore.Images.Media._ID+ "=?",
//                new String[]{selectedPhotoId},
//                null
//        );
//        int dataColumnIndex;
//        int idColumnIndex;
//        String imageId = null;
//        String imageThumbnail = null;
//
//        dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
//        idColumnIndex = imageCursor.getColumnIndex(projection[1]);
//
//        if(imageCursor.moveToFirst()){
//            imageThumbnail = imageCursor.getString(dataColumnIndex);
//            imageId = imageCursor.getString(idColumnIndex);
//        }
//        Log.d(TAG, "setSelectedPhoto: imageThubname : "+imageThumbnail);
//        Log.d(TAG, "setSelectedPhoto: imageId : "+imageId);
//        id_civ.setImageURI(Uri.parse(imageThumbnail));
//
//
//        imageCursor.close();
//        id_civ.setImageURI(imageUri);
//        Log.d(TAG, "setSelectedPhoto: width : "+id_civ.getWidth());
//        Log.d(TAG, "setSelectedPhoto: height : "+id_civ.getHeight());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tg_btn_take_photo:
                selectRadio(R.id.id_tg_btn_take_photo);
                takePicture();
                break;
            case R.id.id_tg_btn_select_photo:
                selectRadio(R.id.id_tg_btn_select_photo);
                pickFromGallery();
                break;
            case R.id.id_tg_btn_later_upload:
                selectRadio(R.id.id_tg_btn_later_upload);
//                insertLater();
                break;
            case R.id.id_tv_next_btn:
                Log.d(TAG, "onClick: next btn");
                // TODO 입력값 체크
                saveData();
                // TODO 사진전송 request
//                uploadImage();
                setCropPhoto();
//                Sitter01IndexFragment fragment = new Sitter01IndexFragment();
//                ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_introduce_title);
//                ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_introduce_title);
                break;
        }
    }

    private void setCropPhoto() {

        Bitmap cropped = id_civ.getCroppedImage();
        id_civ.setImageBitmap(cropped);
//        id_civ.getCroppedImageAsync();
    }

    private void insertLater() {
        Log.d(TAG, "insertLater: ");
//        id_civ.startCrop(
//                mUri,
//                new CropCallback() {
//                    @Override
//                    public void onSuccess(Bitmap cropped) {
//                        id_civ.setImageBitmap(cropped);
//                        Log.d(TAG, "onSuccess: cropcallback");
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.d(TAG, "onerror cropcallback");
//                    }
//                },
//
//                new SaveCallback() {
//
//                    @Override
//                    public void onSuccess(Uri outputUri) {
//                        id_civ.setImageURI(outputUri);
//                        Log.d(TAG, "onerror savecallback");
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.d(TAG, "onerror savecallback");
//                    }
//                }
//        );
    }


    private void saveData() {
        //TODO 저장
        mUser.imageInfo.prf_img_url = "NEXT";
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    private void uploadImage(){
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        File file = new File(getRealPathFromURI(mUri));

        String descriptionString = "hello, this is description speaking";
        String emailString = "nisclan1475742432860@hotmail.com";
        String gubunString = "prf";

        Log.d(TAG, "uploadImage: filename : "+file.getName());
        Log.d(TAG, "uploadImage: filepath : "+file.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), emailString);
        RequestBody gubun = RequestBody.create(MediaType.parse("multipart/form-data"), gubunString);
        Call<Image> call = client.uploadImg(description, email, gubun, body);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if(response.body()!=null){
                    Log.d(TAG, "onResponse: profile :"+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
        Sitter11SelectPhotoFragment fragment = new Sitter11SelectPhotoFragment();
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter_introduce_title);
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");

//        Intent chooser = Intent.createChooser(intent, "Choose a Picture");
//        startActivityForResult(intent, ACTION_REQUEST_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: result code is not ok");
        }else{
            Log.d(TAG, "onActivityResult: result code RESULT_OK");
        }
//        if(null == data){
//            Log.d(TAG, "onActivityResult: data is null : "+data.toString());
//        }else{
//            Log.d(TAG, "onActivityResult: data is not null");
//        }
        switch (requestCode){
            case ACTION_REQUEST_GALLERY:
                Log.d(TAG, "onActivityResult:ACTIONREQEUSTGALLERY ");
                // TODO 사진을 선택하지 않은 경우
                mUri = data.getData();
                id_civ.setImageUriAsync(mUri);
//                id_civ.startLoad(mUri, new LoadCallback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d(TAG, "startLoad onSuccess: ");
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.d(TAG, "startLoad onError: ");
//
//                    }
//                });


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
                    id_civ.setImageUriAsync(mOutputFileUri);

//                    Log.d(TAG, "takePicture: before : "+mNewFile.length());

//                    Uri uri = data.getData();
//                    String filePath = BitmapLoadUtils.getPathFromUri(getActivity(), uri);
//                    Uri filePathUri = Uri.parse(filePath);


//                    mOutputFileUri = data.getData();
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

    private int getRadioValue(int[] radioList){
        for(int i=0; i<radioList.length; i++){
            if (((SisoToggleButton) getView().findViewById(radioList[i])).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private void selectRadio(int selectItem) {
        for(int src:mRadio){
            if(src == selectItem){
                ((SisoToggleButton)getView().findViewById(src)).setChecked(true);
            }else{
                ((SisoToggleButton)getView().findViewById(src)).setChecked(false);
            }
        }
    }

    /**
     * Thumbnail 이미지가 없을경우 Thumbnail 이미지를 background에서 만든다
     */
    private class ThumbnailFactory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
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
            int idColumnIndex;
            idColumnIndex = imageCursor.getColumnIndex(projection[1]);
            Cursor thumbnailCursor = null;
            String[] thumbnailProjection = { MediaStore.Images.Thumbnails.DATA };

            if(imageCursor.moveToFirst()){
                do {
                    imageId = imageCursor.getString(idColumnIndex);
                    thumbnailCursor = resolver.query(
                            MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                            projection,
                            MediaStore.Images.Thumbnails.IMAGE_ID + "=?",
                            new String[]{imageId},
                            null //MediaStore.Images.Thumbnails.MICRO_KIND
                    );
                    if(thumbnailCursor==null){
                        Log.d(TAG, "getThumnailPhoto: thumbnailCursor NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                    }else if(thumbnailCursor.moveToFirst()){
                        Log.d(TAG, "createThumbnail: Thumbnail EXISTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                    }else{
                        MediaStore.Images.Thumbnails.getThumbnail(resolver, Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND, null);
                        Log.d(TAG, "getThumnailPhoto: thumbnail EMPTYYYYYYYYYYYYYYYYYYYYYYYYYYYY ");
                    }
                }while (imageCursor.moveToNext());
            }
            imageCursor.close();
            thumbnailCursor.close();
            return "";
        }
    }

}
