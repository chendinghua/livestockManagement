package com.kymjs.app.base_res.utils.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.baseDao.FragmentActivityBaseDao;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.List;

/**
 * Created by 16486 on 2020/11/24.
 */
public abstract  class BaseresScanResultActivity extends BaseActivity implements FragmentActivityBaseDao{
    @Override
    public void initPresenter() {

    }
    LinearLayout layoutBottomOperation;


    //显示总数量
    protected TextView tvScanResultTotalCount;
    //当前扫描数量
    protected  TextView tvScanResultCurrentCount;
    //当前异常数量
    protected  TextView tvScanResultErrorCount;
    //设置当前频率控件
    protected Baseres_PowerSettingView powerSettingView;
    //标题列表控件
    LinearLayout layoutScanResultTitle;

    protected SlideCutListView lvList;

    protected  int currentCount;
    protected  int errorCount;

    @Override
    public void initView() {
        layoutBottomOperation = findViewById(R.id.layout_bottom_operation);
        tvScanResultTotalCount  = findViewById(R.id.tv_scan_result_total_count);
        tvScanResultCurrentCount = findViewById(R.id.tv_scan_result_current_count);
        tvScanResultErrorCount = findViewById(R.id.tv_scan_result_error_count);
        powerSettingView = findViewById(R.id.psv_scan_result);
        layoutScanResultTitle = findViewById(R.id.layout_scan_result_title);
        lvList = findViewById(R.id.lv_scan_result);

        //获取标题数据集合
        String [] titles = getArrayTitle();
        if(layoutScanResultTitle!=null) {
            if(titles!=null) {
                layoutScanResultTitle.removeAllViews();
                layoutScanResultTitle.setVisibility(View.VISIBLE);
                for (int i = 0; i < titles.length; i++) {
                    TextView tvTitle = new TextView(mContext);
                    tvTitle.setText(titles[i]);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                    tvTitle.setLayoutParams(lp);
                    tvTitle.setGravity(Gravity.CENTER);
                    tvTitle.setMaxLines(2);
                    tvTitle.setEllipsize(TextUtils.TruncateAt.END);
                    layoutScanResultTitle.addView(tvTitle);
                }
            }else{
                layoutScanResultTitle.setVisibility(View.GONE);
            }
        }
        //获取当前底部列表数据集合
        List<BottomViewList<Button>> viewLists = getButtonViewList();
        if(viewLists!=null) {
            layoutBottomOperation.removeAllViews();
            layoutBottomOperation.setVisibility(View.VISIBLE);
            for (int i = 0; i < viewLists.size(); i++) {


                Button btn = viewLists.get(i).getT();


                btn.setText(viewLists.get(i).getTitle());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                btn.setLayoutParams(lp);
                btn.setGravity(Gravity.CENTER);
                btn.setMaxLines(2);
                btn.setEllipsize(TextUtils.TruncateAt.END);
                layoutBottomOperation.addView(btn);

            }
        }else{
            layoutBottomOperation.setVisibility(View.GONE);
        }
        initFragmentActivityView();
    }
    //底部按钮列表显示
    protected abstract List<BottomViewList<Button>> getButtonViewList();

}
