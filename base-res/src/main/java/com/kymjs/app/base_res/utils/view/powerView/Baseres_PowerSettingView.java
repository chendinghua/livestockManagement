package com.kymjs.app.base_res.utils.view.powerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.R2;
import com.kymjs.app.base_res.utils.dialog.ProgressDialog;
import com.kymjs.app.base_res.utils.tools.AnnotateUtil;
import com.kymjs.app.base_res.utils.tools.BindView;
import com.kymjs.app.base_res.utils.tools.SettingPower;
import com.kymjs.app.base_res.utils.tools.ViewUtils;

import devicelib.dao.Device;

/**
 * Created by 16486 on 2020/6/12.
 */

public class Baseres_PowerSettingView extends LinearLayout implements View.OnClickListener{
    //低频
    public static final Integer LOW=1;
    //中频
    public static final Integer INTERMEDIATE=2;
    //高频
    public static final Integer HIGH=3;

    @BindView(id= R2.id.lowFrequency,textValue = R2.string.baseres_lowFrequency,tag = 1)
    private Button lowFrequency;
    @BindView(id=R2.id.intermediateFrequency,textValue =R2.string.baseres_intermediateFrequency,tag = 2)
    private Button intermediateFrequency;
    @BindView(id=R2.id.highFrequency,textValue = R2.string.baseres_highFrequency,tag = 3)
    private Button highFrequency;
    private Context mContext;
    Device device;
    ProgressDialog dialog;
 //   View mView;




    public Baseres_PowerSettingView(Context context) {
        this(context, null);
    }

    public Baseres_PowerSettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Baseres_PowerSettingView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Baseres_PowerSettingView);
        int powerIndex = a.getInteger(R.styleable.Baseres_PowerSettingView_baseres_power_index, 1);
        setPowerButtonStatus(powerIndex);
        init(context, attrs);
    }



    public void init(Context context, AttributeSet attrs) {
    //    AnnotateUtil.initBindView(this,context, R.layout.baseres_power_setting_layout,this);
       // LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    //    mView = inflater.inflate(R.layout.baseres_power_setting_layout, this, true);

        View view = LayoutInflater.from(context).inflate(R.layout.baseres_power_setting_layout, this);
        lowFrequency =view. findViewById(R.id.lowFrequency);
        lowFrequency.setText(R.string.baseres_lowFrequency);
        lowFrequency.setTag(1);



        intermediateFrequency = view.findViewById(R.id.intermediateFrequency);
        intermediateFrequency.setText(R.string.baseres_intermediateFrequency);
        intermediateFrequency.setTag(2);

        highFrequency = view.findViewById(R.id.highFrequency);
        highFrequency.setText(R.string.baseres_highFrequency);
        highFrequency.setTag(3);

        mContext = context;
    }
    public void setListener(Device device, ProgressDialog dialog,int frequency){




        setPowerButtonStatus(frequency);
        this.device=device;
        this.dialog=dialog;
        lowFrequency.setOnClickListener(this);
        intermediateFrequency.setOnClickListener(this);
        highFrequency.setOnClickListener(this);
    }
    private void initPowerBackground(){
        ViewUtils.settingViewBackgroud(lowFrequency,mContext,R.drawable.baseres_power_left_white_bg);
        ViewUtils.settingViewTextColor(lowFrequency,mContext,R.color.baseres_home_title_bg);
        ViewUtils.settingViewBackgroud(intermediateFrequency,mContext,R.drawable.baseres_power_center_white_bg);
        ViewUtils.settingViewTextColor(intermediateFrequency,mContext,R.color.baseres_home_title_bg);
        ViewUtils.settingViewBackgroud(highFrequency,mContext,R.drawable.baseres_power_right_white_bg);
        ViewUtils.settingViewTextColor(highFrequency,mContext,R.color.baseres_home_title_bg);
    }
    public void changeView(){
        AnnotateUtil.changeView(this,mContext,R.layout.baseres_power_setting_layout,this);
    }
    @Override
    public void onClick(View v) {
        //初始化按钮背景颜色
     //   initPowerBackground();
       ViewUtils.settingViewTextColor((Button)v,mContext,R.color.baseres_btn_save_bg);
        setPowerButtonStatus(Integer.parseInt(v.getTag().toString()));
    }
    public  void setPowerButtonStatus(int index){
        if(device!=null) {
            initPowerBackground();
            SettingPower.updateFrequency(device, mContext, index, dialog);
            switch (index) {
                case 1:
                    ViewUtils.settingViewBackgroud(lowFrequency, mContext, R.drawable.baseres_power_left_blue_bg);
                    break;
                case 2:
                    ViewUtils.settingViewBackgroud(intermediateFrequency, mContext, R.drawable.baseres_power_center_blue_bg);
                    break;
                case 3:
                    ViewUtils.settingViewBackgroud(highFrequency, mContext, R.drawable.baseres_power_right_blue_bg);
                    break;
            }
        }
    }
}