package hsj.fieldManagement.com.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.view.viewPager.CustomNoScrollViewPager;
import com.kymjs.app.base_res.utils.view.viewPager.ReceivePagerAdapter;
import com.kymjs.app.base_res.utils.view.viewPager.stepperindicator.StepperIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import hsj.fieldManagement.com.R;
import hsj.fieldManagement.com.R2;
import hsj.fieldManagement.com.fragment.FieldManagementInfoFragment;
import hsj.fieldManagement.com.fragment.FieldManagementScanCodeFragment;

/**
 * Created by 16486 on 2020/10/31.
 */

public class AddFieldManagementActivity extends BaseActivity {
    @BindView(R2.id.toolbar_field_management)
    Toolbar toolbar;
    @BindView(R2.id.pager)
    public CustomNoScrollViewPager pager;               //pagerView分页流程对象

    public ReceivePagerAdapter pagerAdapter;                   //pagerView控件适配器

    ArrayList<Fragment> views = new ArrayList<>();      //pagerView中的适配器中的fragment集合

    public String currentScanCode;

    @Override
    public int getLayoutId() {
        return R.layout.field_management_add_activity;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        toolbar.setTitle("新增栏位");
        toolbar.setNavigationIcon(R.mipmap.bg_back);
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
        FieldManagementScanCodeFragment fieldManagementScanCodeFragment = new FieldManagementScanCodeFragment();
        FieldManagementInfoFragment fieldManagementInfoFragment = new FieldManagementInfoFragment();
        views.add(fieldManagementScanCodeFragment);
        views.add(fieldManagementInfoFragment);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      /*  if(pagerAdapter.getCurrentFragment() instanceof  FieldManagementScanCodeFragment){
            //判断当前页面是否为扫描栏位标签
            FieldManagementScanCodeFragment fragment=(FieldManagementScanCodeFragment)pagerAdapter.getCurrentFragment();
                fragment.onKeyDown(keyCode,event);
        }*/

        return super.onKeyDown(keyCode, event);
    }
}
