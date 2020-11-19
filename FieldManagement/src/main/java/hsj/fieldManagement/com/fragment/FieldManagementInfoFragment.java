package hsj.fieldManagement.com.fragment;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;

import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;
import hsj.fieldManagement.com.R;
import hsj.fieldManagement.com.R2;
import hsj.fieldManagement.com.activity.AddFieldManagementActivity;


/**
 * Created by Administrator on 2019/2/22/022.
 */

/***
 * 栏位信息页面
 */
public class FieldManagementInfoFragment extends BaseFragment<AddFieldManagementActivity> implements View.OnClickListener {

    @BindView(R2.id.btn_add_info_field_management)
    Button btnAddFieldManagement;
    @BindView(R2.id.et_field_management_Name)
    EditText etFieldManagementName;
    @BindView(R2.id.et_field_management_MaxArea)
    EditText etFieldManagementMaxArea;
    @BindView(R2.id.et_field_management_MaxNum)
    EditText etFieldManagementMaxNum;


    HandlerUtils handler;
    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.field_management_info_fragment;
    }

    @Override
    protected void initView() {
        handler = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.POSTADDSTOCK.equals( msg.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"数据提交成功");
                    activity.finish();
                }
            }
        });
    }

    @Override
    protected boolean isLoad() {
        return false;
    }

    @OnClick(R2.id.btn_add_info_field_management)
    public void onViewClicked() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name",etFieldManagementName.getText().toString());
        map.put("RfidNo",activity.currentScanCode);
        map.put("MaxArea",etFieldManagementMaxArea.getText().toString());
        map.put("MaxNum",etFieldManagementMaxNum.getText().toString());
        InteractiveDataUtil.interactiveMessage(activity,map,handler, MethodEnum.POSTADDSTOCK, InteractiveEnum.POST);
    }
}
