package hsj.expmle.com.prevention.entry;


/**
 * Created by 16486 on 2020/11/24.
 */

public class Prevention {


    /**
     * StorageID : 10
     * RfidNo : A20201201175608000000003
     * SerialNo : SERA0000000000000000000005
     * StockID : 6
     * StockRfidNo : A20201201175519000000002
     * StockSerialNo : SERA0000000000000000000001
     * VaccineID : 5
     * VaccineName : 丁型H1N4灭活性疫苗
     * ProductID : 1
     * ProductName : 猪
     * Status : null
     */

    private Integer StorageID;
    private String RfidNo;
    private String SerialNo;
    private Integer StockID;
    private String StockRfidNo;
    private String StockSerialNo;
    private Integer VaccineID;
    private String VaccineName;
    private Integer ProductID;
    private String ProductName;
    private Object Status;
    private String IsFocus="false";

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
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

    public Integer getStockID() {
        return StockID;
    }

    public void setStockID(Integer StockID) {
        this.StockID = StockID;
    }

    public String getStockRfidNo() {
        return StockRfidNo;
    }

    public void setStockRfidNo(String StockRfidNo) {
        this.StockRfidNo = StockRfidNo;
    }

    public String getStockSerialNo() {
        return StockSerialNo;
    }

    public void setStockSerialNo(String StockSerialNo) {
        this.StockSerialNo = StockSerialNo;
    }

    public Integer getVaccineID() {
        return VaccineID;
    }

    public void setVaccineID(Integer VaccineID) {
        this.VaccineID = VaccineID;
    }

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String VaccineName) {
        this.VaccineName = VaccineName;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public Object getStatus() {
        return Status;
    }

    public void setStatus(Object Status) {
        this.Status = Status;
    }
}
