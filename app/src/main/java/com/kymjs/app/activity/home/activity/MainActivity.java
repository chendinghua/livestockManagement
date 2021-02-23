package com.kymjs.app.activity.home.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.R;
import com.kymjs.app.activity.home.dialog.VaccineDialog;
import com.kymjs.app.activity.home.entry.RightInfo;
import com.kymjs.app.base_res.utils.AnimationUtil;
import com.kymjs.app.base_res.utils.base.entry.vaccine.VaccineInfo;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.SharedPreferencesMenu;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.router.FragmentRouter;
import com.kymjs.router.RouterList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import hsj.expmle.com.prevention.activity.PreventionActivity;
import hsj.expmle.com.updateVersion.ParseXmlService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    List<RightInfo> rightInfos;

    DrawerLayout drawer;

    String currentFragment="";

    VaccineDialog mDialog;

    VaccineDialog.Builder builder;

    HandlerUtils handlerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = this;
        builder = new VaccineDialog.Builder(mContext);
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);

        Log.d("updateInfo", "onCreate: "+SPUtils.getSharedBooleanData(mContext,"isUpdate"));
        //判断当前版本是否更新
        if(!SPUtils.getSharedBooleanData(mContext,"isUpdate")) {
            SPUtils.setSharedBooleanData(mContext, "isUpdate", true);
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    InputStream inStream =  (InputStream) msg.obj;
                    // 把version.xml放到网络上，然后获取文件信息
                    // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
                    ParseXmlService service = new ParseXmlService();
                    HashMap<String,String> mHashMap=null;
                    try {

                        mHashMap = service.parseXml(inStream);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (null != mHashMap)
                    {
                        int serviceCode = Integer.valueOf(mHashMap.get("version"));
                        String name = mHashMap.get("name");
                        PackageManagerHook.hook(mContext,serviceCode,name);
                        Log.d("updateInfo", "handleMessage:    updateCode   "+serviceCode  +"    name :"+name);

                    }
                    super.handleMessage(msg);
                }
            };

            new Thread(){

                @Override
                public void run() {
                    InputStream inStream = null;
                    try {
                        //	String versionUrl =	mContext.getResources().getString(R.string.downloadUpdateNewVersionUrl);
                        SharedPreferences sp =	mContext.getSharedPreferences("setting_action_url_config", Context.MODE_PRIVATE);
                        String versionUrl =sp.getString("updateUrl", SharedPreferencesMenu.updateUrl);
                        //	URL url = new URL("http://10.10.1.69/HSJ.AutoUpdate/PDA/version.xml");
                        URL url = new URL(versionUrl);
                        inStream =	url.openStream();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                        Log.d("installLog", "run: "+e.getMessage());
                    }
                    Message msg= new Message();
                    msg.obj = inStream;
                    handler.sendMessage(msg);
                }

            }.start();



        }


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
                currentFragment =  FragmentRouter.replaceFragment(MainActivity.this,R.id.content_main, RouterList.USR_INFO_FRAGMENT);
                //隐藏边缘菜单
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        tvUserName.setText(SPUtils.getSharedStringData(mContext,"userName"));

        rightInfos  =  JSON.parseArray(SPUtils.getSharedStringData(mContext,"rightList"),RightInfo.class);
        for (int i =0;i<rightInfos.size();i++){
            if(rightInfos.get(i).getParentID()==0) {
                for (int j =0;j<rightInfos.size();j++){
                    if(rightInfos.get(j).getParentID() == rightInfos.get(i).getID() && rightInfos.get(j).getRightType()==1){
                        SubMenu subMenu = navigationView.getMenu().
                                addSubMenu(rightInfos.get(j).getName());
                        for (int k=0;k<rightInfos.size();k++){
                            if(rightInfos.get(k).getParentID() == rightInfos.get(j).getID()){

                                subMenu   .add(""+rightInfos.get(k).getName())
                                        .setIcon(mContext.getDrawable(getBitmapIdByName(mContext,rightInfos.get(k).getActionBtn())))
                                        .setTitleCondensed(rightInfos.get(k).getName()+":" + rightInfos.get(k).getActionForm()+":"+rightInfos.get(k).getActionUrl()+" ");
                            }
                        }
                    }else if(rightInfos.get(j).getParentID() == rightInfos.get(i).getID() && rightInfos.get(j).getRightType()==0){
                        navigationView.getMenu().add(rightInfos.get(j).getName())
                                .setIcon(mContext.getDrawable(getBitmapIdByName(mContext,rightInfos.get(j).getActionBtn())))
                                //传入每一列的选中时的下标
                                .setTitleCondensed(rightInfos.get(j).getName()+":" + rightInfos.get(j).getActionForm()+":"+rightInfos.get(j).getActionUrl()+" ");
                    }
                }
            }
        }
        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //显示
                if(MethodEnum.POSTVACCINELIST.equals(msg.getData().getString("method"))){
                    final  List<VaccineInfo> vaccineInfoList =   JSON.parseArray( JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data")).getString("Result") ,VaccineInfo.class);
                    Log.d("vaccineInfoList", "handlerExecutionFunction: "+vaccineInfoList.size());
                    /*疫苗列表信息大于0*/
                    if(vaccineInfoList.size()>0){
                        mDialog = builder.setMessage("请确认选择处理疫苗信息").
                                initVaccine(vaccineInfoList).
                                initOnItemSelectedListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("VaccineID",vaccineInfoList.get(position).getVaccineID());
                                        Utils.gotoActivity(MainActivity.this, PreventionActivity.class,bundle,null);
                                        mDialog.dismiss();
                                    }
                                }).setSingleButton("隐藏", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        }).createSingleButtonDialog();
                        mDialog.show();

                    }


                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main,
                        FragmentRouter.getFragment(RouterList.MEMORY_FRAG_MAIN))
                .commit();

        HashMap<String,Object> vaccineMap = new HashMap<>();
        vaccineMap.put("DeptID",SPUtils.getSharedIntData(mContext,"DeptID"));
        vaccineMap.put("Day",SPUtils.getSharedIntData(mContext,"Day"));
        vaccineMap.put("pageIndex",1);
        vaccineMap.put("pageSize",50);
        InteractiveDataUtil.interactiveMessage(this,vaccineMap,handlerUtils, MethodEnum.POSTVACCINELIST, InteractiveEnum.GET);
        initLoadFragment();
    }

    private void initLoadFragment() {
        String defualtFragmentName= "hsj.home.com.fragment.HomeFragment";
        getToolbar().setTitle("首页");

        SPUtils.setSharedStringData(mContext,"actionUrl","");
        SPUtils.setSharedStringData(mContext,"ActionForm",defualtFragmentName);
        Log.d("MainActivity", "onNavigationItemSelected:  "+defualtFragmentName);
        if(!"".equals(defualtFragmentName) ) {
            FragmentRouter.replaceFragment(this, R.id.content_main, defualtFragmentName);
            currentFragment = defualtFragmentName;
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public int getBitmapIdByName(Context context , String name) {
        if("".equals(name) || name==null){
            return R.drawable.destroy;
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        // int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);
        // return BitmapFactory.decodeResource(getResources(), resID);
        return getResources().getIdentifier(name, "drawable", appInfo.packageName);
    }



    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        //下标0  Name  下标1  ActionForm  下标2  ActionUrl
        String [] selectParam = item.getTitleCondensed().toString().split(":");

        getToolbar().setTitle(selectParam[0]);

        SPUtils.setSharedStringData(mContext,"actionUrl",selectParam[2].trim());
        SPUtils.setSharedStringData(mContext,"ActionForm",selectParam[1]);
        Log.d("MainActivity", "onNavigationItemSelected:  "+selectParam[1] );
        if(!"".equals(selectParam[1]) ){



            FragmentRouter.replaceFragment(this,R.id.content_main,selectParam[1]);
            currentFragment = selectParam[1];
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
    //第一次点击事件发生的时间
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           /* if (mDrawer.isMenuVisible()) {
                //隐藏导航栏
                mDrawer.closeMenu();
            }else {*/
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this,R.string.exitSystem, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            //  }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
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
