package com.kymjs.app.base_res.utils.base.distribute.entry;

/**
 * Created by 16486 on 2021/1/10.
 */

public class DataInfo {


    /**
     * DataID : 1
     * InfoID : 2
     */

    private Integer DataID;         //一次分发箱号id    二次分发包号id
    private Integer InfoID;         //一次分发包号id    二次分发库存id


    public DataInfo(Integer dataID, Integer infoID) {
        DataID = dataID;
        InfoID = infoID;
    }

    public DataInfo() {
    }

    public Integer getDataID() {
        return DataID;
    }

    public void setDataID(Integer DataID) {
        this.DataID = DataID;
    }

    public Integer getInfoID() {
        return InfoID;
    }

    public void setInfoID(Integer InfoID) {
        this.InfoID = InfoID;
    }



}
