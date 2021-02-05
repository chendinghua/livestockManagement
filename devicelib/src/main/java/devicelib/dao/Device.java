package devicelib.dao;

import android.view.KeyEvent;

/**
 * Created by 16486 on 2020/4/8.
 */

public interface Device {
    /**
     * 初始化RFID模块
     */
    public void initUHF();
    //设置声音模块
    /**
     * 播放提示音
     *
     * @param id 成功1，失败2
     */
    public void playSound(int id);

    /***
     * 按键触发
     * @param keyCode 按键值
     * @param event 按键对象
     * @param status 扫描类型 1、RFID 2、条码
     * @param isSingle 表示是否读取单个数据  true 只触发一次  false 读取多条数据
     */
    public void onKeyDown(int keyCode, KeyEvent event,int status,boolean isSingle);

    public void destroy();


    public  String onResume() ;

    public  void onPause();
    //设置功率
    public boolean setPower(int power);
    //设置扫描条码状态
    public void setSanModelEnabel(boolean flag);
    //当前读取状态
    boolean isLoop();
}
