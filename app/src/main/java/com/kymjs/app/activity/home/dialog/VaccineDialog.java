package com.kymjs.app.activity.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.app.R;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.base.entry.vaccine.VaccineInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 16486 on 2020/11/18.
 */

public class VaccineDialog extends Dialog {

    public VaccineDialog(@NonNull Context context) {
        super(context);
    }

    public VaccineDialog(Context context, int theme) {
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

        private AdapterView.OnItemClickListener onItemClickListener;

        private View layout;
        private VaccineDialog dialog;


        List<VaccineInfo> vaccineInfos = new ArrayList<>();
        Context mContext;






        public Builder(Context context) {
            mContext = context;
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new VaccineDialog(context, com.kymjs.app.base_res.R.style.baseres_Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.vaccine_dialog_layout, null);
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
        public VaccineDialog createSingleButtonDialog() {
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
        public VaccineDialog.Builder initVaccine(List<VaccineInfo> vaccineInfos){
            this.vaccineInfos.clear();
            this.vaccineInfos.addAll(vaccineInfos);
            return this;
        }

        public VaccineDialog.Builder initOnItemSelectedListener(AdapterView.OnItemClickListener onItemClickListener ){
            this.onItemClickListener = onItemClickListener;
            return this;
        }






        ListView lvVaccineList;

        AutoAdapter<VaccineInfo> adapter;

        /**
         * 创建双按钮对话框
         * @return
         */
        public VaccineDialog createTwoButtonDialog() {

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
                lvVaccineList =  layout.findViewById(R.id.lv_vaccine_list);
            if(vaccineInfos!=null){
                adapter = new AutoAdapter<>(mContext,vaccineInfos,"StorageCount","VaccineName","ProductName");
                lvVaccineList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            if(onItemClickListener!=null)
            lvVaccineList.setOnItemClickListener(onItemClickListener);


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