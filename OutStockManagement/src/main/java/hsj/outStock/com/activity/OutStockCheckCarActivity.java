package hsj.outStock.com.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskInventoryInfo;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import hsj.outStock.com.R;
import hsj.outStock.com.R2;
import hsj.outStock.com.adapter.OutStockCheckCarAdapter;
import hsj.outStock.com.entry.OutStockCarList;

/**
 * 处理已审核出栏任务  添加多个车辆信息
 * Created by 16486 on 2020/12/6.
 */

public class OutStockCheckCarActivity extends BaseActivity implements OutStockCheckCarAdapter.OnItemOperationClickListener, ResponseHandlerInterface {

    OutStockCheckCarAdapter carAdapter;

    AutoAdapter<TaskInventoryInfo.TaskDetailInfo> adapter;

    //记录临时存储信息集合
    List<TaskInventoryInfo.TaskDetailInfo> tempList = new ArrayList<>();
    //获取当前所有数据集合
    List<TaskInventoryInfo.TaskDetailInfo> tagList = new ArrayList<>();


    List<OutStockCarList> carList = new ArrayList<>();

    @BindView(R2.id.lv_out_stock_check_car_list)
    ListView lvOutStockCheckCarList;
    @BindView(R2.id.tv_scan_result_total_count)
    TextView tvScanResultTotalCount;
    @BindView(R2.id.tv_scan_result_current_count)
    TextView tvScanResultCurrentCount;
    @BindView(R2.id.tv_scan_result_error_count)
    TextView tvScanResultErrorCount;
    @BindView(R2.id.lv_out_stock_check_scan_list)
    SlideCutListView lvOutStockCheckScanList;
    @BindView(R2.id.btn_stock_check_car_back)
    Button btnStockCheckCarBack;
    @BindView(R2.id.btn_stock_check_car_commit)
    Button btnStockCheckCarCommit;

    HandlerUtils handlerUtils;

    //设置当前频率控件
    @BindView(R2.id.psv_out_stock_check_scan_result)
    protected Baseres_PowerSettingView powerSettingView;

    Device device;

    int taskID;

    /*********************
     * 子类实现
     *****************************/
    @Override
    public int getLayoutId() {
        return R.layout.out_stock_check_car_activity;
    }

    @Override
    public void initPresenter() {
        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //获取当前所有数据
                if (MethodEnum.GETTASKINFO.equals(msg.getData().getString("method"))) {
                    final TaskInventoryInfo taskInventoryInfo = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),
                            TaskInventoryInfo.class);
                    tagList.clear();


                    tagList.addAll(taskInventoryInfo.getTaskDetailList());
                    for (TaskInventoryInfo.TaskDetailInfo taskDetailInfo : tagList) {
                        taskDetailInfo.setIsFocus("false");
                    }

