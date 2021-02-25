package com.kymjs.app.base_res.utils.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.Activity.TaskDetailListActivity;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskData;
import com.kymjs.app.base_res.utils.baseDao.FragmentActivityBaseDao;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.lwy.paginationlib.PaginationListView;

import java.util.HashMap;

import butterknife.BindView;
import wang.relish.widget.vehicleedittext.VehicleKeyboardHelper;

/**
 * Created by 16486 on 2020/11/23.
 */

public abstract class BaseresTaskFragment extends BaseFragment implements FragmentActivityBaseDao{

     //标题列表
    LinearLayout layoutTaskAutoTitle;
    protected PaginationListView lvTaskInfo;
    //添加任务列表
    protected   Button btnTaskAdd;
    //筛选任务查询布局
    protected LinearLayout layoutQueryCriteria;
    //任务内容输入栏
    protected EditText etQueryCriteriaContent;
    //任务状态
    protected Spinner spQueryCriteriaTaskStatus;
    //任务查询按钮
    protected Button btnQueryCriteria;

    protected HandlerUtils baseHandlerUtils;

    protected PaginationListView.Adapter<TaskData.TaskDataList> adapter;

    public boolean   isLoadData  = true;
    @Override
    protected void initView() {
        layoutTaskAutoTitle = rootView.findViewById(R.id.layout_task_auto_title);
        lvTaskInfo = rootView.findViewById(R.id.lv_task_info);
        btnTaskAdd =  rootView.findViewById(R.id.btn_task_add);
        layoutQueryCriteria = rootView.findViewById(R.id.layout_query_criteria);
        etQueryCriteriaContent = rootView.findViewById(R.id.et_query_criteria_content);


        spQueryCriteriaTaskStatus = rootView.findViewById(R.id.sp_query_criteria_task_status);
        btnQueryCriteria = rootView.findViewById(R.id.btn_query_criteria);
        layoutTaskAutoTitle.removeAllViews();
        String [] titles = getArrayTitle();
        if(titles.length>0) {
            layoutTaskAutoTitle.setVisibility(View.VISIBLE);
            for (int i = 0; i < titles.length; i++) {
                TextView tvTitle = new TextView(activity);
                tvTitle.setText(titles[i]);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                tvTitle.setLayoutParams(lp);
                tvTitle.setGravity(Gravity.CENTER);
                tvTitle.setMaxLines(2);
                tvTitle.setEllipsize(TextUtils.TruncateAt.END);
                layoutTaskAutoTitle.addView(tvTitle);
            }
        }else{
            layoutTaskAutoTitle.setVisibility(View.GONE);
        }


        if(isShowQueryCriteria()){
            //获取任务状态下拉列表
            HashMap<String,Object> dicMap = new HashMap<>();
            dicMap.put("groupName","TaskStatus");
            SpinnerTools.change(activity,spQueryCriteriaTaskStatus,dicMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class,"Name", "Value",null,true);
            layoutQueryCriteria.setVisibility(View.VISIBLE);

            adapter = new PaginationListView.Adapter(1, activity, -1,getTaskDataList());
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
            baseHandlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
                @Override
                public void handlerExecutionFunction(Message msg) {
                    if(MethodEnum.GETTASKLIST.equals(msg.getData().getString("method"))){
                        TaskData taskData =   JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),TaskData.class);
                        if(taskData!=null){
                            if(isLoadData){
                                isLoadData=false;
                                adapter.setDataTotalCount( taskData.getRowsCount());
                            }
                            adapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), taskData.getResult());
                            lvTaskInfo.setState(PaginationListView.SUCCESS);
                        }
                    }
                }
            });
            btnQueryCriteria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            isLoadData = true;
                            loadData(adapter.getCurrentPagePos(),adapter.getPerPageCount());
                }
            });

            adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<TaskData.TaskDataList>() {
                @Override
                public void onItemClick(View view, TaskData.TaskDataList taskDataList, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID",taskDataList.getID());
                    bundle.putInt("TaskTypeId",taskDataList.getTaskType());
                    Utils.gotoActivity(activity, TaskDetailListActivity.class,bundle,null);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });


        }else {
            layoutQueryCriteria.setVisibility(View.GONE);
        }

        //添加动态列表
        initFragmentActivityView();
    }

    @Override
    public void onResume() {
        isLoadData=true;
        if(isShowQueryCriteria()) {
            loadData(adapter.getCurrentPagePos(), adapter.getPerPageCount());
        }
        //判断当前返回添加的数据为空  则隐藏按钮
        if(setBtnTaskAdd()==null){
            btnTaskAdd.setVisibility(View.GONE);
        }else {
            btnTaskAdd.setVisibility(View.VISIBLE);
            btnTaskAdd.setText(setBtnTaskAdd());
        }
        super.onResume();
    }
    //预加载显示数据
    @Override
    protected boolean isLoad() {
        return true;
    }
    //当前添加按钮  如果数据为空则不显示按钮
    protected abstract String  setBtnTaskAdd();
    //是否显示筛选布局
    protected abstract boolean isShowQueryCriteria();

    private void loadData(int pageIndex, int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        if( "hsj.expmle.com.distribute.DistributeFragment".equals(SPUtils.getSharedStringData(activity,"ActionForm"))){

            map.put("taskType",2);
        }else {
            map.put("taskType", SPUtils.getSharedStringData(activity, "actionUrl"));
        }
           map.put("status",spQueryCriteriaTaskStatus.getTag());
        map.put("DeptID",SPUtils.getSharedIntData(activity,"DeptID"));
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity,map,baseHandlerUtils, MethodEnum.GETTASKLIST, InteractiveEnum.GET,"" + pageIndex);
    }

    protected  abstract String [] getTaskDataList();

}
