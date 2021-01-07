package hsj.instock.com.entry;

/**
 * Created by 16486 on 2020/12/11.
 */

public class MoveStockInfoResult {

    /**
     * ID : 1
     * DataID : 1
     * OutStockID : 6
     * StorageID : 104
     * OutStockName : ppp
     * BirthTime : 2020-12-04T15:31:13.54
     * InStockTime : 2020-12-09T15:31:13.54
     * ProductName : null
     * RfidNo : A20201209143628000000002
     * SerialNo : SERA20201209143628000002
     */

    private Integer ID;
    private Integer DataID;
    private Integer OutStockID;
    private Integer StorageID;
    private String OutStockName;
    private String BirthTime;
    private String InStockTime;
    private Object ProductName;
    private String RfidNo;
    private String SerialNo;

    private String InStockName;     //需要手动赋值  移栏后的栏位

    private String OpTime;          //需要手动赋值   操作的时间


    public String getInStockName() {
        return InStockName;
    }

    public void setInStockName(String inStockName) {
        InStockName = inStockName;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String opTime) {
        OpTime = opTime;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getDataID() {
        return DataID;
    }

    public void setDataID(Integer DataID) {
        this.DataID = DataID;
    }

    public Integer getOutStockID() {
        return OutStockID;
    }

    public void setOutStockID(Integer OutStockID) {
        this.OutStockID = OutStockID;
    }

    public Integer getStorageID() {
        return StorageID;
    }

    public void setStorageID(Integer StorageID) {
        this.StorageID = StorageID;
    }

    public String getOutStockName() {
        return OutStockName;
    }

    public void setOutStockName(String OutStockName) {
        this.OutStockName = OutStockName;
    }

    public String getBirthTime() {
        return BirthTime;
    }

    public void setBirthTime(String BirthTime) {
        this.BirthTime = BirthTime;
    }

    public String getInStockTime() {
        return InStockTime;
    }

    public void setInStockTime(String InStockTime) {
        this.InStockTime = InStockTime;
    }

    public Object getProductName() {
        return ProductName;
    }

    public void setProductName(Object ProductName) {
        this.ProductName = ProductName;
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
}
