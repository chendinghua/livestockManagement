package com.kymjs.app.base_res.utils.base.entry.TaskInfo;

import android.app.Activity;

import java.util.List;

/**
 * Created by 16486 on 2020/12/6.
 */

public class Task{


    private Integer ID;
    private Integer DataID;
    private Integer TaskType;
    private String TaskTypeName;
    private String Content;
    private Integer CreatorID;
    private String CreatorName;
    private String CreatorTime;
    private Integer Status;
    private String StatusName;
    private String Remark;
    private String OpUser;
    private String OpTime;
    private Integer Num;



    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getDataID() {
        return DataID;
    }

    public void setDataID(Integer dataID) {
        DataID = dataID;
    }

    public Integer getTaskType() {
        return TaskType;
    }

    public void setTaskType(Integer taskType) {
        TaskType = taskType;
    }

    public String getTaskTypeName() {
        return TaskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        TaskTypeName = taskTypeName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Integer getCreatorID() {
        return CreatorID;
    }

    public void setCreatorID(Integer creatorID) {
        CreatorID = creatorID;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }

    public String getCreatorTime() {
        return CreatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        CreatorTime = creatorTime;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getOpUser() {
        return OpUser;
    }

    public void setOpUser(String opUser) {
        OpUser = opUser;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String opTime) {
        OpTime = opTime;
    }

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer num) {
        Num = num;
    }


}
