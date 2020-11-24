package com.kymjs.app.base_res.utils.base.entry.vaccine;

/**
 * Created by 16486 on 2020/11/21.
 */

public class VaccineInfo {


    /**
     * StorageCount : 3
     * VaccineID : 1
     * VaccineName : 甲型H1N1灭活性疫苗
     * ProductID : 1
     * ProductName : 猪
     * r : 1
     */

    private Integer StorageCount;
    private Integer VaccineID;
    private String VaccineName;
    private Integer ProductID;
    private String ProductName;
    private Integer r;

    public Integer getStorageCount() {
        return StorageCount;
    }

    public void setStorageCount(Integer StorageCount) {
        this.StorageCount = StorageCount;
    }

    public Integer getVaccineID() {
        return VaccineID;
    }

    public void setVaccineID(Integer VaccineID) {
        this.VaccineID = VaccineID;
    }

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String VaccineName) {
        this.VaccineName = VaccineName;
    }

    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }
}
