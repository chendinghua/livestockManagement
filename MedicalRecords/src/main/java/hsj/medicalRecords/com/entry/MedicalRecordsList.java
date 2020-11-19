package hsj.medicalRecords.com.entry;

/**
 * Created by 16486 on 2020/11/10.
 */

public class MedicalRecordsList {


    /**
     * ID : 1
     * MedicalRID : 1
     * Illness : 复合型新型流行性感冒
     * StorageID : 2
     * RfidNo : TEST
     * Condition : 头昏脑胀呼不得，归来倚墙自叹息
     * DiagnosisTime : 2020-11-02T17:35:19.94
     * Status : 治疗中
     * r : 1
     */

    private Integer ID;
    private Integer MedicalRID;
    private String Illness;
    private Integer StorageID;
    private String RfidNo;
    private String Condition;
    private String DiagnosisTime;
    private String Status;
    private Integer r;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getMedicalRID() {
        return MedicalRID;
    }

    public void setMedicalRID(Integer MedicalRID) {
        this.MedicalRID = MedicalRID;
    }

    public String getIllness() {
        return Illness;
    }

    public void setIllness(String Illness) {
        this.Illness = Illness;
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

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    public String getDiagnosisTime() {
        return DiagnosisTime;
    }

    public void setDiagnosisTime(String DiagnosisTime) {
        this.DiagnosisTime = DiagnosisTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }
}
