package hsj.expmle.com.distribute;
import android.view.View;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.utils.Utils;
import hsj.expmle.com.distribute.activity.DistributeActivity;
/** 分发模块
 * Created by 16486 on 2020/10/20.
 */
public class DistributeFragment extends BaseresTaskFragment {




    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }
    @Override
    protected String setBtnTaskAdd() {
        return "新增分发";
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return true;
    }

    @Override
    protected String[] getTaskDataList() {
        return new String[]{"ID","TaskTypeName", "CreatorTime","OpDeptName","Num","UserName"};
    }


    @Override
    public void initFragmentActivityView() {
       btnTaskAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   Utils.gotoActivity(getActivity(), DistributeActivity.class, null, null);
           }
       });
    }

    @Override
    public String[] getArrayTitle() {
        return new String[]{"ID","任务类型","创建时间","给予分发部门","分发数量","操作人"};
    }



}
