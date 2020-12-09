package com.kymjs.app.base_res.utils.base;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.app.AppManager;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.utils.StatusBarSetting;
import com.kymjs.app.base_res.utils.utils.Utils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;

/**
 * 基类
 */

public abstract class BaseModelActivity extends FragmentActivity implements ResponseHandlerInterface {

    public Context mContext;
    private Unbinder mUnbinder;
    private int count;//记录开启进度条的情况 只能开一个

    public Device device;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        // 默认着色状态栏
        SetStatusBarColor();
        mUnbinder = ButterKnife.bind(this);

        mContext = this;
        if(isOpenUHFModel()){
            DeviceFactory factory = new DeviceFactory(this,this);

            device = factory.getDevice();
            if(device!=null){
                device.initUHF();

            }else{
                Toast.makeText(mContext,"获取RFID模块失败",Toast.LENGTH_SHORT).show();
            }
        }
        initActionBar();

        this.initPresenter();
        this.initView();

    }

    private void initActionBar() {

        ActionBar actionBar = getActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayShowHomeEnabled(true);//显示home区域
            actionBar.setDisplayHomeAsUpEnabled(true);//显示返回图片
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);//设置返回图标
            actionBar.setTitle(getFragmentTitle());
        }
    }





    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化
        if (isOpenUHFModel() && device!=null){
            device.onKeyDown(keyCode,event,1,isSingle());
        }

        return super.onKeyDown(keyCode, event);

    }*/

    public abstract String getFragmentTitle();


    //判断是否打开
    public abstract  boolean isOpenUHFModel();

    //判断RFID模块读取是单个还是多个
    public abstract boolean isSingle();

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView() ;

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarSetting.setColor(this, getResources().getColor(R.color.baseres_colorPrimary));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarSetting.setColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarSetting.setTranslucent(this);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isOpenUHFModel() && device!=null)
        {
            device.destroy();
        }
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            DialogUtils.showAlertDialog(this, new AlertDialogCallBack() {
                @Override
                public void alertDialogFunction() {
                    if(device!=null){
                        device.destroy();
                    }
                    Utils.activityFinish(BaseModelActivity.this,device);
                 //   finish();
                }
            },"是否结束当前流程",null,null);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
}
