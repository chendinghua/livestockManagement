package hsj.outStock.com;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kymjs.app.base_res.utils.fragment.BaseresTaskFragment;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.Utils;

import hsj.outStock.com.activity.OutStockActivity;

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

                final EditText editText = new EditText(activity);

                layout.addView(editText);
                DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
                    @Override
                    public void alertDialogFunction() {
                        //判断车牌号信息不能为空
                        if(!"".equals(editText.getText().toString().trim())){
                            Bundle bundle  = new Bundle();
                            bundle.putString("Car",editText.getText().toString());
                            Utils.gotoActivity(activity, OutStockActivity.class,bundle,null);
                        }else{
                            UIHelper.ToastMessage(activity,"车牌号信息不能为空");
                        }

                    }
                },"请输入有效车牌号码",null,layout);

            }
        });

    }



    @Override
    public String[] getArrayTitle() {
        return new String[0];
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.baseres_task_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
