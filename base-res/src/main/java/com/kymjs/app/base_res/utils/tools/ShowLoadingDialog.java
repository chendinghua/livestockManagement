package com.kymjs.app.base_res.utils.tools;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.kymjs.app.base_res.R;


/** 显示数据加载页面
 * Created by Administrator on 2019/5/22/022.
 */
public class ShowLoadingDialog {

    static Handler resultHandler;
    public static void show(final PopupWindow popupWindow, final Activity activity){
        try {


            popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(false);
            View view = LayoutInflater.from(activity).inflate(R.layout.baseres_popup,null);
            popupWindow.setContentView(view);
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
            resultHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(popupWindow.isShowing()) {
                        UIHelper.ToastMessage(activity, R.string.baseres_RequestTimeout);
                        popupWindow.dismiss();
                    }
                }
            };
        }catch (WindowManager.BadTokenException e){
            e.printStackTrace();
        }
        //     new Handler().postDelayed(runnable,10000);
    }

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            resultHandler.sendMessage(null);
        }
    };
}
