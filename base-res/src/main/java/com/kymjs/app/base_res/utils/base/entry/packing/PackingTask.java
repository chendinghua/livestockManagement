package com.kymjs.app.base_res.utils.base.entry.packing;
import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/**
 * Created by 16486 on 2021/1/6.
 */

public class PackingTask extends PageInfo{

    List<PackingTaskItems> Result;

    public List<PackingTaskItems> getResult() {
        return Result;
    }

    public void setResult(List<PackingTaskItems> result) {
        Result = result;
    }

    /**
     * PID : -1
     * Id : 2
     * Code : P2021010600001
     * CreateDate : 2021-01-06T16:15:50.51
     * TypeName : 生成包号
     * TYPE : 1
     * UserName : 大兴区养殖户
     * r : 2
     */
    public static class PackingTaskItems {


        private Integer PID;
        private Integer Id;
        private String Code;
        private String CreateDate;
        private String TypeName;
        private Integer TYPE;
        private String UserName;
        private Integer r;

        private Integer Num;

        private String IsFocus;
        //装箱状态
        private String packingStatus;


        public PackingTaskItems() {
        }

        public  PackingTaskItems(Integer id, String code) {
            Id = id;
            Code = code;
        }

        public Integer getNum() {
            return Num;
        }

        public void setNum(Integer num) {
            Num = num;
        }

        public String getIsFocus() {
            return IsFocus;
        }

        public void setIsFocus(String isFocus) {
            IsFocus = isFocus;
        }

        public String getPackingStatus() {
            return packingStatus;
        }

        public void setPackingStatus(String packingStatus) {
            this.packingStatus = packingStatus;
        }

        public Integer getPID() {
            return PID;
        }

        public void setPID(Integer PID) {
            this.PID = PID;
            setPackingStatus(PID==-1?"未装箱":"已装箱");
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

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public Integer getTYPE() {
            return TYPE;
        }

        public void setTYPE(Integer TYPE) {
            this.TYPE = TYPE;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public Integer getR() {
            return r;
        }

        public void setR(Integer r) {
            this.r = r;
        }
    }
}
