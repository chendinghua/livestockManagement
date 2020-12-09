package hsj.expmle.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;
import com.kymjs.app.base_res.utils.view.addAndSubView.Baseres_AddAndSubView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hsj.expmle.com.R;
import hsj.expmle.com.R2;
import hsj.expmle.com.entry.DicInfo;

/**
 * Created by Administrator on 2019/1/23/023.
 */

public class PdaParamSettingActivity extends Activity implements View.OnClickListener{
    private TextView lowFrequency;         //低频
    private TextView mediumFrequency;       //中频
    private TextView highFrequency;        //高频
    private Button save;                    //返回按钮
    private Button commit;                  //保存按钮

    Baseres_AddAndSubView asvPrevention;

    List<DicInfo> dicInfoList;//打印信息集合

    private String currentPrinMatchineName;     //当前打印机名

    private List<String> printMatchineNameList = new ArrayList<>(); //选择打印机的下拉列表

    /** popup窗口里的ListView */
    private ListView mTypeLv;

    /** popup窗口 */
    private List<PopupWindow> typeSelectPopups=new ArrayList<>();

    /** 模拟的假数据 */
    private List<String> testData;

    /** 数据适配器 */
    private ArrayAdapter<String> testDataAdapter;

    Button btnPrintMatchine;            //打印保存按钮

    TextView tvPrinterMatchine;

    int selectPrintMatchineIndex=0;

