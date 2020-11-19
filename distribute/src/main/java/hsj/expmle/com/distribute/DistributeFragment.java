package hsj.expmle.com.distribute;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import hsj.expmle.com.distribute.activity.DistributeActivity;

/** 分发模块
 * Created by 16486 on 2020/10/20.
 */

public class DistributeFragment extends BaseFragment {

    @BindView(R2.id.btn_distribute_add)
    Button btnDistribute;

    @Override
    protected int getLayoutResource() {
        return R.layout.distribute_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean isLoad() {
        return true;
    }


    @OnClick({R2.id.btn_distribute_add})
    public void onClickListener(View v) {

        RCaster rcaster = new RCaster(R.class,R2.class);

        Log.d("fragmentOnClick:", "onClick: "+v.getId() +"    "+rcaster.cast(v.getId())+"           "+R2.id.btn_distribute_add);

        switch (rcaster.cast(v.getId())) {
            case R2.id.btn_distribute_add:
                Utils.gotoActivity(getActivity(), DistributeActivity.class,null,null);
                break;
        }

    }



}
