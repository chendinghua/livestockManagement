package com.kymjs.app.entry;

import com.kymjs.app.base_res.utils.base.BaseEntry;

/**
 * Created by 16486 on 2020/11/3.
 */

public class TestData  {

    private String testData;
    private Integer  id;               //编号
    private String  name;              //名称


    public TestData(Integer id,String name, String testData) {
      this.id=id;
        this.name=name;
        this.testData = testData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    @Override
    public String toString() {
        return testData+"    "+name+"    "+id;
    }
}
