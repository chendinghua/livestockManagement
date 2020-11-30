package com.kymjs.app.base_res.utils.fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.R2;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.baseDao.FragmentActivityBaseDao;
import com.lwy.paginationlib.PaginationListView;

import butterknife.BindView;

/**
 * Created by 16486 on 2020/11/23.
 */

public abstract class BaseresTaskFragment extends BaseFragment implements FragmentActivityBaseDao{

     //标题列表
    LinearLayout layoutTaskAutoTitle;
    protected PaginationListView lvTaskInfo;

    protected   Button btnTaskAdd;
    @Override
    protected void initView() {
        layoutTaskAutoTitle = rootView.findViewById(R.id.layout_task_auto_title);
        lvTaskInfo = rootView.findViewById(R.id.lv_task_info);
        btnTaskAdd =  rootView.findViewById(R.id.btn_task_add);
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
        //添加动态列表
        initFragmentActivityView();
    }
    @Override
    protected boolean isLoad() {
        return true;
    }
}
