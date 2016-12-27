package net.gringrid.siso.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 구직 / 구인정보 입력 공통 > 프로필 사진 등록
 */
public class CommonPhotoFragment extends InputBaseFragment implements CommonPhotoEditFragment.OnEditCompleteListener {

    private static final String TAG = "jiho";
    public static final String ACTION_PHOTO = "ACTION_PHOTO";
    public static final String PHOTO_DIRECTORY_NAME = "/SISO/";
    public static final String PROFILE_PHOTO_FILE_NAME = "siso_profile.jpg";
    public static final int ACT_TAKE_PHOTO = 0;
    public static final int ACT_SELECT_PHOTO = 1;
    private static final int ACT_NEXT_TIME = 2;
    private final int PHOTO_MAX_SIZE = 1024;

    private TextView id_tv_next_btn;

    int mRadio[] = new int[]{
            R.id.id_tg_btn_take_photo,
            R.id.id_tg_btn_select_photo,
            R.id.id_tg_btn_later_upload
    };

    private File mNewFile;
    private ImageView id_iv_profile;
    private ImageView id_iv;
    private LinearLayout id_ll_photo;

    private Uri mOutputFileUri;
    private Uri mUri;

    private SisoToggleButton id_tg_btn_take_photo;
    private SisoToggleButton id_tg_btn_select_photo;
    private SisoToggleButton id_tg_btn_laterupload_photo;

    private Bitmap mBitmap;

    private String mRecievedPhotoUrl;
    private String mUserType;

    public CommonPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_common_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        id_iv = (ImageView) view.findViewById(R.id.id_iv);

        loadData();

        // Thumbnail 없는 사진을 background에서 생성한다
        ThumbnailFactory factory = new ThumbnailFactory();
        factory.execute("");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tg_btn_take_photo:
                SisoUtil.selectRadio(mRadio, R.id.id_tg_btn_take_photo, getView());
                moveWithAction(ACT_TAKE_PHOTO);
                break;
            case R.id.id_tg_btn_select_photo:
                SisoUtil.selectRadio(mRadio, R.id.id_tg_btn_select_photo, getView());
                moveWithAction(ACT_SELECT_PHOTO);
                break;
            case R.id.id_tg_btn_later_upload:
                SisoUtil.selectRadio(mRadio, R.id.id_tg_btn_later_upload, getView());
                break;
            case R.id.id_tv_next_btn:
                Log.d(TAG, "onClick: next btn");
                // TODO 입력값 체크
                if(!isValidInput()) return;
                saveData();

                // TODO 사진전송 request
                if(SisoUtil.getRadioValue(mRadio,getView())!=ACT_NEXT_TIME){
                    uploadImage();
                }else{
                    moveNext();
                }
                break;
        }
    }

    private void moveWithAction(int command){
        CommonPhotoEditFragment fragment = new CommonPhotoEditFragment();
        fragment.setOnEditCompleteListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_PHOTO, command);
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
    }

    @Override
    protected void loadData() {
        // 이미 이미지 정보가 있는경우 이미지를 보여준다
        if(mUser.imageInfo!=null) {
            Log.d(TAG, "onResume: profile image url : "+mUser.imageInfo.prf_img_url);
            Picasso.with(getContext()).load(mUser.imageInfo.prf_img_url).into(id_iv);
        }
    }

    @Override
    protected boolean isValidInput() {
        if(!SisoUtil.isRadioGroupSelected(mRadio, getView())){
            SisoUtil.showErrorMsg(getContext(), R.string.invalid_photo_select);
            return false;
        }
        return true;
    }

    @Override
    protected void saveData() {
        if(SisoUtil.getRadioValue(mRadio, getView()) == ACT_NEXT_TIME){
            mUser.imageInfo.prf_img_url = "NEXT";
        }
        if(!TextUtils.isEmpty(mRecievedPhotoUrl)){
            mUser.imageInfo.prf_img_url = mRecievedPhotoUrl;
        }
        SharedData.getInstance(getContext()).setObjectData(SharedData.USER, mUser);
    }

    @Override
    protected void moveNext() {
        Log.d(TAG, "moveNext: userType : "+mUserType);
        if(mUserType.equals(User.USER_TYPE_SITTER)){
            Sitter00IndexFragment fragment = new Sitter00IndexFragment();
            ((BaseActivity) getActivity()).setFragment(fragment, R.string.sitter00_stage3);
        }else if(mUserType.equals(User.USER_TYPE_PARENT)) {
            Parent00IndexFragment fragment = new Parent00IndexFragment();
            ((BaseActivity) getActivity()).setCleanUpFragment(fragment, R.string.sitter_basic_title);
        }
    }

    /**
     * Bitmap 으로 서버에 올릴 파일을 만든다
     * @return
     */
    private File getFileFromBitmapPhoto(){
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+PHOTO_DIRECTORY_NAME;
        File newDir = new File(dir);
        newDir.mkdirs();
        String tmpFileName = dir+PROFILE_PHOTO_FILE_NAME;
        File mNewFile = new File(tmpFileName);
        try {
            mNewFile.createNewFile();
            OutputStream out = new FileOutputStream(mNewFile);
            if(mBitmap.getWidth() > PHOTO_MAX_SIZE){
                mBitmap = Bitmap.createScaledBitmap(mBitmap, PHOTO_MAX_SIZE, PHOTO_MAX_SIZE, true);
            }
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mNewFile;
    }

    /**
     * 사진을 업로드 한다
     */
    private void uploadImage(){
        SisoClient client = ServiceGenerator.getInstance(getActivity()).createService(SisoClient.class);
        File file = getFileFromBitmapPhoto();

        String descriptionString = "hello, this is description speaking";
        String emailString = mUser.personalInfo.email;
        String gubunString = "prf";

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
                    mRecievedPhotoUrl = response.body().prf_img_url;
                    saveData();
                    moveNext();
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onEditComplete(Bitmap photo) {
        mBitmap = photo;
        id_iv.setImageBitmap(photo);
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

            if(imageCursor != null && resolver != null) {

                if (imageCursor.moveToFirst()) {
                    do {
                        imageId = imageCursor.getString(idColumnIndex);
                        thumbnailCursor = resolver.query(
                                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                                projection,
                                MediaStore.Images.Thumbnails.IMAGE_ID + "=?",
                                new String[]{imageId},
                                null //MediaStore.Images.Thumbnails.MICRO_KIND
                        );
                        if (thumbnailCursor == null) {
                            Log.d(TAG, "getThumnailPhoto: thumbnailCursor NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                        } else if (thumbnailCursor.moveToFirst()) {
                            Log.d(TAG, "createThumbnail: Thumbnail EXISTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        } else {
                            MediaStore.Images.Thumbnails.getThumbnail(resolver, Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND, null);
                            Log.d(TAG, "getThumnailPhoto: thumbnail EMPTYYYYYYYYYYYYYYYYYYYYYYYYYYYY ");
                        }
                    } while (imageCursor.moveToNext());
                }
                imageCursor.close();
                thumbnailCursor.close();
            }
            return "";
        }
    }

}
