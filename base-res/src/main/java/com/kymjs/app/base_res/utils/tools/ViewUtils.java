package com.kymjs.app.base_res.utils.tools;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 16486 on 2020/7/18.
 */

public class ViewUtils {

    public static void settingViewBackgroud(View view , Context mContext, int retId){
        view.setBackground(mContext.getResources().getDrawable(retId,null));
    }

    public static void settingViewTextColor(TextView view, Context mContext, int retId){
        view.setTextColor(mContext.getResources().getColor(retId,null));
    }
}
