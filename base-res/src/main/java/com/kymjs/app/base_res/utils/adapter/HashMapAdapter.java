package com.kymjs.app.base_res.utils.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.app.base_res.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 16486 on 2021/1/26.
 */

public class HashMapAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String,String>> list;
    private int fixedLength=-1;                //设置固定长度默认为-1则表示不设置固定长度

    public HashMapAdapter(Context context, List<HashMap<String,String>> list) {
        this.context = context;
        this.list = list;
    }
    public HashMapAdapter(Context context, List<HashMap<String,String>> list, int fixedLength) {
        this.context = context;
        this.list = list;
        this.fixedLength=fixedLength;
    }

    @Override
    public int getCount() {
        int len = 0;
        if(list!=null)
            len= list.size();
        return len;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View contextView, ViewGroup viewGroup) {

        // 通过LayoutInflater 类的 from 方法 再 使用 inflate()方法得到指定的布局
        // 得到ListView中要显示的条目的布局
        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.baseres_activity_auto_item, null);
        // 从要显示的条目布局中 获得指定的组件

        LinearLayout layoutAuto = view.findViewById(R.id.layout_auto);

        HashMap<String,String> maps=  list.get(position);
        Set<Map.Entry<String, String>> entrySet = maps.entrySet();
        Iterator<Map.Entry<String, String>> validDataIterator = entrySet.iterator();
        while(validDataIterator.hasNext()) {
            Map.Entry<String, String> validDataKeyValueMap = validDataIterator.next();
            TextView tvTitle = new TextView(view.getContext());
            tvTitle.setText(validDataKeyValueMap.getValue());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            //设置固定宽度
            if (fixedLength != -1) {
                lp.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fixedLength, context.getResources().getDisplayMetrics()));
            }
            tvTitle.setLayoutParams(lp);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setMaxLines(2);
            tvTitle.setEllipsize(TextUtils.TruncateAt.END);
            layoutAuto.addView(tvTitle);
        }
        return view;
    }


    public void claerList (){
        list.clear();
        notifyDataSetChanged();
    }
}
