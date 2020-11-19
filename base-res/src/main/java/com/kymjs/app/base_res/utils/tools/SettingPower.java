package com.kymjs.app.base_res.utils.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;

import devicelib.dao.Device;
/**
 * Created by 16486 on 2020/3/23.
 */

public class SettingPower {



    /**
     * @param index 修改频率的类型id
     */
    public static void updateFrequency(final Device device, final Context context, final int index, final ProgressDialog progress){

        if(progress!=null)
        progress.show();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.getData().getBoolean("isSettingSuccess")){
                    UIHelper.ToastMessage(context, R.string.baseres_uhf_msg_set_power_succ);
                }else{
                    UIHelper.ToastMessage(context, R.string.baseres_uhf_msg_set_power_fail);
                }
                if(progress!=null){
                    progress.dismiss();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences  sp=context.getSharedPreferences("pda_config", Context.MODE_PRIVATE);
                boolean isSettingSuccess=false;
                switch (index){
                    case 1:
                       isSettingSuccess =device.setPower(sp.getInt("lowFrequency",5));
                        break;
                    case 2:
                        isSettingSuccess=device.setPower(sp.getInt("mediumFrequency",15));

                        break;
                    case 3:
                        isSettingSuccess = device.setPower(sp.getInt("highFrequency",30));
                        break;
                }
               Message msg =  handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("index",index);
                bundle.putBoolean("isSettingSuccess",isSettingSuccess);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }
}
