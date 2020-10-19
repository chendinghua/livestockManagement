package hsj.expmle.com.entry;

/**
 * Created by Administrator on 2019/3/6/006.
 */
//字典信息
public class DicInfo {

    public int ID;                      //id
    public String Name;                 //显示内容值
    public String Value;                //
    public String GroupName;            //组名
    public int State;                   //状态

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
