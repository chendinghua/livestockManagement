package hsj.instock.com.activity;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.BaseModelActivity;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.view.addAndSubView.Baseres_AddAndSubView;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import hsj.instock.com.R;
import hsj.instock.com.R2;
import hsj.instock.com.entry.MoveStockInfo;

/**
 * Created by 16486 on 2020/11/6.
 */

public class ScanInOrMoveStockActivity extends BaseModelActivity implements SlideCutListView.RemoveListener{

    @BindView(R2.id.lv_scan_in_or_move_stock)
    SlideCutListView lvScanResult;

    @BindView(R2.id.asv_in_or_out_stock_daytime)
    Baseres_AddAndSubView asvDayTime;               //出生间隔时间

    AutoAdapter<ScanResult> adapter;

    List<ScanResult> tagList = new ArrayList<>();
    HandlerUtils handlerUtils;


    //判断当前操作状态  操作状态为1：则是入库   2：操作状态为移库
    private Integer operationStatus=1;
    //当前操作的库位id
    private StockInfo stockInfo;

    @BindView(R2.id.tv_scan_in_or_move_stock_current_count)
     TextView tvCurrentCount;

    @BindView(R2.id.tv_scan_in_or_move_stock_error_count)
     TextView tvErrorCount;

    @BindView(R2.id.btn_scan_in_or_move_stock_commit)
    Button btnCommit;

    @BindView(R2.id.tv_in_stock_Name)
    TextView tvOperationStockName;
    @BindView(R2.id.psv_scan_in_or_move_stock)
    Baseres_PowerSettingView powerSetting;


    @BindView(R2.id.layout_daytime)
    LinearLayout layoutDaTime;              //出生日期布局


