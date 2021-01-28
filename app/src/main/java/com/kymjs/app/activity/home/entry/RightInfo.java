package com.kymjs.app.activity.home.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/27/027.
 */
//权限信息
public class RightInfo {
    private int ID;                 //id
    private int Plateform;          //平台类型
    private int RightType;
    private int ParentID;
    private String Name;             //权限名
    private String ActionForm;      //跳转页面
    private String ActionBtn;       //跳转页面图标
    private String ActionUrl;
    private String Remark;


    public String getActionForm() {
        return ActionForm;
    }

    public void setActionForm(String actionForm) {
        ActionForm = actionForm;
    }

    public String getActionBtn() {
        return ActionBtn;
    }

    public void setActionBtn(String actionBtn) {
        ActionBtn = actionBtn;
    }

    public String getActionUrl() {
        return ActionUrl;
    }

    public void setActionUrl(String actionUrl) {
        ActionUrl = actionUrl;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPlateform() {
        return Plateform;
    }

    public void setPlateform(int plateform) {
        Plateform = plateform;
    }

    public int getRightType() {
        return RightType;
    }

    public void setRightType(int rightType) {
        RightType = rightType;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
