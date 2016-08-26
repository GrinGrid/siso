package net.gringrid.siso;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

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
//        mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        mActionBarDrawerToggle.setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){

            @Override
            public void onBackStackChanged() {

                Log.d(TAG, "onCreate: getSupportActionBar().getDisplayOptions()"+getSupportActionBar().getDisplayOptions());
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                Log.d(TAG, "onBackStackChanged:mActionBarDrawerToggle.isDrawerIndicatorEnabled()  "+mActionBarDrawerToggle.isDrawerIndicatorEnabled() );

                if ( mFragmentManager.getBackStackEntryCount() == 1 ){
//                    getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_USE_LOGO | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME| android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE, android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME);
//                    getSupportActionBar().setHomeButtonEnabled(false);
//                    getSupportActionBar().setDisplayShowHomeEnabled(true);
//                    mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }else{
//                    getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP| android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                }
            }
        });
        Log.d(TAG, "onCreate: getSupportActionBar().getDisplayOptions()"+getSupportActionBar().getDisplayOptions());

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

//        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                int fragmentCount = mFragmentManager.getBackStackEntryCount();
                Log.d(TAG, "onClick: fragmentCount "+fragmentCount);
                if ( fragmentCount > 1 ){
                    onBackPressed();
                }else{
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
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: baseactivity"); 
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ( mFragmentManager.getBackStackEntryCount() > 1 ) {
                mFragmentManager.popBackStack();
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
        // 모든 stack을 비운다
        mFragmentManager.popBackStack(MENU, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        switch (menuId){
            case MENU_SIGN_UP:
                Member1Fragment member1Fragment = new Member1Fragment();
                setFragment(member1Fragment, MENU, 0);
                break;
        }
    }

    public void setFragment(Fragment fragment, String title, int drawer){
        Log.d(TAG, "setFragment: call");
        mFragmentManager.beginTransaction().add(R.id.id_rl_for_fragment, fragment)
                .addToBackStack(title)
                .commit();


//        manager.beginTransaction().replace(R.id.id_rl_for_fragment, fragment).commit();
        getSupportActionBar().setTitle(title);
        Log.d(TAG, "setFragment: mFragmentManager.getBackStackEntryCount() : "+mFragmentManager.getBackStackEntryCount());

    }
    
    
}
