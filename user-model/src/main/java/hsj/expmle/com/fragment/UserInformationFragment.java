package hsj.expmle.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.tools.AnnotateUtil;
import com.kymjs.app.base_res.utils.tools.BindView;
import hsj.expmle.com.R;
import hsj.expmle.com.R2;


/**
 * Created by Administrator on 2019/2/22/022.
 */

/***
 * 个人中心页面
 */
public class UserInformationFragment extends Fragment implements View.OnClickListener {



    @BindView(id = R2.id.btn_param_setting, textValue = R2.string.user_model_HandsetParameterSetting)
    private Button paramSetting;    //手持机rfid功率设置
    @BindView(id = R2.id.btn_user_info, textValue = R2.string.user_model_personalInformation)
    private Button userInfo;        //用户信息
    @BindView(id = R2.id.btn_user_exit, textValue = R2.string.user_model_signOut)
    private Button userExit;        //退出系统
 //   private MainActivity activity;

    @BindView(id = R2.id.btn_about, textValue = R2.string.user_model_About)
    private Button btnAbout;

    @BindView(id = R2.id.tv_userNameTitle)
    private TextView tvUserName;

    // private TextView tvDeptName;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_model_fragment_user_information, container, false);
        AnnotateUtil.initBindView(this, view);
     //   activity = (MainActivity) getActivity();
        //初始化UI控件
        initUI(view);
        //初始化控件点击事件
    //    initListener();
        return view;
    }

    private void initUI(View view) {
        tvUserName = (TextView) view.findViewById(R.id.tv_userNameTitle);
   //     tvUserName.setText("用户名 ：" +SPUtils.getSharedStringData(activity,"userName"));
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
        switch (v.getId()) {
            //设置pda的rfid功率
            case R2.id.btn_param_setting:
                /*Intent intent = new Intent(activity, PdaParamSettingActivity.class);
                startActivity(intent);*/
          //      Utils.gotoActivity(activity, PdaParamSettingActivity.class, null, null);

                break;
            //设置用户信息
            case R2.id.btn_user_info:
          //      Intent intent1 = new Intent(activity, UserInfoSettingActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putInt("UserId",activity.UserId);
                intent1.putExtras(bundle);
                startActivity(intent1);*//*
                Bundle bundle = new Bundle();*/
                // bundle.putInt("UserId",activity.UserId);
          //      Utils.gotoActivity(activity, UserInfoSettingActivity.class, null, null);

                break;
            //退出返回登录页面
            case R2.id.btn_user_exit:

            /*    HashMap<String,Object> userExitMap = new HashMap<>();
                userExitMap.put("UserName",activity.userName);
                userExitMap.put("Token",activity.token);
                InteractiveDataUtil.interactiveMessage(userExitMap,null, MethodEnum.LOGINSIGNOUT, InteractiveEnum.POST);
            */

                //       SharedPreferences.Editor editor=activity.getSharedPreferences("login_config", Context.MODE_PRIVATE).edit();
                //清除密码
             /*   editor.putString("password", "");
                editor.apply();*/

            //    Utils.gotoActivity(activity, LoginActivity.class, null, null);

                break;

            case R2.id.btn_about:
           //     Utils.gotoActivity(activity, AboutInfoActivity.class, null, null);


                break;
        }
    }


    public void changeView() {
        AnnotateUtil.changeView(this, view);
        //tvUserName.append("："+UserConfig.UserName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
     //   unbinder.unbind();
    }
}
