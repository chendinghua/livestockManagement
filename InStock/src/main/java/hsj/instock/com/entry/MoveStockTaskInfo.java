package hsj.instock.com.entry;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 16486 on 2020/12/11.
 */

public class MoveStockTaskInfo extends PageInfo implements Serializable{

    List<MoveStockTaskInfoList> Result;

    public List<MoveStockTaskInfoList> getResult() {
        return Result;
    }

    public void setResult(List<MoveStockTaskInfoList> result) {
        Result = result;
    }

    public class MoveStockTaskInfoList implements Serializable{
        /**
         * ID : 1
         * StockID : 5
         * OpDeptID : 20
         * OpUserID : 9
         * Remark :
         * Num : 5
         * OpTime : 2020-12-09T15:35:00.087
         * Name : 大兴养殖户栏位001
         * RfidNo : TEST
         * SerialNo :
         * StockName : 大兴养殖户栏位001
         * UserName : 大兴区养殖户
         * r : 1
         */

        private Integer ID;
        private Integer StockID;
        private Integer OpDeptID;
        private Integer OpUserID;
        private String Remark;
        private Integer Num;
        private String OpTime;
        private String Name;
        private String RfidNo;
        private String SerialNo;
        private String StockName;
        private String UserName;
        private Integer r;

        public Integer getID() {
            return ID;
        }

        public void setID(Integer ID) {
            this.ID = ID;
        }

        public Integer getStockID() {
            return StockID;
        }

        public void setStockID(Integer StockID) {
            this.StockID = StockID;
        }

        public Integer getOpDeptID() {
            return OpDeptID;
        }

        public void setOpDeptID(Integer OpDeptID) {
            this.OpDeptID = OpDeptID;
        }

        public Integer getOpUserID() {
            return OpUserID;
        }

        public void setOpUserID(Integer OpUserID) {
            this.OpUserID = OpUserID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public Integer getNum() {
            return Num;
        }

        public void setNum(Integer Num) {
            this.Num = Num;
        }

        public String getOpTime() {
            return OpTime;
        }

        public void setOpTime(String OpTime) {
            this.OpTime = OpTime;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getRfidNo() {
            return RfidNo;
        }

        public void setRfidNo(String RfidNo) {
            this.RfidNo = RfidNo;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String SerialNo) {
            this.SerialNo = SerialNo;
        }

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String StockName) {
            this.StockName = StockName;
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
