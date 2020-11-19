package hsj.storageInfo.com.entry;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/**
 * Created by 16486 on 2020/11/4.
 */

public class StorageInfo extends PageInfo {

    List<StorageInfoList> Result;


    public List<StorageInfoList> getResult() {
        return Result;
    }

    public void setResult(List<StorageInfoList> result) {
        Result = result;
    }


}
