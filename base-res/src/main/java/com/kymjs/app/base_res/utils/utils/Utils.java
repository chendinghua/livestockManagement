package com.kymjs.app.base_res.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kymjs.app.base_res.utils.tools.AlertDialogCallBack;
import com.kymjs.app.base_res.utils.tools.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import devicelib.dao.Device;

public class Utils {

	// 转向另一个页面

	/**
	 *
	 * @param poFrom		原有页面
	 * @param poTo			跳转页面
	 * @param bundle		跳转传递的数据对象
	 * @param device		释放rfid模块对象
	 */
	public static void gotoActivity(Activity poFrom, Class<?> poTo,
                                    Bundle bundle, Device device) {
		Intent loIntent = new Intent(poFrom, poTo);
		if(bundle!=null) {
			loIntent.putExtras(bundle);
		}
		if(device!=null)
			device.destroy();
		poFrom.startActivity(loIntent);
	}

	public static void activityFinish(Activity poFrom,Device device){
			if(device!=null){
				device.destroy();
			}

				poFrom.finish();

	}


	public static void onBackDialog(  final  Activity activity,final  Device device ){
		DialogUtils.showAlertDialog(activity, new AlertDialogCallBack() {
			@Override
			public void alertDialogFunction() {
				Utils.activityFinish(activity,device);
			}
		}, "是否结束当前流程", null, null);
	}



	// 字符串是否为空（全是不可见字符的字符串认为是空）
	public static boolean isStrEmpty(Editable poStr) {
		String lsStr = poStr.toString();
		return isStrEmpty(lsStr);
	}

	// 字符串是否为空（全是不可见字符的字符串认为是空）
	public static boolean isStrEmpty(String psStr) {
		return psStr == null || psStr.trim().length() == 0;
	}
	
	/**
	 * 关闭软键盘
	 * @param mEditText 输入框
	 * @param mContext 上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	public static void fullScreen(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				//5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
				Window window = activity.getWindow();
				View decorView = window.getDecorView();
				//两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
				int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
				decorView.setSystemUiVisibility(option);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(Color.TRANSPARENT);
				//导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
			} else {
				Window window = activity.getWindow();
				WindowManager.LayoutParams attributes = window.getAttributes();
				int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
				int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
				attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
				window.setAttributes(attributes);
			}
		}
	}
	public static String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
}
