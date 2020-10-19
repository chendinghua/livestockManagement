package com.kymjs.app.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kymjs.app.R;
import com.kymjs.app.base_res.utils.base.BaseActivity;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.HandlerUtilsErrorCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.http.MethodEnum;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.tools.AnnotateUtil;
import com.kymjs.app.tools.BindView;
import com.kymjs.app.base_res.utils.tools.MD5;
import com.kymjs.app.base_res.utils.tools.NetUtil;
import com.kymjs.app.base_res.utils.ui.clearEdit.ClearEditText;
import com.kymjs.router.ActivityRouter;

import java.util.HashMap;
import java.util.Locale;

import hsj.expmle.com.home.activity.MainActivity;


/**
 * Created by 16486 on 2020/9/24.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(id =R.id.et_accountNumber)
    ClearEditText etAccountNumber;
    @BindView(id =R.id.et_password)
    ClearEditText etPassword;
    @BindView(id =R.id.tv_system_setting)
    TextView tvSystemSetting;
    @BindView(id =R.id.btn_login)
    Button btnLogin;

  //  Context mContext;

  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        AnnotateUtil.changeView(this);
        initUI();
        initListener();


    }*/

    @Override
    public boolean isOpenUHFModel() {
        return false;
    }

    /*    private void initUI() {
            etAccountNumber =(ClearEditText) findViewById(R.id.et_accountNumber);
            etPassword = (ClearEditText) findViewById(R.id.et_password);
            tvSystemSetting =(TextView) findViewById(R.id.tv_system_setting);
            btnLogin = (Button) findViewById(R.id.btn_login);
        }

        private void initListener() {
            tvSystemSetting.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
        }*/
    /*  @BindView(R.id.et_accountNumber)
    ClearEditText etAccountNumber;
    @BindView(R.id.et_password)
    ClearEditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_system_setting)
    TextView tvSystemSetting;
*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
     /*   AnnotateUtil.changeView(this);
        etAccountNumber =(ClearEditText) findViewById(R.id.et_accountNumber);
        etPassword = (ClearEditText) findViewById(R.id.et_password);
     //   ButterKnife.bind(this);
     //  btnLogin  =(Button) findViewById(R.id.btn_login);*/

        etAccountNumber =(ClearEditText) findViewById(R.id.et_accountNumber);
        etPassword = (ClearEditText) findViewById(R.id.et_password);
        tvSystemSetting =(TextView) findViewById(R.id.tv_system_setting);
        btnLogin = (Button) findViewById(R.id.btn_login);

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
                //存储用户权限
                SPUtils.setSharedStringData(mContext, "rightList", data.getString("RightList"));
                Log.d("JSON", "handlerExecutionFunction:" + data.getString("RightList"));
                ActivityRouter.startActivity(mContext, MainActivity.class);

            }
        }
    }, new HandlerUtilsErrorCallback() {
        @Override
        public void handlerErrorFunction(Message ms) {
            if (MethodEnum.LOGINSIGNIN.equals(ms.getData().getString("method"))) {
                Toast.makeText(mContext, "登陆失败", Toast.LENGTH_LONG).show();
            }
        }
    });

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
                map.put("Platform", 2);
                InteractiveDataUtil.interactiveMessage(LoginActivity.this, map, handler, MethodEnum.LOGINSIGNIN, InteractiveEnum.POST);
                break;
            case R.id.tv_system_setting:
                startActivity(new Intent(mContext, SettingActionUrlActivity.class));
                break;
        }
    }

    @Override
    public void handleTagdata(String rfid) {

    }

    @Override
    public void handleTriggerPress(boolean pressed) {

    }

    @Override
    public void scanCode(String code) {

    }

/*    @OnClick({R.id.tv_system_setting, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_system_setting:
                break;
            case R.id.btn_login:
                HashMap<String, Object> map = new HashMap<>();
                map.put("", etAccountNumber.getText().toString());
                map.put("userPwd", MD5.ganerateMD5(etPassword.getText().toString() + "H@S#J$2%0&1*8$"));
                map.put("Platform", 2);
                InteractiveDataUtil.interactiveMessage(LoginActivity.this, map, handler, MethodEnum.LOGINSIGNIN, InteractiveEnum.POST);

                break;
        }
    }*/
/*

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_system_setting:
                break;
            case R.id.btn_login:
                HashMap<String, Object> map = new HashMap<>();
                map.put("userName", etAccountNumber.getText().toString());
                map.put("userPwd", MD5.ganerateMD5(etPassword.getText().toString() + "H@S#J$2%0&1*8$"));
                map.put("Platform", 2);
                InteractiveDataUtil.interactiveMessage(LoginActivity.this, map, handler, MethodEnum.LOGINSIGNIN, InteractiveEnum.POST);

                break;
        }
    }
*/

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }*/
}
