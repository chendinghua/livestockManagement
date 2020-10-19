package com.kymjs.router;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by ZhangTao on 10/12/16.
 */

public class FragmentRouter {

    public static Fragment getFragment(String name) {
        Fragment fragment;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fragment;
    }

    /***
     *
     * @param activity
     * @param reId
     * @param actionFragment
     */
    public static String  replaceFragment(FragmentActivity activity,int reId,String actionFragment){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(reId,
                        getFragment(actionFragment))
                .commit();
              return actionFragment;

    }


}
