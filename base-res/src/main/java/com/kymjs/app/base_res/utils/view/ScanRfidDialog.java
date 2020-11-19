package com.kymjs.app.base_res.utils.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.AlertDialogNegativeCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.ACache;

import java.util.HashMap;

import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;

/**
 * Created by 16486 on 2020/11/10.
 */

public class ScanRfidDialog {
    /**
     *
     * @param activity
     * @param title 提示标题内容
     * @param rule 条码筛选条件
     * @param errorRule 条码筛选条件异常提示
     * @param method 执行的方法名
     * @param handlerUtils
     * @return
     */

    public static Device showScanRfid(final Activity activity, final String title, final String rule ,
                                      final  String errorRule,final String method,final String paramName, final HandlerUtils handlerUtils){

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editText = new EditText(activity);
        editText.setEnabled(false);
        layout.addView(editText);
        final  Device device  = new DeviceFactory(activity, new ResponseHandlerInterface() {
            @Override
            public void handleTagdata(String rfid) {
                if(rfid.startsWith(rule)){
                    editText.setText(rfid);
                }else{
                    editText.setText("");
                    UIHelper.ToastMessage(activity,errorRule+rfid);
                }
            }

            @Override
            public void handleTriggerPress(boolean pressed) {

            }

            @Override
            public void scanCode(String code) {

            }
        }).getDevice();
        device.initUHF();
        device.setPower(5);

        DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
            @Override
            public void alertDialogFunction() {
                if (!"".equals(editText.getText().toString())) {
                    HashMap<String, Object> stockMap = new HashMap<String, Object>();
                    stockMap.put(paramName, editText.getText().toString());
                    InteractiveDataUtil.interactiveMessage(activity, stockMap, handlerUtils,method, InteractiveEnum.GET);
                }else{
                    UIHelper.ToastMessage(activity,"RFID数据不能为空");
                    if(device!=null){
                        device.destroy();
                    }
                }
            }
        }, new AlertDialogNegativeCallBack() {
            @Override
            public void alertDialogFunction() {
                if(device!=null){
                    device.destroy();
                }
            }
        }, title, new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                device.onKeyDown(keyCode, event, 1, true);

                return false;
            }
        }, layout);

        return device;
    }
}
