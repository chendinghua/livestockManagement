package com.kymjs.app.base_res.utils.selectSpinner.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.kymjs.app.base_res.R;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16486 on 2020/10/24.
 */

public class SpinnerTools {


        /***
         * @param activity 上下文对象
         * @param spinner 下拉列表对象
         * @param method 调用的方法
         * @param className 返回集合类型
         * @param selectName 显示集合列表的内容字段
         * @param  selectTag 选中赋值的tag值
         */
        public static <T> ArrayList<T> change (final Activity activity, final Spinner spinner,
                                               HashMap<String, Object> supplierMap, String method ,
                                               final Class<T> className, final String selectName, final String selectTag,
                                                final SpinnerPorts spinnerPorts) {
            final ArrayList  datas = new ArrayList<T>();

           /* HashMap<String, Object> supplierMap = new HashMap<>();
            supplierMap.put(paramName, paramValue);*/
            InteractiveDataUtil.interactiveMessage(activity,supplierMap, new HandlerUtils(activity, new HandlerUtilsCallback() {
                @Override
                public void handlerExecutionFunction(Message msg) {
                    try {
                    datas.clear();
                    String result = msg.getData().getString("result");
                        Log.d("tagData", "handlerExecutionFunction: "+result);
                    datas.addAll(JSON.parseArray(JSON.parseObject(result).getString("Data"), className));
                    if(spinner!=null) {
                        final List<String> strs = new ArrayList<String>();
                        final ArrayAdapter adapter = new ArrayAdapter<String>(activity, R.layout.baseres_popup_text_item, strs);
                        spinner.setAdapter(adapter);
                        if(datas.size()>0){
                            for (int i = 0;i<datas.size();i++){

                                    Method m =  datas.get(i).getClass().getMethod("get"+selectName);
                                    strs.add((String) m.invoke(datas.get(i)));

                            }
                            //初始化下标为0默认的tag
                            Method  m=     datas.get(0).getClass().getMethod("get"+selectTag);
                            spinner.setTag(m.invoke(datas.get(0)));

                        }

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {

                                Method  m=     datas.get(position).getClass().getMethod("get"+selectTag);
                                spinner.setTag(m.invoke(datas.get(position)));
                                    //判断是否有需要数据更新的接口
                                   if(spinnerPorts!=null){
                                       //传递当前选中的对象信息
                                       spinnerPorts.selectChangeData(datas.get(position));
                                   }

                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Log.d("tagList", "handlerExecutionFunction: strs  " + Arrays.toString(strs.toArray()));
                        Log.d("tagList", "handlerExecutionFunction: " + Arrays.toString(datas.toArray()));
                        adapter.notifyDataSetChanged();


                    }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }), method, InteractiveEnum.GET);
            return datas;
        }



}
