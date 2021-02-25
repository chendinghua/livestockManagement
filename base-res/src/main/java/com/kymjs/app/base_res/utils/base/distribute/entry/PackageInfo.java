package com.kymjs.app.base_res.utils.base.distribute.entry;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/**
 * Created by 16486 on 2021/1/9.
 */

public class PackageInfo extends PageInfo{

    List<PackageInfoItems> Result;


    public List<PackageInfoItems> getResult() {
        return Result;
    }

    public void setResult(List<PackageInfoItems> result) {
        Result = result;
    }


}
