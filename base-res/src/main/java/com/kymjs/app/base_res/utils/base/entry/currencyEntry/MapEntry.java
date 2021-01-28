package com.kymjs.app.base_res.utils.base.entry.currencyEntry;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 16486 on 2021/1/26.
 */

public class MapEntry {

    String title;
    List<HashMap<String,String>> tempList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HashMap<String, String>> getTempList() {
        return tempList;
    }

    public void setTempList(List<HashMap<String, String>> tempList) {
        this.tempList = tempList;
    }

    @Override
    public String toString() {
        return "MapEntry{" +
                "title='" + title + '\'' +
                ", tempList=" + tempList +
                '}';
    }
}
