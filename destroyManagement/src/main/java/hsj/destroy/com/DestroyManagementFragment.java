package hsj.destroy.com;

import android.os.Message;
import android.view.View;

import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;

import devicelib.dao.Device;

/** 标签销毁模块
 * Created by 16486 on 2020/11/25.
 */
public class DestroyManagementFragment extends BaseresTaskFragment implements View.OnClickListener{

    Device device;

    HandlerUtils handlerUtils;

    @Override
    public void initFragmentActivityView() {
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))){

                }
            }
        });

        btnTaskAdd.setOnClickListener(this);
    }

    @Override
    public String[] getArrayTitle() {
        return new String[0];
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //新增销毁
        if(v.getId()==btnTaskAdd.getId()){
            device = ScanRfidDialog.showScanRfid(activity,"请扫描耳标标签", LabelRule.earmMarkRule,"耳标标签数据异常", MethodEnum.GETSTORAGEINFOBYOUT,"RFID",handlerUtils);
        }
    }
}
