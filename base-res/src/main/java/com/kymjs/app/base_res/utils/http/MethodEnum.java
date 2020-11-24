package com.kymjs.app.base_res.utils.http;

/**
 * Created by Administrator on 2019/1/5/005.
 */

public class MethodEnum {
    //登录路径
    public static String LOGINSIGNIN="/login/signin";
    //退出路径
    public static String LOGINSIGNOUT="/login/signout";
    //修改密码
    public static String RESETPWD="/user/resetpwd";
    //获取字典信息
    public static final String GETDICBYGROUPNAME="/common/getdiclistbygroupname";
    //获取养殖场信息
    public static final String FARMERSALLLISTBYID="/Basics/DeptAllListById";
    //获取畜种信息
    public static final String PRODCTINFO="/Product/ProductInfo";
    //校验库位RFID数据是否有效
    public static final String GETISSTOCKBYRFID="/Animal/GetIsStockByRFID";
    //栏位新增and修改
    public static final String POSTADDSTOCK="/Animal/PostAddStock";
    //根据条件查询 部门库存
    public static final String GETSTORAGEINFOBYDEPTID="/Storage/GetStorageinfoByDeptID";
    //获取栏位信息 可以根据RFID 或者序列号
    public static final String GETSTOCKINFOBYDEPTID="/Animal/GetStockInfoByDeptID";
    //部门出库检查
    public static final String GETSTORAGEINFOBYOUT="/Storage/GetStorageinfoByOut";
    //提交分发耳标（栏位，畜种）
    public static final String POSTADDDISTRIBUTE="/Animal/PostAddDistribute";
    //添加入栏数据
    public static final String POSTPDAINSTOCK="/Animal/PostPDAInStock";
    //添加移栏数据
    public static final String POSTMOVESTOCK="/Animal/PostMoveStock";
    //查询ID就诊记录列表
    public static final String GETMEDICALILLNESSLIST="/MedicalRecords/GetMedicalIllnessList";
    //查询当前栏位列表
    public static final String GETSTOCKINFOBYDEPT="/Animal/GetStockInfoByDept";
    //查询疾病信息
    public static final String GETMEDICALRLIST="/MedicalRecords/GetMedicalRList";
    //提交就诊信息
    public static final String POSTMEDICAL="/MedicalRecords/PostMedical";
    //查询疫苗列表
    public static final String POSTVACCINELIST="/Vaccine/PostVaccineList";
}
