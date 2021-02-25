package examine.expmle.com.activity;

import android.os.Message;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R2;
import com.kymjs.app.base_res.utils.Activity.BaseresScanResultActivity;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.distribute.entry.PackageInfoItems;
import com.kymjs.app.base_res.utils.base.entry.ViewEntry.BottomViewList;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.ForcehUtils;
import com.kymjs.app.base_res.utils.utils.LogUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.powerView.Baseres_PowerSettingView;
import com.kymjs.app.base_res.utils.view.slide.SlideCutListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import devicelib.dao.Device;
import devicelib.dao.ResponseHandlerInterface;
import devicelib.factory.DeviceFactory;
import examine.expmle.com.R;
import examine.expmle.com.entry.Examine;
import hsj.expmle.com.distribute.fragment.ScanResultFragment;

/**
 * Created by 16486 on 2021/2/23.
 */

public class ExamineActivity extends BaseresScanResultActivity implements View.OnClickListener,ResponseHandlerInterface {

    Button btnCommit;
    Button btnPre;
    AutoAdapter<Examine> autoAdapter;

    Device device;

    RadioGroup rgScanPackage;

    LinearLayout layoutRadio;

    List<Examine> tagList = new ArrayList<>();

    Integer taskId;

    boolean isPackage=true;
    @Override
    public void initFragmentActivityView() {
        layoutRadio = findViewById(R.id.layout_one_time_distribution);

        rgScanPackage = findViewById(R.id.rg_scan_package);
        autoAdapter = new AutoAdapter<>(mContext,tagList,"Id","Code","CreateDate","Num");
        lvList.setAdapter(autoAdapter);
        lvList.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                if("error".equals(tagList.get(position).getIsFocus())){
                        tagList.remove(position);
                }else if("true".equals(tagList.get(position).getIsFocus())){
                        tagList.get(position).setIsFocus("false");
                }
                updateTitle();
            }
        });

        DeviceFactory factory = new DeviceFactory(this,this);

        device = factory.getDevice();
        if(device!=null){
            device.initUHF();
            device.onPause();
        }else{
            Toast.makeText(mContext,"获取RFID模块失败",Toast.LENGTH_SHORT).show();
        }
        taskId=getIntent().getExtras().getInt("taskId");
        powerSettingView.setListener(device,new ProgressDialog(mContext), Baseres_PowerSettingView.INTERMEDIATE);
        HashMap<String,Object> taskMap = new HashMap<>();
        taskMap.put("TaskID",taskId);

        InteractiveDataUtil.interactiveMessage(this,taskMap,handlerUtils, MethodEnum.GETEXAMINEINFO, InteractiveEnum.GET);
        layoutRadio.setVisibility(View.VISIBLE);

        rgScanPackage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RCaster rcaster = new RCaster(com.kymjs.app.base_res.R.class, R2.class);
                switch (rcaster.cast(i)) {
                    case R2.id.rb_case_serialNo:
                     isPackage=false;
                        break;
                    case R2.id.rb_package_serialNo:
                      isPackage=true;
                        break;

                }
            }
        });
    }

    HandlerUtils handlerUtils = new HandlerUtils(mContext, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
            /*签收数据详情*/
            if(MethodEnum.GETEXAMINEINFO.equals( msg.getData().getString("method"))) {
                tagList.clear();
                tagList.addAll(JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"), Examine.class));
                autoAdapter.notifyDataSetChanged();
                updateTitle();
            }else if(MethodEnum.GETBOXPACKAGEINFO.equals(msg.getData().getString("method"))){
                List<PackageInfoItems> packageInfos = JSON.parseArray( JSON.parseObject(msg.getData().getString("result")).getString("Data"),PackageInfoItems.class);
                    for (int i =0;i<packageInfos.size();i++){
                        updateTagDataInfo(packageInfos.get(i).getCode());
                    }

            }else if(MethodEnum.POSTEXAMINE.equals(msg.getData().getString("method"))){
                UIHelper.ToastMessage(mContext,"数据提交成功");
                Utils.activityFinish(ExamineActivity.this,device);
            }
        }
    }, new HandlerUtilsErrorCallback() {
        @Override
        public void handlerErrorFunction(Message ms) {
             if(MethodEnum.POSTEXAMINE.equals(ms.getData().getString("method"))){
                UIHelper.ToastMessage(mContext,"数据提交失败");
            }

        }
    });

    @Override
    public String[] getArrayTitle() {
        return new String[]{"编号","包号","创建时间","耳标数量"};
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
        if(device.isLoop()){
            UIHelper.ToastMessage(mContext,"请停止扫描RFID");
            return;
        }

        switch (Integer.parseInt(view.getTag().toString())){
            //返回按钮
            case 0:
                Utils.onBackDialog(this,device);
                break;
            //提交按钮
            case 1:
                List<Integer> lis = new ArrayList<>();
                for(Examine examine :tagList){
                    if("true".equals(examine.getIsFocus())){
                        lis.add(examine.getId());
                    }
                }
                HashMap<String,Object> commitExamineMap = new HashMap<>();
                commitExamineMap.put("lis",lis);
                commitExamineMap.put("TaskID",taskId);
                InteractiveDataUtil.interactiveMessage(this,commitExamineMap,handlerUtils,MethodEnum.POSTEXAMINE,InteractiveEnum.POST);

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否打开扫描RFID并且RFID对象已初始化，并且当前的fragment页面是结果页面
        if(keyCode==280) {
            Log.d("scanCode", "onKeyDown: ");
            device.onKeyDown(keyCode, event, 2, false);
        }else if(keyCode== KeyEvent.KEYCODE_BACK){
          Utils.onBackDialog(this,device);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleTagdata(String rfid) {


    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {
        LogUtils.d(mContext,"scanCode", "scanCode: "+code  +"    "+isPackage);
        if(isPackage) {
            updateTagDataInfo(code);
        }else{
            HashMap<String, Object> scanSerialMap = new HashMap<>();
            scanSerialMap.put("ID", 0);
            scanSerialMap.put("Code", code);
            scanSerialMap.put("QelType", -1);
            InteractiveDataUtil.interactiveMessage(this, scanSerialMap, handlerUtils, MethodEnum.GETBOXPACKAGEINFO, InteractiveEnum.GET);
        }


    }

    public void updateTitle(){
        int successCount =ForcehUtils.getListStatusCount(tagList,"IsFocus","true");
        int errorCount = ForcehUtils.getListStatusCount(tagList,"IsFocus","error");
        tvScanResultTotalCount.setText(tagList.size()+"");

        tvScanResultCurrentCount.setText(successCount+"");
        tvScanResultErrorCount.setText(errorCount+"");
        if(errorCount==0 && successCount>0){
            btnCommit.setEnabled(true);
        }
        autoAdapter.notifyDataSetChanged();


    }

    public void updateTagDataInfo(String code){
        int index=-1;
        if( (index= ForcehUtils.getListIndex(tagList, "Code", code))!=-1  &&  "false".equals(tagList.get(index).getIsFocus())   ||
                (index= ForcehUtils.getListIndex(tagList, "Code", code))!=-1  &&  "true".equals(tagList.get(index).getIsFocus()) ){
                tagList.get(index).setIsFocus("true");
                device.playSound(1);

        }else if( (index= ForcehUtils.getListIndex(tagList, "Code", code))!=-1  &&  "error".equals(tagList.get(index).getIsFocus())) {
            device.playSound(2);
        }else{
            Examine tempExamine = new Examine();
            tempExamine.setIsFocus("error");
            tempExamine.setCode(code);
            tagList.add(tempExamine);
            device.playSound(2);
        }
        updateTitle();

    }

}
