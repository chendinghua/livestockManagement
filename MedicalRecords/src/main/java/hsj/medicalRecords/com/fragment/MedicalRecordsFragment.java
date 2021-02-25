package hsj.medicalRecords.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.Rule.LabelRule;
import com.kymjs.app.base_res.utils.base.BaseFragment;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.view.ScanRfidDialog;
import com.kymjs.app.base_res.utils.view.dateTime.SelectDateTime;
import com.lwy.paginationlib.PaginationListView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import devicelib.dao.Device;
import hsj.medicalRecords.com.R;
import hsj.medicalRecords.com.R2;
import hsj.medicalRecords.com.adapter.SearchAdapter;
import hsj.medicalRecords.com.dialog.MedicalRecordsDialog;
import hsj.medicalRecords.com.entry.MedicalRList;
import hsj.medicalRecords.com.entry.MedicalRecordsInfo;
import hsj.medicalRecords.com.entry.MedicalRecordsList;

/**
 * 就诊管理页面
 * Created by 16486 on 2020/11/10.
 */
public class MedicalRecordsFragment extends BaseFragment {

    @BindView(R2.id.lv_medical_records_list)
    PaginationListView lvModicalRecords;

    PaginationListView.Adapter<MedicalRecordsList> adapter;

    HandlerUtils handlerUtils;
    @BindView(R2.id.tv_storage_info_startTime)
    TextView tvStorageInfoStartTime;
    @BindView(R2.id.tv_storage_info_endTime)
    TextView tvStorageInfoEndTime;
    @BindView(R2.id.et_medicalRecords_input_serialNo)
    EditText etMedicalRecordsInputSerialNo;
    @BindView(R2.id.btn_medicalRecords_Search)
    Button btnMedicalRecordsSearch;
    //更新数量
    boolean loadNumber=true;
   /* @BindView(R2.id.auto_medical_list)
    AutoCompleteTextView  autoCompleteTextView;
*/
    MedicalRecordsDialog mDialog;

    MedicalRecordsDialog.Builder builder;

    Device device;


    @Override
    protected int getLayoutResource() {
        return R.layout.medicalrecords_fragment;
    }



