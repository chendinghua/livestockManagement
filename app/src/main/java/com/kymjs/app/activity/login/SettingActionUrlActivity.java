package com.kymjs.app.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kymjs.app.R;
import com.kymjs.app.base_res.utils.ui.clearEdit.ClearEditText;
import com.kymjs.app.base_res.utils.utils.SharedPreferencesMenu;


/**  设置访问路径页面
 * Created by 16486 on 2020/3/10.
 */
public class  SettingActionUrlActivity extends Activity {

    ClearEditText etInputActionUrl;
    ClearEditText etInputUpdateUrl;
    Button btnSaveActionUrl;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_url);
        sp=getSharedPreferences("setting_action_url_config", Context.MODE_PRIVATE);
        editor = sp.edit();
        initUI();
        //获取接口路径
        etInputActionUrl.setText(sp.getString("actionUrl",SharedPreferencesMenu.actionUrl));
        //获取版本更新路径
        etInputUpdateUrl.setText(sp.getString("updateUrl", SharedPreferencesMenu.updateUrl));
    }

    private void initUI() {
        etInputUpdateUrl =(ClearEditText) findViewById(R.id.et_input_update_url);
        etInputActionUrl =(ClearEditText) findViewById(R.id.et_input_action_url);
        btnSaveActionUrl =(Button) findViewById(R.id.btn_save_action_url);
        btnSaveActionUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("actionUrl",etInputActionUrl.getText().toString());
                editor.putString("updateUrl",etInputUpdateUrl.getText().toString());
                editor.apply();
                Toast.makeText(SettingActionUrlActivity.this,"修改成功", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
