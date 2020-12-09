package com.kymjs.app.base_res.utils.base.entry.TaskInfo;


import java.util.List;

/** 任务详情列表
 * Created by 16486 on 2020/12/5.
 */
public class TaskInfo extends Task{
    /**
     * ID : 1
     * DataID : 0
     * TaskType : 3
     * TaskTypeName : 耳标入栏
     * Content : 
     * CreatorID : 3
     * CreatorName : null
     * CreatorTime : 2020-12-05T14:38:58.187
     * Status : 1
     * StatusName : 在库
     * Remark : 
     * OpUser : null
     * OpTime : null
     * Num : 2
     */


    List<TaskDetailInfo> TaskDetailList;

    public List<TaskDetailInfo> getTaskDetailList() {
        return TaskDetailList;
    }

    public void setTaskDetailList(List<TaskDetailInfo> taskDetailList) {
        TaskDetailList = taskDetailList;
    }


    public class TaskDetailInfo {


        /**
         * TaskDetailInfoID : 1
         * TaskInfoID : 11
         * operater : PDA操作员
         * ProductName : 猪
         * Type : 畜种
         * TaskType : 畜种耳标分发
         * IsEnabled : 未激活
         * Status : 在库
         * RfidNo : A20201203093441000000001
         * SerialNo : SERA20201203093441000001
         * Remark : null
         * DeptName : 大兴区养殖户
         */

        private Integer TaskDetailInfoID;
        private Integer TaskInfoID;
        private String operater;
        private String ProductName;
        private String Type;
        private String TaskType;
        private String IsEnabled;
        private String Status;
        private String RfidNo;
        private String SerialNo;
        private String Remark;
        private String DeptName;

        public Integer getTaskDetailInfoID() {
            return TaskDetailInfoID;
        }

        public void setTaskDetailInfoID(Integer taskDetailInfoID) {
            TaskDetailInfoID = taskDetailInfoID;
        }

        public Integer getTaskInfoID() {
            return TaskInfoID;
        }

        public void setTaskInfoID(Integer taskInfoID) {
            TaskInfoID = taskInfoID;
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

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
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
    }
}
