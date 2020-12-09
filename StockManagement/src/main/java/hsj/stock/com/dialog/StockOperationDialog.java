package hsj.stock.com.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.base.entry.DicInfo;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerTools;

import java.util.HashMap;

import hsj.stock.com.R;


/** 栏位管理弹窗
 * Created by Administrator on 2019/4/13/013.
 */
public class StockOperationDialog extends Dialog {


   public  interface  PositiveButtonClickListener{

        public void onListener(StockInfo stockInfo,int status);


    }


    public StockOperationDialog(@NonNull Context context) {
        super(context);
    } public StockOperationDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private PositiveButtonClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private View layout;
        private StockOperationDialog dialog;
        //判断是修改还是新增
        private int status;


        private StockInfo stockInfo;

        Context mContext;


        public Builder(Context context) {
            mContext=context;

            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new StockOperationDialog(context, R.style.baseres_Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.stock_operation_dialog_layout, null);
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

        /**
         * 设置当前操作状态
         * @param status 1:新增 2:修改
         * @return
         */
        public Builder setStatus(int status){
            this.status=status;
            return this;
        }


        public Builder setPositiveButton(String positiveButtonText, PositiveButtonClickListener listener) {
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
        public StockOperationDialog createSingleButtonDialog() {
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
        public Builder initStockInfo(StockInfo stockInfo){
            this.stockInfo=stockInfo;
            return this;
        }
        //库位名称
        EditText etStockName;
        //栏位平方
        EditText etMaxArea;
        //栏位存放数量
        EditText etMaxNum;

        Spinner spStockType;

        EditText etSerialNo;

        /**
         * 创建双按钮对话框
         * @return
         */
        public StockOperationDialog createTwoButtonDialog() {

            dialog.setCanceledOnTouchOutside(false);//点击屏幕 dialog不消失
            dialog.setCancelable(false);//点击屏幕或返回按钮 dialog不消失


            showTwoButton();
            layout.findViewById(R.id.positiveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positiveButtonClickListener!=null && stockInfo!=null)
                     stockInfo.setName(etStockName.getText().toString());
                     stockInfo.setMaxArea("".equals( etMaxNum.getText().toString().trim())?0:Integer.parseInt(etMaxNum.getText().toString()));
                    stockInfo.setMaxNum( "".equals( etMaxNum.getText().toString().trim())?"0":etMaxNum.getText().toString());
                    stockInfo.setStatus(Integer.parseInt( spStockType.getTag().toString()));
                    stockInfo.setSerialNo(etSerialNo.getText().toString());

                    positiveButtonClickListener.onListener(stockInfo,status);
                }
            });
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
            etStockName = layout.findViewById(R.id.et_stock_operation_name);
            etMaxArea=  layout.findViewById(R.id.et_stock_operation_MaxArea);
            etMaxNum = layout.findViewById(R.id.et_stock_operation_MaxNum);
            spStockType= layout.findViewById(R.id.sp_stock_operation_status);

            etStockName.setText("");
            etMaxArea.setText("");
            etMaxNum.setText("0");


            //启动状态
            HashMap<String,Object> map = new HashMap<>();
            map.put("GroupName","StorageIsEnabled");
            SpinnerTools.change((Activity) mContext,spStockType,map, MethodEnum.GETDICBYGROUPNAME, DicInfo.class,"Name","Value",null,false);
            etSerialNo =  layout.findViewById(R.id.tv_stock_operation_serialNo);

            if(stockInfo!=null){


                  if(status==2){
                    etStockName.setText(stockInfo.getName());
                    etMaxNum.setText(stockInfo.getMaxNum());
                    etMaxArea.setText(stockInfo.getMaxArea()+"");
                      etSerialNo.setText(stockInfo.getSerialNo());
             //       spStockType.setSelection(stockInfo.getStatus()-1);
                }

            }





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