package hsj.expmle.com.prevention.activity;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;

import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import hsj.expmle.com.prevention.entry.Prevention;

/** 疫苗页面
 * Created by 16486 on 2020/10/22.
 */
public class PreventionActivity extends BaseresScanResultActivity implements ResponseHandlerInterface,View.OnClickListener{

    Button btnBack;

    Button btnCommit;

    int vaccineID;


    HandlerUtils handlerUtils;

    AutoAdapter<Prevention> autoAdapter;

    List<Prevention> tagList = new ArrayList<>();

    Device device;

    @Override
    public int getLayoutId() {
        return R.layout.baseres_scan_result_activity;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected List<BottomViewList<Button>> getButtonViewList() {
        List<BottomViewList<Button>> lists = new ArrayList<>();
        btnBack = new Button(mContext);
        btnCommit = new Button(mContext);
        btnCommit.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        lists.add(new BottomViewList<Button>(btnBack,"返回"));
        lists.add(new BottomViewList<Button>(btnCommit,"提交"));
        return lists;
    }

    @Override
    public void initFragmentActivityView() {
        DeviceFactory factory = new DeviceFactory(this,this);

        device = factory.getDevice();
        if(device!=null){
            device.initUHF();

        }else{
            Toast.makeText(mContext,"获取RFID模块失败",Toast.LENGTH_SHORT).show();
        }


        handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if(MethodEnum.POSTVACCINELISTS.equals(msg.getData().getString("method"))){
                    tagList.clear();
                    tagList.addAll(JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"),Prevention.class));
                    updateData();
                }
            }
        });
        vaccineID = getIntent().getExtras().getInt("VaccineID");
        autoAdapter = new AutoAdapter<Prevention>(mContext,tagList,"StorageSerialNo","StorageRfidNo","StockSerialNo","VacctionDate");
        lvList.setAdapter(autoAdapter);
        HashMap<String,Object> map = new HashMap<>();
        map.put("VaccineID",vaccineID);
        InteractiveDataUtil.interactiveMessage(this,map,handlerUtils, MethodEnum.POSTVACCINELISTS, InteractiveEnum.GET);
    }



    @Override
    public String[] getArrayTitle() {
        return new String[]{"库存序列号","库存RFID","库位编号","注射时间"};
    }
    private void updateData() {
        tvScanResultTotalCount.setText(""+tagList.size());
        currentCount=0;
        errorCount=0;
        for (int i =0;i<tagList.size();i++){
            if("true".equals(tagList.get(i).getIsFocus())){
                currentCount++;
            }else if("false".equals(tagList.get(i).getIsFocus())){
                errorCount++;
            }
        }
        tvScanResultCurrentCount.setText(currentCount+"");
        tvScanResultErrorCount.setText(currentCount+"");


    }

    @Override
    public void handleTagdata(String rfid) {
        int index=-1;
        if(rfid.startsWith(LabelRule.earmMarkRule) && (index =isExistList(rfid))!=-1){
            if("false".equals(tagList.get(index).getIsFocus())){
                tagList.get(index).setIsFocus("true");
            }
            device.playSound(1);
        }else{
            if(device!=null){
                device.playSound(2);
            }
        }
    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

    public int isExistList(String rfid){
        int index=-1;
        for(int i=0;i<tagList.size();i++){
            if(rfid.equals( tagList.get(i).getRfidNo())){
                index=i;
                break;
            }

        }

        return index;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()== btnCommit.getId()){



        }else if(v.getId() == btnBack.getId()){
            onbackDialog();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if(keyCode==280) {
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode, event, 1, false);
        }else if(keyCode== KeyEvent.KEYCODE_BACK){
            onbackDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onbackDialog(){
        DialogUtils.showAlertDialog(this, new AlertDialogCallBack() {
            @Override
            public void alertDialogFunction() {
                if(device!=null){
                    device.destroy();
                }

                finish();
            }
        },"是否结束当前流程",null,null);


    }

}