    @Override
    protected void initView() {
        builder =new MedicalRecordsDialog.Builder(activity);


      /*  InteractiveDataUtil.interactiveMessage(activity,null,new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                List<MedicalRList> medicalRLists =   JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"),MedicalRList.class);
                String [] res = new String[medicalRLists.size()];
                for(int i =0;i<medicalRLists.size();i++){
                    res[i]=medicalRLists.get(i).getName();
                }
                Log.d("arrayDatalist", "handlerExecutionFunction: "+ Arrays.toString(res));

            }
        }),MethodEnum.GETMEDICALRLIST, InteractiveEnum.GET);*/
   /*   String [] res = new String[]{"复合型新型流行性感冒","流行性感冒","bbb","bbbb"};

         String[] str = {"大大大", "大大小", "大小大", "大小小", "小大大", "小大小", "小大小", "小小小"};

        SearchAdapter<String> autoadapter = new SearchAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,str,SearchAdapter.ALL);
        autoCompleteTextView.setAdapter(autoadapter);*/

        tvStorageInfoEndTime.setText(SelectDateTime.getTomoData());
     //   SelectDateTime.initCurrentTime( tvStorageInfoEndTime);
        SelectDateTime.initCurrentTime(tvStorageInfoStartTime);

        adapter = new PaginationListView.Adapter(20, activity, -1, "ID", "Illness", "RfidNo", "DiagnosisTime", "Status");
        lvModicalRecords.setAdapter(adapter);

        //分页触发事件
        lvModicalRecords.setListener(new PaginationListView.Listener() {
            @Override
            public void loadMore(int currentPagePosition, int nextPagePosition, int perPageCount, int dataTotalCount) {
                changeData(currentPagePosition + 1, perPageCount);
            }

            @Override
            public void onPerPageCountChanged(int perPageCount) {

            }
        });
        handlerUtils = new HandlerUtils(activity, new HandlerUtilsCallback() {
            @Override
            public void handlerExecutionFunction(Message msg) {
                if (MethodEnum.GETMEDICALILLNESSLIST.equals(msg.getData().getString("method"))) {
                    MedicalRecordsInfo medicalRecordsInfo = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), MedicalRecordsInfo.class);
                    if (medicalRecordsInfo != null) {
                        Log.d("JSONResult", "handlerExecutionFunction: " + msg.getData().getString("result") + "     " + medicalRecordsInfo.getResult());
                        if (loadNumber) {
                            loadNumber = false;
                            adapter.setDataTotalCount(medicalRecordsInfo.getRowsCount());
                        }
                        adapter.setDatas(Integer.parseInt(msg.getData().getString("bindDate")), medicalRecordsInfo.getResult());
                        lvModicalRecords.setState(PaginationListView.SUCCESS);
                        //     adapter.notifyDataSetChanged();

                    }
                //扫描耳标RFID显示耳标信息，并显示提示新增就诊信息
                }else if(MethodEnum.GETSTORAGEINFOBYOUT.equals(msg.getData().getString("method"))){
                    if(device!=null){
                        device.destroy();
                    }
                    final ScanResult scanResult = JSON.parseObject(JSON.parseObject(msg.getData().getString("result")).getString("Data"), ScanResult.class);
                    //判断库存在库状态
                   if(scanResult!=null && scanResult.getStatus()==1){
                       mDialog = builder.setMessage("请确认栏位信息").
                               initScanResult(scanResult)
                               .setPositiveButton("确认", new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       //判断当前疾病是否为空
                                       if(builder.getSpMedical()!=null && !"".equals(builder.getSpMedical().getTag())) {

                                           //添加就诊信息
                                           HashMap<String, Object> hashMap = new HashMap<>();
                                           hashMap.put("MedicalRID", builder.getSpMedical().getTag());
                                         //  hashMap.put("MedicalRecord", builder.getMedical());
                                           hashMap.put("StorageID", scanResult.getStorageID());
                                           hashMap.put("StockID", scanResult.getStockID());
                                           hashMap.put("Condition", builder.getCondition());
                                           hashMap.put("Status", 1);                 //治疗状态  1治疗中  0为康复  2死亡 -9删除
                                           InteractiveDataUtil.interactiveMessage(activity, hashMap, handlerUtils, MethodEnum.POSTMEDICAL, InteractiveEnum.POST);
                                       }else{
                                           UIHelper.ToastMessage(activity,"当前疾病名称不能为空");
                                       }
                                   }
                               })
                               .setNegativeButton("取消", new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       mDialog.dismiss();
                                   }
                               })
                               .createTwoButtonDialog();
                       mDialog.show();
                   }

                }else if(MethodEnum.POSTMEDICAL.equals(msg.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"提交成功");
                    mDialog.dismiss();
                }
            }
        }, new HandlerUtilsErrorCallback() {
            @Override
            public void handlerErrorFunction(Message ms) {
                if (MethodEnum.GETMEDICALILLNESSLIST.equals(ms.getData().getString("method"))) {
                    if (loadNumber) {
                        loadNumber = false;
                        adapter.setDataTotalCount(0);
                    }
                    lvModicalRecords.setState(PaginationListView.SUCCESS);
                }else if(MethodEnum.GETSTORAGEINFOBYOUT.equals(ms.getData().getString("method"))){
                    if(device!=null){
                        device.destroy();
                    }
                   UIHelper.ToastMessage(activity,JSON.parseObject(ms.getData().getString("result")).getString("Message"));
                }else if(MethodEnum.POSTMEDICAL.equals(ms.getData().getString("method"))){
                    UIHelper.ToastMessage(activity,"提交失败");
                    mDialog.dismiss();
                }
            }
        });
    }


    @Override
    protected boolean isLoad() {
        return true;
    }


    public void changeData(int pageIndex, int pageSize) {
        HashMap<String, Object> medicalRecordsMap = new HashMap<>();
        medicalRecordsMap.put("StartDate",tvStorageInfoStartTime.getText().toString());
        medicalRecordsMap.put("EndDate",tvStorageInfoEndTime.getText().toString());
        if(!"".equals(etMedicalRecordsInputSerialNo.getText().toString().trim()))
        medicalRecordsMap.put("RFID",etMedicalRecordsInputSerialNo.getText().toString());
        medicalRecordsMap.put("pageIndex", pageIndex);
        medicalRecordsMap.put("pageSize", pageSize);
        InteractiveDataUtil.interactiveMessage(activity, medicalRecordsMap, handlerUtils, MethodEnum.GETMEDICALILLNESSLIST, InteractiveEnum.GET, "" + pageIndex);
    }
    @OnClick({R2.id.btn_medicalRecords_Search,R2.id.btn_medicalRecords_add,R2.id.tv_storage_info_startTime,R2.id.tv_storage_info_endTime})
    public void onViewClicked(View view) {
        RCaster rCaster = new RCaster(R.class,R2.class);
        switch (rCaster.cast(view.getId())){
            case R2.id.btn_medicalRecords_Search:
                loadNumber=true;
                adapter.clear();
                changeData(adapter.getCurrentPagePos(),adapter.getPerPageCount());
                break;
            case R2.id.btn_medicalRecords_add:
              //显示提示扫描就诊耳标
            device = ScanRfidDialog.showScanRfid(activity,"请扫描耳标标签", LabelRule.earmMarkRule,"耳标标签数据异常",MethodEnum.GETSTORAGEINFOBYOUT,"RFID",handlerUtils,true);
                break;
            case R2.id.tv_storage_info_startTime:
                SelectDateTime.selectDate(tvStorageInfoStartTime,activity);
                break;
            case R2.id.tv_storage_info_endTime:
                SelectDateTime.selectDate(tvStorageInfoEndTime,activity);
                break;
        }
    }
}
