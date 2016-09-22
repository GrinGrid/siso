package net.gringrid.siso;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import net.gringrid.siso.fragments.LoginFragment;
import net.gringrid.siso.fragments.Member1Fragment;
import net.gringrid.siso.fragments.Sitter1Fragment;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;

import org.w3c.dom.Text;

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


    Toolbar mToolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView mNavigationView;
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

//        getSupportActionBar().setHomeButtonEnabled(true);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);
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
        int userType = 0;
        int menuId = 0;

        Personal personal = SharedData.getInstance(this).getUserLoginData();
        if ( personal != null ) {
            Log.d(TAG, "getMenuId: member is not null");
            userType = personal.userType;
        }

        if (TextUtils.isEmpty(sessionKey)) {
            Log.d(TAG, "getMenuId: logout");
            menuId = R.menu.menu_logout;
        }else if ( userType == User.USER_TYPE_SITTER ){
            menuId = R.menu.menu_sitter;
            Log.d(TAG, "getMenuId: sitter ");
        }else if ( userType == User.USER_TYPE_PARENT){
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

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ( mFragmentManager.getBackStackEntryCount() > 1 ) {
                mFragmentManager.popBackStack();
                String fragmentName = mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount()-2).getName();
                setTitle(fragmentName);

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
        switch (menuId){
            case MENU_SIGN_UP:
                Member1Fragment member1Fragment = new Member1Fragment();
                setCleanUpFragment(member1Fragment, R.string.member_title);
                break;

            case MENU_INPUT_SITTER:
                Sitter1Fragment sitter1Fragment = new Sitter1Fragment();
                setCleanUpFragment(sitter1Fragment, R.string.sitter_basic_title);
                break;

            case MENU_LOG_IN:
                LoginFragment loginFragment = new LoginFragment();
                setCleanUpFragment(loginFragment, R.string.login_title);
                break;

            case MENU_LOG_OUT:
                SharedData.getInstance(this).insertGlobalData(SharedData.SESSION_KEY, "");
                SharedData.getInstance(this).insertGlobalData(SharedData.PERSONAL, null);
                LoginFragment fragment = new LoginFragment();
                setCleanUpFragment(fragment, R.string.login_title);

                break;
        }
    }


    /**
     * Content로 사용될 fragment를 호출한다
     * @param fragment
     */
    public void setFragment(Fragment fragment, int titleId){
        hideSoftKeyboard();
        setTitle(getString(titleId));
        mFragmentManager.beginTransaction().add(R.id.id_rl_for_fragment, fragment)
                .addToBackStack(getString(titleId))
                .commit();
    }

    /**
     * Content로 사용될 fragment를 호출한다
     * @param fragment
     */
    public void setCleanUpFragment(Fragment fragment, int titleId){
        Log.d(TAG, "after mFragmentManager.getBackStackEntryCount() : "+mFragmentManager.getBackStackEntryCount());
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.d(TAG, "before mFragmentManager.getBackStackEntryCount() : "+mFragmentManager.getBackStackEntryCount());

        hideSoftKeyboard();
        setTitle(getString(titleId));

        // 모든 stack을 비운다
        mFragmentManager.beginTransaction().replace(R.id.id_rl_for_fragment, fragment)
                .addToBackStack(getString(titleId))
                .commit();
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
