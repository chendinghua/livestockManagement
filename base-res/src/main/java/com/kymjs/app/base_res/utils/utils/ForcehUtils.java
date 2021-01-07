package com.kymjs.app.base_res.utils.utils;

import android.util.Log;

import com.kymjs.app.base_res.utils.adapter.AutoAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 16486 on 2021/1/5.
 */

public class ForcehUtils {

    public static <T> int getListIndex(List<T> list ,String pramName,String valueName){
        int  index = -1;
        for (int i =0;i<list.size();i++){
            try {
                Object model= list.get(i);
                Class cla = list.get(i).getClass();
                Field[] field = cla.getDeclaredFields();
                for (int j=0;j<field.length ;j++) {
                    // 获取属性的名字
                    String name = AutoAdapter.toUpperCaseFirstOne(field[j].getName());

                    // 获取属性类型
                    String type = field[j].getGenericType().toString();

                    Log.d("autoAdapter", "getView: "+name+"      "+type);

                    //关键。。。可访问私有变量
                    field[j].setAccessible(true);
                    if(pramName.equalsIgnoreCase(name)) {
                        //给属性设置
                        if (type.equals("class java.lang.String")) {
                            // 如果type是类类型，则前面包含"class "，后面跟类名
                            Method m = model.getClass().getMethod("get" + name);
                            // 调用getter方法获取属性值
                            String value = (String) m.invoke(model);
                            //关键。。。可访问私有变量
                            if (valueName.equals(value)) {
                                index=i;
                            }
                        }
                    }
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return index;
    }

    /**
     * 获取指定集合的值的总数
     * @param list  集合对象
     * @param pramName  集合参数名
     * @param valueName  比对值
     * @param <T>
     * @return
     */
    public static <T> int getListStatusCount(List<T> list ,String pramName,String valueName){
        int  count = 0;
        for (int i =0;i<list.size();i++){
            try {
                Object model= list.get(i);
                Class cla = list.get(i).getClass();
                //遍历所有属性
                Field[] field = cla.getDeclaredFields();
                for (int j=0;j<field.length ;j++) {



                    // 获取属性的名字
                    String name = AutoAdapter.toUpperCaseFirstOne(field[j].getName());

                    // 获取属性类型
                    String type = field[j].getGenericType().toString();

                    Log.d("autoAdapter", "getView: "+name+"      "+type);

                    //关键。。。可访问私有变量
                    field[j].setAccessible(true);
                    if(pramName.equalsIgnoreCase(name)) {
                        //给属性设置
                        if (type.equals("class java.lang.String")) {
                            // 如果type是类类型，则前面包含"class "，后面跟类名
                            Method m = model.getClass().getMethod("get" + name);
                            // 调用getter方法获取属性值
                            String value = (String) m.invoke(model);
                            //关键。。。可访问私有变量
                            if (valueName.equals(value)) {
                                count++;
                            }
                        }
                    }



                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return count;
    }


}
