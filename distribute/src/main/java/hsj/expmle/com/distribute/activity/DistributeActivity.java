package hsj.expmle.com.distribute.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;

import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.base.BaseModelActivity;
import com.kymjs.app.base_res.utils.view.viewPager.CustomNoScrollViewPager;
import com.kymjs.app.base_res.utils.view.viewPager.ReceivePagerAdapter;
import com.kymjs.app.base_res.utils.view.viewPager.stepperindicator.StepperIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import hsj.expmle.com.distribute.R;
import hsj.expmle.com.distribute.R2;
import hsj.expmle.com.distribute.fragment.FarmersFragment;
import hsj.expmle.com.distribute.fragment.ScanResultFragment;

/** 分发流程模块
 * Created by 16486 on 2020/10/22.
 */

public class DistributeActivity extends BaseModelActivity {
    @BindView(R2.id.pager)
    public CustomNoScrollViewPager pager;               //pagerView分页流程对象

    public ReceivePagerAdapter pagerAdapter;                   //pagerView控件适配器

    ArrayList<Fragment> views = new ArrayList<>();      //pagerView中的适配器中的fragment集合
    //养殖场id
    public  Integer farmersId;
    //畜种id
    public Integer farmersProductId;
    //分发标签类型id
    public Integer farmersProductTypeId;
    //需要扫描的正常数量
    public Integer scanCount;

    @Override
    public String getFragmentTitle() {
        return "分发操作";
    }

    @Override
    public boolean isOpenUHFModel() {
        return true;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.distribute_activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initView(views);

        //获取fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //把fragment集合中的数据添加到适配器中
        pagerAdapter = new ReceivePagerAdapter(fragmentManager,views);
        pager= findViewById(R.id.pager);
        assert pager != null;
        //设置pagerView的适配器
        pager.setAdapter(pagerAdapter);
        //分页标题控件
        final StepperIndicator indicator =findViewById(R.id.stepper_indicator);
        //分页标题和pagerView绑定一下
        indicator.setViewPager(pager, false);

    }

    private void initView(ArrayList<Fragment> views) {
        FarmersFragment farmersFragment = new FarmersFragment();
        ScanResultFragment scanResultFragment = new ScanResultFragment();
        views.add(farmersFragment);
        views.add(scanResultFragment);

    }

    @Override
    public void handleTagdata(String rfid) {
        //扫描RFID数据判断当前显示页面是结果页面
        if(pagerAdapter.getCurrentFragment() instanceof ScanResultFragment){
            ScanResultFragment fragment  =(ScanResultFragment)  pagerAdapter.getCurrentFragment();
            fragment.scanCode(rfid);
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if (isOpenUHFModel() && device!=null && pagerAdapter.getCurrentFragment() instanceof ScanResultFragment){
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode,event,1,isSingle());
        }

        return super.onKeyDown(keyCode, event);
    }
}
