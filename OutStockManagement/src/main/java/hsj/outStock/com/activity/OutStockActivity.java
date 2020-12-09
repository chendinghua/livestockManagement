package hsj.outStock.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

/** 申请出栏
 * Created by 16486 on 2020/11/26.
 */

public class OutStockActivity extends BaseresScanResultActivity implements ResponseHandlerInterface,View.OnClickListener {
    Device device;

    AutoAdapter<ScanResult> autoAdapter;

    List<ScanResult> tagList = new ArrayList<>();

    List<String> tempList = new ArrayList<>();


    HandlerUtils handlerUtils;

    Button btnPre;

    Button btnCommit;


    @Override
    public void initFragmentActivityView() {
        device = new DeviceFactory(mContext, this).getDevice();
        if (device != null) {
            device.initUHF();

        } else {
            Toast.makeText(mContext, "获取RFID模块失败", Toast.LENGTH_SHORT).show();
        }
        powerSettingView.setListener(device, ProgressDialog.createDialog(mContext), 2);
        autoAdapter = new AutoAdapter<ScanResult>(mContext, tagList, "StorageID", "RfidNo", "SerialNo", "StorageStatusName","StockSerialNo");
        lvList.setAdapter(autoAdapter);


        lvList.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                int index;

                if((index=isExistList(tagList.get(position).getRfidNo()))!=-1){
                    tempList.remove(index);
                }
                tagList.remove(position);
                playSound(-1);

            }
        });
        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null && scanResult.getStatus() == 1 && scanResult.getIsEnabled() == 1) {
                        scanResult.setIsFocus("true");
                        tagList.add(scanResult);
                        playSound(1);

                    } else {
                        scanResult.setIsFocus("error");
                        tagList.add(0, scanResult);
                        playSound(2);
                    }

                }else if(MethodEnum.POSTPDAOUTSTOCK.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交成功");
                    Utils.activityFinish(OutStockActivity.this,device);
                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))) {
                    ScanResult scanResult = new ScanResult(ms.getData().getString("bindDate"), "", "", "error");
                    tagList.add(0, scanResult);
                    playSound(2);
                }else if(MethodEnum.POSTPDAOUTSTOCK.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交失败");
                }
            }
        });
    }

    @Override
    public String[] getArrayTitle() {
        return new String[]{"库存编号", "RFID", "序列号", "库存状态","栏位编号"};
    }

    /*********************
     * 子类实现
     *****************************/
    @Override
    public int getLayoutId() {
        return R.layout.baseres_scan_result_activity;
    }

    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        btnCommit = new Button(mContext);
        btnPre = new Button(mContext);
        btnCommit.setEnabled(false);
        btnCommit.setTag(1);
        btnPre.setTag(2);

        btnCommit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        List<BottomViewList<Button>> list = new ArrayList<>();

        list.add(new BottomViewList<>(btnPre, "返回"));
        list.add(new BottomViewList<>(btnCommit, "提交"));
        return list;
    }

    @Override
    public void handleTagdata(String rfid) {
        int index ;

        if (rfid.startsWith(LabelRule.earmMarkRule) && (index = isExistList(rfid)) == -1) {
            tempList.add(rfid);
            HashMap<String, Object> scanMap = new HashMap<>();
            scanMap.put("RFID", rfid);
            InteractiveDataUtil.interactiveMessage(this, scanMap, handlerUtils, MethodEnum.GETSTORAGEINFOBYOUT, InteractiveEnum.GET, rfid);
        } else {
            //1:判断临时集合里面有数据并且实际tagList集合里面没有数据 表示数据还在查询
            //2: 判断时集合里面有数据并且实际tagList集合里面有数据 显示数据是正常的
            if ( isExistList(rfid) != -1 && isExistTagList(rfid) ==-1 ||  isExistList(rfid) != -1 && (index=isExistTagList(rfid)) !=-1 && "true".equals(tagList.get(index).getIsFocus())) {
                playSound(1);

            } else {
                playSound(2);
            }
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    public int isExistList(String rfid) {
        int index = -1;
        for (int i = 0; i < tempList.size(); i++) {
            if (rfid.equals(tempList.get(i))) {
                index = i;
                break;
            }

        }

        return index;
    }
    public int isExistTagList(String rfid) {
        int index = -1;
        for (int i = 0; i < tagList.size(); i++) {
            if (rfid.equals(tagList.get(i).getRfidNo())) {
                index = i;
                break;
            }

        }

        return index;
    }


    public void playSound(int i) {
        if (device != null && i!=-1) {
            device.playSound(i);
        }
        errorCount = 0;
        currentCount = 0;
        for (int j = 0; j < tagList.size(); j++) {
            if ("true".equals(tagList.get(j).getIsFocus())) {
                currentCount++;
            } else {
                errorCount++;
            }
        }
        if (currentCount > 0 && errorCount == 0) {
            btnCommit.setEnabled(true);
        } else {
            btnCommit.setEnabled(false);
        }
        tvScanResultCurrentCount.setText("" + currentCount);
        tvScanResultTotalCount.setText("" + currentCount);
        tvScanResultErrorCount.setText("" + errorCount);
        autoAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        //提交
        if (Integer.parseInt(v.getTag().toString())  == 1) {
            Bundle  bundle = getIntent().getExtras();

            HashMap<String,Object> outStock = new HashMap<>();
            outStock.put("Type",bundle.getString("Type"));
            outStock.put("OutDeptID", getIntent().getExtras().getString("outDeptID"));
            outStock.put("Client",bundle.getString("Client"));
            outStock.put("ClientTelphone",bundle.getString("ClientTelphone"));
            outStock.put("Num",tagList.size());
            outStock.put("Remark","");
            outStock.put("DataInfo",tagList);
            InteractiveDataUtil.interactiveMessage(this,outStock,handlerUtils,MethodEnum.POSTPDAOUTSTOCK,InteractiveEnum.POST);
        //返回
        } else if (Integer.parseInt(v.getTag().toString()) == 0) {
            onBackDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if (keyCode == 280) {
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode, event, 1, false);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackDialog() {
        DialogUtils.showAlertDialog(this, new AlertDialogCallBack() {
            @Override
            public void alertDialogFunction() {
                Utils.activityFinish(OutStockActivity.this,device);
            }
        }, "是否结束当前流程", null, null);


    }
}
