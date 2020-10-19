package com.kymjs.app.tools;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by 16486 on 2020/1/14.
 */

public class AnnotateUtil {
    public AnnotateUtil() {
    }

    public static void initBindView(Object currentClass, View sourceView) {
        Field[] fields = currentClass.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0) {
            Field[] var6 = fields;
            int var5 = fields.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                BindView bindView = (BindView)field.getAnnotation(BindView.class);
                if(bindView != null) {
                    int viewId = bindView.id();
                    boolean clickLis = bindView.click();

                    try {
                        field.setAccessible(true);
                        if(clickLis) {
                            sourceView.findViewById(viewId).setOnClickListener((View.OnClickListener)currentClass);
                        }
                        View view = sourceView.findViewById(viewId);
                        if(view instanceof Button){
                           Button btn = (Button)view;
                            btn.setTag(bindView.tag());
                        }

                        field.set(currentClass, sourceView.findViewById(viewId));
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }
            }
        }

    }


    public static void initBindView(Object currentClass, Context mContext, int layoutId, ViewGroup viewGroup) {
     /*   Field[] fields = currentClass.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0) {
            Field[] var6 = fields;
            int var5 = fields.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                BindView bindView = (BindView)field.getAnnotation(BindView.class);
                if(bindView != null) {
                    int viewId = bindView.id();
                    boolean clickLis = bindView.click();

                    try {
                        field.setAccessible(true);
                        if(clickLis) {
                            LayoutInflater.from(mContext).inflate(layoutId,viewGroup).findViewById(viewId).setOnClickListener((View.OnClickListener)currentClass);
                        }

                        field.set(currentClass,   LayoutInflater.from(mContext).inflate(layoutId,null).findViewById(viewId));
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }
            }
        }*/
            initBindView(currentClass, LayoutInflater.from(mContext).inflate(layoutId,viewGroup));
    }


    public static void changeView(Object currentClass, Context mContext, int layoutId, ViewGroup viewGroup) {
        changeView(currentClass, LayoutInflater.from(mContext).inflate(layoutId,viewGroup));

    }

    public static void changeView(Object currentClass, View sourceView){
        Field[] fields = currentClass.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0) {
            Field[] var6 = fields;
            int var5 = fields.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                BindView bindView = (BindView)field.getAnnotation(BindView.class);
                if(bindView != null) {
                    int viewId = bindView.id();
                    int textValue = bindView.textValue();
                    int hintValue = bindView.hintValue();
                    try {
                        if(textValue!=0) {
                            View view = sourceView.findViewById(viewId);
                            if (view instanceof Button) {
                                Button btn = (Button) view;
                                btn.setText(textValue);
                            }else if(view instanceof EditText){
                                EditText editText = (EditText) view;
                                if(textValue!=-1) {
                                    editText.setText(textValue);
                                }
                                if(hintValue!=0){
                                    editText.setHint(hintValue);
                                }
                            }else if(view instanceof TextView){
                                TextView tv = (TextView) view;
                                tv.setText(textValue);
                            }
                        }
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }
            }
        }
    }
    public static  void changeView(View view){
        Context cxt = view.getContext();
        if(cxt instanceof Activity) {
            changeView((Activity)cxt);
        } else {
            Log.d("AnnotateUtil.java", "the view don\'t have root view");
        }
    }
    public static  void changeView(Activity aty){
        changeView(aty, aty.getWindow().getDecorView());
    }

    public static void initBindView(Activity aty) {
        initBindView(aty, aty.getWindow().getDecorView());
    }

    public static void initBindView(View view) {
        Context cxt = view.getContext();
        if(cxt instanceof Activity) {
            initBindView((Activity)cxt);
        } else {
            Log.d("AnnotateUtil.java", "the view don\'t have root view");
        }
    }

    @TargetApi(11)
    public static void initBindView(Fragment frag) {
        initBindView(frag, frag.getActivity().getWindow().getDecorView());
    }
}
