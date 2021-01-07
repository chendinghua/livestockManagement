package hsj.instock.com;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskData;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.dialog.stockDialog.StockDialog;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;
import com.lwy.paginationlib.PaginationListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hsj.instock.com.activity.MoveStockInfoResultActivity;
import hsj.instock.com.activity.ScanInOrMoveStockActivity;
import hsj.instock.com.entry.MoveStockTaskInfo;

/** 入栏管理、移栏管理
 * Created by 16486 on 2020/11/6.
 */

public class InStockInfoFragment extends BaseresTaskFragment implements View.OnClickListener {
    HandlerUtils handlerUtils;
    private StockDialog.Builder builder;
    private StockDialog mDialog;

    boolean isMoveLoadData=true;

    PaginationListView.Adapter<MoveStockTaskInfo.MoveStockTaskInfoList>  moveAdapter;

    List<MoveStockTaskInfo> moveTagList = new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }



    @Override
    public void initFragmentActivityView() {
        builder = new StockDialog.Builder(activity);

        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.GETSTOCKINFOBYDEPTID.equals(msg.getData().getString("method"))){

                    final List<StockInfo> stockInfos = JSON.parseArray( JSON.parseObject(msg.getData().getString("result")).getString("Data"),StockInfo.class);

                    if(stockInfos.size()>0) {
                        mDialog = builder.setMessage("请确认栏位信息").
                                initStockInfo(stockInfos.get(0))
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                        //页面跳转，并且传递栏位信息
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("stockInfo", stockInfos.get(0));
                                        startActivity(ScanInOrMoveStockActivity.class, bundle);
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
                    }else{
                        UIHelper.ToastMessage(activity,"栏位标签有误");
                    }
                    //获取移栏列表数据
                }else if(MethodEnum.GETMOVESTOCKLIST.equals(msg.getData().getString("method"))){
                    MoveStockTaskInfo taskData =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),MoveStockTaskInfo.class);
                    if(taskData!=null){
                        if(isMoveLoadData){
                            isMoveLoadData=false;
                            moveAdapter.setDataTotalCount( taskData.getRowsCount());
                        }
                        moveAdapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), taskData.getResult());
                        lvTaskInfo.setState(PaginationListView.SUCCESS);
                    }
                }
            }
        });
        btnTaskAdd.setOnClickListener(this);
        //判断如果是移栏则重新调用移栏接口
            if(Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))!=3){
                moveAdapter = new PaginationListView.Adapter<>(1,activity,-1,"ID","StockName","OpTime","Num","UserName");
                lvTaskInfo.setAdapter(moveAdapter);
                etQueryCriteriaContent.setHint("请输入操作人名");
                SpinnerTools.change(activity,spQueryCriteriaTaskStatus,null,MethodEnum.GETSTOCKINFOBYDEPTID, StockInfo.class, "Name", "ID", null,true);
                moveLoadData(moveAdapter.getCurrentPagePos(), moveAdapter.getPerPageCount());

                lvTaskInfo.setListener(new PaginationListView.Listener() {
                    @Override
                    public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                        moveLoadData(currentPagePosition + 1, perPageCount);
                    }

                    @Override
                    public void onPerPageCountChanged(int perPageCount) {

                    }
                });
                //移栏点击列表
                moveAdapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<MoveStockTaskInfo.MoveStockTaskInfoList>() {
                    @Override
                    public void onItemClick(View view, MoveStockTaskInfo.MoveStockTaskInfoList item, int position) {

                        Bundle bundle  = new Bundle();
                        bundle.putSerializable("MoveStockInfo",item);
                        Utils.gotoActivity(activity, MoveStockInfoResultActivity.class,bundle,null);

                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });

                btnQueryCriteria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isMoveLoadData=true;
                        moveLoadData(moveAdapter.getCurrentPagePos(), moveAdapter.getPerPageCount());
                    }
                });
            }
    }

    @Override
    public String[] getArrayTitle() {
        return
                Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))==3?
                new String[]{"ID","创建时间","数量","操作人"}:
                        new String []{"ID","栏位名称","操作时间","数量","操作人"};
    }





    @Override
    public void onClick(View view) {
       if(view.getId() == btnTaskAdd.getId()) {
           //显示扫描栏位
            ScanRfidDialog.showStockInfoList(activity,
                   "请扫描栏位标签",
                   MethodEnum.GETSTOCKINFOBYDEPTID, "StockID", handlerUtils);
       }
    }

    @Override
    protected String setBtnTaskAdd() {
        return Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))==3?"新增入栏":"操作移栏";
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return    true;
    }

    @Override
    protected String[] getTaskDataList() {
        return
                new String[]{"ID", "CreatorTime","Num","UserName"};

    }
    //显示移栏数据
    private void moveLoadData(int pageIndex, int pageSize){

        HashMap<String,Object> map = new HashMap<>();
        map.put("RFIDNo","");
        map.put("SerailNo","");
        map.put("StockID",  spQueryCriteriaTaskStatus.getTag()!=null? spQueryCriteriaTaskStatus.getTag():-1);
        map.put("UserName",etQueryCriteriaContent.getText().toString());
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity,map,handlerUtils, MethodEnum.GETMOVESTOCKLIST, InteractiveEnum.GET,"" + pageIndex);
    }
}
