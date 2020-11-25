package hsj.destroy.com;

import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.dialog.StorageDialog.StorageDialog;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;

import java.util.HashMap;

import devicelib.dao.Device;

/** 标签销毁模块
 * Created by 16486 on 2020/11/25.
 */
public class DestroyManagementFragment extends BaseresTaskFragment implements View.OnClickListener{

    Device device;

    HandlerUtils handlerUtils;

    StorageDialog mDialog;
    StorageDialog.Builder builder;

    @Override
    public void initFragmentActivityView() {
        builder = new StorageDialog.Builder(activity);

        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))){
                    final ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if(scanResult!=null && scanResult.getIsEnabled()==1 && scanResult.getStatus()==1) {
                        mDialog = builder.setMessage("请确认销毁耳标信息").
                                initScanResult(scanResult).
                                setPositiveButton("销毁", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                        if (device != null) {
                                            device.destroy();
                                        }
                                        HashMap<String, Object> destoryMap = new HashMap<>();
                                        destoryMap.put("RFIDNo", scanResult.getRfidNo());

                                        InteractiveDataUtil.interactiveMessage(activity, destoryMap, handlerUtils, MethodEnum.POSTSCRAPSTORAGE, InteractiveEnum.POST);
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                                if (device != null) {
                                    device.destroy();
                                }
                            }
                        }).createTwoButtonDialog();
                        mDialog.create();
                    }else{
                        if(scanResult!=null && scanResult.getStatus()!=1){
                            UIHelper.ToastMessage(activity,"耳标标签不在库");
                        }else if(scanResult!=null && scanResult.getIsEnabled()!=1){
                            UIHelper.ToastMessage(activity,"耳标标签未启用");
                        }else {
                            UIHelper.ToastMessage(activity, "耳标标签异常");
                        }
                    }

                }else if(MethodEnum.POSTSCRAPSTORAGE.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"销毁成功");
                }
            }
        });

        btnTaskAdd.setOnClickListener(this);
    }

    @Override
    public String[] getArrayTitle() {
        return new String[0];
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //新增销毁
        if(v.getId()==btnTaskAdd.getId()){
            device = ScanRfidDialog.showScanRfid(activity,"请扫描耳标标签", LabelRule.earmMarkRule,"耳标标签数据异常", MethodEnum.GETSTORAGEINFOBYOUT,"RFID",handlerUtils);
        }
    }
}