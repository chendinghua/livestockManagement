package hsj.packing.com.activity;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.base.entry.packing.PackingTask;
import com.kymjs.app.base_res.utils.base.entry.packing.PickCommitEntry;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.ForcehUtils;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
/*
* 装包装箱操作
* */
public class PickingActivity extends BaseresScanResultActivity implements ResponseHandlerInterface,View.OnClickListener {
    Device device;
    int status=1;
    AutoAdapter<ScanResult> adapter ;
    List<ScanResult> tagList = new ArrayList<>();

    AutoAdapter<PackingTask.PackingTaskItems> packingAdapter ;
    List<PackingTask.PackingTaskItems> packingTagList = new ArrayList<>();


    Button btnCommit;
    Button btnPre;
    //获取类型1为装包  2为装箱
    int pickType ;
    @Override
    public void initFragmentActivityView() {
        device = new DeviceFactory(mContext, this).getDevice();
        if (device != null) {
            device.initUHF();
            device.onPause();
        } else {
            Toast.makeText(mContext, "获取RFID模块失败", Toast.LENGTH_SHORT).show();
        }
        powerSettingView.setListener(device, ProgressDialog.createDialog(mContext), 2);
        initListener();
        adapter = new AutoAdapter<>(mContext,tagList,"StorageID", "RfidNo", "SerialNo", "StorageStatusName","IsEnabledName");
        packingAdapter = new AutoAdapter<PackingTask.PackingTaskItems>(mContext,packingTagList,"Id","Code","CreateDate","TypeName");
        lvList.setAdapter(pickType==1?adapter:packingAdapter);
        lvList.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                if(pickType==1) {
                    tagList.remove(position);
                }else{
                    packingTagList.remove(position);
                }
                updateCommitStatus();
            }
        });
        updateCommitStatus();

    }
    private void initListener() {
        rbScanRfid.setOnClickListener(this);
        rbScanCode.setOnClickListener(this);
    }
    @Override
    public String[] getArrayTitle() {
        pickType =Integer.parseInt(SPUtils.getSharedStringData(mContext,"actionUrl"));
        return pickType==1?new String[]{"库存编号", "RFID", "序列号", "库存状态","启用状态"}: new String[]{"ID","包号","创建时间","箱包类型"};
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
    protected List<View> getLayoutScanResultOperation() {
        List<View> views = new ArrayList<>();
      /*  RadioGroup radioGroup = new RadioGroup(mContext);
        LinearLayout.LayoutParams rglp = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, 0);
        radioGroup.setLayoutParams(rglp);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        RadioButton rbRFIDCode = new RadioButton(mContext);
        rbRFIDCode.setText("RFID");
        rbRFIDCode.setLayoutParams(lp);
        RadioButton rbScanCode = new RadioButton(mContext);
        rbScanCode.setText("条形码");
        rbScanCode.setLayoutParams(lp);
        radioGroup.addView(rbRFIDCode, 0, LinearLayout.LayoutParams.MATCH_PARENT);
        radioGroup.addView(rbScanCode, 0, LinearLayout.LayoutParams.MATCH_PARENT);
        views.add(radioGroup);*/
        return views;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            Utils.onBackDialog(this,device);
        }else if(keyCode==280){
            if(pickType==2 && rbScanRfid.isChecked()) {
                UIHelper.ToastMessage(mContext,"当前功能不支持RFID模块");
            }else{
                device.onKeyDown(keyCode, event, status, false);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void handleTagdata(String rfid) {

            Log.d("scanRFID", "handleTagdata: " + rfid);

            initListData(rfid, SCANTYPE.RFID);

        //  device.playSound(1);
    }
    @Override
    public void handleTriggerPress(boolean pressed) {
    }
    @Override
    public void scanCode(String code) {
        Log.d("scanCode", "handleTagdata: "+code);
        //  device.playSound(1);
        initListData(code,pickType==1?SCANTYPE.CODE:SCANTYPE.PACKINGCODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        device.destroy();
    }
    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.kymjs.app.base_res.R.id.rb_scanCode) {
            status=2;
        }else if(i== com.kymjs.app.base_res.R.id.rb_scanRfid){
            status=1;
        }else if(Integer.parseInt(v.getTag().toString())==1){

            List<PickCommitEntry> commitList = new ArrayList<>();
            switch (pickType){
                //提交集合数据为耳标id数据
                case 1:
                    for (int j=0;j<tagList.size();j++){
                        commitList.add(new PickCommitEntry(tagList.get(j).getStorageID()));
                    }
                    break;
                //提交集合数据为包号id数据
                case 2:
                    for (int j=0;j<packingTagList.size();j++){
                        commitList.add(new PickCommitEntry(packingTagList.get(j).getId()));
                    }
                    break;
            }
            HashMap<String,Object> commitMap = new HashMap<>();
            commitMap.put("Type",pickType );
            commitMap.put("DataInfo",commitList);
            InteractiveDataUtil.interactiveMessage(this,commitMap,handler,MethodEnum.POSTADDBOXPACKAGE,InteractiveEnum.POST);
            //返回按钮点击
        }else if(Integer.parseInt(v.getTag().toString()) == 0){
            Utils.onBackDialog(this,device);
        }
    }
    HandlerUtils handler = new HandlerUtils(mContext, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
            //扫描耳标数据
            if (MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {
                final ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                if (scanResult != null) {
                    //判断当前分发标签状态是未启用  1：启用  2：未激活
                    if (scanResult.getIsEnabled() == 2) {
                        scanResult.setIsFocus("true");
                    } else {
                        scanResult.setIsFocus("false");
                    }
                    tagList.add(scanResult);
                    updateCommitStatus();
                }
                device.playSound(1);
                //提交箱包号数据
            }else if(MethodEnum.POSTADDBOXPACKAGE.equals(msg.getData().getString("method"))){
                UIHelper.ToastMessage(mContext,"数据提交成功");
                Utils.activityFinish(PickingActivity.this,device);
            }else if(MethodEnum.GETQELBOXPACKAGE.equals(msg.getData().getString("method"))){
                PackingTask packingTask =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackingTask.class);
                if(packingTask!=null){

                  List<PackingTask.PackingTaskItems> items = packingTask.getResult();
                    if(items.size()>0){
                        PackingTask.PackingTaskItems item = items.get(0);
                        if (item.getPID() == -1) {
                            item.setIsFocus("true");
                        } else {
                            item.setIsFocus("false");
                        }
                        packingTagList.add(item);
                        updateCommitStatus();

                    }else{
                        UIHelper.ToastMessage(mContext,"当前包号条码不存在");
                    }

                }
            }
        }
    }, new HandlerUtilsErrorCallback() {
        @Override
        public void handlerErrorFunction(Message ms) {
            if (MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))) {
                final ScanResult scanResult = JSON.parseObject(JSON.parseObject(ms.getData().getString("result")).getString("Data"), ScanResult.class);
                if (scanResult != null) {
                    scanResult.setIsFocus("false");
                    tagList.add(scanResult);
                    updateCommitStatus();
                }
                device.playSound(2);
            }else if(MethodEnum.POSTADDBOXPACKAGE.equals(ms.getData().getString("method"))){
                UIHelper.ToastMessage(mContext,"数据提交失败");
            }
        }
    });
    //更新数量信息和提交按钮状态
    private void  updateCommitStatus(){
        //获取正常数据数量
        int successCount = ForcehUtils.getListStatusCount(pickType==1?tagList:packingTagList,"IsFocus","true");
        //获取异常数据数量
        int filedCount = ForcehUtils.getListStatusCount(pickType==1?tagList:packingTagList,"IsFocus","false");
         adapter.notifyDataSetChanged();
        packingAdapter.notifyDataSetChanged();

        tvScanResultCurrentCount.setText(""+successCount);
        tvScanResultErrorCount.setText(""+filedCount);
        tvScanResultTotalCount.setText(""+(pickType==1?tagList.size():packingTagList.size()));
        if(successCount>0 && filedCount==0){
            btnCommit.setEnabled(true);
        }else{
            btnCommit.setEnabled(false);
        }
    }
    public enum SCANTYPE{
        RFID("RfidNo"),CODE("SerialNo"),PACKINGCODE("Code");
        private String type;
        private SCANTYPE(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    }
    /**
     * 扫描处理数据
     * @param rfid
     */
    private void initListData(String rfid,SCANTYPE type){
        int index ;
        //判断当前扫描类型为条码并且当前处理的装箱管理
        if(type== SCANTYPE.PACKINGCODE && pickType==2){
            if( (index=ForcehUtils.getListIndex(packingTagList,type.getType(),rfid))!=-1){
                if("true".equals(packingTagList.get(index).getIsFocus())){
                    device.playSound(1);
                }else{
                    device.playSound(2);
                }
            }else {
                HashMap<String, Object> packingMap = new HashMap<>();
                packingMap.put("Code", rfid);
                packingMap.put("Type", 1);
                packingMap.put("pageIndex", 1);
                packingMap.put("pageSize", 1);
                InteractiveDataUtil.interactiveMessage(this, packingMap, handler, MethodEnum.GETQELBOXPACKAGE, InteractiveEnum.GET);
            }
        }else{

            if( (index=ForcehUtils.getListIndex(tagList,type.getType(),rfid))!=-1){
                if("true".equals(tagList.get(index).getIsFocus())){
                    device.playSound(1);
                }else{
                    device.playSound(2);
                }
            }else {
                HashMap<String, Object> map = new HashMap<>();
                switch (type) {
                    case RFID:
                        map.put("RFID", rfid);
                        break;
                    case CODE:
                        map.put("RFID", "");
                        map.put("SerialNo", rfid);
                        break;
                }
                InteractiveDataUtil.interactiveMessage(this, map, handler, MethodEnum.GETSTORAGEINFOBYOUT, InteractiveEnum.GET);
            }
        }
    }
}