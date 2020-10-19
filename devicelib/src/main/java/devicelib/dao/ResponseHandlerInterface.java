package devicelib.dao;


/**
 * Created by 16486 on 2020/4/9.
 */

public interface ResponseHandlerInterface {

    //返回RFID数据
    void handleTagdata(String rfid);
    //返回当前扫描状态
    void handleTriggerPress(boolean pressed);
    //void handleStatusEvents(Events.StatusEventData eventData);

    void scanCode(String code);
}
