package com.kymjs.app.base_res.utils.Activity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.Task;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskInfo;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskInventoryInfo;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16486 on 2020/12/5.
 */

public class TaskDetailListActivity extends BaseActivity {
    private TextView taskId;            //任务编号
    private TextView taskName;          //任务类型名
    private TextView taskContent;       //任务描述
    private TextView taskCreatorName;   //创建人名
    private TextView CreatorTime;       //创建时间
    private TextView taskStatus;        //任务状态名
    private TextView remark;            //备注

    private TextView tvDetailListSize; //当前处理数量

    private Button btnBack;             //返回上一页


    private ListView taskPickInfoList;

    private LinearLayout layoutTaskInfoPack;

    HandlerUtils handlerUtils;

    int taskType;


    private void initUI() {
        layoutTaskInfoPack = findViewById(R.id.layout_task_info_pick);
        taskPickInfoList = findViewById(R.id.lv_task_pick_info_list);

        taskId = findViewById(R.id.tv_task_id);
        taskName = findViewById(R.id.tv_task_name);
        taskContent = findViewById(R.id.tv_task_content);
        taskCreatorName = findViewById(R.id.tv_task__creator_name);
        CreatorTime = findViewById(R.id.tv_task_creator_time);
        taskStatus = findViewById(R.id.tv_task_status);
        remark =findViewById(R.id.tv_remark);
        btnBack=findViewById(R.id.btn_task_back);
        tvDetailListSize = findViewById(R.id.tv_task_detail_list_size);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*********************
     * 子类实现
     *****************************/
    @Override
    public int getLayoutId() {
        return R.layout.baseres_task_detail_list_activity;
    }

    @Override
    public void initPresenter() {
        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if (MethodEnum.GETTASKINFO.equals( msg.getData().getString("method"))){
                    boolean isShowInventory = false;
                    String [] param=null;
                    //3、耳标入栏 4、耳标出栏 5屠宰场入库
                    if(taskType==3 || taskType==4 || taskType==5) {
                        isShowInventory = true;
                        param = new String[]{"TaskDetailInfoID", "RfidNo", "SerialNo", "ProductName", "DeptName"};
                    //1、库存导入 2、畜种耳标分发 6、栏位标签分发
                    }else if(taskType==1 || taskType==2 || taskType == 6) {

                        param = new String[]{"StorageID","RfidNo","SerialNo","ProjectName","opDeptID"};
                        isShowInventory=false;
                    }else if(taskType==8){
                        param = new String[]{"ID","TaskTypeName","CretaeDeptName","OpDeptName","CreatorTime"};


                    }
                    final Task taskInfo = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"),
                            isShowInventory?TaskInventoryInfo.class : TaskInfo.class );






                    if(taskInfo!=null){

                       /*      taskPickInfoList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else if(){
                            tagList = new ArrayList<TaskInventoryInfo.TaskDetailInfo>();
                            adapter = new AutoAdapter<>(mContext, tagList, 20, "TaskDetailInfoID", "RfidNo", "SerialNo", "ProductName", "DeptName");
                            taskPickInfoList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }*/



                        taskId.setText(taskInfo.getID()+"");
                        taskName.setText(taskInfo.getTaskTypeName());
                        taskContent.setText(taskInfo.getContent());
                        taskCreatorName.setText(taskInfo.getCreatorName());
                        CreatorTime.setText(taskInfo.getCreatorTime());
                        taskStatus.setText(taskInfo.getStatusName());
                        remark.setText(taskInfo.getRemark());

                        initAdapter( taskInfo,param);


                    }
                }
            }
        });
    }
/* "TaskDetailInfoID", "RfidNo", "SerialNo", "ProductName", "DeptName"*/

    public   void initAdapter( Task task , String[] param){
        if(task instanceof TaskInventoryInfo ){
            List<  TaskInventoryInfo.TaskDetailInfo  > tagList= new ArrayList<>();
            AutoAdapter< TaskInventoryInfo.TaskDetailInfo >  adapter = new AutoAdapter<>(mContext, tagList,param);
            taskPickInfoList.setAdapter(adapter);
            TaskInventoryInfo taskInventoryInfo = (TaskInventoryInfo) task;

            if(taskInventoryInfo.getTaskDetailList()!=null) {
                //判断是否筛选已在库的数据
             boolean isScreen=false;

                if(taskType==4){
                if( getIntent().getExtras().getInt("status")==1){
                    isScreen=true;
                }

                }
                if(!isScreen) {
                    tagList.addAll(taskInventoryInfo.getTaskDetailList());
                    tvDetailListSize.setText(""+(taskInventoryInfo.getTaskDetailList()!=null ? taskInventoryInfo.getTaskDetailList().size():0));

                }else{
                      List< TaskInventoryInfo.TaskDetailInfo> itemsList = new ArrayList<>();
                    for ( TaskInventoryInfo.TaskDetailInfo temp: taskInventoryInfo.getTaskDetailList()){
                        if(Integer.parseInt(temp.getStatus())==2){
                            itemsList.add(temp);
                        }

                    }
                    tagList.addAll(itemsList);
                    tvDetailListSize.setText(itemsList.size()+"");

                }
                adapter.notifyDataSetChanged();
             }
        }else if(task instanceof  TaskInfo){
            List<  TaskInfo.TaskDetailInfo  > tagList= new ArrayList<>();
            AutoAdapter< TaskInfo.TaskDetailInfo >  adapter = new AutoAdapter<>(mContext, tagList,param);
            taskPickInfoList.setAdapter(adapter);
            TaskInfo taskInfo = (TaskInfo) task;
            if(taskInfo.getTaskDetailList()!=null) {
                tagList.addAll(taskInfo.getTaskDetailList());
                adapter.notifyDataSetChanged();
                tvDetailListSize.setText(""+(taskInfo.getTaskDetailList()!=null ? taskInfo.getTaskDetailList().size():0));

            }
        }



    }

    @Override
    public void initView() {
        initUI();
        taskType = getIntent().getExtras().getInt("TaskTypeId");

        HashMap<String,Object> taskMap = new HashMap<>();
        taskMap.put("ID", getIntent().getExtras().getInt("ID"));
        InteractiveDataUtil.interactiveMessage(this,taskMap,handlerUtils, MethodEnum.GETTASKINFO, InteractiveEnum.GET);
    }


}
