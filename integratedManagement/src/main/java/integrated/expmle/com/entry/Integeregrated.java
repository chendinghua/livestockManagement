package integrated.expmle.com.entry;

/**
 * Created by 16486 on 2021/2/20.
 */

public class Integeregrated {

    /**
     * StockID : 5
     * StockSerialNo : null
     * StorageID : 0
     * RfidNo : A20201208164230000000001
     * SerialNo : SERA20201208164230000001
     * Status : 1
     * BirthTime : 2020/12/8 16:46:14
     * IsEnabled : 3
     * ProductID : 0
     * DeptID : 13
     * HealthStatus : 1
     * StorageStatusName : 在库
     * IsEnabledName : 灭活
     * ProductName : null
     * DeptName : 朝阳区屠宰场
     * HealthStatusName : 健康 
     * VaccineList : null
     * MedicalList : null
     */

    private Integer StockID;
    private String StockSerialNo;
    private Integer StorageID;
    private String RfidNo;
    private String SerialNo;
    private Integer Status;
    private String BirthTime;
    private Integer IsEnabled;
    private Integer ProductID;
    private Integer DeptID;
    private Integer HealthStatus;
    private String StorageStatusName;
    private String IsEnabledName;
    private String ProductName;
    private String DeptName;
    private String HealthStatusName;
    private String VaccineList;
    private String MedicalList;


    private String IsFocus="false";


    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }

    public Integer getStockID() {
        return StockID;
    }

    public void setStockID(Integer StockID) {
        this.StockID = StockID;
    }

    public String getStockSerialNo() {
        return StockSerialNo;
    }

    public void setStockSerialNo(String StockSerialNo) {
        this.StockSerialNo = StockSerialNo;
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

    public Integer getHealthStatus() {
        return HealthStatus;
    }

    public void setHealthStatus(Integer HealthStatus) {
        this.HealthStatus = HealthStatus;
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

    public String getHealthStatusName() {
        return HealthStatusName;
    }

    public void setHealthStatusName(String HealthStatusName) {
        this.HealthStatusName = HealthStatusName;
    }

    public String getVaccineList() {
        return VaccineList;
    }

    public void setVaccineList(String VaccineList) {
        this.VaccineList = VaccineList;
    }

    public String getMedicalList() {
        return MedicalList;
    }

    public void setMedicalList(String MedicalList) {
        this.MedicalList = MedicalList;
    }
}
