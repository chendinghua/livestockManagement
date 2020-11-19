package com.kymjs.app.base_res.utils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;


public abstract class  BaseModelFragment<T  extends Activity> extends Fragment implements ResponseHandlerInterface {
    protected View rootView;

    private Unbinder mUnbinder;
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;
    protected T activity;

    protected Device device;

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     *//*
    private boolean isFragmentVisible;

    *//**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     *//*
    protected View rootView;*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);

        activity = (T)getActivity();
        mUnbinder = ButterKnife.bind(this, rootView);

        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirst) {
            onFragmentVisibleChange(true);
        }
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();


    //初始化view
    protected abstract void initView();


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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst&&isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    //判断是否开启RFID模块
    public abstract boolean isOpenUHFModel();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if(isOpenUHFModel() && device!=null){
            device.destroy();
        }
    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(isVisible){
            initView();

            if(isOpenUHFModel()){
                DeviceFactory factory = new DeviceFactory(activity,this);

                device = factory.getDevice();
                if(device!=null){
                    device.initUHF();

                }else{
                    Toast.makeText(activity,"获取RFID模块失败",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            if(device!=null){
                device.destroy();
            }
        }
    }

    public abstract boolean isSingle();

    public void onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化
        if (isOpenUHFModel() && device!=null){
            device.onKeyDown(keyCode,event,1,isSingle());
        }
    }

}

