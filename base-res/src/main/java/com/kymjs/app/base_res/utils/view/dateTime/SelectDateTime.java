package com.kymjs.app.base_res.utils.view.dateTime;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.view.dateTime.kycalendar.KyCalendar;
import com.kymjs.app.base_res.utils.view.dateTime.kycalendar.OnSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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


    //获得明天日期
    public static String getTomoData(){

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
        String[] months_little = { "4", "6", "9", "11" };

        List<String> list_big = Arrays.asList(months_big);
        List<String> list_little = Arrays.asList(months_little);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        if(day==30){
            if (list_big.contains(String.valueOf(month))){
                day=31;
            }
            if (list_little.contains(String.valueOf(month))){
                day=1;
                if(month==12){
                    year++;
                    month=1;
                }else{
                    month++;
                }

            }
        }else if(day==31){
            day=1;
            if(month==12){
                year++;
                month=1;
            }else{
                month++;
            }

        }else {
            day++;
        }
        String data;
        if( day<10)
        {

            data=year+"-"+"0"+month+"-"+"0"+day;
        }
        else
        {
            data=year+"-"+"0"+month+"-"+day;
        }

        return data;
    }

}
