package com.kymjs.app.activity.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kymjs.app.R;
import com.kymjs.app.activity.home.activity.MainActivity;
import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.http.OkHttpUtils;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.tools.MD5;
import com.kymjs.app.base_res.utils.tools.NetUtil;
import com.kymjs.app.base_res.utils.ui.clearEdit.ClearEditText;
import com.kymjs.router.ActivityRouter;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by 16486 on 2020/9/24.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.et_accountNumber)
    ClearEditText etAccountNumber;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.tv_system_setting)
    TextView tvSystemSetting;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {


        btnLogin.setOnClickListener(this);
        tvSystemSetting.setOnClickListener(this);
    }

    HandlerUtils handler = new HandlerUtils(mContext, new HandlerUtilsCallback() {
        @Override
        public void handlerExecutionFunction(Message msg) {
            if (MethodEnum.LOGINSIGNIN.equals(msg.getData().getString("method"))) {
                //点击记住密码把用户名和密码保存到本地文件里面

                Toast.makeText(mContext, "登陆成功", Toast.LENGTH_LONG).show();
                JSONObject data = JSON.parseObject(msg.getData().getString("result")).getJSONObject("Data");
                //存储用户名
                SPUtils.setSharedStringData(mContext, "userName", data.getString("Name"));
                //存储用户id
                SPUtils.setSharedIntData(mContext, "UserID", data.getIntValue("UserID"));
                //存储部门id
                SPUtils.setSharedIntData(mContext,"DeptID",data.getIntValue("DeptID"));

                SPUtils.setSharedStringData(mContext,"AdressProvince",data.getString("AdressProvince"));

                SPUtils.setSharedStringData(mContext,"AdressCity",data.getString("AdressCity"));

                SPUtils.setSharedStringData(mContext,"AdressCounty",data.getString("AdressCounty"));
                //用户类型 1、合作商 2、畜牧局 3、养猪户  4、屠宰场
                SPUtils.setSharedIntData(mContext,"DeptType",data.getIntValue("DeptType"));

                SPUtils.setSharedIntData(mContext,"Type",data.getIntValue("Type"));

                SPUtils.setSharedStringData(mContext,"DeptName",data.getString("DeptName"));

                //存储用户权限
                SPUtils.setSharedStringData(mContext, "rightList", data.getString("RightList"));
                Log.d("JSON", "handlerExecutionFunction:" + data.getString("RightList"));
                ActivityRouter.startActivity(mContext, MainActivity.class);

            }
        }
    },null);

    /**
     * 更改应用语言
     *
     * @param locale
     */
    public void changeAppLanguage(Locale locale) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        //重新启动Activity
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登陆按钮
            case R.id.btn_login:
                if (!NetUtil.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, R.string.NoNetworkConnection, Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("userName", etAccountNumber.getText().toString());
                map.put("userPwd", MD5.ganerateMD5(etPassword.getText().toString() + "H@S#J$2%0&1*8$"));
                map.put("deviceNo", OkHttpUtils.getMacAddress(mContext));
                map.put("Platform", 2);
                InteractiveDataUtil.interactiveMessage(LoginActivity.this, map, handler, MethodEnum.LOGINSIGNIN, InteractiveEnum.POST);
                break;
            case R.id.tv_system_setting:
                startActivity(new Intent(mContext, SettingActionUrlActivity.class));
                break;
        }
    }
}
