package com.kymjs.app.base_res.utils.base.entry.location;

/**
 * Created by 16486 on 2021/1/7.
 */

public class LocationEntry {


    /**
     * ID : 1000000
     * Name : 北京市
     * Short_name : 京
     * Code : 110000
     * Parent_id : -1
     * Level : 1
     * OpUserID : 1
     */

    private String ID;
    private String Name;
    private String Short_name;
    private String Code;
    private String Parent_id;
    private Integer Level;
    private Integer OpUserID;

    public LocationEntry(String ID ,  String name, Integer level) {
        this.ID=ID;
        Name = name;
        Level = level;
    }

    public LocationEntry() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getShort_name() {
        return Short_name;
    }

    public void setShort_name(String Short_name) {
        this.Short_name = Short_name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getParent_id() {
        return Parent_id;
    }

    public void setParent_id(String Parent_id) {
        this.Parent_id = Parent_id;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer Level) {
        this.Level = Level;
    }

    public Integer getOpUserID() {
        return OpUserID;
    }

    public void setOpUserID(Integer OpUserID) {
        this.OpUserID = OpUserID;
    }
}
