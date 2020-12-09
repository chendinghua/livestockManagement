package hsj.expmle.com.prevention.entry;

/**
 * Created by 16486 on 2020/12/9.
 */

public class VreCommitData {


    /**
     * DataID : 13
     * Status : 1
     */

    private Integer DataID;
    private Integer Status;

    public VreCommitData(Integer dataID, Integer status) {
        DataID = dataID;
        Status = status;
    }

    public VreCommitData() {
    }

    public Integer getDataID() {
        return DataID;
    }

    public void setDataID(Integer DataID) {
        this.DataID = DataID;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }
}
