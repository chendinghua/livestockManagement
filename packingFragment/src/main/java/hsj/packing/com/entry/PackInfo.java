package hsj.packing.com.entry;

/**
 * Created by 16486 on 2021/2/4.
 */

public class PackInfo {

    /**
     * ID : 3
     * PID : 39
     * Status : 1
     * OpTime : null
     * BoxCode : B2021010700001
     * Code : null
     * CreateDate : null
     * Num : 1
     * DeptNum : 0
     * StatusName : 未拆
     * OpDeptID : null
     */

    private Integer ID;
    private Integer PID;
    private Integer Status;
    private String OpTime;
    private String BoxCode;
    private String Code;
    private String CreateDate;
    private Integer Num;
    private Integer DeptNum;
    private String StatusName;
    private String OpDeptID;

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

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String BoxCode) {
        this.BoxCode = BoxCode;
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

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer Num) {
        this.Num = Num;
    }

    public Integer getDeptNum() {
        return DeptNum;
    }

    public void setDeptNum(Integer DeptNum) {
        this.DeptNum = DeptNum;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public String getOpDeptID() {
        return OpDeptID;
    }

    public void setOpDeptID(String OpDeptID) {
        this.OpDeptID = OpDeptID;
    }
}
