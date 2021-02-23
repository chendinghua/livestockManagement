package com.kymjs.app.base_res.utils.utils;

import android.content.Context;
import android.util.Log;

import com.kymjs.app.base_res.R;

/**
 * Created by 16486 on 2021/2/19.
 */

public class LogUtils {

    public static void d(Context context,String tag, String msg){
        context.getResources().getBoolean(R.bool.baseres_logEnable);
        Log.d(tag,msg);

    }

}
