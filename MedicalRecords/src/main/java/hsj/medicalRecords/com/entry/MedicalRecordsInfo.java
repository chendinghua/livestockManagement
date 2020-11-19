package hsj.medicalRecords.com.entry;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/**
 * Created by 16486 on 2020/11/10.
 */

public class MedicalRecordsInfo extends PageInfo {

    List<MedicalRecordsList> Result;


    public List<MedicalRecordsList> getResult() {
        return Result;
    }

    public void setResult(List<MedicalRecordsList> result) {
        Result = result;
    }

}
