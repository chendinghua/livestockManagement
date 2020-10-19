package com.kymjs.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.AnimationUtil;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.entry.RightInfo;
import com.kymjs.router.FragmentRouter;
import com.kymjs.router.RouterList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
     List<RightInfo> rightInfos;
    DrawerLayout drawer;

    String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView tvUserName = (TextView) headerView.findViewById(R.id.tv_userName);
        ImageView ivImage = (ImageView) headerView.findViewById(R.id.imageView);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //页面fragment切换的到个人信息页面
                currentFragment =  FragmentRouter.replaceFragment(MainActivity.this,R.id.content_main,RouterList.USR_INFO_FRAGMENT);
            }
        });

        tvUserName.setText(SPUtils.getSharedStringData(mContext,"userName"));

        rightInfos  =  JSON.parseArray(SPUtils.getSharedStringData(mContext,"rightList"),RightInfo.class);
        //
        for (int i =0;i<rightInfos.size();i++){
            if(rightInfos.get(i).getParentID()!=0) {
                //设置标题
                navigationView.getMenu().add(rightInfos.get(i).getName())
                        //设置导航栏每一列的小图标
                        //     .setIcon(getActivity().getDrawable(getBitmapIdByName(mainActivity,rightInfos.get(i).getActionBtn())))
                        //.setTitleCondensed(rightInfos.get(i).getActionForm());
                        //传入每一列的选中时的下标
                        .setTitleCondensed("" + i);
            }
        }




        navigationView.setNavigationItemSelectedListener(this);

       getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main,
                        FragmentRouter.getFragment(RouterList.MEMORY_FRAG_MAIN))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
      RightInfo rightInfo = rightInfos.get( Integer.parseInt(  item.getTitleCondensed().toString()));

        // Handle navigation view item clicks here.
      /*  int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            ActivityRouter.startActivity(this, RouterList.EXPLORER_ATY_MAIN);
        } else if (id == R.id.nav_share) {
            ActivityRouter.startActivity(this, RouterList.MEMORY_ATY_MAIN);
        } else if (id == R.id.nav_send) {
            throw new RuntimeException("throw exception");
        }*/
      String actionUrl = rightInfo.getActionUrl();
        if(!"".equals(actionUrl) && "".equals(currentFragment)){
            FragmentRouter.replaceFragment(this,R.id.content_main,actionUrl);


        }



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (BuildConfig.ISAPP) {
            return handleExitTip(keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }

    /////////////////////////// show exit tip ////////////////////////////////////

    private boolean isOnKeyBacking;
    private Handler mMainLoopHandler = new Handler(Looper.getMainLooper());
    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            cancleExit();
        }
    };

    private boolean handleExitTip(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isOnKeyBacking) {
                mMainLoopHandler.removeCallbacks(onBackTimeRunnable);
                isOnKeyBacking = false;
                finish();
            } else {
                isOnKeyBacking = true;
                showExitTip();
                mMainLoopHandler.postDelayed(onBackTimeRunnable, 2000);
            }
            return true;
        }
        return false;
    }

    /**
     * 显示Toolbar的退出tip
     */
    public void showExitTip() {
        TextView view = (TextView) findViewById(R.id.titlebar_text_exittip);
        view.setVisibility(View.VISIBLE);
        Animation a = AnimationUtil.getTranslateAnimation(0f, 0f, -getToolbar().getHeight(), 0f, 300);
        view.startAnimation(a);
    }

    /**
     * 取消退出
     */
    public void cancleExit() {
        final TextView view = (TextView) findViewById(R.id.titlebar_text_exittip);
        Animation a = AnimationUtil.getTranslateAnimation(0f, 0f, 0f, -getToolbar().getHeight(), 300);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(a);
    }
}
