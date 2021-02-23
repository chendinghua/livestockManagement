package hsj.expmle.com.distribute;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hsj.expmle.com.distribute.activity.DistributeActivity;

/**
 * 分发模块
 * Created by 16486 on 2020/10/20.
 */
public class DistributeFragment extends BaseresTaskFragment {


    @BindView(R2.id.layout_task_bottom)
    LinearLayout layoutTaskBottom;
    Unbinder unbinder;
    @BindView(R2.id.btn_operation1)
    Button btnOperation1;
    @BindView(R2.id.btn_operation2)
    Button btnOperation2;
    Unbinder unbinder1;

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
        return new String[]{"ID", "TaskTypeName", "CreatorTime", "OpDeptName", "Num", "UserName"};
    }

    @Override
    public void initFragmentActivityView() {
        btnTaskAdd.setVisibility(View.GONE);
        layoutTaskBottom.setVisibility(View.VISIBLE);
        btnOperation1.setText("一次分发");
        btnOperation2.setText("二次分发");
        //判断当前账号区域等级大于市级 则隐藏二次分发
       if( SPUtils.getSharedIntData(activity,"Type")<=2){
           btnOperation2.setVisibility(View.GONE);
       }
        //判断当前是新增签收功能则修改一次分发名称为 ‘新增签收’
       if(Integer.parseInt( SPUtils.getSharedStringData(activity,"actionUrl"))==8){
           btnOperation1.setText("新增签收");
       }

     /*   btnTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.gotoActivity(getActivity(), DistributeActivity.class, null, null);
            }
        });*/
    }

    @Override
    public String[] getArrayTitle() {
        return new String[]{"ID", "任务类型", "创建时间", "给予分发部门", "分发数量", "操作人"};
    }


    @OnClick({R2.id.btn_operation1, R2.id.btn_operation2})
    public void onViewClicked(View view) {
        RCaster rCaster = new RCaster(R.class, R2.class);

        switch (rCaster.cast(view.getId())) {
            case R2.id.btn_operation1:
                SPUtils.setSharedIntData(activity,"OperationType",1);
                break;
            case R2.id.btn_operation2:
                SPUtils.setSharedIntData(activity,"OperationType",2);
                break;
        }
        Utils.gotoActivity(getActivity(), DistributeActivity.class, null, null);
    }


}
