package com.kymjs.app.base_res.utils.base.entry.ViewEntry;

import android.view.View;

/**
 * Created by 16486 on 2020/11/24.
 */

public class BottomViewList <T extends View>{

    private  T t;
    private String title;


    public BottomViewList(T t, String title) {
        this.t = t;
        this.title = title;
    }

    public BottomViewList() {
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
