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
    //库存查询接口
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
    //查询疫苗详情列表
    public static final String POSTVACCINELISTS="/Vaccine/PostVaccineLists";
    //耳标销毁
    public static final String POSTSCRAPSTORAGE="/Storage/PostScrapStorage";
    //提交出库信息
    public static final String POSTPDAOUTSTOCK="/Animal/PostPDAOutStock";
    //查询任务列表
    public static final String GETTASKLIST="/task/GetTaskList";
    //根据任务详情ID查询详细任务内容
    public static final String GETTASKINFO="/task/GetTaskInfo";
    //确认耳标出栏
    public static final String POSTPDAFOROUTSTOCK="/Animal/PostPDAForOutStock";
    //疫苗接种提交
    public static final String VACCINEPOSTADD="/Vaccine/PostAdd";
    //提交屠宰数据
    public static final String POSTPDAINHOUSES="/Animal/PostPDAInHouses";
    //获取移栏列表
    public static final String GETMOVESTOCKLIST="/Basics/GetMoveStockLst";
    //移栏详情
    public static final String GETMOVESTOCKINFO="/Basics/GetMoveStockInfo";
    //根据库存ID和任务类型 综合查询
    public static final String GETRFIDINFORMATION="/Animal/GetRFIDInformation";
    //添加箱包信息
    public static final String POSTADDBOXPACKAGE="/Basics/PostAddBoxPackage";
    //查询箱包记录
    public static final String GETQELBOXPACKAGE="/Basics/GetQelBoxPackage";
    //获取地区信息
    public static final String GETREGINFO="/Basics/GetReginfo";
    //获取箱包详情记录
    public static final String GETBOXPACKAGEINFO="/Basics/GetBoxPackageInfo";
    //一次分发提交接口
    public static final String POSTADDDISTRIBUTEONE="/Animal/PostAddDistributeOne";
    //查询首页信息
    public static final String GETINDEX="/Basics/GetIndex";
    //根据箱号查询库存信息
    public static final String GETBOXINFO="/Basics/GetBoxInfo";
}
