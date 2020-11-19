package com.kymjs.app.base_res.utils.view.dateTime;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.view.dateTime.kycalendar.KyCalendar;
import com.kymjs.app.base_res.utils.view.dateTime.kycalendar.OnSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by 16486 on 2020/9/9.
 */

public class SelectDateTime {
    public static void selectDate(final TextView tvPutOnTime, Context context) {
        KyCalendar dateWidget = new KyCalendar(context);
        dateWidget.setFormat("yyyy-MM-dd");//设置日期格式,默认为"yyyy/MM/dd"
        dateWidget.setText(tvPutOnTime.getText().toString().trim());
        final PopupWindow popupWindow = new PopupWindow(dateWidget, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dateWidget.setOnSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(String date) {
                tvPutOnTime.setText(date);
                popupWindow.dismiss();
            }
        });

        popupWindow.setFocusable(true);//获得焦点
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(tvPutOnTime, 0, 0);
    }

    public static void initCurrentTime(TextView tvTime){
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));

    }
}
