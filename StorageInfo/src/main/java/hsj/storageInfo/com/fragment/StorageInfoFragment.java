package hsj.storageInfo.com.fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.view.dateTime.SelectDateTime;
import com.kymjs.app.base_res.utils.view.expandlib.ExpandLayout;
import com.kymjs.app.base_res.utils.view.expandlib.LogUtils;
import com.lwy.paginationlib.PaginationListView;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;
import hsj.storageInfo.com.R;
import hsj.storageInfo.com.R2;
import hsj.storageInfo.com.entry.StorageInfo;
import hsj.storageInfo.com.entry.StorageInfoList;
/**
 * Created by Administrator on 2019/2/22/022.
 */
/***
 * 库存查询页面
 */
public class StorageInfoFragment extends BaseFragment {

    @BindView(R2.id.layout_comprehensive_query)
    LinearLayout layoutComprehensiveQuery;
    @BindView(R2.id.sp_storage_info_status)
    Spinner spStorageInfoStatus;
    @BindView(R2.id.sp_storage_info_enable_status)
    Spinner spStorageInfoEnableStatus;
    @BindView(R2.id.sp_storage_info_stock)
    Spinner spStorageInfoStock;
    @BindView(R2.id.et_storage_info_serialNo)
    EditText etStorageInfoSerialNo;
    @BindView(R2.id.tv_storage_info_outTime)
    TextView tvStorageInfoOutTime;
    @BindView(R2.id.tv_storage_info_inTime)
    TextView tvStorageInfoInTime;
    //伸缩高度
    private int measuredHeight;
    @BindView(R2.id.expand)
    ExpandLayout expand;
    @BindView(R2.id.ll_tag_btn)
    LinearLayout layoutTagBtn;
    @BindView(R2.id.lv_storage_info)
    PaginationListView lvStorageInfo;
    private int twoLineTagHeight;
    private static final int TAG_LAYOUT_FINISH = 521;
    @BindView(R2.id.iv_expand)
    ImageView ivExpand;
    HandlerUtils handlerUtils;
    PaginationListView.Adapter<StorageInfoList> adapter;
    boolean loadNumber=true;
    @Override
    protected int getLayoutResource() {
        return R.layout.storageinfo_fragment;
    }
    @Override
    protected void initView() {

        initExpend();
        adapter = new PaginationListView.Adapter(20,activity,-1,"SerialNo","BirthTime","StorageStatusName","IsEnabledName","ProductName");
        lvStorageInfo.setAdapter(adapter);
        layoutTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("点击事件");
                expand.toggleExpand();
            }
        });
        //设置动画时间
        expand.setAnimationDuration(300);
        //折叠或者展开操作后的监听
        expand.setOnToggleExpandListener(new ExpandLayout.OnToggleExpandListener() {
            @Override
            public void onToggleExpand(boolean isExpand) {
                if (isExpand) {
                    ivExpand.setBackgroundResource(R.mipmap.icon_btn_collapse);
                } else {
                    ivExpand.setBackgroundResource(R.mipmap.icon_btn_expand);
                }
            }
        });
        initSpinnerInfo();
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                //获取综合查询信息
                if (MethodEnum.GETSTORAGEINFOBYDEPTID.equals(msg.getData().getString("method"))) {
                    StorageInfo storageInfo = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), StorageInfo.class);
                    if (storageInfo != null) {
                        Log.d("JSONResult", "handlerExecutionFunction: " + msg.getData().getString("result") + "     " + storageInfo.getResult());
                        if (loadNumber) {
                            loadNumber = false;
                            adapter.setDataTotalCount(storageInfo.getRowsCount());
                        }
                        adapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), storageInfo.getResult());
                        lvStorageInfo.setState(PaginationListView.SUCCESS);
                    }
                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                //无返回结果
                if (MethodEnum.GETSTORAGEINFOBYDEPTID.equals(ms.getData().getString("method"))) {
                        if (loadNumber) {
                            loadNumber = false;
                            adapter.setDataTotalCount(0);
                        }
                        adapter.setDatas(Integer.parseInt(ms.getData().getString("bindDate")), new ArrayList<StorageInfoList>());
                        lvStorageInfo.setState(PaginationListView.SUCCESS);
                }
            }
        });
        SelectDateTime.initCurrentTime( tvStorageInfoInTime);
        SelectDateTime.initCurrentTime(tvStorageInfoOutTime);
    }
    @Override
    protected boolean isLoad() {
        return true;
    }

    private void initSpinnerInfo() {

        HashMap<String, Object> storageInfoEnableStatusMap = new HashMap<>();
        storageInfoEnableStatusMap.put("groupName", "StorageIsEnabled");
        SpinnerTools.change(activity, spStorageInfoEnableStatus,
                storageInfoEnableStatusMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", null);
        HashMap<String, Object> storageInfoStatusMap = new HashMap<>();
        storageInfoStatusMap.put("groupName", "StorageStatus");
        SpinnerTools.change(activity, spStorageInfoStatus,
                storageInfoStatusMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", null);
        SpinnerTools.change(activity,spStorageInfoStock,null,MethodEnum.GETSTOCKINFOBYDEPTID, DicInfo.class, "Name", "Value", null);
        //分页触发事件
        lvStorageInfo.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                    changeData(currentPagePosition+1, perPageCount);
            }
            @Override
            public void onPerPageCountChanged(int perPageCount) {
            }
        });
    }
    private void initExpend() {
        layoutComprehensiveQuery.post(new Runnable() {
            @Override
            public void run() {
                measuredHeight = layoutComprehensiveQuery.getMeasuredHeight();
                LogUtils.d("flowView--获取内容布局---" + measuredHeight);
                handler.sendEmptyMessage(TAG_LAYOUT_FINISH);
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TAG_LAYOUT_FINISH:
                    //这个是三行的高度，实际开发中看UI设置的高度，这里仅仅是假设操作
                    twoLineTagHeight = dip2px(activity, 95.0f);
                    if (measuredHeight > twoLineTagHeight) {
                        //如果大于三行，则显示折叠
                        layoutTagBtn.setVisibility(View.VISIBLE);
                        expand.initExpand(false, twoLineTagHeight);
                    } else {
                        //如果小于或者等于三行，则不显示折叠控件
                        layoutTagBtn.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @OnClick({R2.id.tv_storage_info_outTime, R2.id.tv_storage_info_inTime,R2.id.btn_storage_info_select})
    public void onViewClicked(View view) {
        RCaster rcaster = new RCaster(R.class,R2.class);
        switch (rcaster.cast(view.getId())) {
            case R2.id.tv_storage_info_outTime:
             SelectDateTime.selectDate(tvStorageInfoOutTime,activity);
                break;
            case R2.id.tv_storage_info_inTime:
                SelectDateTime.selectDate(tvStorageInfoInTime,activity);
                break;
            case R2.id.btn_storage_info_select:
                loadNumber=true;
                adapter.clear();
                changeData(adapter.getCurrentPagePos(),adapter.getPerPageCount());
                break;
        }
    }
    public void changeData(int pageIndex,int pageSize){
        HashMap<String,Object> storageInfoSelectMap = new HashMap<>();
        if(spStorageInfoStatus.getTag()!=null)
        storageInfoSelectMap.put("status",spStorageInfoStatus.getTag().toString());
        if(spStorageInfoEnableStatus.getTag()!=null)
        storageInfoSelectMap.put("IsEnabled",spStorageInfoEnableStatus.getTag().toString());
        storageInfoSelectMap.put("startTime",tvStorageInfoInTime.getText().toString());
        storageInfoSelectMap.put("endTime",tvStorageInfoOutTime.getText().toString());
        storageInfoSelectMap.put("LotNumber",etStorageInfoSerialNo.getText().toString());
        if(spStorageInfoStock.getTag()!=null)
        storageInfoSelectMap.put("StockID",spStorageInfoStock.getTag().toString());
        storageInfoSelectMap.put("pageIndex",pageIndex);
        storageInfoSelectMap.put("pageSize",pageSize);
        InteractiveDataUtil.interactiveMessage(activity,storageInfoSelectMap,handlerUtils,MethodEnum.GETSTORAGEINFOBYDEPTID, InteractiveEnum.GET,""+pageIndex);
    }
}
