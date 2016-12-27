package net.gringrid.siso.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.R;
import net.gringrid.siso.views.SisoInputProgress;

/**
 * 가이드 공통 fragment
 */
public class GuideCommonFragment extends Fragment {

    private static final String TAG = "jiho";
    public int mViewId;
    private LinearLayout id_ll_content;
    private ImageView id_iv_img;
    private TextView id_tv_main;
    private TextView id_tv_sub;

    private int mCurrentGuide;

    public GuideCommonFragment() {
        // Required empty public constructor
    }

    public GuideCommonFragment(int guideNum){
        mCurrentGuide = guideNum;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        id_ll_content = (LinearLayout)view.findViewById(R.id.id_ll_content);
        id_iv_img = (ImageView)view.findViewById(R.id.id_iv_img);
        id_tv_main = (TextView) view.findViewById(R.id.id_tv_main);
        id_tv_sub = (TextView) view.findViewById(R.id.id_tv_sub);

        initializeView();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeView() {
        setImg();
        setMainText();
        setSubText();
    }

    private void setImg() {
        TypedArray rscAry = getResources().obtainTypedArray(R.array.guide_img);
        id_iv_img.setImageDrawable(rscAry.getDrawable(mCurrentGuide));
    }

    private void setMainText() {
        String []rscAry = getResources().getStringArray(R.array.guide_main);
        id_tv_main.setText(rscAry[mCurrentGuide]);
    }

    private void setSubText() {
        String []rscAry = getResources().getStringArray(R.array.guide_sub);
        id_tv_sub.setText(rscAry[mCurrentGuide]);
    }

}

