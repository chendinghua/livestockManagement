package hsj.expmle.com.prevention;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.entry.vaccine.VaccineInfo;
import com.kymjs.app.base_res.utils.base.entry.vaccine.VaccineInfoResult;
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
import hsj.expmle.com.prevention.activity.PreventionActivity;

import com.kymjs.app.base_res.R;

/** 疫苗模块
 * Created by 16486 on 2020/10/20.
 */
public class PreventionFragment extends BaseresTaskFragment {


    PaginationListView.Adapter<VaccineInfo> adapter;

    HandlerUtils handlerUtils;


    boolean isLoadData=true;
    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

   /* @Override
    protected void initView() {
            activity = getActivity();
            tvStartTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
            tvEndTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
    }*/

    @Override
    public void initFragmentActivityView() {
        adapter = new PaginationListView.Adapter(20, activity, -1, "StorageCount","VaccineName","ProductName");
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
                if(MethodEnum.POSTVACCINELIST.equals(msg.getData().getString("method"))){
                    VaccineInfoResult vaccineInfoList =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), VaccineInfoResult.class);
                    if(vaccineInfoList!=null){
                        if(isLoadData){
                            isLoadData=false;
                            adapter.setDataTotalCount( vaccineInfoList.getRowsCount());
                        }
                        adapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), vaccineInfoList.getResult());
                        lvTaskInfo.setState(PaginationListView.SUCCESS);
                    }

                }
            }
        });

        adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<VaccineInfo>() {
            @Override
            public void onItemClick(View view, VaccineInfo vaccineInfo, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("VaccineID",vaccineInfo.getVaccineID());
                Utils.gotoActivity(activity, PreventionActivity.class,bundle,null);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        btnTaskAdd.setVisibility(View.GONE);

        btnTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.gotoActivity(activity, PreventionActivity.class,null,null);
            }
        });
    }



    @Override
    public String[] getArrayTitle() {
        return new String[]{"处理数量","疫苗名称","畜种"};
    }

    private void loadData(int pageIndex, int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        map.put("DeptID", SPUtils.getSharedIntData(activity,"DeptID"));
        map.put("Day",SPUtils.getSharedIntData(activity,"PreventionDay",3));
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        InteractiveDataUtil.interactiveMessage(activity,map,handlerUtils, MethodEnum.POSTVACCINELIST, InteractiveEnum.GET,"" + pageIndex);

    }

    @Override
    protected String[] getTaskDataList() {
        return new String[0];
    }

    @Override
    protected String setBtnTaskAdd() {
        return "";
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return false;
    }


  /*  @OnClick({R2.id.btn_distribute_add})
    public void onClickListener(View v) {
        RCaster rcaster = new RCaster(R.class,R2.class);
        Log.d("fragmentOnClick:", "onClick: "+v.getId() +"    "+rcaster.cast(v.getId())+"           "+R2.id.btn_distribute_add);

        switch (rcaster.cast(v.getId())) {
            case R2.id.btn_distribute_add:
                Utils.gotoActivity(getActivity(), PreventionActivity.class,null,null);
                break;
            case R2.id.tv_put_out_start_time:
             //   datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
                selectTime(tvStartTime);
                break;
            case R2.id.tv_put_out_end_time:
                selectTime(tvEndTime);
                break;
        }
    }
    private void selectTime(final TextView tvTime){
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day) {
                tvTime.setText(year + "-" + month + "-" + day);

            }
        });
    }*/
}
