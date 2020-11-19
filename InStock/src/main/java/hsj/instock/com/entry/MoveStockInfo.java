package hsj.instock.com.entry;

/** 移位提交数据
 * Created by 16486 on 2020/11/7.
 */
public class MoveStockInfo {


    /**
     * OutStockID : 1
     * StorageID : 1
     */

    private Integer OutStockID;             //操作栏位id
    private Integer StorageID;              //库存id


    public MoveStockInfo(Integer outStockID, Integer storageID) {
        OutStockID = outStockID;
        StorageID = storageID;
    }

    public MoveStockInfo() {
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
}
