package net.gringrid.siso;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import net.gringrid.siso.fragments.LoginFragment;
import net.gringrid.siso.fragments.Member01UserTypeFragment;
import net.gringrid.siso.fragments.MemberModifyFragment;
import net.gringrid.siso.fragments.Parent00IndexFragment;
import net.gringrid.siso.fragments.Sitter00IndexFragment;
import net.gringrid.siso.fragments.CommonPhotoFragment;
import net.gringrid.siso.fragments.SitterListFragment;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;

import java.util.List;

public class BaseActivity extends RootActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    final String TAG = "jiho";

    final static String MENU                = "MENU";
    final static int MENU_MY_INFO           = R.id.nav_my_info;
    final static int MENU_MESSAGE           = R.id.nav_message;
    final static int MENU_INPUT_SITTER      = R.id.nav_input_sitter;
    final static int MENU_INPUT_PARENT      = R.id.nav_input_parent;
    final static int MENU_SITTER_LIST       = R.id.nav_sitter_list;
    final static int MENU_PARENT_LIST       = R.id.nav_parent_list;
    final static int MENU_SUGGESTION        = R.id.nav_suggestion;
    final static int MENU_GUIDE             = R.id.nav_guide;
    final static int MENU_SIGN_UP           = R.id.nav_sign_up;
    final static int MENU_LOG_IN            = R.id.nav_log_in;
    final static int MENU_LOG_OUT           = R.id.nav_log_out;
    final static int MENU_SHARE             = R.id.nav_share;

    final int DRAWER_MODE_HAMBURGER = 0;
    final int DRAWER_MODE_BACK = 1;

    public static final int TITLE_NONE = Integer.MAX_VALUE;
    public static final int ACTIONBAR_HIDE = Integer.MIN_VALUE;
    public static final int TITLE_KEEP = Integer.MAX_VALUE - 1;

    Toolbar mToolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView mNavigationView;
    TextView id_toolbar_title;
    ImageView id_toolbar_logo;
    ImageView id_toolbar_memo;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle= new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mActionBarDrawerToggle);

        mActionBarDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        id_toolbar_title = (TextView)mToolbar.findViewById(R.id.id_toolbar_title);
        id_toolbar_logo = (ImageView)mToolbar.findViewById(R.id.id_toolbar_logo);
        id_toolbar_memo = (ImageView)mToolbar.findViewById(R.id.id_toolbar_memo);

