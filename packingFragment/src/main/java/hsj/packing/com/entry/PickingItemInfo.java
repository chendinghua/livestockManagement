package hsj.packing.com.entry;

/**
 * Created by 16486 on 2021/2/4.
 */

public class PickingItemInfo {

    /**
     * ID : 1
     * PID : 1
     * Status : 1
     * OpTime : null
     * Code : P2020123100001
     * CreateDate : 2020-12-31T16:29:11.01
     * StorageID : 1
     * RfidNo : A00000000000000000000001
     * SerialNo : SERA0000000000000000000001
     * DeptID : 20
     * IsEnabled : 3
     * IsEnabledName : 灭活
     * DeptName : 大兴区养殖户
     * ProductName : 猪
     */

    private Integer ID;
    private Integer PID;
    private Integer Status;
    private String OpTime;
    private String Code;
    private String CreateDate;
    private Integer StorageID;
    private String RfidNo;
    private String SerialNo;
    private Integer DeptID;
    private Integer IsEnabled;
    private String IsEnabledName;
    private String DeptName;
    private String ProductName;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String OpTime) {
        this.OpTime = OpTime;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Integer getStorageID() {
        return StorageID;
    }

    public void setStorageID(Integer StorageID) {
        this.StorageID = StorageID;
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

    public Integer getDeptID() {
        return DeptID;
    }

    public void setDeptID(Integer DeptID) {
        this.DeptID = DeptID;
    }

    public Integer getIsEnabled() {
        return IsEnabled;
    }

    public void setIsEnabled(Integer IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public String getIsEnabledName() {
        return IsEnabledName;
    }

    public void setIsEnabledName(String IsEnabledName) {
        this.IsEnabledName = IsEnabledName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }
}
