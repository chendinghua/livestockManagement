package integrated.expmle.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.ForcehUtils;
import com.kymjs.app.base_res.utils.utils.LogUtils;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.winterpei.LicensePlateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import integrated.expmle.com.R;
import integrated.expmle.com.R2;
import integrated.expmle.com.entry.Integeregrated;

/**
 * 综合管理  （检疫站车牌查询）
 * Created by 16486 on 2021/2/20.
 */
public class IntegratedFragment extends Fragment implements LicensePlateView.InputListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R2.id.rg_license_plate)
    RadioGroup rgLicensePlate;
    @BindView(R2.id.activity_lpv)
    LicensePlateView mPlateView;
    @BindView(R2.id.main_rl_container)
    RelativeLayout mContainer;
    Unbinder unbinder;
    View rootView;
    Activity activity;

    AutoAdapter<Integeregrated> adapter;

    List<Integeregrated> tagList = new ArrayList<>();
    @BindView(R2.id.tv_scan_result_total_count)
    TextView tvScanResultTotalCount;
    @BindView(R2.id.tv_scan_result_current_count)
    TextView tvScanResultCurrentCount;
    @BindView(R2.id.tv_scan_result_error_count)
    TextView tvScanResultErrorCount;

    //当前是否扫描
    boolean isScan = false;
    @BindView(R2.id.btn_integrated_refresh)
    Button btnIntegratedRefresh;
    @BindView(R2.id.lv_integrated_list)
    ListView lvIntegratedList;

    Device device;
    @BindView(R2.id.psv_scan_result)
    Baseres_PowerSettingView psvScanResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_integrated, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        activity = getActivity();
        adapter = new AutoAdapter<>(activity, tagList, "StorageID", "RfidNo", "SerialNo", "StorageStatusName", "DeptName", "HealthStatusName");
        lvIntegratedList.setAdapter(adapter);
        rgLicensePlate.setOnCheckedChangeListener(this);
        mPlateView.setInputListener(this);
        mPlateView.setKeyboardContainerLayout(mContainer);
        mPlateView.hideLastView();
        mPlateView.onSetTextColor(R.color.colorAccent);
        updateTitle(false);

        return rootView;
    }

    //重新初始化物理按键触发扫描二维码模块
    @Override
    public void onResume() {
        device = new DeviceFactory(activity, new ResponseHandlerInterface() {
            @Override
            public void handleTagdata(String rfid) {
                int index = -1;

                if ((index = ForcehUtils.getListIndex(tagList, "RfidNo", rfid)) != -1) {
                    if (!"error".equals(tagList.get(index).getIsFocus())) {
                        tagList.get(index).setIsFocus("true");
                        device.playSound(1);
                    } else {
                        device.playSound(2);
                    }
                } else {
                    Integeregrated temp = new Integeregrated();
                    temp.setRfidNo(rfid);
                    temp.setIsFocus("error");
                    tagList.add(0, temp);

                }

                updateTitle(true);
            }

            @Override
            public void handleTriggerPress(boolean pressed) {
            }

            @Override
            public void scanCode(String code) {

            }
        }).getDevice();
        device.initUHF();
        device.onPause();
        rootView.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isScan) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        Log.d("onKeyCode", "onKey: " + i);
                        device.onKeyDown(i, keyEvent, 1, false);
                        return true;
                    }
                } else {
                    UIHelper.ToastMessage(activity, "当前数据为空");
                    return true;
                }
                return false;
            }
        });
        ProgressDialog progress = ProgressDialog.createDialog(activity);
        psvScanResult.setListener(device, progress, Baseres_PowerSettingView.INTERMEDIATE);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (device != null)
            device.destroy();
        super.onDestroy();
    }

    @Override
    public void inputComplete(final String content) {
        DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
            @Override
            public void alertDialogFunction() {
                HashMap<String, Object> map = new HashMap<>();
                map.put("CarName", content);

                InteractiveDataUtil.interactiveMessage(activity, map, new HandlerUtils(activity, new HandlerUtilsCallback() {
                    @Override
                    public void handlerExecutionFunction(Message msg) {
                        tagList.clear();
                        tagList.addAll(JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), Integeregrated.class));
                        updateTitle(true);
                    }
                }), MethodEnum.GETCARMESSAGE, InteractiveEnum.GET);
            }
        }, "请确认车牌信息" + content, null, null);
        LogUtils.d(activity, "inputComplete", "inputComplete:       " + content);
    }

    @Override
    public void deleteContent() {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        if(device!=null && device.isLoop()){
            UIHelper.ToastMessage(activity,"请停止扫描RFID");
            return;
        }
        RCaster caster = new RCaster(R.class, R2.class);
        switch (caster.cast(checkedId)) {
            case R2.id.rb_ordinary:
                mPlateView.hideLastView();
                break;
            case R2.id.rb_new_energy:
                mPlateView.showLastView();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void updateTitle(boolean flag) {
        isScan = flag;
        btnIntegratedRefresh.setEnabled(flag);
        tvScanResultTotalCount.setText("" + tagList.size());
        tvScanResultCurrentCount.setText(ForcehUtils.getListStatusCount(tagList, "IsFocus", "true") + "");
        tvScanResultErrorCount.setText(ForcehUtils.getListStatusCount(tagList, "IsFocus", "error") + "");
        adapter.notifyDataSetChanged();

    }

    @OnClick({R2.id.btn_integrated_refresh, R2.id.btn_integrated_clear})
    public void onViewClicked(View view) {
        if(device!=null && device.isLoop()){
            UIHelper.ToastMessage(activity,"请停止扫描RFID");
            return;
        }

        RCaster caster = new RCaster(R.class, R2.class);
        switch (caster.cast(view.getId())) {
            case R2.id.btn_integrated_refresh:
                for (int i =0;i<tagList.size() ; i++) {

                    Integeregrated integeregrated = tagList.get(i);
                    //判断当前为异常数据清除
                    if("error".equals(integeregrated.getIsFocus())){
                        tagList.remove(i);
                        i--;
                        //除异常数据以外全部恢复为默认未扫描状态
                    }else {
                        integeregrated.setIsFocus("false");
                    }
                }
                updateTitle(true);
                break;
            case R2.id.btn_integrated_clear:
                tagList.clear();
                updateTitle(false);
                mPlateView.clearEditText();
                mPlateView.setKeyboardContainerLayout(mContainer);
                break;
        }
    }
}