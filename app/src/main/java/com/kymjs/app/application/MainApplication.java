package com.kymjs.app.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.kymjs.common.App;
import com.kymjs.common.BuildConfig;
import com.kymjs.common.LogUtils;
import com.kymjs.crash.CustomActivityOnCrash;

/**
 * Created by 16486 on 2020/11/23.
 */

public class MainApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //配置分包
        MultiDex.install(this);
        LogUtils.mLogEnable = BuildConfig.DEBUG;
        CustomActivityOnCrash.install(App.INSTANCE);
    }
}