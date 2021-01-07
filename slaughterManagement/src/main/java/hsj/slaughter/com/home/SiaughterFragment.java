package hsj.slaughter.com.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kymjs.app.base_res.utils.Activity.TaskDetailListActivity;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskData;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.lwy.paginationlib.PaginationListView;

import hsj.slaughter.com.home.activity.SlaughterScanActivity;

/**  屠宰场管理
 * Created by 16486 on 2020/12/9.
 */

public class SiaughterFragment extends BaseresTaskFragment {
    @Override
    public void initFragmentActivityView() {
            btnTaskAdd.setVisibility(View.GONE);
        adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<TaskData.TaskDataList>() {
            @Override
            public void onItemClick(View view, TaskData.TaskDataList taskDataList, int position) {
                //用户点击的任务是需处理的任务
                if(taskDataList.getStatus()==2){
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId",taskDataList.getID());
                    Utils.gotoActivity(activity,SlaughterScanActivity.class,bundle,null);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID",taskDataList.getID());
                    bundle.putInt("TaskTypeId",taskDataList.getTaskType());
                    bundle.putInt("status",taskDataList.getStatus());
                    Utils.gotoActivity(activity, TaskDetailListActivity.class,bundle,null);
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
        return new String[]{"ID","任务类型","创建时间","数量","操作人","状态"};
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;

    }

    @Override
    protected String setBtnTaskAdd() {
        return "";
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return true;
    }

    @Override
    protected String[] getTaskDataList() {
        return new String[]{"ID","TaskTypeName", "CreatorTime","Num","UserName","StatusName"};
    }
}
