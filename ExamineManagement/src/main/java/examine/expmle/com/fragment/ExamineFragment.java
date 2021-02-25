package examine.expmle.com.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kymjs.app.base_res.utils.Activity.TaskDetailListActivity;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskData;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.lwy.paginationlib.PaginationListView;

import examine.expmle.com.R;
import examine.expmle.com.activity.ExamineActivity;

/** 签收页面
 * Created by 16486 on 2021/2/23.
 */
public class ExamineFragment extends BaseresTaskFragment {
    @Override
    public void initFragmentActivityView() {
        adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<TaskData.TaskDataList>() {
            @Override
            public void onItemClick(View view, TaskData.TaskDataList taskDataList, int position) {
                /*当前任务状态 为未处理和处理中则跳转到签收处理页面*/
                if(taskDataList.getStatus()==2 || taskDataList.getStatus()==6) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId",taskDataList.getID());
                    Utils.gotoActivity(activity, ExamineActivity.class,bundle,null);
                /*其他跳转到任务详情页面*/
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", taskDataList.getID());
                    bundle.putInt("TaskTypeId", taskDataList.getTaskType());
                    Utils.gotoActivity(activity, TaskDetailListActivity.class, bundle, null);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public String[] getArrayTitle() {
        return       new String[]{"ID","创建时间","箱包数量","创建人","任务状态"};
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

    @Override
    protected String setBtnTaskAdd() {
        return null;
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return true;
    }

    @Override
    protected String[] getTaskDataList() {
        return
                new String[]{"ID", "CreatorTime","Num","UserName","StatusName"};
    }
}
