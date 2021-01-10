package com.kymjs.app.base_res.utils.base.entry;

/**
 * Created by 16486 on 2020/11/4.
 */

public class ScanResult {


    /**
     * RfidNo : TEST1233
     * SerialNo : null
     * Status : 0
     * BirthTime : null
     * IsEnabled : 0
     * ProductID : 0
     * DeptID : 0
     * StorageStatusName : null
     * IsEnabledName : null
     * ProductName : null
     * DeptName : null
     */

    private Integer StorageID;

    private String RfidNo;
    private String SerialNo;
    private Integer Status;
    private String BirthTime;
    private Integer IsEnabled;
    private Integer ProductID;
    private Integer DeptID;
    private String StorageStatusName;
    private String IsEnabledName;
    private String ProductName;
    private String DeptName;
    //背景颜色控制字段
    private String IsFocus="false";
    //库位id
    private Integer StockID;

    private String StockSerialNo;

    private Integer Package;

    public Integer getPackage() {
        return Package;
    }

    public void setPackage(Integer aPackage) {
        Package = aPackage;
    }

    public String getStockSerialNo() {
        return StockSerialNo;
    }

    public void setStockSerialNo(String stockSerialNo) {
        StockSerialNo = stockSerialNo;
    }

    public ScanResult(String rfidNo, String serialNo, String isEnabledName, String isFocus) {
        RfidNo = rfidNo;
        SerialNo = serialNo;
        IsEnabledName=isEnabledName;
        IsFocus = isFocus;
    }

    public Integer getStorageID() {
        return StorageID;
    }

    public void setStorageID(Integer storageID) {
        StorageID = storageID;
    }

    public ScanResult() {
    }

    public Integer getStockID() {
        return StockID;
    }

    public void setStockID(Integer stockID) {
        StockID = stockID;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }

    public String getRfidNo() {
        return RfidNo;
    }

    public void setRfidNo(String RfidNo) {
        this.RfidNo = RfidNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getBirthTime() {
        return BirthTime;
    }

    public void setBirthTime(String BirthTime) {
        this.BirthTime = BirthTime;
    }

    public Integer getIsEnabled() {
        return IsEnabled;
    }

    public void setIsEnabled(Integer IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer ProductID) {
        this.ProductID = ProductID;
    }

    public Integer getDeptID() {
        return DeptID;
    }

    public void setDeptID(Integer DeptID) {
        this.DeptID = DeptID;
    }

    public String getStorageStatusName() {
        return StorageStatusName;
    }

    public void setStorageStatusName(String StorageStatusName) {
        this.StorageStatusName = StorageStatusName;
    }

    public String getIsEnabledName() {
        return IsEnabledName;
    }

    public void setIsEnabledName(String IsEnabledName) {
        this.IsEnabledName = IsEnabledName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }
}
