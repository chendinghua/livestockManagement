package com.kymjs.app.base_res.utils.base.distribute.entry;

import com.kymjs.app.base_res.utils.base.BaseEntry;

/**
 * Created by 16486 on 2020/10/24.
 */

public class ProductInfo extends BaseEntry {


    /**
     * ProductName : 猪
     * ProductType : 1
     * Origin : 山东
     * Period : 50
     * DescInfo : 商品猪
     * Weight : 125
     * Size : 200*10*20
     * Remark : 测试数据
     * Status : 1
     * OpTime : 2020-10-19T18:34:53.443
     * OpUser : 系统管理员
     * r : 1
     */

    private String ProductName;
    private Integer ProductType;
    private String Origin;
    private Integer Period;
    private String DescInfo;
    private String Weight;
    private String Size;
    private String Remark;
    private Integer Status;
    private String OpTime;
    private String OpUser;

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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public Integer getProductType() {
        return ProductType;
    }

    public void setProductType(Integer ProductType) {
        this.ProductType = ProductType;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public Integer getPeriod() {
        return Period;
    }

    public void setPeriod(Integer Period) {
        this.Period = Period;
    }

    public String getDescInfo() {
        return DescInfo;
    }

    public void setDescInfo(String DescInfo) {
        this.DescInfo = DescInfo;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
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
}
