package hsj.fieldManagement.com.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.base.BaseModelFragment;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hsj.fieldManagement.com.R;
import hsj.fieldManagement.com.R2;
import hsj.fieldManagement.com.activity.AddFieldManagementActivity;

/**
 * 栏位扫描标签
 * Created by 16486 on 2020/10/31.
 */
public class FieldManagementScanCodeFragment extends BaseModelFragment<AddFieldManagementActivity> {

    HandlerUtils handler;

    @BindView(R2.id.btn_field_management_scan_code)
    Button btnScanCode;


    @Override
    public void handleTagdata(String rfid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("RFID", rfid);
        InteractiveDataUtil.interactiveMessage(activity, map, handler, MethodEnum.GETISSTOCKBYRFID, InteractiveEnum.GET,rfid);

    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.field_management_scan_code_fragment;
    }

    @Override
    protected void initView() {
        handler = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(final Message msg) {



                if (MethodEnum.GETISSTOCKBYRFID.equals(msg.getData().getString("method"))) {
                    LinearLayout linearLayoutParent = new LinearLayout(activity);
                    linearLayoutParent.setOrientation(LinearLayout.VERTICAL);






                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    TextView  resultScanSerialNoTitle  = new TextView(activity);
                    resultScanSerialNoTitle.setText("序列号:");
                    TextView  resultScanSerialNo  = new TextView(activity);
                    resultScanSerialNo.setText("12345");
                    linearLayout.addView(resultScanSerialNoTitle);
                    linearLayout.addView(resultScanSerialNo);


                    LinearLayout linearLayout1 = new LinearLayout(activity);
                    linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                    TextView  resultScanRfidTitle  = new TextView(activity);
                    resultScanRfidTitle.setText("RFID:");
                    TextView  resultScanRfidNo  = new TextView(activity);
                    resultScanRfidNo.setText(msg.getData().getString("bindDate"));
                    linearLayout1.addView(resultScanRfidTitle);
                    linearLayout1.addView(resultScanRfidNo);



                    linearLayoutParent.addView(linearLayout1);
                    linearLayoutParent.addView(linearLayout);


                    DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
                        @Override
                        public void alertDialogFunction() {
                                activity.currentScanCode=msg.getData().getString("bindDate");
                            activity.pager.setCurrentItem(activity.pager.getCurrentItem()+1);
                        }
                    }, "请确认标签信息", null,linearLayoutParent);
                }
            }
        });
    }


    @Override
    public boolean isOpenUHFModel() {
        return true;
    }

    @Override
    public boolean isSingle() {
        return false;
    }




}
