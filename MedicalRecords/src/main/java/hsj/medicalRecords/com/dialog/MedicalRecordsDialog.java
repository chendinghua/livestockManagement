package hsj.medicalRecords.com.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.utils.base.entry.ScanResult;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hsj.medicalRecords.com.R;
import hsj.medicalRecords.com.entry.MedicalRList;


/**
 * Created by Administrator on 2019/4/13/013.
 */

public class MedicalRecordsDialog extends Dialog {

    public MedicalRecordsDialog(@NonNull Context context) {
        super(context);
    } public MedicalRecordsDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private View layout;
        private MedicalRecordsDialog dialog;




        private ScanResult scanResult;

        Context mContext;

        public Builder(Context context) {
            mContext=context;
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new MedicalRecordsDialog(context, R.style.medicalrecords_Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.medicalrecords_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setSingleButton(String singleButtonText, View.OnClickListener listener) {
            this.singleButtonText = singleButtonText;
            this.singleButtonClickListener = listener;
            return this;
        }

        /**
         * 创建单按钮对话框
         * @return
         */
        public MedicalRecordsDialog createSingleButtonDialog() {
            showSingleButton();
            layout.findViewById(R.id.singleButton).setOnClickListener(singleButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“返回”
            if (singleButtonText != null) {
                ((Button) layout.findViewById(R.id.singleButton)).setText(singleButtonText);
            } else {
                ((Button) layout.findViewById(R.id.singleButton)).setText("返回");
            }
            create();
            return dialog;
        }
        public Builder initScanResult(ScanResult scanResult){
            this.scanResult=scanResult;
            return this;
        }

        /**
         * 创建双按钮对话框
         * @return
         */
        public MedicalRecordsDialog createTwoButtonDialog() {

            dialog.setCanceledOnTouchOutside(false);//点击屏幕 dialog不消失
            dialog.setCancelable(false);//点击屏幕或返回按钮 dialog不消失


            showTwoButton();
            layout.findViewById(R.id.positiveButton).setOnClickListener(positiveButtonClickListener);
            layout.findViewById(R.id.negativeButton).setOnClickListener(negativeButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“是”和“否”
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
            } else {
                ((Button) layout.findViewById(R.id.positiveButton)).setText("是");
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
            } else {
                ((Button) layout.findViewById(R.id.negativeButton)).setText("否");
            }

            create();
            return dialog;
        }
        //就诊原因
        AutoCompleteTextView autoCompleteTextView;
        //病情描述
        EditText etCondition;

        public String getMedical(){

            return autoCompleteTextView.getText().toString();
        }

        public String getCondition(){

            String condition="";

            if(etCondition!=null){
                condition = etCondition.getText().toString();
            }
            return condition;
        }



        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private void create() {
            if (message != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {       //如果使用Builder的setContentview()方法传入了布局，则使用传入的布局
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            autoCompleteTextView = layout.findViewById(R.id.auto_medical_list);
            etCondition = layout.findViewById(R.id.et_medicalrecords_Condition);

            if(scanResult!=null){
                ((TextView)layout.findViewById(R.id.tv_medicalrecords_serialNo)).setText(scanResult.getSerialNo());

                ((TextView)layout.findViewById(R.id.tv_medicalrecords_productName)).setText(scanResult.getProductName());

            }

         //   SpinnerTools.change((Activity) mContext,spMedical,null, MethodEnum.GETMEDICALRLIST, MedicalRList.class,"Illness","ID",null);
            InteractiveDataUtil.interactiveMessage((Activity) mContext,null,new HandlerUtils(mContext, new HandlerUtilsCallback() {
                @Override
                public void handlerExecutionFunction(Message msg) {
                    List<MedicalRList> medicalRLists =   JSON.parseArray(JSON.parseObject(msg.getData().getString("result")).getString("Data"),MedicalRList.class);
                    String [] res = new String[medicalRLists.size()];
                    for(int i =0;i<medicalRLists.size();i++){
                        res[i]=medicalRLists.get(i).getName();
                    }
                    Log.d("arrayDatalist", "handlerExecutionFunction: "+ Arrays.toString(res));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_dropdown_item_1line,res);
                    autoCompleteTextView.setAdapter(adapter);

                }
            }),MethodEnum.GETMEDICALRLIST, InteractiveEnum.GET);


            dialog.setContentView(layout);
            dialog.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
        }

        /**
         * 显示双按钮布局，隐藏单按钮
         */
        private void showTwoButton() {
            layout.findViewById(R.id.singleButtonLayout).setVisibility(View.GONE);
            layout.findViewById(R.id.twoButtonLayout).setVisibility(View.VISIBLE);
        }

        /**
         * 显示单按钮布局，隐藏双按钮
         */
        private void showSingleButton() {
            layout.findViewById(R.id.singleButtonLayout).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.twoButtonLayout).setVisibility(View.GONE);
        }

    }
}