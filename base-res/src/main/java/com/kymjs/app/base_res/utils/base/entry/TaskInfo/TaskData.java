package com.kymjs.app.base_res.utils.base.entry.TaskInfo;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;

import java.util.List;

/** 任务列表信息实体类
 * Created by 16486 on 2020/12/5.
 */
public class TaskData extends PageInfo {


    List<TaskDataList> Result;

    public List<TaskDataList> getResult() {
        return Result;
    }

    public void setResult(List<TaskDataList> result) {
        Result = result;
    }

    /**
     * ID : 1
     * TaskType : 3
     * TaskTypeName : 耳标入栏
     * Content : 
     * CreatorTime : 2020-12-05T14:38:58.187
     * Status : 1
     * StatusName : 在库
     * LotNumber : 
     * DeptName : 畜牧局总局
     * UserName : PDA操作员
     * Remark : 
     * r : 1
     */
   public class TaskDataList {

        /**
         * ID : 1
         * TaskType : 2
         * TaskTypeName : 畜种耳标分发
         * Content : 
         * CreatorTime : 2020-12-05T14:38:58.187
         * Status : 1
         * StatusName : 已处理
         * LotNumber : 
         * CretaeDeptName : 畜牧局总局
         * OpDeptName : 大兴区养殖户
         * UserName : PDA操作员
         * Num : 2
         * Remark : 
         * r : 1
         */

        private Integer ID;
        private Integer TaskType;
        private String TaskTypeName;
        private String Content;
        private String CreatorTime;
        private Integer Status;
        private String StatusName;
        private String LotNumber;
        private String CretaeDeptName;
        private String OpDeptName;
        private String UserName;
        private Integer Num;
        private String Remark;
        private Integer r;

        public Integer getID() {
            return ID;
        }

        public void setID(Integer ID) {
            this.ID = ID;
        }

        public Integer getTaskType() {
            return TaskType;
        }

        public void setTaskType(Integer TaskType) {
            this.TaskType = TaskType;
        }

        public String getTaskTypeName() {
            return TaskTypeName;
        }

        public void setTaskTypeName(String TaskTypeName) {
            this.TaskTypeName = TaskTypeName;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getCreatorTime() {
            return CreatorTime;
        }

        public void setCreatorTime(String CreatorTime) {
            this.CreatorTime = CreatorTime;
        }

        public Integer getStatus() {
            return Status;
        }

        public void setStatus(Integer Status) {
            this.Status = Status;
        }

        public String getStatusName() {
            return StatusName;
        }

        public void setStatusName(String StatusName) {
            this.StatusName = StatusName;
        }

        public String getLotNumber() {
            return LotNumber;
        }

        public void setLotNumber(String LotNumber) {
            this.LotNumber = LotNumber;
        }

        public String getCretaeDeptName() {
            return CretaeDeptName;
        }

        public void setCretaeDeptName(String CretaeDeptName) {
            this.CretaeDeptName = CretaeDeptName;
        }

        public String getOpDeptName() {
            return OpDeptName;
        }

        public void setOpDeptName(String OpDeptName) {
            this.OpDeptName = OpDeptName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public Integer getNum() {
            return Num;
        }

        public void setNum(Integer Num) {
            this.Num = Num;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public Integer getR() {
            return r;
        }

        public void setR(Integer r) {
            this.r = r;
        }
    }
}
