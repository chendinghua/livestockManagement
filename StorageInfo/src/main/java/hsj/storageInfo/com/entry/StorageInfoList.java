package hsj.storageInfo.com.entry;

/**
 * Created by 16486 on 2020/11/4.
 */

public class StorageInfoList {
    /**
     * ID : 1
     * ProductID : 1
     * ProductTypeID : 1
     * Type : 1
     * RfidNo : -1
     * SerialNo : 518000
     * Remark : 这是蜘猪
     * Status : 1
     * TaskType : 6
     * TaskId : 6
     * DeptID : 1
     * StockID : null
     * BirthTime : 2020-10-27T11:09:49.713
     * IsEnabled : 1
     * Time : 7
     * StorageStatusName : 在库
     * IsEnabledName : 启用
     * ProductName : 猪
     * DeptName : 畜牧局总局
     * DeptTypeName : 畜牧局
     * r : 1
     */

    private Integer ID;
    private Integer ProductID;
    private Integer ProductTypeID;
    private Integer Type;
    private String RfidNo;
    private String SerialNo;
    private String Remark;
    private Integer Status;
    private Integer TaskType;
    private Integer TaskId;
    private Integer DeptID;
    private Object StockID;
    private String BirthTime;
    private Integer IsEnabled;
    private Integer Time;
    private String StorageStatusName;
    private String IsEnabledName;
    private String ProductName;
    private String DeptName;
    private String DeptTypeName;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer ProductID) {
        this.ProductID = ProductID;
    }

    public Integer getProductTypeID() {
        return ProductTypeID;
    }

    public void setProductTypeID(Integer ProductTypeID) {
        this.ProductTypeID = ProductTypeID;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer Type) {
        this.Type = Type;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public Integer getTaskType() {
        return TaskType;
    }

    public void setTaskType(Integer TaskType) {
        this.TaskType = TaskType;
    }

    public Integer getTaskId() {
        return TaskId;
    }

    public void setTaskId(Integer TaskId) {
        this.TaskId = TaskId;
    }

    public Integer getDeptID() {
        return DeptID;
    }

    public void setDeptID(Integer DeptID) {
        this.DeptID = DeptID;
    }

    public Object getStockID() {
        return StockID;
    }

    public void setStockID(Object StockID) {
        this.StockID = StockID;
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

    public Integer getTime() {
        return Time;
    }

    public void setTime(Integer Time) {
        this.Time = Time;
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

    public String getDeptTypeName() {
        return DeptTypeName;
    }

    public void setDeptTypeName(String DeptTypeName) {
        this.DeptTypeName = DeptTypeName;
    }

    @Override
    public String toString() {
        return "StorageInfoList{" +
                "ID=" + ID +
                ", ProductID=" + ProductID +
                ", ProductTypeID=" + ProductTypeID +
                ", Type=" + Type +
                ", RfidNo='" + RfidNo + '\'' +
                ", SerialNo='" + SerialNo + '\'' +
                ", Remark='" + Remark + '\'' +
                ", Status=" + Status +
                ", TaskType=" + TaskType +
                ", TaskId=" + TaskId +
                ", DeptID=" + DeptID +
                ", StockID=" + StockID +
                ", BirthTime='" + BirthTime + '\'' +
                ", IsEnabled=" + IsEnabled +
                ", Time=" + Time +
                ", StorageStatusName='" + StorageStatusName + '\'' +
                ", IsEnabledName='" + IsEnabledName + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", DeptTypeName='" + DeptTypeName + '\'' +
                '}';
    }
}
