package hsj.slaughter.com.home.activity;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import hsj.slaughter.com.home.entry.SlaughterCommitData;
import hsj.slaughter.com.home.entry.SlaughterInfo;

/**
 * Created by 16486 on 2020/12/9.
 */

public class SlaughterScanActivity extends BaseresScanResultActivity implements ResponseHandlerInterface,View.OnClickListener {

    int taskId;
    HandlerUtils handlerUtils;

    AutoAdapter<SlaughterInfo.SlaughterInfoList> adapter;

    List<SlaughterInfo.SlaughterInfoList> tagList = new ArrayList<>();


    Device device;

    Button btnCommit;

    Button btnBack;
    @Override
    public void initFragmentActivityView() {
        DeviceFactory factory = new DeviceFactory(this,this);

        device = factory.getDevice();
        if(device!=null){
            device.initUHF();

        }else{
            Toast.makeText(mContext,"获取RFID模块失败",Toast.LENGTH_SHORT).show();
        }


        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.GETTASKINFO.equals(msg.getData().getString("method"))){
                    final SlaughterInfo taskInfo = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), SlaughterInfo.class);
                            tagList.addAll(taskInfo.getTaskDetailList());
                    updateData();
                }else if(MethodEnum.POSTPDAINHOUSES.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交成功");
                    Utils.onBackDialog(SlaughterScanActivity.this,device);
                }
            }
        });

        adapter = new AutoAdapter<>(mContext,tagList,"StorageID","RfidNo","SerialNo","ProductName","CarNum");
        lvList.setAdapter(adapter);

        lvList.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                if("true".equals(tagList.get(position).getIsFocus())){
                    tagList.get(position).setIsFocus("false");
                }else if("error".equals(tagList.get(position).getIsFocus())){
                    tagList.remove(position);
                }
                updateData();

            }
        });
        powerSettingView.setListener(device, ProgressDialog.createDialog(mContext),2);
        taskId =    getIntent().getExtras().getInt("taskId");

        HashMap<String,Object> slaughterMap = new HashMap<>();
        slaughterMap.put("ID",taskId);
        InteractiveDataUtil.interactiveMessage(this,slaughterMap,handlerUtils, MethodEnum.GETTASKINFO, InteractiveEnum.GET);

    }

    @Override
    public String[] getArrayTitle() {
        return new String[]{"ID","RFID","序列号","产品名","车牌号"};
    }

    /*********************
     * 子类实现
     *****************************/
    @Override
    public int getLayoutId() {
        return com.kymjs.app.base_res.R.layout.baseres_scan_result_activity;
    }

    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        List<BottomViewList<Button>> lists = new ArrayList<>();
        btnBack = new Button(mContext);
        btnCommit = new Button(mContext);
        btnCommit.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        lists.add(new BottomViewList<Button>(btnBack,"返回"));
        lists.add(new BottomViewList<Button>(btnCommit,"提交"));
        return lists;
    }

    @Override
    protected List<View> getLayoutScanResultOperation() {
        return null;
    }

    private void updateData() {
        tvScanResultTotalCount.setText(""+tagList.size());
        currentCount=0;
        errorCount=0;
        for (int i =0;i<tagList.size();i++){
            if("true".equals(tagList.get(i).getIsFocus())){
                currentCount++;
            }else if("error".equals(tagList.get(i).getIsFocus())){
                errorCount++;
            }
        }
        tvScanResultCurrentCount.setText(currentCount+"");
        tvScanResultErrorCount.setText(errorCount+"");
        adapter.notifyDataSetChanged();

        if( currentCount>0){
            btnCommit.setEnabled(true);
        }else {
            btnCommit.setEnabled(false);
        }
            adapter.notifyDataSetChanged();

    }

    @Override
    public void handleTagdata(String rfid) {
        int index=-1;
        if(rfid.startsWith(LabelRule.earmMarkRule) && (index =isExistList(rfid))!=-1){
            if("false".equals(tagList.get(index).getIsFocus())){
                tagList.get(index).setIsFocus("true");
            }
            device.playSound(1);
        }else{
            if(device!=null){
                device.playSound(2);
            }
        }
        updateData();
    }

    public int isExistList(String rfid){
        int index=-1;
        for(int i=0;i<tagList.size();i++){
            if(rfid.equals( tagList.get(i).getRfidNo())){
                index=i;
                break;
            }
        }
        return index;
    }


    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //提交屠宰信息
        if(Integer.parseInt(v.getTag().toString()) ==1){

            DialogUtils.showAlertDialog(mContext, new AlertDialogCallBack() {
                @Override
                public void alertDialogFunction() {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("TaskID",taskId);
                    List<SlaughterCommitData> list = new ArrayList<>();
                    for (int i =0;i<tagList.size();i++){
                            if("true".equals( tagList.get(i).getIsFocus())){
                                list.add(new SlaughterCommitData(tagList.get(i).getStorageID()));
                            }
                    }
                    map.put("DataInfo",list);
                    map.put("Remark","屠宰数据提交");
                    InteractiveDataUtil.interactiveMessage(SlaughterScanActivity.this,map,handlerUtils,MethodEnum.POSTPDAINHOUSES,InteractiveEnum.POST);
                }
            },"请问是否提交数据",null,null);




        }else if(Integer.parseInt(v.getTag().toString()) ==0){
            Utils.onBackDialog(this,device);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if (keyCode == 280) {
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode, event, 1, false);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.onBackDialog(this,device);
        }
        return super.onKeyDown(keyCode, event);
    }

}
