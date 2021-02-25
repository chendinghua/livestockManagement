package com.kymjs.app.base_res.utils.base.distribute.entry;

/**
 * Created by 16486 on 2021/2/23.
 */

public class PackageInfoItems {
    /**
     * PID : 3
     * Id : 1
     * Code : P2020123100001
     * CreateDate : 2020-12-31T16:29:11.01
     * TypeName : 生成包号
     * TYPE : 1
     * Lot : null
     * Num : 2
     * DeptNum : 1
     * UserName : 系统管理员
     * OpDeptID : 9
     * CreateDeptID : 1
     * r : 1
     */
    private Integer PID;
    private Integer Id;
    private String Code;
    private String CreateDate;
    private String TypeName;
    private Integer TYPE;
    private String Lot;
    private Integer Num;
    private Integer DeptNum;
    private String UserName;
    private Integer OpDeptID;
    private Integer CreateDeptID;
    private Integer r;

    private String IsFocus="false";

    private Integer Status;             //状态id  如果为3则为预出库  供应商发给畜牧局  表示供应商不能在包号上再次处理耳标寄出

    private String StatusName;


    private String BoxCode;

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


    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public String getLot() {
        return Lot;
    }

    public void setLot(String lot) {
        Lot = lot;
    }

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer num) {
        Num = num;
    }

    public Integer getDeptNum() {
        return DeptNum;
    }

    public void setDeptNum(Integer deptNum) {
        DeptNum = deptNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getOpDeptID() {
        return OpDeptID;
    }

    public void setOpDeptID(Integer opDeptID) {
        OpDeptID = opDeptID;
    }

    public Integer getCreateDeptID() {
        return CreateDeptID;
    }

    public void setCreateDeptID(Integer createDeptID) {
        CreateDeptID = createDeptID;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }
}
