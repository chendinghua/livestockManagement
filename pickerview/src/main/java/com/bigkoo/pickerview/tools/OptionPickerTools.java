package com.bigkoo.pickerview.tools;
import android.app.Activity;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kymjs.app.base_res.utils.adapter.AutoAdapter;
import com.kymjs.app.base_res.utils.http.HandlerUtils;
import com.kymjs.app.base_res.utils.http.HandlerUtilsCallback;
import com.kymjs.app.base_res.utils.http.InteractiveDataUtil;
import com.kymjs.app.base_res.utils.http.InteractiveEnum;
import com.kymjs.app.base_res.utils.selectSpinner.tools.SpinnerPorts;
import com.kymjs.app.base_res.utils.tools.UIHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by 16486 on 2021/1/20.
 */
public class OptionPickerTools {

    /***
     * @param activity 上下文对象
     * @param tvSelect 下拉列表对象
     * @param method 调用的方法
     * @param className 返回集合类型
     * @param selectName 显示集合列表的内容字段
     * @param  selectTag 选中赋值的tag值
     */
    public static <T> ArrayList<T> optionsPickerChange (final Activity activity, final TextView tvSelect,
                                                        HashMap<String, Object> supplierMap, String method ,
                                                        final Class<T> className, final String selectName, final String selectTag,
                                                        final SpinnerPorts spinnerPorts, final boolean isDefault,final String title,final boolean loadding) {



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
                            Field[] fields = clazz.getDeclaredFields();//得到要修改的对象的属性数组
                            for (int i=0;i<fields.length;i++){
                                Field field = fields[i];   //得到每个属性
                                field.setAccessible(true);  //打破封装
                                String name = AutoAdapter.toUpperCaseFirstOne( field.getName());  //得到属性名
                                Class type=field.getType();     //得到该属性的类型

                                if(name.equalsIgnoreCase(selectName)){

                                    //找到与原对象相同的属性的set方法
                                    Method m=className.getMethod("set"+selectName,type);
                                    m.invoke(obj,(Object) "全部");//给该方法设置值
                                }else if(name.equalsIgnoreCase(selectTag)){
                                    //找到与原对象相同的属性的set方法
                                    Method mValue=className.getMethod("set"+selectTag,type);
                                    mValue.invoke(obj,-1);//给该方法设置值
                                }
                            }
                            datas.add(0,obj);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("SpinnerToolsTag", "handlerExecutionFunction: "+e.getMessage()+"   "+e.getLocalizedMessage());
                        }
                    }


                    final OptionsPickerView optionPicker = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            try {
                                Method  m=  datas.get(options1).getClass().getMethod("get"+selectTag);
                                tvSelect.setTag(m.invoke(datas.get(options1)));

                                Method showData =  datas.get(options1).getClass().getMethod("get"+selectName);
                                tvSelect.setText((String) showData.invoke(datas.get(options1)));
                                //判断是否有需要数据更新的接口
                                if(spinnerPorts!=null){
                                    //传递当前选中的对象信息
                                    spinnerPorts.selectChangeData(datas.get(options1));
                                }
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }
                    }).setTitleText(title)
                            .setDividerColor(Color.BLACK)
                            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                            .setContentTextSize(20)
                            .build();
                    optionPicker.setPicker(datas);//三级选择器


                    if(datas.size()>0 && loadding) {

                        Method m = datas.get(0).getClass().getMethod("get" + selectTag);
                        tvSelect.setTag(m.invoke(datas.get(0)));

                        Method showData = datas.get(0).getClass().getMethod("get" + selectName);
                        tvSelect.setText((String) showData.invoke(datas.get(0)));
                        if(spinnerPorts!=null){
                            //传递当前选中的对象信息
                            spinnerPorts.selectChangeData(datas.get(0));
                        }
                    }else if(datas.size()==0){
                        tvSelect.setTag("");
                        tvSelect.setText("");
                    }

                  tvSelect.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if(datas.size()>0) {
                              optionPicker.show();
                          }else{
                              UIHelper.ToastMessage(activity,"当前列表为空");
                          }
                      }
                  });

                 }catch (NullPointerException e){
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }), method, InteractiveEnum.GET);
        return datas;
    }
}
