package com.kymjs.app.base_res.utils.base.entry.vaccine;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/**
 * Created by 16486 on 2020/11/23.
 */

public class VaccineInfoResult extends PageInfo {

    List<VaccineInfo> Result;

    public List<VaccineInfo> getResult() {
        return Result;
    }

    public void setResult(List<VaccineInfo> result) {
        Result = result;
    }
}
