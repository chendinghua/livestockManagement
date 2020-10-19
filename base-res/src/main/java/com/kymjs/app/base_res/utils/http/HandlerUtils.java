package com.kymjs.app.base_res.utils.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.tools.UIHelper;

/**
 * Created by Administrator on 2019/1/16/016.
 */

public class HandlerUtils extends Handler {
    private HandlerUtilsCallback callback;
    private HandlerUtilsErrorCallback errorCallback;
    private Context context;
    public HandlerUtils(Context context, HandlerUtilsCallback callback) {
        this.callback=callback;
        this.context=context;
    }
    public HandlerUtils(Context context, HandlerUtilsCallback callback, HandlerUtilsErrorCallback errorCallback){
        this.context=context;
        this.callback=callback;
        this.errorCallback=errorCallback;
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what){
            case 1:
                callback.handlerExecutionFunction(msg);
                break;
            case -1:
                    if(errorCallback!=null){
                     errorCallback.handlerErrorFunction(msg);
                        Log.d("JSON", "HandlerUtilsErrorCallback: ");
                    }else {
                        if(context!=null)
                      //  UIHelper.ToastMessage(context, "数据异常");
                            UIHelper.ToastMessage(context,JSON.parseObject(msg.getData().getString("result")).getString("Message"));
                    }
                break;
            case -2:
                if(context!=null)
                  //  if(!MethodEnum.HEARTBEAT.equals(msg.getData().getString("method")))
                    try {

                        UIHelper.ToastMessage(context, JSON.parseObject(msg.getData().getString("result")).getString("Message"));
                    }catch (NullPointerException e){
                        UIHelper.ToastMessage(context,"接口异常提示为空");
                    }
                break;
        }
        super.handleMessage(msg);
    }
}
