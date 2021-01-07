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
         * TaskDetailID : 46
         * ProjectName : 猪
         * StorageID : 31
         * RfidNo : A20201208111324000000002
         * SerialNo : SERA20201208111324000002
         * opDeptID : 江苏省养殖户
         * StorageName : 畜种耳标分发
         * TaskTypeName : 畜种耳标分发
         * operater : PDA操作员
         * StatusName : 在库
         */

        private Integer TaskDetailID;
        private String ProjectName;
        private Integer StorageID;
        private String RfidNo;
        private String SerialNo;
        private String opDeptID;
        private String StorageName;
        private String TaskTypeName;
        private String operater;
        private String StatusName;

        public Integer getTaskDetailID() {
            return TaskDetailID;
        }

        public void setTaskDetailID(Integer TaskDetailID) {
            this.TaskDetailID = TaskDetailID;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public Integer getStorageID() {
            return StorageID;
        }

        public void setStorageID(Integer StorageID) {
            this.StorageID = StorageID;
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

        public String getOpDeptID() {
            return opDeptID;
        }

        public void setOpDeptID(String opDeptID) {
            this.opDeptID = opDeptID;
        }

        public String getStorageName() {
            return StorageName;
        }

        public void setStorageName(String StorageName) {
            this.StorageName = StorageName;
        }

        public String getTaskTypeName() {
            return TaskTypeName;
        }

        public void setTaskTypeName(String TaskTypeName) {
            this.TaskTypeName = TaskTypeName;
        }

        public String getOperater() {
            return operater;
        }

        public void setOperater(String operater) {
            this.operater = operater;
        }

        public String getStatusName() {
            return StatusName;
        }

        public void setStatusName(String StatusName) {
            this.StatusName = StatusName;
        }
    }
}
