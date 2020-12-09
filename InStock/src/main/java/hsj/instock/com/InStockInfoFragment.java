package hsj.instock.com;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.dialog.stockDialog.StockDialog;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;

import java.util.List;
import hsj.instock.com.activity.ScanInOrMoveStockActivity;

/** 入栏管理、移栏管理
 * Created by 16486 on 2020/11/6.
 */

public class InStockInfoFragment extends BaseresTaskFragment implements View.OnClickListener {
    HandlerUtils handlerUtils;
    private StockDialog.Builder builder;
    private StockDialog mDialog;
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
                }
            }
        });
        btnTaskAdd.setOnClickListener(this);

    }

    @Override
    public String[] getArrayTitle() {
        return new String[]{"ID","任务类型","创建时间","数量","操作人"};
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
        return true;
    }

    @Override
    protected String[] getTaskDataList() {
        return new String[]{"ID","TaskTypeName", "CreatorTime","Num","UserName"};
    }
}
