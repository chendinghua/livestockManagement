package com.kymjs.app.base_res.utils.baseDao;

import android.widget.LinearLayout;

/**
 * Created by 16486 on 2020/11/24.
 */

public interface FragmentActivityBaseDao {
    //获取当前标题layout
     LinearLayout getLinearLayout();
    //初始化页面数据
     void initFragmentActivityView();
    //初始化列表信息
     String [] getArrayTitle();

}
