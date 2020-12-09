package com.kymjs.app.base_res.utils.base.entry.TaskInfo;

import java.util.List;

/**
 * Created by 16486 on 2020/12/6.
 */

public class TaskInventoryInfo extends Task{

    List<TaskDetailInfo> TaskDetailList;

    public List<TaskDetailInfo> getTaskDetailList() {
        return TaskDetailList;
    }

    public void setTaskDetailList(List<TaskDetailInfo> taskDetailList) {
        TaskDetailList = taskDetailList;
    }
    public TaskDetailInfo getTaskDetailInfo(){

        return new TaskDetailInfo();
    }


    public class TaskDetailInfo {


        /**
         * TaskDetailInfoID : 5
         * TaskInfoID : 11
         * operater : 大兴区养殖户
         * ProductName : 猪
         * Type : 畜种
         * TaskType : 耳标入栏
         * IsEnabled : 启用
         * Status : 在库
         * RfidNo : A20201203093441000000001
         * SerialNo : SERA20201203093441000001
         * Remark : null
         * DeptName : 大兴区养殖户
         */

        private Integer TaskDetailInfoID;
        private Integer StorageID;
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

        private String IsFocus;

        public String getIsFocus() {
            return IsFocus;
        }

        public void setIsFocus(String isFocus) {
            IsFocus = isFocus;
        }

        public Integer getTaskDetailInfoID() {
            return TaskDetailInfoID;
        }

        public void setTaskDetailInfoID(Integer TaskDetailInfoID) {
            this.TaskDetailInfoID = TaskDetailInfoID;
        }

        public Integer getStorageID() {
            return StorageID;
        }

        public void setStorageID(Integer storageID) {
            StorageID = storageID;
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

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getTaskType() {
            return TaskType;
        }

        public void setTaskType(String TaskType) {
            this.TaskType = TaskType;
        }

        public String getIsEnabled() {
            return IsEnabled;
        }

        public void setIsEnabled(String IsEnabled) {
            this.IsEnabled = IsEnabled;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }
    }
}
