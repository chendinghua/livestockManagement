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
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
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
                                               final SpinnerPorts spinnerPorts, final boolean isDefault) {
            final ArrayList  datas = new ArrayList<T>();
            InteractiveDataUtil.interactiveMessage(activity,supplierMap, new HandlerUtils(activity, new HandlerUtilsCallback() {
                @Override
                public void handlerExecutionFunction(Message msg) {
                    try {
                    datas.clear();
                    String result = msg.getData().getString("result");
                        Log.d("tagData", "handlerExecutionFunction: "+result);
                    datas.addAll(JSON.parseArray(JSON.parseObject(result).getString("Data"), className));

                        //设置默认参数
                        if(isDefault){

                            try{
                            Class<? extends Object> clazz = className.getClass();

                           Object obj =     className.newInstance();
                         //   Class<? extends Object> clazzUpdate = update.getClass();
                            Field[] fields = clazz.getDeclaredFields();//得到要修改的对象的属性数组
                            for (int i=0;i<fields.length;i++){
                                Field field = fields[i];   //得到每个属性
                                field.setAccessible(true);  //打破封装
                                String name = AutoAdapter.toUpperCaseFirstOne( field.getName());  //得到属性名
                                Class type=field.getType();     //得到该属性的类型

                                if(name.equalsIgnoreCase(selectName)){
                                   /* Object spName = field.get("get"+selectName); //得到该属性的get方法的值
                                    if(spName!=null){*/
                                        //找到与原对象相同的属性的set方法
                                        Method m=className.getMethod("set"+selectName,type);
                                        m.invoke(obj,(Object) "全部");//给该方法设置值
                                  //  }
                                }else if(name.equalsIgnoreCase(selectTag)){
                                   /* Object spValue = field.get("get"+selectTag);
                                    if(  spValue!=null){*/
                                        //找到与原对象相同的属性的set方法
                                        Method mValue=className.getMethod("set"+selectTag,type);
                                        mValue.invoke(obj,-1);//给该方法设置值
                                  //  }

                                }



                            }
                            datas.add(0,obj);

                        } catch (Exception e) {
                            e.printStackTrace();
                                Log.d("SpinnerToolsTag", "handlerExecutionFunction: "+e.getMessage()+"   "+e.getLocalizedMessage());
                        }


                        }



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


    //首字母转大写，转小写就加32
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }



}
