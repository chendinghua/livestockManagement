package com.kymjs.app.base_res.utils.fragment;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.baseDao.FragmentActivityBaseDao;

/**
 * Created by 16486 on 2020/11/24.
 */
public abstract  class BaseresScanResultFragment<T1 extends Activity> extends BaseFragment<T1> implements FragmentActivityBaseDao{


    @Override
    public void initView() {
        String [] titles = getArrayTitle();
        LinearLayout linearLayout = getLinearLayout();
        if(linearLayout!=null) {
            for (int i = 0; i < titles.length; i++) {
                TextView tvTitle = new TextView(activity);
                tvTitle.setText(titles[i]);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                tvTitle.setLayoutParams(lp);
                tvTitle.setGravity(Gravity.CENTER);
                tvTitle.setMaxLines(2);
                tvTitle.setEllipsize(TextUtils.TruncateAt.END);
                linearLayout.addView(tvTitle);
            }
        }
    }


}