                    adapter.notifyDataSetChanged();
                    updateTitle();

                }else if(MethodEnum.POSTPDAFOROUTSTOCK.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(mContext,"数据提交成功");
                    Utils.activityFinish(OutStockCheckCarActivity.this,device);
                }
            }
        });


    }

    @Override
    public void initView() {


        //默认添加空的车辆信息
        OutStockCarList outStockCarList = new OutStockCarList();
        carList.add(outStockCarList);
        //初始化车辆集合信息适配器
        carAdapter = new OutStockCheckCarAdapter(mContext, carList, this);
        lvOutStockCheckCarList.setAdapter(carAdapter);
        //显示扫描列表数据
        adapter = new AutoAdapter<>(mContext, tagList, "StorageID", "RfidNo", "SerialNo", "ProductName", "DeptName");
        lvOutStockCheckScanList.setAdapter(adapter);

        lvOutStockCheckScanList.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                //判断当前滑动的是异常的数据
                if ("error".equals(tagList.get(position).getIsFocus())) {
                    tagList.remove(position);
                    //判断当前数据为正常数据  修改当前数据为未操作
                } else if ("true".equals(tagList.get(position).getIsFocus())) {
                    tagList.get(position).setIsFocus("false");
                }
                updateTitle();
            }
        });


        device = new DeviceFactory(mContext, this).getDevice();
        device.initUHF();
        //获取当前全部需要处理的数据
        HashMap<String, Object> taskMap = new HashMap<>();
        taskID = getIntent().getExtras().getInt("taskId");

        taskMap.put("ID", taskID);
        InteractiveDataUtil.interactiveMessage(this, taskMap, handlerUtils, MethodEnum.GETTASKINFO, InteractiveEnum.GET);
        //加载初始化RFID模块加载提示
        ProgressDialog progress = ProgressDialog.createDialog(mContext);
        //设置当前默认功率
        powerSettingView.setListener(device, progress, Baseres_PowerSettingView.INTERMEDIATE);
    }


    /**
     * 获取当前已经扫描完成和未完成的数据
     *
     * @param status 1 查询当前扫描已完成数据  2 查询当前扫描未完成数据 3 查询当前异常数
     * @return
     */
    public List<TaskInventoryInfo.TaskDetailInfo> getSuccessList(int status) {
        List<TaskInventoryInfo.TaskDetailInfo> successTempList = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            if ("true".equals(tagList.get(i).getIsFocus()) && status == 1 || "false".equals(tagList.get(i).getIsFocus()) && status == 2 || "error".equals(tagList.get(i).getIsFocus()) && status == 3 || status == 4) {
                successTempList.add(tagList.get(i));
            }
        }
        return successTempList;
    }


    /**
     * 车辆列表点击添加按钮的触发事件
     *
     * @param index
     */
    @Override
    public void onAddClick(int index, OutStockCarList outStockCarList) {
        //判断当前有效情况则添加记录数据
        if (isCheckCarInfo(outStockCarList)) {
            Log.d("itemCode", "onAddClick: " + outStockCarList.toString() + "  currentIndex   " + index);

            //添加当前扫描已处理的数据
            outStockCarList.setInfo(getSuccessList(1));
            //记录当前输入的车牌信息
            carList.add(index, outStockCarList);
            tempList.clear();
            tempList.addAll(getSuccessList(2));
            //添加当前扫描页面数据
            tagList.clear();
            tagList.addAll(tempList);

            //更新当前页面数据
            carAdapter.notifyDataSetChanged();
            updateTitle();
        }


    }


    /**
     * 车辆列表点击移除按钮的触发事件
     *
     * @param index
     */
    @Override
    public void onRemoveClick(int index) {
        Log.d("itemCode", "onRemoveClick:    carList.size() " + carList.size() + "  index   " + index);

        //判断当前数据为最后一条并且选中操作的栏也是第一个
        if (carList.size() == 1 && index == 0) {
            UIHelper.ToastMessage(mContext, "当前数据是最后一条无法删除");
            //判断当前操作的数据是为最后一条
        } else if (carList.size() - 1 == index) {
            //先把最后的集合里面的值放到前面集合里面
            carList.get(index - 1).getInfo().addAll(clearScanStatus(getSuccessList(4)));
            //显示添加按钮
            carList.get(index - 1).setShowAdd(true);
            //把最后集合的前面一个值内容显示出来
            tagList.clear();
            tagList.addAll(clearScanStatus(carList.get(index - 1).getInfo()));
            carList.remove(index);


            //处理中间数据
        } else {
            //追加中间移除的数据
            tagList.addAll(clearScanStatus(carList.get(index).getInfo()));
            carList.remove(index);

        }

        //更新当前页面数据
        carAdapter.notifyDataSetChanged();
        updateTitle();
    }


    public boolean isCheckCarInfo(OutStockCarList carInfo) {
        boolean isCheck = true;
        if (carInfo == null) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "车辆对象为空");
        } else if (isCheckOutStockCarList(carInfo)) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "车辆或者驾驶员信息为空");
        } else if (device != null && device.isLoop()) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "请停止标签读取操作");
        } else if (getSuccessList(2).size() == 0) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "当前无其他数据可进行处理");
        } else if (getSuccessList(1).size() == 0) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "当前车辆无扫描数据");
        } else if (getSuccessList(3).size() != 0) {
            isCheck = false;
            UIHelper.ToastMessage(mContext, "当前车辆存在异常数据");
        }


        return isCheck;


    }

    //判断当前车辆信息是否为空
    public boolean isCheckOutStockCarList(OutStockCarList carInfo){
        boolean isCheck=false;
        if (Utils.isStrEmpty(carInfo.getCar()) || Utils.isStrEmpty(carInfo.getOPERName()) || Utils.isStrEmpty(carInfo.getOPERTelphone())){
            isCheck=true;
        }
        return isCheck;
    }


    @Override
    public void handleTagdata(String rfid) {

        int index = -1;
        for (int i = 0; i < tagList.size(); i++) {
            if (rfid.equals(tagList.get(i).getRfidNo())) {
                index = i;
            }
        }
        //判断当前扫描的rfid数据是存在的
        if (index != -1) {
            //判断当前数据是未扫描正常的数据 修改为已读取   是为了排除异常数据
            if ("false".equals(tagList.get(index).getIsFocus())) {
                tagList.get(index).setIsFocus("true");
                device.playSound(1);
            } else if ("true".equals(tagList.get(index).getIsFocus())) {
                device.playSound(1);
            } else {
                device.playSound(2);
            }
        } else {
            TaskInventoryInfo.TaskDetailInfo taskDetailInfo = new TaskInventoryInfo().getTaskDetailInfo();
            taskDetailInfo.setRfidNo(rfid);
            taskDetailInfo.setIsFocus("error");
            tagList.add(0, taskDetailInfo);
            device.playSound(1);
        }
        updateTitle();

    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if (keyCode == 280) {
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode, event, 1, false);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            //提示弹窗退出当前页面
            Utils.onBackDialog(this, device);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void updateTitle() {
        adapter.notifyDataSetChanged();
        int success = getSuccessList(1).size();
        int total = getSuccessList(2).size();
        int error = getSuccessList(3).size();

        tvScanResultTotalCount.setText("" + (total + success));
        tvScanResultCurrentCount.setText("" + success);
        tvScanResultErrorCount.setText("" + error);
    }

    /**
     * 清除扫描状态
     *
     * @param list
     * @return
     */
    public List<TaskInventoryInfo.TaskDetailInfo> clearScanStatus(List<TaskInventoryInfo.TaskDetailInfo> list) {
        List<TaskInventoryInfo.TaskDetailInfo> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TaskInventoryInfo.TaskDetailInfo info = list.get(i);
            info.setIsFocus("false");
            temp.add(info);
        }

        return temp;
    }



    @OnClick({R2.id.btn_stock_check_car_back, R2.id.btn_stock_check_car_commit})
    public void onViewClicked(View view) {
        RCaster rCaster = new RCaster(R.class,R2.class);

        switch (rCaster.cast(view.getId())) {
            case R2.id.btn_stock_check_car_back:
                //提示弹窗退出当前页面
                Utils.onBackDialog(this, device);

                break;
            case R2.id.btn_stock_check_car_commit:
                List<OutStockCarList> tempOutList= new ArrayList<>();
                tempOutList.addAll(carList);
                tempOutList.remove(0);

                //获取更新当前除最后一条数据的更新并检测数据是否有效
                for (int i =0;i<lvOutStockCheckCarList.getChildCount()-1;i++){
                    //获取当前行里面的数据
                    OutStockCarList tempOutStockCarList =   getOutStockCarList(lvOutStockCheckCarList.getChildAt(i));
                    //判断当前行的数据是否为空   如果为空则弹出数据无效
                    if(isCheckOutStockCarList(tempOutStockCarList)){
                        UIHelper.ToastMessage(mContext,"当前所有车辆信息有数据为空");
                        return;
                    }
                    //把当前行的数据里面的扫描数据重新赋值
                    tempOutStockCarList.setInfo(carList.get(i).getInfo());
                    tempOutList.add(i,tempOutStockCarList);

                }

                //如果最后一条无有效数据则忽略  如都有效则创建一个新的OutStockCarList对象  并且把当前集合里面的数据赋值进去
                if(getSuccessList(3).size()==0) {

                    //获取最后一条数据
                    View lvViewItem = lvOutStockCheckCarList.getChildAt(lvOutStockCheckCarList.getChildCount()-1);
               /*     EditText etOperName = lvViewItem.findViewById(R.id.et_out_stock_check_OPERName);
                    EditText etOperTelPhone = lvViewItem.findViewById(R.id.et_out_stock_check_OPERTelphone);
                    EditText etCar = lvViewItem.findViewById(R.id.et_out_stock_check_Car);
                    OutStockCarList outStockCarList = new OutStockCarList();
                    outStockCarList.setCar(etCar.getText().toString());
                    outStockCarList.setOPERName(etOperName.getText().toString());
                    outStockCarList.setOPERTelphone(etOperTelPhone.getText().toString());*/
               OutStockCarList outStockCarList = getOutStockCarList(lvViewItem);
                    if(!isCheckOutStockCarList(outStockCarList)   && getSuccessList(1).size()>0) {
                        outStockCarList.setInfo(getSuccessList(1));
                        tempOutList.add(outStockCarList);
                        //无车辆信息
                    }else if(isCheckOutStockCarList(outStockCarList) ) {
                        UIHelper.ToastMessage(mContext,"当前存在扫描，但未填写车辆信息");
                        return;
                        //有车辆信息，但是没有扫描数据
                    }else if(getSuccessList(1).size()==0 && !isCheckOutStockCarList(outStockCarList) ){
                        UIHelper.ToastMessage(mContext,"有车辆信息，但是没有扫描数据");
                    }
                }else if(getSuccessList(3).size()>0){
                    UIHelper.ToastMessage(mContext,"当前扫描列表有异常数据");
                    return;
                }



                //判断车辆集合是否只有一条数据  并且扫描集合里面有有效数据
                if(tempOutList.size()>1 ||  tempOutList.size()==1 && getSuccessList(1).size()>0){
                    HashMap<String,Object> outStockCarInfoMap = new HashMap<>();

                    outStockCarInfoMap.put("DataInfo",tempOutList);
                    outStockCarInfoMap.put("TaskID",taskID);
                    outStockCarInfoMap.put("Remark","测试数据");
                    InteractiveDataUtil.interactiveMessage(this,outStockCarInfoMap,handlerUtils,MethodEnum.POSTPDAFOROUTSTOCK,InteractiveEnum.POST);

                    Log.d("successLog", "onViewClicked: "+JSON.toJSONString(tempOutList));
                }else{
                    UIHelper.ToastMessage(mContext,"当前没有操作任何数据");
                }

                break;
        }
    }
    public OutStockCarList getOutStockCarList(View view){
        EditText etOperName = view.findViewById(R.id.et_out_stock_check_OPERName);
        EditText etOperTelPhone = view.findViewById(R.id.et_out_stock_check_OPERTelphone);
        EditText etCar = view.findViewById(R.id.et_out_stock_check_Car);
        OutStockCarList outStockCarList = new OutStockCarList();
        outStockCarList.setCar(etCar.getText().toString());
        outStockCarList.setOPERName(etOperName.getText().toString());
        outStockCarList.setOPERTelphone(etOperTelPhone.getText().toString());
        return  outStockCarList;
    }

}
