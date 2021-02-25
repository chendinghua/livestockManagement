package examine.expmle.com.entry;

/**
 * Created by 16486 on 2021/2/23.
 */

public class Examine {

         /**
     * ID : 6
     * Code : B2021020400001
     * CreateDate : 2021-02-04T09:29:38.823
     * Type : 2
     */



    private String IsFocus="false";
    /**
     * Id : 3
     * Code : P2021022300003
     * CreateDate : 2021-02-23T18:44:27.723
     * TYPE : 1
     * OpUserID : 3
     * OpDeptID : 6
     * PID : 4
     * CreateDeptID : 1
     * Lot : 20210223
     * Num : 2
     */

    private Integer Id;
    private String Code;
    private String CreateDate;
    private Integer TYPE;
    private Integer OpUserID;
    private Integer OpDeptID;
    private Integer PID;
    private Integer CreateDeptID;
    private String Lot;
    private Integer Num;

    public String getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(String isFocus) {
        IsFocus = isFocus;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
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

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public Integer getOpUserID() {
        return OpUserID;
    }

    public void setOpUserID(Integer OpUserID) {
        this.OpUserID = OpUserID;
    }

    public Integer getOpDeptID() {
        return OpDeptID;
    }

    public void setOpDeptID(Integer OpDeptID) {
        this.OpDeptID = OpDeptID;
    }

    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

    public Integer getCreateDeptID() {
        return CreateDeptID;
    }

    public void setCreateDeptID(Integer CreateDeptID) {
        this.CreateDeptID = CreateDeptID;
    }

    public String getLot() {
        return Lot;
    }

    public void setLot(String Lot) {
        this.Lot = Lot;
    }

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer Num) {
        this.Num = Num;
    }
}
