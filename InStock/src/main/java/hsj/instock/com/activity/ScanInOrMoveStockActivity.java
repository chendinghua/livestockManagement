package hsj.instock.com.activity;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.kymjs.app.base_res.utils.tools.NumberUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
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
    EditText asvDayTime;               //出生间隔时间

    AutoAdapter<ScanResult> adapter;

    List<ScanResult> tagList = new ArrayList<>();
    HandlerUtils handlerUtils;


    //判断当前操作状态  操作状态为3：则是入库   -1：操作状态为移库
    private Integer operationStatus=-1;
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


    List<String> tempList = new ArrayList<>();

    @Override
    public void handleTagdata(String rfid) {
        if(rfid.startsWith(LabelRule.earmMarkRule) && isExtenData(rfid)==-1){
            tempList.add(rfid);
            HashMap<String,Object> map = new HashMap<>();
            map.put("RFID",rfid);
            InteractiveDataUtil.interactiveMessage(this,map,handlerUtils,MethodEnum.GETSTORAGEINFOBYOUT,InteractiveEnum.GET);

        }else{
            int index = isExistTagList(rfid);

            if(index!=-1 && "true".equals(tagList.get(index).getIsFocus())){
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
        return  operationStatus==3? "入栏操作":"移栏操作";
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
                    if (scanResult != null  && scanResult.getStatus()==1 && scanResult.getIsEnabled()==2
                            && operationStatus==3
                            ||scanResult !=null && scanResult.getStatus()==1 && scanResult.getIsEnabled()==1
                            && !stockInfo.getID().equals(scanResult.getStockID()) && operationStatus==-1 ) {
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
                    UIHelper.ToastMessage(mContext,"数据提交成功" );
                    Utils.activityFinish(ScanInOrMoveStockActivity.this,device);
                }else if(MethodEnum.POSTPDAINSTOCK.equals(msg.getData().getString("method"))){
                    Utils.activityFinish(ScanInOrMoveStockActivity.this,device);
                    UIHelper.ToastMessage(mContext,"数据提交成功" );
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


                    }
                    updateCommitStatus();
                    device.playSound(2);
                }else if(MethodEnum.POSTPDAINSTOCK.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交失败" );

                }else if(MethodEnum.POSTMOVESTOCK.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交失败" );
                }
            }
        });
        adapter = new AutoAdapter<ScanResult>(mContext, tagList, "RfidNo", "SerialNo","IsEnabledName","DeptName");
        lvScanResult.setAdapter(adapter);

        lvScanResult.setRemoveListener(this);
        //获取操作栏位信息
        stockInfo =(StockInfo) getIntent().getExtras().getSerializable("stockInfo");
        //获取当前是入栏还是移栏操作
        operationStatus = Integer.parseInt( SPUtils.getSharedStringData(mContext,"actionUrl"));

        if(tvOperationStockName!=null)
        tvOperationStockName.setText(stockInfo.getName());
        //判断当前是否为移栏操作
        if(operationStatus==-1){
            layoutDaTime.setVisibility(View.GONE);
        }else{
            layoutDaTime.setVisibility(View.VISIBLE);
        }
        updateCommitStatus();
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        int index =-1;
        if((index=isExtenData(tagList.get(position).getRfidNo()))!=-1){
            tempList.remove(index);
        }
        tagList.remove(position);
        updateCommitStatus();
    }



    public int  isExtenData(String code){
        int  index=-1;
        for (int i =0;i<tempList.size();i++){
            if(code.equals( tempList.get(i))){
                index=i;
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



    @OnClick({R2.id.btn_scan_in_or_move_stock_commit})
    public void onViewClicked(View view) {

        //添加入库
       if(operationStatus==3) {

           StringBuffer rfidStr = new StringBuffer();
           for (int i = 0; i < tagList.size(); i++) {
               if (tagList.size() - 1 == i) {
                   rfidStr.append(tagList.get(i).getRfidNo());
               } else {
                   rfidStr.append(tagList.get(i).getRfidNo() + ",");
               }
           }


           HashMap<String, Object> commitMap = new HashMap<>();
           commitMap.put("DayTime", NumberUtils.getNumberData( asvDayTime.getText().toString()));
           commitMap.put("Num", tagList.size());
           commitMap.put("StockID", stockInfo.getID());
           commitMap.put("RFIDStr", rfidStr.toString());
           InteractiveDataUtil.interactiveMessage(this, commitMap, handlerUtils, MethodEnum.POSTPDAINSTOCK, InteractiveEnum.POST);
        //添加移库
       }else if(operationStatus==-1){
           List<MoveStockInfo> moveStockInfos = new ArrayList<>();
           for (int i=0; i<tagList.size();i++){
               moveStockInfos.add(new MoveStockInfo(tagList.get(i).getStockID(),tagList.get(i).getStorageID()));
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
