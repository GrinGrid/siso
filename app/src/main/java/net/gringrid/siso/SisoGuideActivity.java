package net.gringrid.siso;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.fragments.GuideCommonFragment;
import net.gringrid.siso.fragments.SisoStaticViewFragment;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoInputProgress;



public class SisoGuideActivity extends RootActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    protected static final String TAG = "jiho";

    private LinearLayout id_ll_nav;
    private LinearLayout id_ll_last;
    private TextView id_tv_start;
    private SisoInputProgress id_sip;
    private ImageView id_iv_close;
    private ImageView id_iv_next;

    private static final int TOTAL_PAGES = 6;
    private static final int GUIDE_00 = 0x00;
    private static final int GUIDE_01 = 0x01;
    private static final int GUIDE_02 = 0x02;
    private static final int GUIDE_03 = 0x03;
    private static final int GUIDE_04 = 0x04;
    private static final int GUIDE_05 = 0x05;

    private FragmentPagerAdapter mFragmentPagerAdapter;
    private ViewPager id_vp;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPink));
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();


        id_vp = (ViewPager)findViewById(R.id.id_vp);
        mFragmentPagerAdapter = new SisoGuidePagerAdapter(getSupportFragmentManager());
        id_vp.setAdapter(mFragmentPagerAdapter);
        id_sip = (SisoInputProgress)findViewById(R.id.id_sip);
        id_ll_nav = (LinearLayout)findViewById(R.id.id_ll_nav);
        id_ll_last = (LinearLayout)findViewById(R.id.id_ll_last);
        id_tv_start = (TextView)findViewById(R.id.id_tv_start);
        id_iv_close = (ImageView)findViewById(R.id.id_iv_close);
        id_iv_next = (ImageView)findViewById(R.id.id_iv_next);
        id_vp.addOnPageChangeListener(this);
        id_iv_close.setOnClickListener(this);
        id_iv_next.setOnClickListener(this);
        id_tv_start.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        if(position == GUIDE_05){
            id_ll_last.setVisibility(View.VISIBLE);
            id_ll_nav.setVisibility(View.GONE);
        }else{
            id_ll_nav.setVisibility(View.VISIBLE);
            id_ll_last.setVisibility(View.GONE);
        }
        id_sip.setStage(TOTAL_PAGES, position);
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_iv_close:
            case R.id.id_tv_start:
                if(v.getId() == R.id.id_tv_start){
                    SharedData.getInstance(this).insertGlobalData(SharedData.IS_READ_GUIDE, true);
                }
                Intent intent = new Intent(SisoGuideActivity.this, BaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("menu", BaseActivity.MENU_SIGN_UP);
                startActivity(intent);
                finish();
                break;
            case R.id.id_iv_next:
                id_vp.setCurrentItem(id_vp.getCurrentItem()+1, true);
                break;
        }
    }

    public static class SisoGuidePagerAdapter extends FragmentPagerAdapter{

        public SisoGuidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == GUIDE_05){
            }
            GuideCommonFragment fragment = new GuideCommonFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }
}
