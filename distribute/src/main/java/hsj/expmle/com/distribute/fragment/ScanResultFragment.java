package hsj.expmle.com.distribute.fragment;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.fragment.BaseresScanResultFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.R2;
import hsj.expmle.com.distribute.activity.DistributeActivity;

/**
 * 猪耳标分发接取页面
 * Created by 16486 on 2020/10/23.
 */
public class ScanResultFragment extends BaseresScanResultFragment<DistributeActivity> implements SlideCutListView.RemoveListener,View.OnClickListener {
    HandlerUtils handlerUtils;

    //按钮提交
    Button btnCommit;
    //返回上一页
    Button btnPrev;


    AutoAdapter<ScanResult> adapter;



    @BindView(R2.id.lv_scan_result)
    SlideCutListView lvScanResult;



    List<ScanResult> tagList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_scan_result_activity;
    }



    @Override
    protected boolean isLoad() {
        return false;
    }

    @Override
    public void onClick(View view) {

            if(view.getId() == btnPrev.getId() ) {
                if (activity instanceof DistributeActivity) {
                    tagList.clear();
                    updateCommitStatus();
                    DistributeActivity distributeActivity = (DistributeActivity) activity;
                    distributeActivity.pager.setCurrentItem(distributeActivity.pager.getCurrentItem() - 1);
                }
            }else if( view.getId() == btnCommit.getId()) {
                StringBuffer rfidBuffer = new StringBuffer();
                for (int i = 0; i < tagList.size(); i++) {
                    if (tagList.size() - 1 == i) {
                        rfidBuffer.append(tagList.get(i).getRfidNo());
                    } else {
                        rfidBuffer.append(tagList.get(i).getRfidNo() + ",");
                    }
                }
                HashMap<String, Object> commitMap = new HashMap<>();
                commitMap.put("ProductID", activity.farmersProductId);
                commitMap.put("Num", activity.scanCount);
                commitMap.put("FarmersID", activity.farmersId);
                commitMap.put("RFIDStr", rfidBuffer.toString());
                commitMap.put("Type", activity.farmersProductTypeId);
                InteractiveDataUtil.interactiveMessage(activity, commitMap, handlerUtils, MethodEnum.POSTADDDISTRIBUTE, InteractiveEnum.POST);


            }
    }
    public void scanCode(String code) {
        //判断当前标签有效 并且RFID不存在列表中
        if (checkLabelRule(code) &&  isExtenData(code)==null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("RFID", code);
            InteractiveDataUtil.interactiveMessage(activity, map, handlerUtils, MethodEnum.GETSTORAGEINFOBYOUT, InteractiveEnum.GET);

        } else {
            ScanResult result =isExtenData(code);
            //判断如果标签在列表中 并且是有效数据则提示有效声音 否则提示异常声音
            if(result!=null && "true".equals(result.getIsFocus()) ) {

                activity.device.playSound(1);
            }else{
                activity.device.playSound(2);
            }
        }
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


    private void updateCommitStatus() {
        currentCount=0;
        errorCount=0;
        for (int i=0;i<tagList.size();i++){
            if("true".equals(tagList.get(i).getIsFocus())){
                currentCount++;
            }else if("false".equals(tagList.get(i).getIsFocus())){
                errorCount++;
            }
        }
        tvScanResultCurrentCount.setText(""+currentCount);
        tvScanResultErrorCount.setText(""+errorCount);
        if(errorCount==0 && currentCount==activity.scanCount){
            btnCommit.setEnabled(true);
        }else{
            btnCommit.setEnabled(false);
        }
        adapter.notifyDataSetChanged();


    }

    private boolean checkLabelRule(String code) {

        return activity.farmersProductTypeId == 1 && code.startsWith(LabelRule.earmMarkRule)
                || activity.farmersProductTypeId == 2 && code.startsWith(LabelRule.stockRule);
    }

    //左右滑动ListView删除数据
    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        tagList.remove(position);
        updateCommitStatus();
    }



    @Override
    public void initFragmentActivityView() {




        btnPrev.setOnClickListener(this);
        btnCommit.setOnClickListener(this);



        Log.d("loadingLayout", "initView: ");
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //扫描耳标栏位
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null) {
                        //判断当前分发标签状态是未启用  1：启用  2：未激活
                        if(scanResult.getIsEnabled()==2) {
                            scanResult.setIsFocus("true");
                        }else{
                            scanResult.setIsFocus("false");
                        }
                        tagList.add(scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(1);
                    //扫描分发栏位
                }else if(MethodEnum.GETSTOCKINFOBYDEPTID.equals(msg.getData().getString("method"))){
                    final List<StockInfo> stockInfos = JSON.parseArray( JSON.parseObject(msg.getData().getString("result")).getString("Data"),StockInfo.class);
                    if(stockInfos!=null && stockInfos.size()>0){
                        StockInfo stockInfo = stockInfos.get(0);
                        ScanResult scanResult = new ScanResult(stockInfo.getRFIDNo(),stockInfo.getSerialNo(),stockInfo.getStatusName(),"true");

                        //判断当前栏位标签是停用状态才能使用  如果为启用或者删除都无法分发  1启用，2停用  -9 删除
                        if(stockInfo.getStatus()!=2){
                            scanResult.setIsFocus("false");
                        }

                        tagList.add(scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(1);

                }else if(MethodEnum.POSTADDDISTRIBUTE.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"数据提交成功");
                    activity.finish();
                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                //扫描分发耳标 异常数据
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(ms.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null   ) {
                        scanResult.setIsFocus("false");
                        tagList.add(0, scanResult);
                        updateCommitStatus();

                    }
                    activity.device.playSound(2);
                    //扫描分发栏位 异常数据
                }else if(MethodEnum.GETSTOCKINFOBYDEPTID.equals(ms.getData().getString("method"))){
                    final List<StockInfo> stockInfos = JSON.parseArray( JSON.parseObject(ms.getData().getString("result")).getString("Data"),StockInfo.class);
                    if(stockInfos!=null && stockInfos.size()>0){
                        StockInfo stockInfo = stockInfos.get(0);
                        ScanResult scanResult = new ScanResult(stockInfo.getRFIDNo(),stockInfo.getSerialNo(),stockInfo.getStatusName(),"false");
                        tagList.add(0,scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(2);

                }else if(MethodEnum.POSTADDDISTRIBUTE.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"数据提交失败");
                }
            }
        });
        adapter = new AutoAdapter<ScanResult>(activity, tagList, "RfidNo", "SerialNo","IsEnabledName");
        lvScanResult.setAdapter(adapter);

        lvScanResult.setRemoveListener(this);
        tvScanResultTotalCount.setText(activity.scanCount+"");
        updateCommitStatus();
        ProgressDialog progress = ProgressDialog.createDialog(activity);
        powerSettingView.setListener(activity.device,progress, Baseres_PowerSettingView.INTERMEDIATE);
    }
    //初始化标题数据
    @Override
    public String[] getArrayTitle() {
        return new String[]{"RFID","序列号","状态"};
    }

    //初始化底部数据
    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        btnPrev = new Button(activity);
        btnCommit = new Button(activity);
        List<BottomViewList<Button>> list = new ArrayList<>();
        list.add(new BottomViewList<Button>(btnPrev,"返回"));
        list.add(new BottomViewList<Button>(btnCommit,"提交"));
        return list;
    }
}
