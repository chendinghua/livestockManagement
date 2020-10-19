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
    //心跳监控接口
    public static final String HEARTBEAT="/common/heartbeat";
    //异常信息记录
    public static final String ADDERRORINFO="/common/AddErrorInfo";
    //获取字典信息
    public static final String GETDICBYGROUPNAME="/common/getdiclistbygroupname";

    //任务列表
    public static final String GETTASKLIST="/task/gettasklist";

    //根据rfid获取产品信息
    public static final String GETPRODUCTINFOBYRFIDNO="/storage/GetStorageInfoByRfidNo";

    //获取报废列表信息
    public static final String GETSCRAPLIST="/scrap/getscraplist";



    //报废信息详细
    public static final String GETSCRAPINFO="/scrap/getscrapinfo";

    //报废申请
    public static final String ADDSCRAPINFO = "/scrap/addscrapinfo";

    //编辑报废任务
    public static final String EDITSCRAPINFO="/scrap/EditScrapInfo";

    //取消报废申请
    public static final String UPDATESTATUS ="/scrap/updatestatus";

    //根据库存的RFID获取库存详细信息(包含库存的库位信息)
    public static final String GETSTORAGEINFOBYRFIDNO="/Storage/GetStorageInfoByRfidNo";

    //根据库存的JOB号获取库存详细信息
    public static final String GETSTORAGEINFOBYCODE="/storage/getstorageinfobycode";



    //获取盘点信息
    public static final String GETINVENTORYLIST = "/inventory/GetInventoryList";

    //记录盘点信息
    public static final String ADDINVENTORYINFO ="/inventory/addinventoryinfo";

    //获取包装列表的信息
    public static final String GETPACKAGELIST="/pack/GetPackageList";
    public static final String COMMITCHECK="/check/commitcheck";


    //入库单号
    public static final String GETORDERNO="/Instock/GetOrderNo";

    //获取供应商信息
    public static final String GETSUPPLIERLIST="/User/GetSupplierList_LName";
    //根据序列号查询
    public static final String GETSTORAGEINFOBYSERIALNO="/Storage/GetStorageInfoBySerialNo";
    //出库提交接口
    public static final String POSADDTOUTSTOCK="/Storage/PosAddtOutStock";
    //根据类型查询产品名
    public static final String PDASELECTPRODUCTLIST="/ProduceInfo/PDASelectProductList";
    //综合查询
    public static final String POSTSTORAGEINFOLIST="/Storage/PostStorageInfoList";
    //获取用户信息集合
    public static final String GETUSERLIST="/User/GetUserList";
    //根据产品名称查询PN信息
    public static final String PDASELECTPRODUCTLISTBYNAME="/ProduceInfo/PDASelectProductListByName";
    //根据PN查询规格等信息
    public static final String PDASELECTPRODUCTLISYBYPN="/ProduceInfo/PDASelectProductListByPN";

    //添加采购清单
    public static final String POSTADDINSTOCK="/Storage/PosAddtInStock";

    public static final String PDAQELLNSTOCKINFOLIST="/Storage/PDAQelInStockInfoList";
}
