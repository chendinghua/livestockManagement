package hsj.packing.com.fragment;
import android.os.Message;
import android.view.View;
import com.alibaba.fastjson.JSON;
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
import hsj.packing.com.activity.PickingActivity;
/**  箱包管理
 * Created by 16486 on 2020/12/31.
 */
public class PackingFragment extends BaseresTaskFragment  implements View.OnClickListener{
    private  PaginationListView.Adapter<PackingTask.PackingTaskItems> packAdapter;
  //  Device device;
    @Override
    public void initFragmentActivityView() {
        btnTaskAdd.setOnClickListener(this);
        btnQueryCriteria.setOnClickListener(this);
        spQueryCriteriaTaskStatus.setVisibility(View.GONE);
        etQueryCriteriaContent.setHint("请输入箱号");
        layoutQueryCriteria.setVisibility(View.VISIBLE);
        packAdapter = new PaginationListView.Adapter(20, activity, -1, "Id", "Code", "CreateDate", "TypeName", "UserName");
        lvTaskInfo.setAdapter(packAdapter);
        lvTaskInfo.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                loadData(currentPagePosition + 1, perPageCount);
            }
            @Override
            public void onPerPageCountChanged(int perPageCount) {
            }
        });
       /* device =new DeviceFactory(activity, new ResponseHandlerInterface() {
            @Override
            public void handleTagdata(String rfid) {
            }
            @Override
            public void handleTriggerPress(boolean pressed) {
            }
            @Override
            public void scanCode(String code) {
            }
        }).getDevice();
        device.onPause();*/
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
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == btnTaskAdd.getId()) {
            Utils.gotoActivity(activity, PickingActivity.class,null,null);
        }else if(v.getId() == btnQueryCriteria.getId()){
            isLoadData=true;
            loadData(packAdapter.getCurrentPagePos(), packAdapter.getPerPageCount());
        }
    }
    HandlerUtils handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
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
            }
        }
    });


    private void loadData(int pageIndex, int pageSize){
        HashMap<String,Object> packingMap = new HashMap<>();
        packingMap.put("Code",etQueryCriteriaContent.getText());
        packingMap.put("Type",SPUtils.getSharedStringData(activity,"actionUrl"));
        packingMap.put("pageIndex", pageIndex);
        packingMap.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity,packingMap,handlerUtils,MethodEnum.GETQELBOXPACKAGE, InteractiveEnum.GET,"" + pageIndex);



    }
}
