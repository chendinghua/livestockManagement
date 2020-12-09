package hsj.outStock.com.entry;

import com.kymjs.app.base_res.utils.base.entry.TaskInfo.TaskInventoryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16486 on 2020/12/7.
 */

public class OutStockCarList {


    /**
     * Car : 这是车牌信息2
     * OPERName : 客户姓名2
     * OPERTelphone : 13548332387
     */

    private String Car;
    private String OPERName;
    private String OPERTelphone;


    private boolean isShowAdd=true;

    public boolean isShowAdd() {
        return isShowAdd;
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    private List<TaskInventoryInfo.TaskDetailInfo> Info = new ArrayList<>();

    public List<TaskInventoryInfo.TaskDetailInfo> getInfo() {
        return Info;
    }

    public void setInfo(List<TaskInventoryInfo.TaskDetailInfo> info) {
        Info.clear();
        Info.addAll(info);
    }

    public String getCar() {
        return Car;
    }

    public void setCar(String Car) {
        this.Car = Car;
    }

    public String getOPERName() {
        return OPERName;
    }

    public void setOPERName(String OPERName) {
        this.OPERName = OPERName;
    }

    public String getOPERTelphone() {
        return OPERTelphone;
    }

    public void setOPERTelphone(String OPERTelphone) {
        this.OPERTelphone = OPERTelphone;
    }

    @Override
    public String toString() {
        return "OutStockCarList{" +
                "Car='" + Car + '\'' +
                ", OPERName='" + OPERName + '\'' +
                ", OPERTelphone='" + OPERTelphone + '\'' +
                ", Info=" + Info +
                '}';
    }
}
