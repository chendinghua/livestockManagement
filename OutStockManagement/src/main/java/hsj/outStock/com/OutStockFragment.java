package hsj.outStock.com;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.base.entry.Farmer.Farmers;
import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskData;
import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerPorts;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.lwy.paginationlib.PaginationListView;
import java.util.HashMap;
import hsj.outStock.com.activity.OutStockActivity;
import hsj.outStock.com.activity.OutStockCheckCarActivity;

/** 出栏管理
 * Created by 16486 on 2020/11/25.
 */

public class OutStockFragment extends BaseresTaskFragment {

    private static final String  TAG = "OutStockFragment";



    @Override
    public void initFragmentActivityView() {
        btnTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);


              final   LinearLayout clientLayout = new LinearLayout(activity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                //选择出栏类型
                LinearLayout outStockTypeLayout = new LinearLayout(activity);
                TextView tvOutStockType = new TextView(activity);
                tvOutStockType.setGravity(Gravity.CENTER);
                tvOutStockType.setText("请选择出栏类型");
                tvOutStockType.setLayoutParams(lp);
                final Spinner spOutStockType = new Spinner(activity);
                spOutStockType.setLayoutParams(lp);
                HashMap<String, Object> outStockTypeMap = new HashMap<>();
                outStockTypeMap.put("groupName", "OutStockType");
                spOutStockType.setLayoutParams(lp);
                SpinnerTools.change(activity, spOutStockType, outStockTypeMap, MethodEnum.GETDICBYGROUPNAME, DicInfo.class, "Name", "Value", new SpinnerPorts<DicInfo>() {
                    //根据出栏方式显示客户信息输入栏
                    @Override
                    public void selectChangeData(DicInfo data) {
                        if("1".equals( data.getValue())){

                            clientLayout.setVisibility(View.VISIBLE);
                        }else if("2".equals( data.getValue())){
                            clientLayout.setVisibility(View.GONE);
                        }
                    }
                },false);
                outStockTypeLayout.addView(tvOutStockType);
                outStockTypeLayout.addView(spOutStockType);
                layout.addView(outStockTypeLayout);



                //屠宰信息
                LinearLayout slaughterLayout = new LinearLayout(activity);
                slaughterLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView tvSlaughter = new TextView(activity);
                tvSlaughter.setText("请选择操作的屠宰场");
                tvSlaughter.setGravity(Gravity.CENTER);
                tvSlaughter.setLayoutParams(lp);
                final Spinner spinner = new Spinner(activity);
                HashMap<String,Object> deptMap = new HashMap<String, Object>();
                deptMap.put("DeptType",4);
                deptMap.put("AdressProvince", SPUtils.getSharedStringData(activity,"AdressProvince"));
                deptMap.put("AdressCity",SPUtils.getSharedStringData(activity,"AdressCity"));
                deptMap.put("AdressCounty",SPUtils.getSharedStringData(activity,"AdressCounty"));
                deptMap.put("TempType",1);
                SpinnerTools.change(activity,spinner,deptMap, MethodEnum.FARMERSALLLISTBYID, Farmers.class, "Name", "ID",null,false);
                spinner.setLayoutParams(lp);
                slaughterLayout.addView(tvSlaughter);
                slaughterLayout.addView(spinner);
                layout.addView(slaughterLayout);


                //客户信息填写
                clientLayout.setOrientation(LinearLayout.HORIZONTAL);

                final EditText etClient = new EditText(activity);
                etClient.setHint("请输入客户信息");
                final EditText etClientTelPhone = new EditText(activity);
                etClientTelPhone.setHint("请输入客户电话");
                etClient.setLayoutParams(lp);
                etClientTelPhone.setLayoutParams(lp);

                clientLayout.addView(etClient);
                clientLayout.addView(etClientTelPhone);


                layout.addView(clientLayout);
                DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
                    @Override
                    public void alertDialogFunction() {
                        //判断车牌号信息不能为空  或者出栏是屠宰场类型则可以湖绿
                     if("2".equals( spOutStockType.getTag().toString()) ||  !Utils.isStrEmpty(etClient.getText()) && !Utils.isStrEmpty(etClientTelPhone.getText())) {

                         Bundle bundle = new Bundle();
                         //传输屠宰场id
                         bundle.putString("outDeptID", spinner.getTag().toString());
                         bundle.putString("Client",etClient.getText().toString());
                         bundle.putString("ClientTelphone",etClientTelPhone.getText().toString());
                         bundle.putString("Type",spOutStockType.getTag().toString());
                         Utils.gotoActivity(activity, OutStockActivity.class, bundle, null);
                     }else{
                         UIHelper.ToastMessage(activity,"客户信息不能为空");
                     }
                    }
                },"请输入出栏相关信息",null,layout);

            }
        });

        adapter.setOnItemClickListener(new PaginationListView.Adapter.OnItemClickListener<TaskData.TaskDataList>() {
            @Override
            public void onItemClick(View view, TaskData.TaskDataList taskDataList, int position) {
                //用户点击的任务是已审核通过
                if(taskDataList.getStatus()==4){
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId",taskDataList.getID());
                    Utils.gotoActivity(activity,OutStockCheckCarActivity.class,bundle,null);
                }

                    UIHelper.ToastMessage(activity,"点击成功");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }



    @Override
    public String[] getArrayTitle() {
        return new String[]{"id","创建时间","处理屠宰场","出栏数量","任务状态"};
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected String setBtnTaskAdd() {
        return "新增出栏";
    }

    @Override
    protected boolean isShowQueryCriteria() {
        return true;
    }

    @Override
    protected String[] getTaskDataList() {
        return new String[]{"ID", "CreatorTime","OpDeptName","Num","StatusName"};
    }
}
