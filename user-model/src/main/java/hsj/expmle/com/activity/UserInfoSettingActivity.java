package hsj.expmle.com.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;
import com.kymjs.app.base_res.utils.tools.MD5;
import com.kymjs.app.base_res.utils.tools.UIHelper;
import com.kymjs.app.base_res.utils.utils.SPUtils;

import java.util.HashMap;

import hsj.expmle.com.R;


/**  用户信息设置页面
 * Created by Administrator on 2019/2/22/022.
 */

public class UserInfoSettingActivity extends Activity implements View.OnClickListener{

    EditText oldPwd;                //旧密码
    EditText newPwd;                //新密码
    EditText reNewPwd;              //重新输入的新密码
    Button savePwd;                 //保存密码按钮
    Button btnQuit;                 //返回上一页
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_model_activity_user_info_setting);
        mContext=this;
        ActionBar actionBar = getActionBar();

        /*actionBar.setTitle(R.string.user_model_ChangePassword);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.user_model_user_title_color,null)));
*/
        //初始化UI控件
        initUI();
        //初始化控件点击事件
        initListener();
    }
    private void initUI() {
        oldPwd =(EditText) findViewById(R.id.et_old_pwd);
        newPwd =(EditText) findViewById(R.id.et_new_pwd);
        reNewPwd =(EditText) findViewById(R.id.et_re_new_pwd);
        savePwd =(Button) findViewById(R.id.btn_reset_pwd_save);
        btnQuit =(Button) findViewById(R.id.btn_quit_pwd_setting);
    }
    private void initListener() {
        savePwd.setOnClickListener(this);
        btnQuit.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
    //    switch (v.getId()){
            if(v.getId() == R.id.btn_reset_pwd_save) {
                final String newPwdStr = newPwd.getText().toString();
                final String oldPwdStr = oldPwd.getText().toString();
                String rePwdStr = reNewPwd.getText().toString();
                if (newPwdStr.trim().length() < 6 || oldPwdStr.trim().length() < 6 || rePwdStr.trim().length() < 6) {
                    UIHelper.ToastMessage(UserInfoSettingActivity.this, R.string.user_model_PasswordLengthCannotBeLessThan);
                    return;
                } else if (!newPwdStr.equals(rePwdStr)) {
                    UIHelper.ToastMessage(UserInfoSettingActivity.this, R.string.user_model_TwoDifferentPasswordEntries);
                    return;
                } else if (newPwdStr.equals(oldPwdStr)) {
                    UIHelper.ToastMessage(UserInfoSettingActivity.this, R.string.user_model_OldPasswordAndNewPasswordCannotBeTheSame);
                    return;
                }
                DialogUtils.showAlertDialog(this, new AlertDialogCallBack() {
                    @Override
                    public void alertDialogFunction() {
                        HandlerUtils handler = new HandlerUtils(UserInfoSettingActivity.this, new HandlerUtilsCallback() {
                            @Override
                            public void handlerExecutionFunction(Message msg) {
                                UIHelper.ToastMessage(UserInfoSettingActivity.this, R.string.user_model_PasswordModifiedSuccessfully);
                                finish();
                            }
                        }, new HandlerUtilsErrorCallback() {
                            @Override
                            public void handlerErrorFunction(Message ms) {
                                UIHelper.ToastMessage(UserInfoSettingActivity.this, R.string.user_model_PasswordModificationFailed);
                                finish();
                            }
                        });
                        HashMap<String, Object> param = new HashMap<>();
                        param.put("UserID", SPUtils.getSharedIntData(mContext, "UserId"));
                        param.put("OldPwd", MD5.ganerateMD5(oldPwdStr + "H@S#J$2%0&1*8$"));
                        param.put("NewPwd", MD5.ganerateMD5(newPwdStr + "H@S#J$2%0&1*8$"));
                        InteractiveDataUtil.interactiveMessage(UserInfoSettingActivity.this, param, handler, MethodEnum.RESETPWD, InteractiveEnum.POST);
                    }
                }, R.string.user_model_AreYouSureToChangeThePassword, null, null);
            }else  if (v.getId() == R.id.btn_quit_pwd_setting){
                finish();
            }
           /*     break;
            case R.id.btn_quit_pwd_setting:

                break;*/
      //  }
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
