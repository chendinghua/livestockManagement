package hsj.expmle.com.distribute.entry;

/**
 * Created by 16486 on 2021/1/9.
 */

public class PackageOrStorage {


    /**
     * ID : 1
     * PID : 3
     * Status : 1
     * OpTime : null
     * Code : P2020123100001
     * StatusName : 未拆
     * RfidNo : A00000000000000000000002
     * SerialNo : SERA0000000000000000000001
     * ProductName : null
     * DeptID : 1
     */
    private int ID;
    private int PID;
    private int Status;
    private Object OpTime;
    private String Code;
    private String StatusName;
    private String RfidNo;
    private String SerialNo;
    private Object ProductName;
    private int DeptID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Object getOpTime() {
        return OpTime;
    }

    public void setOpTime(Object OpTime) {
        this.OpTime = OpTime;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
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

    public Object getProductName() {
        return ProductName;
    }

    public void setProductName(Object ProductName) {
        this.ProductName = ProductName;
    }

    public int getDeptID() {
        return DeptID;
    }

    public void setDeptID(int DeptID) {
        this.DeptID = DeptID;
    }
}
