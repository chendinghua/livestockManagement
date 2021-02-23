package examine.expmle.com.activity;

import android.view.View;
import android.widget.Button;

import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16486 on 2021/2/23.
 */

public class ExamineActivity extends BaseresScanResultActivity implements View.OnClickListener{

    Button btnCommit;
    Button btnPre;

    @Override
    public void initFragmentActivityView() {

    }

    @Override
    public String[] getArrayTitle() {
        return new String[0];
    }

    /*********************
     * 子类实现
     *****************************/
    @Override
    public int getLayoutId() {
        return com.kymjs.app.base_res.R.layout.baseres_scan_result_activity;
    }

    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        btnCommit = new Button(mContext);
        btnPre = new Button(mContext);
        btnCommit.setEnabled(false);
        btnCommit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        List<BottomViewList<Button>> list = new ArrayList<>();
        list.add(new BottomViewList<>(btnPre, "返回"));
        list.add(new BottomViewList<>(btnCommit, "提交"));
        return list;
    }

    @Override
    protected List<View> getLayoutScanResultOperation() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (Integer.parseInt(view.getTag().toString())){
            //返回按钮
            case 0:
                break;
            //提交按钮
            case 1:
                break;
        }
    }
}
