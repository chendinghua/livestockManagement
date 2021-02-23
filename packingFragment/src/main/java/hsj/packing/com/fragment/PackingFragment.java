package hsj.packing.com.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.packing.PackingTask;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.lwy.paginationlib.PaginationListView;
import java.util.HashMap;
import java.util.List;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import hsj.packing.com.activity.PickingActivity;
import hsj.packing.com.entry.PackInfo;
import hsj.packing.com.entry.PickingItemInfo;
/**  箱包管理
 * Created by 16486 on 2020/12/31.
 */
public class PackingFragment extends BaseresTaskFragment  implements View.OnClickListener{
    private  PaginationListView.Adapter<PackingTask.PackingTaskItems> packAdapter;
    Device device;
    @Override
    public void initFragmentActivityView() {
        btnTaskAdd.setOnClickListener(this);
        btnQueryCriteria.setOnClickListener(this);
        spQueryCriteriaTaskStatus.setVisibility(View.GONE);
        etQueryCriteriaContent.setHint("请输入箱号");
        layoutQueryCriteria.setVisibility(View.VISIBLE);
        packAdapter = new PaginationListView.Adapter(20, activity, -1, "Id", "Code", "CreateDate", "TypeName", "UserName");
        lvTaskInfo.setAdapter(packAdapter);
        //分页控件选项事件
        lvTaskInfo.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                loadData(currentPagePosition + 1, perPageCount);
            }
            @Override
            public void onPerPageCountChanged(int perPageCount) {
            }
        });
        //点击查询箱包查询信息
        packAdapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<PackingTask.PackingTaskItems>(){
            @Override
            public void onItemClick(View view, PackingTask.PackingTaskItems item, int position) {
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("ID",item.getId());
                map.put("Type",SPUtils.getSharedStringData(activity,"actionUrl"));
                map.put("Code",item.getCode());
                map.put("QelType",1);
                InteractiveDataUtil.interactiveMessage(activity,map,handlerUtils,MethodEnum.GETBOXPACKAGEINFO,InteractiveEnum.GET);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    //重新初始化物理按键触发扫描二维码模块
    @Override
    public void onResume() {
        device =new DeviceFactory(activity, new ResponseHandlerInterface(){
            @Override
            public void handleTagdata(String rfid) {
            }
            @Override
            public void handleTriggerPress(boolean pressed) {
            }
            @Override
            public void scanCode(String code) {
                etQueryCriteriaContent.setText(code);
            }
        }).getDevice();
        device.initUHF();
        device.onPause();
        rootView.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Log.d("onKeyCode", "onKey: " + i);
                    device.onKeyDown(i, keyEvent, 2, false);
                    return true;
                }
                return false;
            }
        });
        loadData(packAdapter.getCurrentPagePos(), packAdapter.getPerPageCount());
        super.onResume();
    }
    @Override
    public void onDestroy() {
        if(device!=null)
            device.destroy();
        super.onDestroy();
    }
    @Override
    public String[] getArrayTitle() {
        return new String[]{"ID","编号","创建时间","箱包类型","创建人"};
    }
    @Override
    protected int getLayoutResource() {
        return com.kymjs.app.base_res.R.layout.baseres_task_fragment;
    }
    @Override
    protected String setBtnTaskAdd() {
        return Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))==1?"新增包号":"新增箱号";
    }
    @Override
    protected boolean isShowQueryCriteria() {
        return false;
    }
    @Override
    protected String[] getTaskDataList() {
        return new String[0];
    }
    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //新增箱包信息
        if(v.getId() == btnTaskAdd.getId()) {
            Utils.gotoActivity(activity, PickingActivity.class,null,device);
            //查询箱包信息列表
        }else if(v.getId() == btnQueryCriteria.getId()){
            isLoadData=true;
            loadData(packAdapter.getCurrentPagePos(), packAdapter.getPerPageCount());
        }
    }
    HandlerUtils handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
            //初始化箱包信息列表
            if(MethodEnum.GETQELBOXPACKAGE.equals(msg.getData().getString("method"))){
                PackingTask packingTask =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackingTask.class);
                if(packingTask!=null){
                    if(isLoadData){
                        isLoadData=false;
                        packAdapter.setDataTotalCount( packingTask.getRowsCount());
                    }
                    packAdapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), packingTask.getResult());
                    lvTaskInfo.setState(PaginationListView.SUCCESS);
                }
                //查看箱包信息详情
            }else if(MethodEnum.GETBOXPACKAGEINFO.equals(msg.getData().getString("method"))){
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //箱号信息列表详情
                if(Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))==1) {
                    LinearLayout linearLayout = new LinearLayout(activity);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setLayoutParams(lp);
                    initLinerLayoutData(linearLayout, new String []{"库存id","RFID","序列号"});
                    builder.setCustomTitle(linearLayout);
                    List<PickingItemInfo> pickingItemInfos = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), PickingItemInfo.class);
                    AutoAdapter<PickingItemInfo> adapter = new AutoAdapter<>(activity, pickingItemInfos,
                            "StorageID", "RfidNo", "SerialNo");
                    builder.setAdapter(adapter,null);
                    //包号信息列表详情
                }else{
                    LinearLayout linearLayout = new LinearLayout(activity);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setLayoutParams(lp);
                    initLinerLayoutData(linearLayout,   new String []{"包号id","包号编号","标签数量","包号状态"});
                    builder.setCustomTitle(linearLayout);
                    List<PackInfo> packInfos = JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), PackInfo.class);
                    AutoAdapter<PackInfo> adapter = new AutoAdapter<>(activity, packInfos,
                            "ID", "BoxCode", "Num","StatusName");
                    builder.setAdapter(adapter,null);
                }
                builder.setCancelable(false);//不允许被某些方式取消,比如按对话框之外的区域或者是返回键
                builder.show();
            }
        }
    });
    /**
     * 初始化箱包信息信息标题
     * @param layout 标题控件
     * @param titles 标题内容
     */
    public void initLinerLayoutData(LinearLayout layout,String [] titles){
        for(String title:titles){
            TextView tvTitle = new TextView(activity);
            tvTitle.setText(title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            tvTitle.setLayoutParams(lp);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setMaxLines(2);
            tvTitle.setEllipsize(TextUtils.TruncateAt.END);
            layout.addView(tvTitle);
        }
    }

    /**
     * 初始化列表信息
     * @param pageIndex 当期页
     * @param pageSize 当前显示数量
     */
    private void loadData(int pageIndex, int pageSize){
        HashMap<String,Object> packingMap = new HashMap<>();
        packingMap.put("Code",etQueryCriteriaContent.getText());
        packingMap.put("Type",SPUtils.getSharedStringData(activity,"actionUrl"));
        packingMap.put("pageIndex", pageIndex);
        packingMap.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity,packingMap,handlerUtils,MethodEnum.GETQELBOXPACKAGE, InteractiveEnum.GET,"" + pageIndex);
    }
}
