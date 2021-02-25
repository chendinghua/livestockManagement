package hsj.expmle.com.distribute.fragment;

import android.os.Message;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.R2;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.distribute.entry.DataInfo;
import com.kymjs.app.base_res.utils.base.distribute.entry.PackageInfo;
import com.kymjs.app.base_res.utils.base.distribute.entry.PackageInfoItems;
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
import com.kymjs.app.base_res.utils.utils.ForcehUtils;
import com.kymjs.app.base_res.utils.utils.LogUtils;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import hsj.expmle.com.distribute.activity.DistributeActivity;
/**
 * 猪耳标分发接取页面
 * Created by 16486 on 2020/10/23.
 */
public class ScanResultFragment extends BaseresScanResultFragment<DistributeActivity> implements SlideCutListView.RemoveListener, View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    HandlerUtils handlerUtils;

    //按钮提交
    Button btnCommit;
    //返回上一页
    Button btnPrev;
    AutoAdapter<ScanResult> adapter;
    @BindView(R2.id.lv_scan_result)
    SlideCutListView lvScanResult;
    List<ScanResult> tagList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();

    AutoAdapter<PackageInfoItems> packAdapter;

    List<PackageInfoItems> packTagList = new ArrayList<>();

    List<String> tempSerialNoList = new ArrayList<>();

    @BindView(R2.id.layout_two_time_distribution)
    LinearLayout twoTimeDistribute;
    @BindView(R2.id.rb_scan_distribute_Rfid)
    RadioButton rbScanDistributeRfid;
    @BindView(R2.id.rb_scan_distribute_Code)
    RadioButton rbScanDistributeCode;
    @BindView(R2.id.rb_packageCode)
    RadioButton rbPackageCode;
    @BindView(R2.id.layout_one_time_distribution)
    LinearLayout layoutOneTimeDistribution;
    @BindView(R2.id.rb_case_serialNo)
    RadioButton rbCaseSerialNo;
    @BindView(R2.id.rb_package_serialNo)
    RadioButton rbPackageSerialNo;

    @BindView(R2.id.rg_scan_package)
    RadioGroup rgScanStorage;
    @BindView(R2.id.rg_scan_storage)
    RadioGroup rgScanPackage;
    @BindView(R2.id.rb_case_Code)
    RadioButton rbCaseCode;
    @BindView(R2.id.layout_scan_result_operation)
    LinearLayout layoutScanResultOperation;

    int operationType;

    public SCANTYPE scantype;



    int deptId;

    public enum SCANTYPE{
        SCANDISTRIBUTERFID,         //二次分发扫描耳标RFID
        SCANDISTRIBUTECODE,         //二次分发扫描耳标序列号
        SCANPACKCODE,               //二次分发扫描包号
        SCANCASECODE,               //二次分发扫描箱号
        SCANCASESERIALNO,           //一次分发扫描箱号
        SCANPACKAGESERIALNO         //一次分发扫描包号

    }

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
        //返回上一步
        if (Integer.parseInt(view.getTag().toString()) == 0) {
            if (activity instanceof DistributeActivity) {
                tempList.clear();
                tempSerialNoList.clear();
                packTagList.clear();
                tagList.clear();
                updateCommitStatus();
                DistributeActivity distributeActivity = activity;
                distributeActivity.pager.setCurrentItem(distributeActivity.pager.getCurrentItem() - 1);
            }
            //提交数据
        } else if (Integer.parseInt(view.getTag().toString()) == 1) {
            if(operationType==1){
                HashMap<String,Object> packMap = new HashMap<>();
                packMap.put("ProductID", activity.farmersProductId);
                packMap.put("OutDeptID",activity.farmersId);
                //当前任务类型  2： 分发管理  8 ：新增签收
                packMap.put("Type",SPUtils.getSharedStringData(activity,"actionUrl"));
                List<DataInfo> dataInfos  = new ArrayList<>();
                for (int i =0;i<packTagList.size();i++){
                    dataInfos.add(new DataInfo(packTagList.get(i).getPID(),packTagList.get(i).getId()));
                }
                packMap.put("DataInfo",dataInfos);
                InteractiveDataUtil.interactiveMessage(activity,packMap,handlerUtils,MethodEnum.POSTADDDISTRIBUTEONE,InteractiveEnum.POST);
            }else {
                HashMap<String,Object> storageMap = new HashMap<>();
                storageMap.put("FarmersID",activity.farmersId);
                storageMap.put("Type",activity.farmersProductId);
                storageMap.put("Remark","");
                storageMap.put("Num",tagList.size());
                List<DataInfo> dataInfos  = new ArrayList<>();
                for (int i =0;i<tagList.size();i++){
                    dataInfos.add(new DataInfo(tagList.get(i).getPackage(),tagList.get(i).getStorageID()));
                }
                storageMap.put("DataInfo",dataInfos);
                InteractiveDataUtil.interactiveMessage(activity,storageMap,handlerUtils,MethodEnum.POSTADDDISTRIBUTE,InteractiveEnum.POST);
            }
        }
    }
    public void scanCode(String code) {
        //判断当前标签有效 并且RFID不存在列表中
        if (checkLabelRule(code) && isExtenData(tempList, code) == -1) {
            tempList.add(code);
            HashMap<String, Object> map = new HashMap<>();
            map.put("RFID", code);
            InteractiveDataUtil.interactiveMessage(activity, map, handlerUtils, MethodEnum.GETSTORAGEINFOBYOUT, InteractiveEnum.GET);
        } else {
            int index = isExistTagList(code);
            //判断如果标签在列表中 并且是有效数据则提示有效声音 否则提示异常声音
            if (index != -1 && "true".equals(tagList.get(index).getIsFocus())) {
                activity.device.playSound(1);
            } else {
                activity.device.playSound(2);
            }
        }
    }

    public void scanSerialNo(String code){
        code=code.trim();
        Log.d("scanSerialNoCode", "scanSerialNo: "+scantype+"     "+code);

        if (  isExtenData(tempSerialNoList, code) == -1) {

            switch (scantype) {
                case SCANDISTRIBUTECODE:         //二次分发扫描耳标序列号
                    tempSerialNoList.add(code);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("RFID", "");
                    map.put("SerialNo", code);
                    InteractiveDataUtil.interactiveMessage(activity, map, handlerUtils, MethodEnum.GETSTORAGEINFOBYOUT, InteractiveEnum.GET);

                    break;
                case SCANPACKCODE:              //二次分发扫描包号
                    if(code.startsWith(LabelRule.PackageRule)) {
                        tempSerialNoList.add(code);
                        HashMap<String, Object> scanSerialMap = new HashMap<>();
                        scanSerialMap.put("ID", 0);
                        scanSerialMap.put("Code", code);
                        scanSerialMap.put("QelType", -1);
                        InteractiveDataUtil.interactiveMessage(activity, scanSerialMap, handlerUtils, MethodEnum.GETBOXPACKAGEINFO, InteractiveEnum.GET);
                    }else{
                        UIHelper.ToastMessage(activity,"当前条码无效");
                    }
                    break;

                case SCANCASESERIALNO:           //一次分发扫描箱号
                    if(code.startsWith(LabelRule.CaseRule)) {
                        tempSerialNoList.add(code);
                        HashMap<String, Object> scanSerialMap = new HashMap<>();
                        scanSerialMap.put("ID", 0);
                        scanSerialMap.put("Code", code);
                        scanSerialMap.put("QelType", -1);
                        InteractiveDataUtil.interactiveMessage(activity, scanSerialMap, handlerUtils, MethodEnum.GETBOXPACKAGEINFO, InteractiveEnum.GET);
                    }else{
                        UIHelper.ToastMessage(activity,"当前条码无效");
                    }


                    break;
                case SCANPACKAGESERIALNO:        //一次分发扫描包号
                    if(code.startsWith(LabelRule.PackageRule)) {
                        tempSerialNoList.add(code);
                        HashMap<String, Object> packingMap = new HashMap<>();
                        packingMap.put("Code", code);
                        packingMap.put("Type", 1);
                        packingMap.put("pageIndex", 1);
                        packingMap.put("pageSize", 1);
                        //     packingMap.put("OpDeptID", SPUtils.getSharedIntData(activity,"DeptID"));
                        packingMap.put("OpDeptID", -1);
                        InteractiveDataUtil.interactiveMessage(activity, packingMap, handlerUtils, MethodEnum.GETQELBOXPACKAGE, InteractiveEnum.GET, code);
                    }else{
                        UIHelper.ToastMessage(activity,"当前条码无效");
                    }
                    break;
                case SCANCASECODE:            //二次分发箱号扫描
                    if(code.startsWith(LabelRule.CaseRule)) {
                        tempSerialNoList.add(code);
                        HashMap<String, Object> caseMap = new HashMap<>();
                        caseMap.put("Code", code);
                        caseMap.put("QelType", 2);
                        InteractiveDataUtil.interactiveMessage(activity, caseMap, handlerUtils, MethodEnum.GETBOXINFO, InteractiveEnum.GET,code);
                    }else{
                        UIHelper.ToastMessage(activity,"当前条码无效");
                    }
                    break;
            }
        }

    }


    public int isExtenData(List<String> items,  String code) {
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (code.equals(items.get(i))) {
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
    //更新标题和提交按钮状态
    private void updateCommitStatus() {
        currentCount = 0;
        errorCount = 0;
        if(operationType==1) {
            for (int i = 0; i < packTagList.size(); i++) {
                if ("true".equals(packTagList.get(i).getIsFocus())) {
                    currentCount++;
                } else if ("false".equals(packTagList.get(i).getIsFocus())) {
                    errorCount++;
                }
            }

        }else{
            for (int i = 0; i < tagList.size(); i++) {
                if ("true".equals(tagList.get(i).getIsFocus())) {
                    currentCount++;
                } else if ("false".equals(tagList.get(i).getIsFocus())) {
                    errorCount++;
                }
            }
        }

        tvScanResultCurrentCount.setText("" + currentCount);
        tvScanResultErrorCount.setText("" + errorCount);
        if (errorCount == 0 && currentCount>0) {
            btnCommit.setEnabled(true);
        } else {
            btnCommit.setEnabled(false);
        }
        if(operationType==1) {
            packAdapter.notifyDataSetChanged();
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private boolean checkLabelRule(String code) {
        return activity.farmersProductTypeId == 1 && code.startsWith(LabelRule.earmMarkRule)
                || activity.farmersProductTypeId == 2 && code.startsWith(LabelRule.stockRule);
    }

    //左右滑动ListView删除数据
    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        int index = -1;
        if(operationType==1){
            //移除当前包号临时数据
            if ((index = isExtenData(tempSerialNoList, packTagList.get(position).getCode())) != -1) {
                tempSerialNoList.remove(index);
            }
            index=-1;
            //移除当前箱号临时数据
            if ((index = isExtenData(tempSerialNoList, packTagList.get(position).getBoxCode())) != -1) {
                tempSerialNoList.remove(index);
            }

            packTagList.remove(position);
        }else {
                LogUtils.d(activity,"removeItem", "removeItem: "+tagList.get(position).toString());

            if ((index = isExtenData(tempList, tagList.get(position).getRfidNo())) != -1) {
                tempList.remove(index);
            }
            //移除扫描耳标序列号信息的临时数据
            if ((index = isExtenData(tempSerialNoList, tagList.get(position).getSerialNo())) != -1) {
                tempSerialNoList.remove(index);
            }
            //语出扫描包号的临时数据
            if ((index = isExtenData(tempSerialNoList, tagList.get(position).getCode())) != -1) {
                tempSerialNoList.remove(index);
            }

            tagList.remove(position);
        }
        updateCommitStatus();
    }
    @Override
    public void initFragmentActivityView() {
        layoutScanResultOperation.setVisibility(View.GONE);
        deptId =  SPUtils.getSharedIntData(activity,"DeptID");

        //判断当前为二次分发
        if(operationType==2){
            rbScanDistributeRfid.setChecked(true);
            scantype= SCANTYPE.SCANDISTRIBUTERFID;
            twoTimeDistribute.setVisibility(View.VISIBLE);
            layoutOneTimeDistribution.setVisibility(View.GONE);
            //判断当前为一次分发
        }else{
            rbPackageCode.setChecked(true);
            scantype=SCANTYPE.SCANPACKAGESERIALNO;
            twoTimeDistribute.setVisibility(View.GONE);
            layoutOneTimeDistribution.setVisibility(View.VISIBLE);
        }

        initListener();


        Log.d("loadingLayout", "initView: ");
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //扫描耳标栏位
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null) {
                        //判断当前分发标签状态是未启用  1：启用  2：未激活    产品类型判断
                        if (scanResult.getIsEnabled() == 2  && scanResult.getProductID()==  activity.farmersProductId) {
                            scanResult.setIsFocus("true");
                        } else {
                            scanResult.setIsFocus("false");
                        }
                        tagList.add(scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(1);
                    //扫描分发栏位    暂时栏位标签未启用
                } else if (MethodEnum.GETSTOCKINFOBYDEPTID.equals(msg.getData().getString("method"))) {
                    final List<StockInfo> stockInfos = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), StockInfo.class);
                    if (stockInfos != null && stockInfos.size() > 0) {
                        StockInfo stockInfo = stockInfos.get(0);
                        ScanResult scanResult = new ScanResult(stockInfo.getRFIDNo(), stockInfo.getSerialNo(), stockInfo.getStatusName(), "true");

                        //判断当前栏位标签是停用状态才能使用  如果为启用或者删除都无法分发  1启用，2停用  -9 删除
                        if (stockInfo.getStatus() != 2) {
                            scanResult.setIsFocus("false");
                        }
                        tagList.add(scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(1);

                } else if (MethodEnum.POSTADDDISTRIBUTE.equals(msg.getData().getString("method"))) {
                    UIHelper.ToastMessage(activity, "数据提交成功");
                    Utils.activityFinish(activity, activity.device);
                    //箱包详情  主要查询第二次分发的包号数据里面的库存集合信息    和查询第一次箱号里面的包号集合信息
                } else if (MethodEnum.POSTADDDISTRIBUTEONE.equals(msg.getData().getString("method"))) {
                    UIHelper.ToastMessage(activity, "数据提交成功");
                    Utils.activityFinish(activity, activity.device);
                    //箱包详情  主要查询第二次分发的包号数据里面的库存集合信息    和查询第一次箱号里面的包号集合信息
                }else if(MethodEnum.GETBOXPACKAGEINFO.equals(msg.getData().getString("method"))){
                    //当前是一次分发返回查询的箱号里面的包号集合信息
                    if(operationType==1){
                        List<PackageInfoItems> packageInfos = JSON.parseArray( JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackageInfoItems.class);
                        if(packageInfos!=null && packageInfos.size()>0) {
                            for (int i = 0; i < packageInfos.size(); i++) {
                                //判断当前已添加到临时数组
                                if (isExtenData(tempSerialNoList, packageInfos.get(i).getCode()) == -1) {
                                    tempSerialNoList.add(packageInfos.get(i).getCode());
                                    //判断当前部门库存数量和  部门id是否为当前部门
                                    if(packageInfos.get(i).getDeptNum()>0 && packageInfos.get(i).getOpDeptID()==deptId && packageInfos.get(i).getStatus()!=3){
                                        packageInfos.get(i).setIsFocus("true");
                                    }else{
                                        packageInfos.get(i).setIsFocus("false");
                                    }
                                    packTagList.add(packageInfos.get(i));
                                }
                            }
                            updateCommitStatus();
                        }
                     //当前为二次分发返回查询的库存集合信息
                    }else if(operationType==2){
                       List<ScanResult> scanResults = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                        if(scanResults!=null && scanResults.size()>0){
                            for (int i =0;i<scanResults.size();i++) {
                                //判断当前包里面
                                if (ForcehUtils.getListStatusCount(tagList, "RfidNo", scanResults.get(i).getRfidNo()) == 0) {
                                    //判断当前分发标签状态是未启用  1：启用  2：未激活
                                    if (scanResults.get(i).getIsEnabled() == 2   && scanResults.get(i).getProductID()==activity.farmersProductId) {
                                        scanResults.get(i).setIsFocus("true");
                                    } else {
                                        scanResults.get(i).setIsFocus("false");
                                    }
                                    tempList.add(scanResults.get(i).getRfidNo());
                                    tagList.add(scanResults.get(i));
                                }
                            }
                        }
                        updateCommitStatus();
                    }
                    //查询包号有效信息
                }else if(MethodEnum.GETQELBOXPACKAGE.equals(msg.getData().getString("method"))){
                   PackageInfo packageInfo = JSON.parseObject( JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackageInfo.class);
                    if(packageInfo!=null && packageInfo.getResult().size()>0) {
                        PackageInfoItems  packageInfoItems =  packageInfo.getResult().get(0);
                        //判断当前部门库存数量和  部门id是否为当前部门
                                if(packageInfoItems.getDeptNum()>0  && packageInfoItems.getOpDeptID()==deptId && packageInfoItems.getStatus()!=3){
                                    packageInfoItems.setIsFocus("true");
                                }else{
                                    packageInfoItems.setIsFocus("false");
                                }
                                packageInfoItems.setBoxCode(msg.getData().getString("bindDate"));
                                packTagList.add(packageInfoItems);
                        updateCommitStatus();
                    }
                    //查询箱号库存信息
                }else if(MethodEnum.GETBOXINFO.equals(msg.getData().getString("method"))){
                    List<ScanResult> scanResults = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if(scanResults!=null && scanResults.size()>0){
                        for (int i =0;i<scanResults.size();i++) {
                            //判断当前包里面
                            if (ForcehUtils.getListStatusCount(tagList, "RfidNo", scanResults.get(i).getRfidNo()) == 0) {
                                //判断当前分发标签状态是未启用  1：启用  2：未激活
                                if (scanResults.get(i).getIsEnabled() == 2   && scanResults.get(i).getProductID()==activity.farmersProductId) {
                                    scanResults.get(i).setIsFocus("true");
                                } else {
                                    scanResults.get(i).setIsFocus("false");
                                }
                                scanResults.get(i).setCode(msg.getData().getString("bindDate"));
                                tempList.add(scanResults.get(i).getRfidNo());
                                tagList.add(scanResults.get(i));
                            }
                        }
                    }
                    updateCommitStatus();

                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                //扫描分发耳标 异常数据
                if (MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(ms.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult != null) {
                        scanResult.setIsFocus("false");
                        tagList.add(0, scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(2);
                    //扫描分发栏位 异常数据
                } else if (MethodEnum.GETSTOCKINFOBYDEPTID.equals(ms.getData().getString("method"))) {
                    final List<StockInfo> stockInfos = JSON.parseArray(JSON.parseObject(ms.getData().getString("result")).getString("Data"), StockInfo.class);
                    if (stockInfos != null && stockInfos.size() > 0) {
                        StockInfo stockInfo = stockInfos.get(0);
                        ScanResult scanResult = new ScanResult(stockInfo.getRFIDNo(), stockInfo.getSerialNo(), stockInfo.getStatusName(), "false");
                        tagList.add(0, scanResult);
                        updateCommitStatus();
                    }
                    activity.device.playSound(2);

                } else if (MethodEnum.POSTADDDISTRIBUTE.equals(ms.getData().getString("method"))) {
                    UIHelper.ToastMessage(activity, "数据提交失败");
                }else if (MethodEnum.POSTADDDISTRIBUTEONE.equals(ms.getData().getString("method"))) {
                    UIHelper.ToastMessage(activity, "数据提交失败");
                }
            }
        });
        adapter = new AutoAdapter<>(activity, tagList, "RfidNo", "SerialNo", "IsEnabledName", "DeptName");


        packAdapter  = new AutoAdapter<>(activity,packTagList,"ID","Code","DeptNum","CreateDate","StatusName");

        if(operationType==1){
            lvScanResult.setAdapter(packAdapter);
        }else {
            lvScanResult.setAdapter(adapter);
        }
        lvScanResult.setRemoveListener(this);
        tvScanResultTotalCount.setText(activity.scanCount + "");
        updateCommitStatus();
        ProgressDialog progress = ProgressDialog.createDialog(activity);
        powerSettingView.setListener(activity.device, progress, Baseres_PowerSettingView.INTERMEDIATE);
    }

    private void initListener() {
        btnPrev.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        rgScanStorage.setOnCheckedChangeListener(this);
        rgScanPackage.setOnCheckedChangeListener(this);


    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RCaster rcaster = new RCaster(R.class,R2.class);
        switch (rcaster.cast(checkedId)){
            case R2.id.rb_case_serialNo:
                scantype = SCANTYPE.SCANCASESERIALNO;
                break;
            case R2.id.rb_package_serialNo:
                scantype = SCANTYPE.SCANPACKAGESERIALNO;
                break;
            case R2.id.rb_packageCode:
                scantype = SCANTYPE.SCANPACKCODE;
                break;
            case R2.id.rb_scan_distribute_Code:
                scantype = SCANTYPE.SCANDISTRIBUTECODE;
                break;
            case R2.id.rb_scan_distribute_Rfid:
                scantype = SCANTYPE.SCANDISTRIBUTERFID;
                break;
            case R2.id.rb_case_Code:
                scantype=SCANTYPE.SCANCASECODE;
                break;
        }
    }

    //初始化标题数据
    @Override
    public String[] getArrayTitle() {
        operationType =  SPUtils.getSharedIntData(activity,"OperationType");
        return  (operationType==1)?new String[]{"编号", "包号", "库存数量", "创建时间","箱包状态"}: new String[]{"RFID", "序列号", "状态", "部门信息"};
    }

    //初始化底部数据
    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        btnPrev = new Button(activity);
        btnCommit = new Button(activity);
        List<BottomViewList<Button>> list = new ArrayList<>();
        list.add(new BottomViewList<Button>(btnPrev, "返回"));
        list.add(new BottomViewList<Button>(btnCommit, "提交"));
        return list;
    }


}