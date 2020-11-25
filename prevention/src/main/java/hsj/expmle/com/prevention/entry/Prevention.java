package hsj.expmle.com.prevention.entry;

/**
 * Created by 16486 on 2020/11/24.
 */

public class Prevention {


    /**
     * StorageID : 2
     * RfidNo : F00000000000000000000001
     * SerialNo : TEST
     * StockID : null
     * StockRfidNo : null
     * StockSerialNo : null
     * VaccineID : 6
     * VaccineName : 戊型H1N5灭活性疫苗
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
    private Integer Status;

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

    public void setStorageID(Integer storageID) {
        StorageID = storageID;
    }

    public String getRfidNo() {
        return RfidNo;
    }

    public void setRfidNo(String rfidNo) {
        RfidNo = rfidNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public Integer getStockID() {
        return StockID;
    }

    public void setStockID(Integer stockID) {
        StockID = stockID;
    }

    public String getStockRfidNo() {
        return StockRfidNo;
    }

    public void setStockRfidNo(String stockRfidNo) {
        StockRfidNo = stockRfidNo;
    }

    public String getStockSerialNo() {
        return StockSerialNo;
    }

    public void setStockSerialNo(String stockSerialNo) {
        StockSerialNo = stockSerialNo;
    }

    public Integer getVaccineID() {
        return VaccineID;
    }

    public void setVaccineID(Integer vaccineID) {
        VaccineID = vaccineID;
    }

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String vaccineName) {
        VaccineName = vaccineName;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
