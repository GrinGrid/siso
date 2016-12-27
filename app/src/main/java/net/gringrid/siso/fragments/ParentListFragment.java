package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.R;

/**
 * 구인자리스트 관리
 */
public class ParentListFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private static final String TAG = "jiho";
    private static int NUM_ITEMS = 4;
    private static final int TAB_O_SISO = 0;
    private static final int TAB_1_FAVOTITE = 1;
    private static final int TAB_2_SEND = 2;
    private static final int TAB_3_RECEIVE = 3;


    private ViewPager id_vp;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private TabLayout id_tl;

    private int[] tabTextList = new int[]{
            R.string.parent_list_tab_siso,
            R.string.parent_list_tab_favorite,
            R.string.parent_list_tab_send,
            R.string.parent_list_tab_receive
    };

    private static int[] tabIconNormalList = new int[]{
            R.drawable.tab_icon_siso_normal,
            R.drawable.tab_icon_favorite_normal,
            R.drawable.tab_icon_send_normal,
            R.drawable.tab_icon_receive_normal
    };

    private static int[] tabIconSelectedList = new int[]{
            R.drawable.tab_icon_siso_selected,
            R.drawable.tab_icon_favorite_selected,
            R.drawable.tab_icon_send_selected,
            R.drawable.tab_icon_receive_selected
    };

    public ParentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitter_list, container, false);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        id_vp = (ViewPager)getView().findViewById(R.id.id_vp);
        id_tl = (TabLayout) getView().findViewById(R.id.id_tl);

        // 아래와 같이 getFragmentManager()로 Adapter를 생성하면 두번째 호출부터 비정상적으로 loading됨
        // mFragmentPagerAdapter = new ParentListPagerAdapter(getFragmentManager());
        mFragmentPagerAdapter = new ParentListPagerAdapter(getChildFragmentManager());
        id_vp.setAdapter(mFragmentPagerAdapter);
        id_vp.setCurrentItem(TAB_O_SISO);

        id_tl.setupWithViewPager(id_vp);
        id_tl.setOnTabSelectedListener(this);
        createTabIcons();
        super.onResume();
    }

    private void createTabIcons(){
        for(int i=0; i<NUM_ITEMS; i++){
            id_tl.getTabAt(i).setCustomView(R.layout.sitter_list_tab);
            id_tl.getTabAt(i).setIcon(tabIconNormalList[i]);
            id_tl.getTabAt(i).setText(tabTextList[i]);
        }
    }

    private void setIconTextColor(int position, int textColor){
        TextView textView = (TextView)id_tl.getTabAt(position).getCustomView().findViewById(android.R.id.text1);
        textView.setTextColor(ContextCompat.getColor(getContext(),textColor));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        tab.setIcon(tabIconSelectedList[position]);
        setIconTextColor(position, R.color.colorContentTextPink);
        id_vp.setCurrentItem(position, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        tab.setIcon(tabIconNormalList[position]);
        setIconTextColor(position, R.color.colorTextGray);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static class ParentListPagerAdapter extends FragmentPagerAdapter{

        public ParentListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case TAB_O_SISO:
                    return new ParentListSIsoFragment();
                case TAB_1_FAVOTITE:
//                    return SitterListFavoriteFragment.newInstance(1, R.string.sitter_list_tab_favorite);
                    return new SitterListFavoriteFragment();
                case TAB_2_SEND:
                    return new SitterListSendFragment();
                case TAB_3_RECEIVE:
                    return new SitterListReceiveFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
