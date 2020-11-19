package hsj.expmle.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.tools.AnnotateUtil;
import com.kymjs.app.base_res.utils.tools.RCaster;
import com.kymjs.app.base_res.utils.utils.SPUtils;
import com.kymjs.app.base_res.utils.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hsj.expmle.com.R;
import hsj.expmle.com.R2;
import hsj.expmle.com.activity.AboutInfoActivity;
import hsj.expmle.com.activity.PdaParamSettingActivity;
import hsj.expmle.com.activity.UserInfoSettingActivity;


/**
 * Created by Administrator on 2019/2/22/022.
 */

/***
 * 个人中心页面
 */
public class UserInformationFragment extends Fragment{


    @butterknife.BindView(R2.id.iv_user_head)
    ImageView ivUserHead;
    @butterknife.BindView(R2.id.tv_userNameTitle)
    TextView tvUserNameTitle;
    @butterknife.BindView(R2.id.btn_user_info)
    Button btnUserInfo;
    @butterknife.BindView(R2.id.btn_param_setting)
    Button btnParamSetting;
    @butterknife.BindView(R2.id.btn_about)
    Button btnAbout;
    @butterknife.BindView(R2.id.btn_user_exit)
    Button btnUserExit;
    Unbinder unbinder;
   /* @BindView(id = R2.id.btn_param_setting, textValue = R2.string.user_model_HandsetParameterSetting)
    private Button paramSetting;    //手持机rfid功率设置
    @BindView(id = R2.id.btn_user_info, textValue = R2.string.user_model_personalInformation)
    private Button userInfo;        //用户信息
    @BindView(id = R2.id.btn_user_exit, textValue = R2.string.user_model_signOut)
    private Button userExit;        //退出系统


    @BindView(id = R2.id.btn_about, textValue = R2.string.user_model_About)
    private Button btnAbout;

    @BindView(id = R2.id.tv_userNameTitle)
    private TextView tvUserName;*/

    // private TextView tvDeptName;
    private Activity activity;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_model_fragment_user_information, container, false);
      //  AnnotateUtil.initBindView(this, view);
        unbinder = ButterKnife.bind(this, view);
        activity = getActivity();
        //初始化UI控件
        initUI(view);
        //初始化控件点击事件
            initListener();

        return view;
    }

    private void initUI(View view) {
     //   tvUserNameTitle = (TextView) view.findViewById(R.id.tv_userNameTitle);
        tvUserNameTitle.setText("用户名 ：" + SPUtils.getSharedStringData(activity,"userName"));
        //     tvDeptName.setText("所属部门："+UserConfig.deptName);
    }

    private void initListener() {
      /*  btnParamSetting.setOnClickListener(this);
        btnUserInfo.setOnClickListener(this);
        btnUserExit.setOnClickListener(this);
        btnAbout.setOnClickListener(this);*/
    }
    @OnClick({R2.id.btn_param_setting, R2.id.btn_user_info, R2.id.btn_user_exit,R2.id.btn_about})
    public void onClickListener(View v) {
        RCaster rcaster = new RCaster(R.class,R2.class);

        Log.d("fragmentOnClick:", "onClick: "+v.getId() +"    "+rcaster.cast(v.getId())+"           "+R2.id.btn_param_setting);

        switch (rcaster.cast(v.getId())) {
            //设置pda的rfid功率
            case R2.id.btn_param_setting:
                /*Intent intent = new Intent(activity, PdaParamSettingActivity.class);
                startActivity(intent);*/
                Utils.gotoActivity(activity, PdaParamSettingActivity.class, null, null);

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
                Utils.gotoActivity(activity, UserInfoSettingActivity.class, null, null);

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
                Utils.gotoActivity(activity, AboutInfoActivity.class, null, null);


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
        unbinder.unbind();
    }
}