    @Override
    public void handleTagdata(String rfid) {
        if(rfid.startsWith(LabelRule.earmMarkRule) && isExtenData(rfid)==null){
            HashMap<String,Object> map = new HashMap<>();
            map.put("RFID",rfid);
            InteractiveDataUtil.interactiveMessage(this,map,handlerUtils,MethodEnum.GETSTORAGEINFOBYOUT,InteractiveEnum.GET);

        }else{
            if(isExtenData(rfid)!=null && "true".equals(isExtenData(rfid).getIsFocus())){
                if (device != null) {
                    device.playSound(1);
                }
            }else {

                if (device != null) {
                    device.playSound(2);
                }
            }
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    @Override
    public String getFragmentTitle() {
        return  operationStatus==1? "入栏操作":"移栏操作";
    }

    @Override
    public boolean isOpenUHFModel() {
        return true;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.instock_scan_in_or_move_stock_activity;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(device!=null){
            device.onKeyDown(keyCode,event,1,false);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initView() {
        ProgressDialog progress = ProgressDialog.createDialog(mContext);
        powerSetting.setListener(device,progress, Baseres_PowerSettingView.INTERMEDIATE);

        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {



                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);


                    Log.d("urlScanInOrMoveStock", "handlerExecutionFunction:  operationStockID："+stockInfo.getID()+"\n  scanResultStockId："+scanResult.getStockID()
                    +"\n  IsEnabled："+scanResult.getIsEnabled() +"  \n operationStatus："+operationStatus);

                    //判断对象不为空  状态为在库 激活状态为未激活 操作状态为1 入库
                    //判断当前对象不为空 状态为在库  激活状态为已激活  库位id和移动的库位id不一样 操作状态为移库
                    if (scanResult != null  && scanResult.getStatus()==1 && scanResult.getIsEnabled()==2  && operationStatus==1
                            ||scanResult !=null && scanResult.getStatus()==1 && scanResult.getIsEnabled()==1
                            && !stockInfo.getID().equals(scanResult.getStockID()) && operationStatus==2 ) {
                        scanResult.setIsFocus("true");
                        tagList.add(scanResult);

                        device.playSound(1);
                    }else {
                        scanResult.setIsFocus("false");
                        tagList.add(0, scanResult);
                        device.playSound(2);
                    }
                      updateCommitStatus();
                }else if(MethodEnum.POSTMOVESTOCK.equals(msg.getData().getString("method"))){

                }else if(MethodEnum.POSTPDAINSTOCK.equals(msg.getData().getString("method"))){
                    if(device!=null){
                        device.destroy();
                    }
                    UIHelper.ToastMessage(mContext,"数据提交成功" );
                    finish();

                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(ms.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null) {
                        scanResult.setIsFocus("false");
                        tagList.add(0, scanResult);
                              updateCommitStatus();

                    }
                    device.playSound(2);
                }else if(MethodEnum.POSTPDAINSTOCK.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交失败" );

                }
            }
        });
        adapter = new AutoAdapter<ScanResult>(mContext, tagList, "RfidNo", "SerialNo","IsEnabledName");
        lvScanResult.setAdapter(adapter);

        lvScanResult.setRemoveListener(this);
        //获取操作栏位信息
        stockInfo =(StockInfo) getIntent().getExtras().getSerializable("stockInfo");
        //获取当前是入栏还是移栏操作
        operationStatus = Integer.parseInt( SPUtils.getSharedStringData(mContext,"actionUrl"));

        if(tvOperationStockName!=null)
        tvOperationStockName.setText(stockInfo.getSerialNo());
        //判断当前是否为移栏操作
        if(operationStatus==2){
            layoutDaTime.setVisibility(View.GONE);
        }else{
            layoutDaTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        tagList.remove(position);
        updateCommitStatus();
    }



    public ScanResult isExtenData(String code){
        ScanResult scanResult=null;
        for (int i =0;i<tagList.size();i++){
            if(code.equals( tagList.get(i).getRfidNo())){
                scanResult = tagList.get(i);
                break;
            }
        }
        return scanResult;
    }

    @OnClick({R2.id.btn_scan_in_or_move_stock_commit})
    public void onViewClicked(View view) {
       /* RCaster rcaster = new RCaster(R.class, R2.class);

        switch (rcaster.cast(view.getId())) {
        }*/
        //添加入库
       if(operationStatus==1) {

           StringBuffer rfidStr = new StringBuffer();
           for (int i = 0; i < tagList.size(); i++) {
               if (tagList.size() - 1 == i) {
                   rfidStr.append(tagList.get(i).getRfidNo());
               } else {
                   rfidStr.append(tagList.get(i).getRfidNo() + ",");
               }
           }


           HashMap<String, Object> commitMap = new HashMap<>();
           commitMap.put("DayTime", asvDayTime.getCurrentCount());
           commitMap.put("Num", tagList.size());
           commitMap.put("StockID", stockInfo.getID());
           commitMap.put("RFIDStr", rfidStr.toString());
           InteractiveDataUtil.interactiveMessage(this, commitMap, handlerUtils, MethodEnum.POSTPDAINSTOCK, InteractiveEnum.POST);
        //添加移库
       }else if(operationStatus==2){
           List<MoveStockInfo> moveStockInfos = new ArrayList<>();
           for (int i=0; i<tagList.size();i++){
               moveStockInfos.add(new MoveStockInfo(tagList.get(i).getStockID(),0));
           }
           HashMap<String,Object> moveMap = new HashMap<>();
           moveMap.put("StockID",stockInfo.getID());
           moveMap.put("DetailList",moveStockInfos);

           InteractiveDataUtil.interactiveMessage(this,moveMap,handlerUtils,MethodEnum.POSTMOVESTOCK,InteractiveEnum.POST);

       }

    }

    public void updateCommitStatus(){
      Integer  currentCount=0;
        Integer  errorCount=0;
        for (int i=0;i<tagList.size();i++){
            if("true".equals(tagList.get(i).getIsFocus())){
                currentCount++;
            }else if("false".equals(tagList.get(i).getIsFocus())){
                errorCount++;
            }
        }
        tvCurrentCount.setText(""+currentCount);
        tvErrorCount.setText(""+errorCount);
        if(errorCount==0 && currentCount>0){
            btnCommit.setEnabled(true);
        }else{
            btnCommit.setEnabled(false);
        }
        adapter.notifyDataSetChanged();

    }
}
