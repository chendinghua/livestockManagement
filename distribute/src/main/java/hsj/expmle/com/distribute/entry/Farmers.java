package hsj.expmle.com.distribute.entry;

import com.kymjs.app.base_res.utils.base.BaseEntry;

/** 养殖户信息
 * Created by 16486 on 2020/10/24.
 */

public class Farmers  extends BaseEntry{


    /**
     * Phone : TEST
     * Email : TEST
     * ProductType : TEST
     * OpTime : 2020-10-16T16:42:32.64
     * OpUser : TEST用户
     * Status : 1
     * AdressProvince : 1000018
     * AdressCity : 1001969
     * AdressCounty : 1001974
     * AddressInfo : 详细地址
     */

    private String Phone;               //手机号
    private String Email;               //邮箱
    private String ProductType;
    private String OpTime;              //创建时间
    private String OpUser;              //创建人
    private Integer Status;             //状态
    private String AdressProvince;
    private String AdressCity;
    private String AdressCounty;
    private String AddressInfo;          //详情信息



     private String AdressProvinceName; //省级
    private String AdressCityName;      //市级
    private String AdressCountyName;    //区级


   /* public Integer getId(){
        return  ID;
    }
    public String getName(){
        return Name;

    }*/
   public Integer getID() {
       return ID;
   }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdressProvinceName() {
        return AdressProvinceName;
    }

    public void setAdressProvinceName(String adressProvinceName) {
        AdressProvinceName = adressProvinceName;
    }

    public String getAdressCityName() {
        return AdressCityName;
    }

    public void setAdressCityName(String adressCityName) {
        AdressCityName = adressCityName;
    }

    public String getAdressCountyName() {
        return AdressCountyName;
    }

    public void setAdressCountyName(String adressCountyName) {
        AdressCountyName = adressCountyName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public String getOpTime() {
        return OpTime;
    }

    public void setOpTime(String OpTime) {
        this.OpTime = OpTime;
    }

    public String getOpUser() {
        return OpUser;
    }

    public void setOpUser(String OpUser) {
        this.OpUser = OpUser;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getAdressProvince() {
        return AdressProvince;
    }

    public void setAdressProvince(String AdressProvince) {
        this.AdressProvince = AdressProvince;
    }

    public String getAdressCity() {
        return AdressCity;
    }

    public void setAdressCity(String AdressCity) {
        this.AdressCity = AdressCity;
    }

    public String getAdressCounty() {
        return AdressCounty;
    }

    public void setAdressCounty(String AdressCounty) {
        this.AdressCounty = AdressCounty;
    }

    public String getAddressInfo() {
        return AddressInfo;
    }

    public void setAddressInfo(String AddressInfo) {
        this.AddressInfo = AddressInfo;
    }
}
