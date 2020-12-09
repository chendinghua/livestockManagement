package hsj.medicalRecords.com.entry;

/**
 * Created by 16486 on 2020/11/18.
 */

public class MedicalRList {


    /**
     * ID : 1
     * Illness : 复合型新型流行性感冒
     * Status : 1
     * OpUser : 系统管理员
     * OpTime : 2020-10-31T14:43:45.82
     */

    private Integer ID;
    private String Name;
    private String Illness;
    private Integer Status;
    private String OpUser;
    private String OpTime;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getIllness() {
        return Illness;
    }

    public void setIllness(String illness) {
        Illness = illness;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getOpUser() {
        return OpUser;
    }

    public void setOpUser(String opUser) {
        OpUser = opUser;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String opTime) {
        OpTime = opTime;
    }
}
