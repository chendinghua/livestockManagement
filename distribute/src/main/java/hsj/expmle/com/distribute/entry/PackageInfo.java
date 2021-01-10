package hsj.expmle.com.distribute.entry;

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

    public static   class PackageInfoItems {

        /**
         * PID : 3
         * Id : 1
         * Code : P2020123100001
         * CreateDate : 2020-12-31T16:29:11.01
         * TypeName : 生成包号
         * TYPE : 1
         * Lot : null
         * Num : 2
         * DeptNum : 1
         * UserName : 系统管理员
         * OpDeptID : 9
         * CreateDeptID : 1
         * r : 1
         */
        private Integer PID;
        private Integer Id;
        private String Code;
        private String CreateDate;
        private String TypeName;
        private Integer TYPE;
        private Object Lot;
        private Integer Num;
        private Integer DeptNum;
        private String UserName;
        private Integer OpDeptID;
        private Integer CreateDeptID;
        private Integer r;

        private String IsFocus;


        private String BoxCode;

        public String getBoxCode() {
            return BoxCode;
        }

        public void setBoxCode(String boxCode) {
            BoxCode = boxCode;
        }

        public String getIsFocus() {
            return IsFocus;
        }

        public void setIsFocus(String isFocus) {
            IsFocus = isFocus;
        }

        public Integer getPID() {
            return PID;
        }

        public void setPID(Integer PID) {
            this.PID = PID;
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

        public Object getLot() {
            return Lot;
        }

        public void setLot(Object Lot) {
            this.Lot = Lot;
        }

        public Integer getNum() {
            return Num;
        }

        public void setNum(Integer Num) {
            this.Num = Num;
        }

        public Integer getDeptNum() {
            return DeptNum;
        }

        public void setDeptNum(Integer DeptNum) {
            this.DeptNum = DeptNum;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public Integer getOpDeptID() {
            return OpDeptID;
        }

        public void setOpDeptID(Integer OpDeptID) {
            this.OpDeptID = OpDeptID;
        }

        public Integer getCreateDeptID() {
            return CreateDeptID;
        }

        public void setCreateDeptID(Integer CreateDeptID) {
            this.CreateDeptID = CreateDeptID;
        }

        public Integer getR() {
            return r;
        }

        public void setR(Integer r) {
            this.r = r;
        }
    }
}
