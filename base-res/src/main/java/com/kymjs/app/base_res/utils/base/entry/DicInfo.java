package com.kymjs.app.base_res.utils.base.entry;

import com.kymjs.app.base_res.utils.base.BaseEntry;

/**
 * Created by 16486 on 2020/11/2.
 */

public class DicInfo extends BaseEntry{
  /*  private Integer  ID;               //编号
    private String  Name;              //名称
    public Integer getId(){
        return  ID;
    }
    public String getName(){
        return Name;

    }
    public void setId(Integer ID){
        this.ID=ID;
    }
    public void setName(String Name){
        this.Name=Name;
    }
*/
  public String getValue() {
      return Value;
  }

    public void setValue(String value) {
        Value = value;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