    private String savePrintMatchName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_model_activity_pda_param_setting);
       /* android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle(R2.string.user_model_ChangePassword);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R2.color.user_model_user_title_color,null)));
*/
        //初始化UI控件
        initUI();
        testData();
        typeSelectPopups.add(null);
        typeSelectPopups.add(null);
        typeSelectPopups.add(null);
        typeSelectPopups.add(null);
        lowFrequency.setOnClickListener(this);
        mediumFrequency.setOnClickListener(this);
        highFrequency.setOnClickListener(this);
        save.setOnClickListener(this);
        commit.setOnClickListener(this);
        btnPrintMatchine.setOnClickListener(this);
        tvPrinterMatchine.setOnClickListener(this);

        loading();


    }

    private void initUI() {
        lowFrequency=(TextView) findViewById(R.id.tv_low_frequency);
        mediumFrequency=(TextView)findViewById(R.id.tv_medium_frequency);
        highFrequency=(TextView)findViewById(R.id.tv_high_frequency);
        save=(Button) findViewById(R.id.btn_pda_param_save);
        commit=(Button)findViewById(R.id.btn_pda_param_commit);
        tvPrinterMatchine =(TextView) findViewById(R.id.tv_print_matchine);
        btnPrintMatchine=(Button)findViewById(R.id.btn_print_matchine);
        asvPrevention = findViewById(R.id.asv_prevention_day);
    }

    private void loading() {
        SharedPreferences sp=getSharedPreferences("pda_config", Context.MODE_PRIVATE);
        lowFrequency.setText(sp.getInt("lowFrequency",5)+"");
        mediumFrequency.setText(sp.getInt("mediumFrequency",15)+"");
        highFrequency.setText(sp.getInt("highFrequency",30)+"");

        SharedPreferences printMatchineConfig=getSharedPreferences("print_matchine_config", Context.MODE_PRIVATE);
        savePrintMatchName = printMatchineConfig.getString("PrintMatchineName","");

       asvPrevention.setInitValue(SPUtils.getSharedIntData(this,"PreventionDay",3));


        initPrintMatchineData();            //调用获取打印机信息
    }

    @Override
    public void onClick(View v) {
     //   switch (v.getId()) {
            if(v.getId() == R.id.tv_low_frequency) {
                // 点击控件后显示popup窗口
                initSelectPopup(lowFrequency, 0);
                // 使用isShowing()检查popup窗口(下拉列表)是否在显示状态
                if (typeSelectPopups.get(0) != null && !typeSelectPopups.get(0).isShowing()) {
                    typeSelectPopups.get(0).showAsDropDown(lowFrequency, 0, 10);
                }
            }else if(v.getId()== R.id.tv_medium_frequency) {
                // 点击控件后显示popup窗口
                initSelectPopup(mediumFrequency, 1);
                // 使用isShowing()检查popup窗口(下拉列表)是否在显示状态
                if (typeSelectPopups.get(1) != null && !typeSelectPopups.get(1).isShowing()) {
                    typeSelectPopups.get(1).showAsDropDown(mediumFrequency, 0, 10);
                }
            }else if(v.getId() == R.id.tv_high_frequency) {
                initSelectPopup(highFrequency, 2);
                // 使用isShowing()检查popup窗口(下拉列表)是否在显示状态
                if (typeSelectPopups.get(2) != null && !typeSelectPopups.get(2).isShowing()) {
                    typeSelectPopups.get(2).showAsDropDown(highFrequency, 0, 10);
                }
            }else if(v.getId()== R.id.btn_pda_param_save) {
                SharedPreferences.Editor editor = getSharedPreferences("pda_config", Context.MODE_PRIVATE).edit();
                editor.putInt("lowFrequency", Integer.parseInt(lowFrequency.getText().toString()));
                editor.putInt("mediumFrequency", Integer.parseInt(mediumFrequency.getText().toString()));
                editor.putInt("highFrequency", Integer.parseInt(highFrequency.getText().toString()));
                editor.apply();
                SPUtils.setSharedIntData(this,"PreventionDay",asvPrevention.getCurrentCount());

                UIHelper.ToastMessage(this, "设置保存成功");

                Utils.activityFinish(this,null);
            }
             else if(v.getId()== R.id.btn_pda_param_commit){

                }
             else if (v.getId()== R.id.tv_print_matchine) {
                initSelectPopup(tvPrinterMatchine, 3);
                // 使用isShowing()检查popup窗口(下拉列表)是否在显示状态
                if (typeSelectPopups.get(3) != null && !typeSelectPopups.get(3).isShowing()) {
                    typeSelectPopups.get(3).showAsDropDown(tvPrinterMatchine, 0, 10);
                }
            }else if (v.getId()== R.id.btn_print_matchine) {
                SharedPreferences.Editor printEditor = getSharedPreferences("print_matchine_config", Context.MODE_PRIVATE).edit();
                printEditor.putString("PrintMatchineName", dicInfoList.get(selectPrintMatchineIndex).getValue());
                printEditor.apply();
                UIHelper.ToastMessage(this, "设置使用打印机配置成功");
            }
        /*        break;
        }*/
    }

    /**
     * 初始化popup窗口
     */
    private void initSelectPopup(final TextView mSelectTv, final int index) {
        mTypeLv = new ListView(this);

        // 设置适配器
        testDataAdapter = new ArrayAdapter<String>(this, R.layout.user_model_popup_text_item,(mSelectTv.getId()==R.id.tv_print_matchine)?printMatchineNameList:testData);
        mTypeLv.setAdapter(testDataAdapter);
        // 设置ListView点击事件监听
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data="";
                if(mSelectTv.getId()==R.id.tv_print_matchine){
                    data = printMatchineNameList.get(position);
                    selectPrintMatchineIndex = position;
                }else {
                    data = testData.get(position);
                }
                // 把选择的数据展示对应的TextView上
                mSelectTv.setText(data);

                // 选择完后关闭popup窗口
                typeSelectPopups.get(index).dismiss();
            }
        });

        final PopupWindow typeSelectPopup = new PopupWindow(mTypeLv, mSelectTv.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.user_model_bg_filter_down);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
        typeSelectPopups.add(index,typeSelectPopup);
    }
    /**
     * 模拟假数据
     */
    private void testData() {
        testData = new ArrayList<>();
        for (int i =1;i<=30;i++){
            testData.add(""+i);
        }
    }
    HandlerUtils handlerUtils = new HandlerUtils(this, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
            dicInfoList =   JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"),DicInfo.class);
            printMatchineNameList.clear();
            if(dicInfoList!=null){

                  Log.d("url", " 设置当前控件 "+dicInfoList.get(0).getName());

              for (int i = 0;i<dicInfoList.size();i++){
                  if(dicInfoList.get(i).getValue().equals(savePrintMatchName)){
                      tvPrinterMatchine.setText(dicInfoList.get(i).getName());
                  }

                  printMatchineNameList.add(dicInfoList.get(i).getName());
                  Log.d("url", " "+dicInfoList.get(i).getName());
              }
              if(tvPrinterMatchine.getText().toString().trim().equals("")){
                  tvPrinterMatchine.setText(dicInfoList.get(0).getName());
              }
            }
        }
    });

    private void initPrintMatchineData(){

        HashMap<String,Object> map = new HashMap<>();
        map.put("groupName","PrintMatchine");
        InteractiveDataUtil.interactiveMessage(PdaParamSettingActivity.this,map,handlerUtils, MethodEnum.GETDICBYGROUPNAME, InteractiveEnum.GET);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
