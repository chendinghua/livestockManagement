package hsj.slaughter.com.home.entry;

import com.kymjs.app.base_res.utils.base.entry.TaskInfo.Task;

import java.util.List;

/**
 * Created by Administrator on 2019/2/27/027.
 */
//权限信息
public class SlaughterInfo  extends Task {


    List<SlaughterInfoList>  TaskDetailList;

    public List<SlaughterInfoList> getTaskDetailList() {
        return TaskDetailList;
    }

    public void setTaskDetailList(List<SlaughterInfoList> taskDetailList) {
        TaskDetailList = taskDetailList;
    }

    public class SlaughterInfoList {

        /**
         * TaskDetailInfoID : 123
         * StorageID : 104
         * RfidNo : A20201209143628000000002
         * SerialNo : SERA20201209143628000002
         * opDeptID : 朝阳区屠宰场
         * operater : 大兴区养殖户
         * ProductName : null
         * Type : 畜种
         * TaskType : 耳标出栏
         * IsEnabled : 启用
         * Remark : null
         * DeptName : 大兴区养殖户
         * CarNum : null
         */

        private Integer TaskDetailInfoID;
        private Integer StorageID;
        private String RfidNo;
        private String SerialNo;
        private String opDeptID;
        private String operater;
        private String ProductName;
        private String Type;
        private String TaskType;
        private String IsEnabled;
        private String Remark;
        private String DeptName;
        private String CarNum;

        private String IsFocus="false";

        public String getIsFocus() {
            return IsFocus;
        }

        public void setIsFocus(String isFocus) {
            IsFocus = isFocus;
        }

        public Integer getTaskDetailInfoID() {
            return TaskDetailInfoID;
        }

        public void setTaskDetailInfoID(Integer taskDetailInfoID) {
            TaskDetailInfoID = taskDetailInfoID;
        }

        public Integer getStorageID() {
            return StorageID;
        }

        public void setStorageID(Integer storageID) {
            StorageID = storageID;
        }

        public String getRfidNo() {
            return RfidNo;
        }

        public void setRfidNo(String rfidNo) {
            RfidNo = rfidNo;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String serialNo) {
            SerialNo = serialNo;
        }

        public String getOpDeptID() {
            return opDeptID;
        }

        public void setOpDeptID(String opDeptID) {
            this.opDeptID = opDeptID;
        }

        public String getOperater() {
            return operater;
        }

        public void setOperater(String operater) {
            this.operater = operater;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getTaskType() {
            return TaskType;
        }

        public void setTaskType(String taskType) {
            TaskType = taskType;
        }

        public String getIsEnabled() {
            return IsEnabled;
        }

        public void setIsEnabled(String isEnabled) {
            IsEnabled = isEnabled;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String deptName) {
            DeptName = deptName;
        }

        public String getCarNum() {
            return CarNum;
        }

        public void setCarNum(String carNum) {
            CarNum = carNum;
        }
    }
}
