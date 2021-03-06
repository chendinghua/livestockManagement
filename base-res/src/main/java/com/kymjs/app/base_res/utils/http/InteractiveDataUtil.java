package com.kymjs.app.base_res.utils.http;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kymjs.app.base_res.utils.tools.ShowLoadingDialog;
import com.kymjs.app.base_res.utils.utils.SharedPreferencesMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/1/15/015.
 */

public class InteractiveDataUtil {

    /***
     *  调用接口的方法
     * @param activity 当前的页面对象
     * @param params    数据传入集合
     * @param handler   结果响应对象
     * @param method    调用接口名
     * @param interactiveType   调用接口方式 POST、GET
     */
    public static void interactiveMessage(final Activity activity , final HashMap<String,Object> params, final HandlerUtils handler, final String method, final String interactiveType ){
        final PopupWindow popupWindow = new PopupWindow();

        SharedPreferences sp = activity.getSharedPreferences("setting_action_url_config", Context.MODE_PRIVATE);
        final String path = sp.getString("actionUrl", SharedPreferencesMenu.actionUrl);
        handler.setContext(activity);
   /*  final Runnable startRunnable = new Runnable() {
          @Override
          public void run() {
              if(activity instanceof Activity)
              ShowLoadingDialog.show(popupWindow, (Activity) activity);
          }
      };
    final Runnable stopRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (popupWindow != null && popupWindow.isShowing())
                    popupWindow.dismiss();
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    };*/
        final Handler loadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                if(popupWindow!=null) {
                    popupWindow.dismiss();
                    Log.d("onKeyBack", "handleMessage:     dismiss" );
                }
            }
        };


        if(activity instanceof Activity){
            ShowLoadingDialog.show(popupWindow, activity);

        }

        if(activity instanceof Activity){
            ShowLoadingDialog.show(popupWindow, activity);

        }
        new Thread(new Runnable() {
            @Override
            public  void run() {
          //      new Handler(Looper.getMainLooper()).post(startRunnable);//在子线程中直接去new 一个handler
                Message msg = new Message();
                Bundle bundle = new Bundle();
                try {
                    String result = "";
                    OkHttpUtils httpUtils = OkHttpUtils.getInstance();
                    if (interactiveType.equals(InteractiveEnum.GET)) {
                        int index = -1;
                        StringBuffer paths = new StringBuffer(path + method);
                        if(params!=null) {
                            for (Map.Entry<String, Object> entry : params.entrySet()) {
                                if (index == -1) {
                                    paths.append("?" + entry.getKey() + "=" + entry.getValue());
                                    index = 1;
                                } else {
                                    paths.append("&" + entry.getKey() + "=" + entry.getValue());
                                }
                            }
                        }
                        result = httpUtils.getData(activity,paths.toString()).body().string();
                    } else if (interactiveType.equals(InteractiveEnum.POST)) {
                       String json =  JSON.toJSONString(params);
                        Log.d("JSON", "run: "+json);
                        result = httpUtils.postJson(activity,path + method, json);

                    } else if (interactiveType.equals(InteractiveEnum.UPLOAD)) {
                      //  result = httpUtils.uploadImage((Context) activity, params, method);
                    }


                    Log.d("JSON", "run 1 : " + result);
                    if (result.trim().equals("")) {
                        msg.what = -2;
                    } else {
                        JSONObject object = JSON.parseObject(result);
                        if (object.getBoolean("Success")) {
                            //操作成功
                            msg.what = 1;
                            bundle.putString("result", result);
                        } else {
                            bundle.putString("result", result);
                            //操作失败
                            msg.what = -1;
                        }
                    }
                    bundle.putString("method", method);
                    msg.setData(bundle);
                    if (handler != null)
                        handler.sendMessage(msg);


                }catch (Exception ex){
                    msg.what=-1;

                bundle.putString("result",    "{\"Success\": false,\n" +
                        "\"Message\": \"系统错误，请联系管理员\"} ");
                    msg.setData(bundle);
                    if (handler != null)
                        handler.sendMessage(msg);
                    Log.d("errorMessage", "run: "+  ex.getMessage());
                }finally {
                  //  new Handler(Looper.getMainLooper()).post(stopRunnable);//在子线程中直接去new 一个handler
                    loadHandler.sendMessage(new Message());
                }

            }
        }).start();

    }

    /***
     * 用来处理绑定的数据
     * @param activity 当前的页面对象
     * @param params    数据传入集合
     * @param handler   结果响应对象
     * @param method    调用接口名
     * @param interactiveType   调用接口方式 POST、GET
     * @param bindDate  绑定处理的数据
     */
    public static void interactiveMessage(final Activity activity, final HashMap<String,Object> params, final Handler handler, final String method, final String interactiveType, final String bindDate ){


        final PopupWindow popupWindow = new PopupWindow();
        SharedPreferences sp = activity.getSharedPreferences("setting_action_url_config", Context.MODE_PRIVATE);
        final String path = sp.getString("actionUrl",SharedPreferencesMenu.actionUrl);
        final Handler loadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                if(popupWindow!=null)
                    popupWindow.dismiss();
            }
        };


        if(activity instanceof Activity){
          View view =  ShowLoadingDialog.show(popupWindow, activity);
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                   /* if(keyCode==KeyEvent.KEYCODE_BACK){
                        loadHandler.sendMessage(new Message());
                        Log.d("onKeyBack", "onKey: ");
                    }*/
                    Log.d("onKeyBack", "onKey: ");
                    //do something...
                    return true;
                }
            });
        }

        new Thread(new Runnable() {
            @Override
            public  void run() {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                    String result = "";
                    try {
                        OkHttpUtils httpUtils = OkHttpUtils.getInstance();
                        if (interactiveType.equals(InteractiveEnum.GET)) {
                            int index = -1;
                            StringBuffer paths = new StringBuffer(path + method);
                            for (Map.Entry<String, Object> entry : params.entrySet()) {
                                if (index == -1) {
                                    paths.append("?" + entry.getKey() + "=" + entry.getValue());
                                    index = 1;
                                } else {
                                    paths.append("&" + entry.getKey() + "=" + entry.getValue());
                                }
                            }
                            result = httpUtils.getData(activity, paths.toString()).body().string();
                        } else if (interactiveType.equals(InteractiveEnum.POST)) {
                            result = httpUtils.postJson(activity, path + method, JSON.toJSONString(params));

                        } else if (interactiveType.equals(InteractiveEnum.UPLOAD)) {
                            //  result = httpUtils.uploadImage((Context) activity, params, method);
                        }

                        Log.d("JSON", "run: " + result);
                        if (result.trim().equals("")) {
                            msg.what = -2;
                        } else {
                            JSONObject object = JSON.parseObject(result);
                            if (object.getBoolean("Success")) {
                                //操作成功
                                msg.what = 1;
                                bundle.putString("result", result);
                            } else {
                                bundle.putString("result", result);
                                //操作失败
                                msg.what = -1;
                            }
                        }
                        bundle.putString("method", method);
                        bundle.putString("bindDate", bindDate);
                        msg.setData(bundle);
                        if (handler != null)
                            handler.sendMessage(msg);

                        loadHandler.sendMessage(new Message());
                    } catch (IOException ex) {
                        msg.what = -1;
                        bundle.putString("method", method);
                        bundle.putString("result","{ \"Success\": false}");
                        msg.setData(bundle);
                        if (handler != null)
                            handler.sendMessage(msg);

                        loadHandler.sendMessage(new Message());
                    }

                }

        }).start();
    }
}