//        getSupportActionBar().setHomeButtonEnabled(true);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        // custom title, logo 를 사용하기 위해 기본적은 title은 사용하지 않는다
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // ToolBar의 왼쪽 아이콘 버튼이 Hamburg 버튼이면
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                int fragmentCount = mFragmentManager.getBackStackEntryCount();
                Log.d(TAG, "onClick: onclick fragment count : "+fragmentCount);
                if ( fragmentCount > 1 ){
                    onBackPressed();
                }else{

                    mNavigationView.getMenu().clear();
//                    mNavigationView.inflateMenu(R.menu.menu_parent);
//                    mNavigationView.inflateMenu(R.menu.menu_sitter);
                    mNavigationView.inflateMenu(getMenuId());

                    int drawerLockMode = mDrawer.getDrawerLockMode(GravityCompat.START);
                    if (mDrawer.isDrawerVisible(GravityCompat.START)
                            && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
                        mDrawer.closeDrawer(GravityCompat.START);
                    } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
                        mDrawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });

        // 요청 메뉴로 이동
        callMenu(getIntent().getIntExtra(MENU, MENU_SIGN_UP));
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "BaseActivity onResume: ");
        super.onResume();
    }

    /**
     * 로그인 여부와 부모/시터에 따라 메뉴구성을 달리 한다
     * @return
     */
    private int getMenuId() {
        String sessionKey = SharedData.getInstance(this).getGlobalDataString(SharedData.SESSION_KEY);
        String userType=null;
        int menuId = 0;

        User user = SharedData.getInstance(this).getUserData();
        Log.d(TAG, "getMenuId: getMenuId user : "+user.toString());
//        Personal personal = SharedData.getInstance(this).getUserLoginData();
        Personal personal = user.personalInfo;
        if ( personal != null ) {
            Log.d(TAG, "getMenuId: member is not null");
            userType = personal.user_type;
        }
        if (TextUtils.isEmpty(sessionKey)) {
            Log.d(TAG, "getMenuId: logout");
            menuId = R.menu.menu_logout;
        }else if (userType.equals(User.USER_TYPE_SITTER)){
            menuId = R.menu.menu_sitter;
            Log.d(TAG, "getMenuId: sitter ");
        }else if (userType.equals(User.USER_TYPE_PARENT)){
            menuId = R.menu.menu_parent;
            Log.d(TAG, "getMenuId: paretn");
        }else{
            menuId = R.menu.menu_logout;
            Log.d(TAG, "getMenuId: default");
        }

        return menuId;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int backStackCount = mFragmentManager.getBackStackEntryCount();

        Log.d(TAG, "onBackPressed: backStackCount : "+backStackCount);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ( mFragmentManager.getBackStackEntryCount() > 1 ) {
                mFragmentManager.popBackStack();
//                for(Fragment fragment : mFragmentManager.getFragments()){
//                    if(fragment!=null) fragment.onResume();
//                }
                String fragmentName = mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount()-2).getName();
                setToolbarTitle(Integer.parseInt(fragmentName));


//                setTitle(fragmentName);

            }else{
                super.onBackPressed();
            }
            // fragment를 pop 해서 1이되면 뒤로가기 아이콘을 햄버거 아이콘으로 변경
            if ( mFragmentManager.getBackStackEntryCount() == 1 ) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.base, menu);
        Log.d(TAG, "onCreateOptionMenu");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(TAG, "onOptionsItemSelected");
        if (id == android.R.id.home){
            Log.d(TAG, "onOptionsItemSelected: HOME");
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.d(TAG, "onMenuOpened: ");
        return super.onMenuOpened(featureId, menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Log.d(TAG, "onNavigationItemSelected: ");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        callMenu(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 해당 메뉴로 이동
     * @param menuId
     */
    private void callMenu(int menuId){
        Fragment fragment;
        switch (menuId){
            case MENU_SIGN_UP:
                fragment = new Member01UserTypeFragment();
                setCleanUpFragment(fragment, R.string.member_title);
                break;

            case MENU_INPUT_SITTER:
                fragment = new Sitter00IndexFragment();
                setCleanUpFragment(fragment, R.string.sitter_title);
                break;

            case MENU_INPUT_PARENT:
                fragment = new Parent00IndexFragment();
                setCleanUpFragment(fragment, R.string.parent_title);
                break;

            case MENU_MY_INFO:
                Log.d(TAG, "callMenu: MENU_MY_INFO");
                fragment = new MemberModifyFragment();
                setCleanUpFragment(fragment, R.string.parent_title);
                break;

            case MENU_SITTER_LIST:
                fragment = new SitterListFragment();
                setCleanUpFragment(fragment, TITLE_NONE);
                break;

            case MENU_PARENT_LIST:
                fragment = new SitterListFragment();
                setCleanUpFragment(fragment, TITLE_NONE);
                break;

            case MENU_LOG_IN:
                fragment = new LoginFragment();
                setCleanUpFragment(fragment, R.string.login_title);
                break;

            case MENU_LOG_OUT:
                SharedData.getInstance(this).insertGlobalData(SharedData.SESSION_KEY, "");
                SharedData.getInstance(this).insertGlobalData(SharedData.USER, null);
                fragment = new LoginFragment();
                setCleanUpFragment(fragment, R.string.login_title);
                break;
        }
    }



    public void setToolbarTitle(String title){
        id_toolbar_title.setText(title);
    }

    public void setToolbarTitle(int id){
        id_toolbar_title.setVisibility(View.GONE);
        id_toolbar_logo.setVisibility(View.GONE);
        id_toolbar_memo.setVisibility(View.GONE);
        mToolbar.setVisibility(View.VISIBLE);

        if(id==TITLE_NONE) {
            id_toolbar_logo.setVisibility(View.VISIBLE);
            id_toolbar_memo.setVisibility(View.VISIBLE);
            // TODO memo count
        }else if (id==ACTIONBAR_HIDE){
            mToolbar.setVisibility(View.GONE);
        }else if (id==TITLE_KEEP){
            return;
        }else{
            id_toolbar_title.setVisibility(View.VISIBLE);
            id_toolbar_title.setText(id);
        }
    }

    /**
     * Content로 사용될 fragment를 호출한다
     * @param fragment
     */
    public void setFragment(Fragment fragment, int titleId){
        hideSoftKeyboard();

        mFragmentManager.beginTransaction().add(R.id.id_rl_for_fragment, fragment)
                .addToBackStack(String.valueOf(titleId))
//                .addToBackStack(getString(titleId))
                .commit();
        setToolbarTitle(titleId);
    }

    /**
     * Content로 사용될 fragment를 호출한다
     * @param fragment
     */
    public void setCleanUpFragment(Fragment fragment, int titleId){
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        hideSoftKeyboard();

        // 모든 stack을 비운다
        mFragmentManager.beginTransaction().replace(R.id.id_rl_for_fragment, fragment)
                .addToBackStack(String.valueOf(titleId))
                .commit();
        setToolbarTitle(titleId);
    }

    /**
     * softkeyboard가 열려 있을경우 감춘다.
     */
    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if ( view != null ){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 상단 Toolbar 의 왼쪽 아이콘 컨트롤
     * 햄버거 <-> 뒤로가기 화살표 toggle
     */
    @Override
    public void onBackStackChanged() {
        Log.d(TAG, "onBackStackChanged: size : "+mFragmentManager.getFragments().size());
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        if(fragmentList!=null && fragmentList.size()>0 && fragmentList.get(fragmentList.size()-1) instanceof CommonPhotoFragment){
        }
        if ( mFragmentManager.getBackStackEntryCount() == 1 ){
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        }else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

//    Dialog loadingDialog = null;
    public void showProgress2(){
//        if (mHandler==null){
//            mHandler = new Handler(Looper.getMainLooper());
//        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loadingDialog = new Dialog(BaseActivity.this);
                loadingDialog.setTitle("Loading data..");
                loadingDialog.setContentView(R.layout.loading);
                loadingDialog.show();

            }
        });
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog = new Dialog(BaseActivity.this);
//                loadingDialog.setTitle("Loading data..");
//                loadingDialog.setContentView(R.layout.loading);
//                loadingDialog.show();
//
//            }
//        });
    }

//    public void hideProgress(){
//
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                loadingDialog.dismiss();
//            }
//        });
//    }
}
