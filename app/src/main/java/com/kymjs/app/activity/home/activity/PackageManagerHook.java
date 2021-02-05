package com.kymjs.app.activity.home.activity;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 16486 on 2021/2/2.
 */

public class PackageManagerHook {
    public static void hook(final Context context,final int versionCode,final String name) {
        try {
            //1、得到ActivityThread类
            Class<?> activityThreadClz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThreadClz.getMethod("currentActivityThread");
            //2、得到当前的ActivityThread对象
            Object activityThread = currentActivityThread.invoke(null);
            //3、得到PackageManager对象
            Method getPackageManager = activityThreadClz.getMethod("getPackageManager");
            final Object pkgManager = getPackageManager.invoke(activityThread);

            Class<?> packageManagerClz = Class.forName("android.content.pm.IPackageManager");
            //hook sPackageManager
            Field packageManagerField = activityThreadClz.getDeclaredField("sPackageManager");
            packageManagerField.setAccessible(true);
            //动态代理
            packageManagerField.set(activityThread, Proxy.newProxyInstance(context.getClassLoader(),
                    new Class[] { packageManagerClz }, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Object result = method.invoke(pkgManager,args);
                            if ("getPackageInfo".equals(method.getName())){
                                PackageInfo pkgInfo = (PackageInfo) result;

                                //修改App的版本信息
                                pkgInfo.versionCode = versionCode;
                                pkgInfo.versionName = name;
                            }
                            return result;
                        }
                    }));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
