package com.kymjs.app.base_res.utils.base.entry.stock;

import java.io.Serializable;

/**
 * Created by 16486 on 2020/11/6.
 */

public class StockInfo implements Serializable {

    /**
     * ID : 1
     * FarmersID : 4
     * Name : 测试栏位A
     * StorageID : 1
     * MaxArea : 123
     * MaxNum : 1232
     * OpUserID : 1
     * OpTime : 2020-10-22T10:01:42.327
     * Status : 2
     * RFIDNo : -1
     * SerialNo : 518000
     */

    private Integer ID;
    private String FarmersID;
    private String Name;
    private Integer StorageID;
    private Integer MaxArea;
    private String MaxNum;
    private String OpUserID;
    private String OpTime;
    private Integer Status;
    private String RFIDNo;
    private String SerialNo;

    private String StatusName;

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getFarmersID() {
        return FarmersID;
    }

    public void setFarmersID(String FarmersID) {
        this.FarmersID = FarmersID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getStorageID() {
        return StorageID;
    }

    public void setStorageID(Integer StorageID) {
        this.StorageID = StorageID;
    }

    public Integer getMaxArea() {
        return MaxArea;
    }

    public void setMaxArea(Integer MaxArea) {
        this.MaxArea = MaxArea;
    }

    public String getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(String MaxNum) {
        this.MaxNum = MaxNum;
    }

    public String getOpUserID() {
        return OpUserID;
    }

    public void setOpUserID(String OpUserID) {
        this.OpUserID = OpUserID;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String OpTime) {
        this.OpTime = OpTime;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getRFIDNo() {
        return RFIDNo;
    }

    public void setRFIDNo(String RFIDNo) {
        this.RFIDNo = RFIDNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }
}
