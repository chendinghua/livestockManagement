package hsj.stock.com.fragment;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;
import com.lwy.paginationlib.PaginationListView;
import java.util.HashMap;
import java.util.List;
import devicelib.dao.Device;
import hsj.stock.com.dialog.StockOperationDialog;
import hsj.stock.com.entry.StockInfoList;
/**
 * 栏位管理
 * Created by 16486 on 2020/11/13.
 */
public class StockInfoFragment extends BaseresTaskFragment {
    PaginationListView.Adapter<StockInfo> adapter;
    HandlerUtils handlerUtils;
    Device device;
    StockOperationDialog mDialog;
    StockOperationDialog.Builder builder;
    private int status;
    private int stockId;
    private boolean isLoad=true;
    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }
    @Override
    public  void initFragmentActivityView() {
        btnTaskAdd.setText("新增栏位");
        builder = new StockOperationDialog.Builder(activity);

        adapter = new PaginationListView.Adapter(20, activity, -1, "Name", "SerialNo", "MaxNum", "OpTime", "ProductName");
        lvTaskInfo.setAdapter(adapter);
        lvTaskInfo.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                loadData(currentPagePosition + 1, perPageCount);
            }
            @Override
            public void onPerPageCountChanged(int perPageCount) {
            }
        });
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //返回栏位信息
                if (MethodEnum.GETSTOCKINFOBYDEPTID.equals(msg.getData().getString("method"))) {
                    if (device != null) {
                        device.destroy();
                    }
                    final List<StockInfo> stockInfos = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), StockInfo.class);
                    //判断当前是修改栏位操作或者新增栏位操作  status=1 新增栏位  status=2修改栏位
                    if (stockInfos!=null && stockInfos.size()>0 && stockInfos.get(0).getStatus()==1 && status ==2 ||
                            stockInfos != null && stockInfos.size() > 0 && stockInfos.get(0).getStatus() == 2  && status==1) {
                        mDialog = builder.setMessage("请确认栏位信息").
                                initStockInfo(stockInfos.get(0))
                                .setStatus(status)
                                .setPositiveButton("确认", new StockOperationDialog.PositiveButtonClickListener() {
                                    @Override
                                    public void onListener(StockInfo stockInfo, int status) {
                                        //提交栏位信息
                                        if (stockInfo != null) {
                                            HashMap<String, Object> map = new HashMap<>();
                                            if (status == 2) {
                                                map.put("ID", stockId);
                                            }
                                            map.put("DeptName", SPUtils.getSharedStringData(activity, "DeptName"));
                                            map.put("Name", stockInfo.getName());
                                            map.put("RfidNo", stockInfo.getRFIDNo());
                                            map.put("MaxArea", stockInfo.getMaxArea());
                                            map.put("MaxNum", stockInfo.getMaxNum());
                                            map.put("Type", status);
                                            map.put("IsEnabled",stockInfo.getStatus());
                                            InteractiveDataUtil.interactiveMessage(activity, map, handlerUtils, MethodEnum.POSTADDSTOCK, InteractiveEnum.POST);
                                        }
                                        mDialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                    }
                                })
                                .createTwoButtonDialog();
                        mDialog.show();
                    } else {
                        UIHelper.ToastMessage(activity, "当前栏位无法进行操作");
                    }
                    //查询在库栏位数据
                }else if(MethodEnum.GETSTOCKINFOBYDEPT.equals(msg.getData().getString("method"))) {
                    StockInfoList stockInfoList = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), StockInfoList.class);
                    if (stockInfoList != null) {
                        Log.d("JSONResult", "handlerExecutionFunction: " + msg.getData().getString("result") + "     " + stockInfoList.getResult());
                        if (isLoad) {
                            isLoad = false;
                            adapter.setDataTotalCount(stockInfoList.getRowsCount());
                        }
                        adapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), stockInfoList.getResult());
                        lvTaskInfo.setState(PaginationListView.SUCCESS);
                    }
                }else if(MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))) {
                    ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    if (scanResult.getIsEnabled() == 2) {
                        StockInfo stockInfo = new StockInfo();
                        stockInfo.setSerialNo(scanResult.getSerialNo());
                        stockInfo.setRFIDNo(scanResult.getRfidNo());
                        mDialog = builder.setMessage("请确认栏位信息").
                                initStockInfo(stockInfo)
                                .setStatus(status)
                                .setPositiveButton("确认", new StockOperationDialog.PositiveButtonClickListener() {
                                    @Override
                                    public void onListener(StockInfo stockInfo, int status) {
                                        //提交栏位信息
                                        if (stockInfo != null) {
                                            HashMap<String, Object> map = new HashMap<>();
                                            if (status == 2) {
                                                map.put("ID", stockId);
                                            }
                                            map.put("DeptName", SPUtils.getSharedStringData(activity, "DeptName"));
                                            map.put("Name", stockInfo.getName());
                                            map.put("RfidNo", stockInfo.getRFIDNo());
                                            map.put("MaxArea", stockInfo.getMaxArea());
                                            map.put("MaxNum", stockInfo.getMaxNum());
                                            map.put("Type", status);
                                            map.put("IsEnabled",stockInfo.getStatus());
                                            InteractiveDataUtil.interactiveMessage(activity, map, handlerUtils, MethodEnum.POSTADDSTOCK, InteractiveEnum.POST);
                                        }
                                        mDialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                    }
                                })
                                .createTwoButtonDialog();
                        mDialog.show();
                    }else {
                        UIHelper.ToastMessage(activity, "当前栏位无法进行操作");
                    }
                }else if(MethodEnum.POSTADDSTOCK.equals(msg.getData().getString("method"))){
                    isLoad=true;
                    loadData(adapter.getCurrentPagePos(),adapter.getPerPageCount());
                    UIHelper.ToastMessage(activity,"数据提交成功");
                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                if (MethodEnum.GETSTOCKINFOBYDEPTID.equals(ms.getData().getString("method"))) {
                    if (device != null) {
                        device.destroy();
                    }

                }else if(MethodEnum.GETSTOCKINFOBYDEPT.equals(ms.getData().getString("method"))) {
                    if (isLoad) {
                        isLoad = false;
                        adapter.setDataTotalCount(0);
                    }
                    lvTaskInfo.setState(PaginationListView.SUCCESS);
                }else if(MethodEnum.POSTADDSTOCK.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"数据提交失败");
                }
            }
        });
        adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<StockInfo>() {
            @Override
            public void onItemClick(View view, StockInfo stockInfo, int position) {
                stockId = stockInfo.getID();
                status = 2;
                HashMap<String, Object> stockMap = new HashMap<String, Object>();
                stockMap.put("RFIDNo", stockInfo.getRFIDNo());
                InteractiveDataUtil.interactiveMessage(activity, stockMap, handlerUtils, MethodEnum.GETSTOCKINFOBYDEPTID, InteractiveEnum.GET);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        btnTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1;
                stockId = -1;
                device = ScanRfidDialog.showScanRfid(activity, "请扫描激活栏位标签", LabelRule.stockRule,
                        "扫描栏位标签有误", MethodEnum.GETSTORAGEINFOBYOUT, "RFID", handlerUtils);
            }
        });
    }
    @Override
    public String[] getArrayTitle() {
        return new String[]{"栏位名称","序列号","栏位最大数","创建时间"};
    }
    private void loadData(int pageIndex, int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        map.put("StorkName","");    //栏位名称
        map.put("Status",1);        //栏位状态
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity,map,handlerUtils,MethodEnum.GETSTOCKINFOBYDEPT,InteractiveEnum.GET,"" + pageIndex);
    }
}