package hsj.instock.com;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.dialog.stockDialog.StockDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;
import com.lwy.paginationlib.PaginationListView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import devicelib.dao.Device;
import hsj.instock.com.activity.ScanInOrMoveStockActivity;

/** 入栏管理
 * Created by 16486 on 2020/11/6.
 */

public class InStockInfoFragment extends BaseFragment {

    //入库列表
    @BindView(R2.id.lv_in_stock_info)
    PaginationListView lvInStock;


    HandlerUtils handlerUtils;


    private StockDialog.Builder builder;
    private StockDialog mDialog;

    Device device;

    @Override
    protected int getLayoutResource() {
        return R.layout.instock_info_fragment;
    }

    @Override
    protected void initView() {
        builder = new StockDialog.Builder(activity);

        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.GETSTOCKINFOBYDEPTID.equals(msg.getData().getString("method"))){
                    if(device!=null){
                        device.destroy();
                    }
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

    }

    @Override
    protected boolean isLoad() {
        return true;
    }


    @OnClick({R2.id.btn_add_in_stock})
    public void onViewClicked(View view) {
        RCaster rcaster = new RCaster(R.class,R2.class);
        switch (rcaster.cast(view.getId())){
            case R2.id.btn_add_in_stock:
                //显示扫描栏位
                device =  ScanRfidDialog.showScanRfid(activity,
                        "请扫描栏位标签",LabelRule.stockRule,"栏位标签数据异常", MethodEnum.GETSTOCKINFOBYDEPTID,"RFIDNo",handlerUtils);
                break;

        }

    }

}
