package hsj.expmle.com..fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import hsj.expmle.com.platformwms.R;
import hsj.expmle.com.platformwms.flowingdrawer.MainActivity;
import hsj.expmle.com.platformwms.tools.AnnotateUtil;
import hsj.expmle.com.platformwms.tools.BindView;
import hsj.expmle.com.platformwms.tools.UserConfig;
import hsj.expmle.com.platformwms.tools.Utils;
import hsj.expmle.com.platformwms.ui.activity.AboutInfoActivity;
import hsj.expmle.com.platformwms.ui.activity.LoginActivity;
import hsj.expmle.com.platformwms.ui.activity.PdaParamSettingActivity;
import hsj.expmle.com.platformwms.ui.activity.UserInfoSettingActivity;

/**
 * Created by Administrator on 2019/2/22/022.
 */

/***
 * 个人中心页面
 */
public class LabelFragment extends Fragment implements View.OnClickListener{


    @BindView(id=R.id.btn_param_setting,textValue = R.string.HandsetParameterSetting)
    private Button paramSetting;    //手持机rfid功率设置
    @BindView(id=R.id.btn_user_info,textValue = R.string.personalInformation)
    private Button userInfo;        //用户信息
    @BindView(id=R.id.btn_user_exit,textValue = R.string.signOut)
    private Button userExit;        //退出系统
    private MainActivity activity;

    @BindView(id=R.id.btn_about,textValue = R.string.about)
    private Button btnAbout;

    @BindView(id = R.id.tv_userNameTitle,textValue = R.string.UserName)
    private TextView tvUserName;

   // private TextView tvDeptName;
   View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_user_information,container,false);
        AnnotateUtil.initBindView(this,view);
        activity = (MainActivity) getActivity();
        //初始化UI控件
        initUI(view);
        //初始化控件点击事件
        initListener();
        return view;
    }
    private void initUI(View view) {
        tvUserName = view.findViewById(R.id.tv_userNameTitle);
        tvUserName.setText(getResources().getString(R.string.UserName)+"："+ UserConfig.UserName);
   //     tvDeptName.setText("所属部门："+UserConfig.deptName);
    }
    private void initListener() {
        paramSetting.setOnClickListener(this);
        userInfo.setOnClickListener(this);
        userExit.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //设置pda的rfid功率
            case R.id.btn_param_setting:
                /*Intent intent = new Intent(activity, PdaParamSettingActivity.class);
                startActivity(intent);*/
                Utils.gotoActivity(activity,PdaParamSettingActivity.class,null,null);

                break;
            //设置用户信息
            case R.id.btn_user_info:
                Intent intent1 = new Intent(activity, UserInfoSettingActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putInt("UserId",activity.UserId);
                intent1.putExtras(bundle);
                startActivity(intent1);*//*
                Bundle bundle = new Bundle();*/
               // bundle.putInt("UserId",activity.UserId);
                Utils.gotoActivity(activity,UserInfoSettingActivity.class,null,null);

                break;
            //退出返回登录页面
            case R.id.btn_user_exit:

            /*    HashMap<String,Object> userExitMap = new HashMap<>();
                userExitMap.put("UserName",activity.userName);
                userExitMap.put("Token",activity.token);
                InteractiveDataUtil.interactiveMessage(userExitMap,null, MethodEnum.LOGINSIGNOUT, InteractiveEnum.POST);
            */

         //       SharedPreferences.Editor editor=activity.getSharedPreferences("login_config", Context.MODE_PRIVATE).edit();
                //清除密码
             /*   editor.putString("password", "");
                editor.apply();*/

                Utils.gotoActivity(activity,LoginActivity.class,null,null);

                break;

            case  R.id.btn_about:
                Utils.gotoActivity(activity,AboutInfoActivity.class,null,null);


                break;
        }
    }


    public void changeView(){
      AnnotateUtil.changeView(this,view);
        tvUserName.append("："+UserConfig.UserName);
    }
}
